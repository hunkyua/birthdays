package servlets.filter;

import dao.UserDAO;
import model.ROLE;
import model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;
import static model.ROLE.UNKNOWN;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        @SuppressWarnings("unchecked") final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");
        UserDAO userDAO = dao.get();
        User user = userDAO.getUserByLoginPassword(login,password);
        boolean isUserExist = dao.get().isUserExist(login, password);

        final HttpSession session = req.getSession();

        //Logged user.
        if (nonNull(session) && session.getAttribute("login") != null && (session.getAttribute("password") != null )) {
            final ROLE role = (ROLE) session.getAttribute("role");
            moveToMenu(req, res, role);
        } else if (isUserExist) {
            final ROLE role = userDAO.getUserRoleByLoginPassword(login, password);
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            req.getSession().setAttribute("userID", userDAO.getUserId(user));

            moveToMenu(req, res, role);
        } else {
            if (login != null && password != null) {
                req.setAttribute("Error", "Account doesn't exist");
            }
           moveToMenu(req, res, UNKNOWN);
        }
    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final ROLE role) throws ServletException, IOException {
        switch (role) {
            case ADMIN:
                req.getRequestDispatcher("/admin_menu.jsp").forward(req, res);
                break;
            case USER:
                req.getRequestDispatcher("/user_menu.jsp").forward(req, res);
                break;
            default:
                req.getRequestDispatcher("/birthdays.jsp").forward(req, res);
        }
    }


    @Override
    public void destroy() {
    }

}