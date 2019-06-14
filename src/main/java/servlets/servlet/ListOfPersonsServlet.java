package servlets.servlet;

import dao.PersonDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hunky on 6/14/19.
 */
public class ListOfPersonsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAO();

        int userID = Integer.valueOf(request.getSession().getAttribute("userID").toString());

        request.setAttribute("persons", personDAO.getAllPersonsByUserId(userID));
        request.getRequestDispatcher("/listOfPersons.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
