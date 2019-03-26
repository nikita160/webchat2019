package frontend.servlets;


import model.accountservice.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogOutServlet extends HttpServlet{

    private AccountService accountService;

    public LogOutServlet (AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userLogin = (String)session.getAttribute("user");
        String token = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie c: cookies){
            if (c.getName().equals("token")){
                token = c.getValue();
                break;
            }
        }
        try{
            if (token!=null) accountService.deleteToken(token);
        }
        catch (Exception e){
            System.err.println("Exception during logout when delete token");
        }
        session.invalidate();
        resp.sendRedirect("/webchat2019");
    }
}
