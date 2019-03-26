package frontend.servlets;

import dbservise.dao.dbentities.User;
import frontend.template.PageGenerator;
import model.accountservice.AccountService;
import model.accountservice.AuthDataSet;
import model.accountservice.UserAccountDataSet;
import model.accountservice.datasets.CreateUserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SignUpServlet extends HttpServlet {

    public static final String PAGE_URL = "/signup";

    private static final String ERROR_FIELD_NAME = "errorMessage";
    private final String registerPageName = "register.html";

    private final AccountService accountService;

    public SignUpServlet(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPageSingleField(registerPageName, ERROR_FIELD_NAME, ""));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();

        try{
            //AuthDataSet dataSet = accountService.(login);
            if (!accountService.isUserExist(login)) {
                try{
                    accountService.createNewUser(new CreateUserDataSet(login, password));
                }
                catch (Exception e){
                }
                HttpSession session=req.getSession();
                session.setAttribute("user", login);
                session.setAttribute("password", password);
                resp.sendRedirect("/webchat2019");
            }
            else {

                pw.println(PageGenerator.instance().getPageSingleField(registerPageName, ERROR_FIELD_NAME,
                        "UserAccountDataSet already exists!"));
            }
        }
        catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println("Exception while adding user");
            e.printStackTrace();
        }

    }
}
