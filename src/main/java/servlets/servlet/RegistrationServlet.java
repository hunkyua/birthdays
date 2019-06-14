package servlets.servlet;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.ROLE.USER;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        response.setContentType("text/html");
        String login = request.getParameter("login");
        String password = request.getParameter("login");
        login = login == null ? "" : login.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        password = password == null ? "" : password.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        User user = userDAO.getUserByLoginPassword(login,password);

        boolean isUserExist = user.isUserExist(user);

        if (isUserExist) {
            request.setAttribute("Error", "Sorry Login is already exist!");
            request.getRequestDispatcher("/registration.jsp").forward(request, response);
        } else {
            if (login.isEmpty() && password.isEmpty()) {
                request.getRequestDispatcher("/registration.jsp").forward(request, response);
                return;
            }
            user.setLogin(login);
            user.setPassword(password);
            user.setRole(USER);
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
