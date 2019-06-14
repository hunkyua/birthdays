package servlets.servlet;

import dao.UserDAO;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static model.ROLE.USER;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String login = request.getParameter("login");
        String password = request.getParameter("login");
        login = login == null ? "" : login.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        password = password == null ? "" : password.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        User user = new User(login, password, USER);

        boolean isUserExist = user.isUserExist(user);

        if (isUserExist) {
            out.append("<h2 style=\"text-align:center;color:red\">Sorry Login is already exist!</h2>");
            RequestDispatcher rd = request.getRequestDispatcher("/registration.jsp");
            rd.include(request, response);
            rd.forward(request, response);
        } else {
            userDAO.createUser(user);
            request.getRequestDispatcher("/birthdays.jsp").forward(request, response);
        }

    }

    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}
