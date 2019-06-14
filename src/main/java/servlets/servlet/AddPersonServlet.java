package servlets.servlet;

import dao.PersonDAO;
import model.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddPersonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PersonDAO personDAO = new PersonDAO();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String dateOfBirthday = req.getParameter("dateOfBirthday");
        name = name == null ? "" : name.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        surname = surname == null ? "" : surname.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        Date date = null;
        Calendar validateDateBefore = new GregorianCalendar(1920,0,1);

        if(name == null || surname == null || email == null || dateOfBirthday == null) {
            req.getRequestDispatcher("/addPerson.jsp").forward(req, res);
            return;
        }

        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(dateOfBirthday.replace("-", "/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null && (date.before(validateDateBefore.getTime()) || date.after(Calendar.getInstance().getTime()))) {
            req.setAttribute("Error", "Wrong data inside field \"Date of birth\"");
            req.getRequestDispatcher("/addPerson.jsp").forward(req, res);
            return;
        }

        int userID = Integer.valueOf(req.getSession().getAttribute("userID").toString());
        Person person = new Person(name, surname, email, dateOfBirthday, userID);
        personDAO.addPerson(person);

        req.getRequestDispatcher("/addPerson.jsp").forward(req, res);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}
