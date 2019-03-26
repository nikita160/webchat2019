package frontend.servlets;


import model.accountservice.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserToContactServlet extends HttpServlet{

    private AccountService accountService;

    public AddUserToContactServlet(AccountService accountService){

        this.accountService = accountService;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUserName = (String)req.getSession().getAttribute("user");
        String contactToAddLogin = req.getParameter("add");
        System.out.println(currentUserName+":::"+contactToAddLogin);
        if (currentUserName!=null){
            try {
                int res  = accountService.addContactToUserContactList(currentUserName, contactToAddLogin);
                System.out.println("TRARARA:"+res);
                if (res>0) resp.setStatus(HttpServletResponse.SC_OK);
                else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            catch (Exception e){
                System.err.println("*Add user exception*");
            }

        }
        else resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
