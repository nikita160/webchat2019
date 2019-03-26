package frontend.servlets;

import frontend.template.PageGenerator;
import model.accountservice.AccountService;
import model.accountservice.AuthDataSet;
import model.accountservice.UserAccountDataSet;
import model.authservice.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class AuthServlet extends HttpServlet {

    public static final String PAGE_URL = "/auth";

    private final AccountService accountService;
    private final AuthService authService;

    private String authPageName = "auth.html";
    private static Map<String, String> errorMessageMap = new HashMap<>();
    private static final String ERROR_FIELD_NAME = "errorMessage";

    //Error messages
    private static final String EMPTY_VALUE =  "emptyValue";
    private static final String WRONG_PASSWORD =  "wrongPassword";
    private static final String USER_NOT_EXISTS =  "userNotExists";

    public AuthServlet (AccountService accountService, AuthService authService){
        this.accountService = accountService;
        this.authService = authService;
        buildErrorMessageMap(errorMessageMap);
    }



    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPageSingleField(authPageName, ERROR_FIELD_NAME, ""));
    }


    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");
        //AuthDataSet dataSet = new AuthDataSet(login, password);

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();
        if (login.matches("\\s*")||password.matches("\\s*")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            pw.println(PageGenerator.instance().getPageSingleField(authPageName, ERROR_FIELD_NAME,
                    errorMessageMap.get(EMPTY_VALUE)));
        }



        else {
            try {
                int checkResult = accountService.verify(authService, new AuthDataSet(login,password));
                if (checkResult==0){
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    pw.println(PageGenerator.instance().getPageSingleField(authPageName, ERROR_FIELD_NAME,
                            errorMessageMap.get(USER_NOT_EXISTS)));
                }
                else {

                    if (checkResult<0) {
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        pw.println(PageGenerator.instance().getPageSingleField(authPageName, ERROR_FIELD_NAME,
                                errorMessageMap.get(WRONG_PASSWORD)));
                    } else {
                        resp.setStatus(HttpServletResponse.SC_OK);
                        req.getSession().setAttribute("user", login);
                        resp.sendRedirect("/webchat2019");
                    }
                }
            }
            catch (Exception e){
                System.err.println("*Exception during authoriztion*\r\n *when getting from database*");
                e.printStackTrace();
            }

        }

    }

    private static void buildErrorMessageMap (Map<String, String> map){
        map.put(EMPTY_VALUE, "Login or password can't be empty!");
        map.put(WRONG_PASSWORD, "Wrong password!");
        map.put(USER_NOT_EXISTS, "No user with this name!");

    }


}
