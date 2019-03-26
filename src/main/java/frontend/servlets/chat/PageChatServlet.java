package frontend.servlets.chat;

import frontend.template.PageGenerator;
import model.accountservice.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.UUID;

public class PageChatServlet extends HttpServlet {

    public static final String PAGE_URL = "/webchat2019";

    private final AccountService accountService;

    private final String PAGE_NAME = "webchat.html";

    private static final String NO_CONTACTS_MESSAGE = "You haven't any contacts yet!";
    private static final String ANONYMOUS = "You have to login to talk with a specific user!";


    public PageChatServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isAnonymous = true;
        HttpSession session = req.getSession();
        String userLogin = (String) session.getAttribute("user");
        if (userLogin == null) {
            userLogin = "N/A_" + session.getId().substring(0, 4);
        } else {
            isAnonymous = false;
        }

        String token = UUID.randomUUID().toString();
        try {
            accountService.createUserToken(userLogin, token);
            Cookie cookie = new Cookie("token", token);
            System.out.println(token);
            cookie.setMaxAge(60 * 10);
            Map<String, Object> data = new HashMap<>();
            data.put("username", userLogin);
            data.put("isAnonymous", isAnonymous);
            if (!isAnonymous) data.put("users", accountService.getMainPageDataSet(userLogin).getContactList());

            resp.setContentType("text/html;charset=utf-8");
            resp.addCookie(cookie);
            resp.getWriter().println(PageGenerator.instance().getPage(PAGE_NAME, data));
        }
        catch (Exception e) {
            System.err.println("*Set token exception*");
            e.printStackTrace();
        }

    }


}
