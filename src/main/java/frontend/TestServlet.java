package frontend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=Windows-1251");

        PrintWriter out = resp.getWriter();

        // Отладочный вывод названия кодировки для проверки
        out.println("Encoding: " + resp.getCharacterEncoding());

        out.println("<!DOCTYPE html><html><head></head><body><h1>ККЛ!</h1></body></html>");
        out.close();

    }
}
