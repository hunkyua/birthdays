package servlets.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/birthdays")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("lol");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (password.equals("test")) {
            RequestDispatcher rd = request.getRequestDispatcher("/birthday");
            rd.forward(request, response);
        } else {
            out.append("<h2 style=\"text-align:center;color:red\">Sorry Login or Password are Wrong!</h2>");
            RequestDispatcher rd = request.getRequestDispatcher("/birthdays.jsp");
            rd.include(request, response);
        }
    }
}
