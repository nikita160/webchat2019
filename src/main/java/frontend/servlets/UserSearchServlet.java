package frontend.servlets;

import service.parsing.xml.jaxb.JAXBHandler;
import model.accountservice.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserSearchServlet extends HttpServlet {

    private AccountService accountService;

    public UserSearchServlet(AccountService accountService){
        this.accountService = accountService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("s");
        System.out.println(query);
        String currentUserName = (String)req.getSession().getAttribute("user");
        try {
            List<String> result = accountService.searchUser(query, currentUserName);


            PrintWriter pw = resp.getWriter();
            resp.setContentType("application/xml");
            if ((result != null) && (query != null)) {
                String resXML = JAXBHandler.getXMLFromList(result);
                //  System.out.println(resXML);
                if (resXML != null) {
                    pw.write(resXML);
                }
            } else pw.write("");
        }
        catch (Exception e){
            System.err.println("*Search exception*");
        }


    }
}
