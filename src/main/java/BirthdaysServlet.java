import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet("/birthdays")
public class BirthdaysServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("name", "Valentin");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        String name = request.getParameter("name");
        name = name == null? "" : name.replaceAll("<", "&lt;").replaceAll(">","&gt;");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        Integer day = 0;
        Integer month = 0;
        Integer year = 0;
        if (request.getParameter("day") != null && !request.getParameter("day").isEmpty()) {
             day = Integer.valueOf(request.getParameter("day"));
            if (day < 1 || day > 31) {
                request.setAttribute("Error", "Wrong data inside field day");
            }
        }
        if (request.getParameter("month") != null && !request.getParameter("month").isEmpty()) {
             month = Integer.valueOf(request.getParameter("month"));
            if (month < 1 || month > 12) {
                request.setAttribute("Error", "Wrong data inside field month");
            }
        }
        if (request.getParameter("year") != null && !request.getParameter("year").isEmpty()) {
            year = Integer.valueOf(request.getParameter("year"));
            if (year < 1920 || year > Calendar.getInstance().get(Calendar.YEAR)) {
                request.setAttribute("Error", "Wrong data inside field month");
            }

        }
        System.out.println(name);
        System.out.println(surname);
        System.out.println(email);
        System.out.println(day);
        System.out.println(month);
        System.out.println(year);


    }


    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}
