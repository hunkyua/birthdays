package ua.hunky.servlets.filter;

import ua.hunky.dao.UserDAO;
import ua.hunky.model.ROLE;
import ua.hunky.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;
import static ua.hunky.model.ROLE.UNKNOWN;

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

        @SuppressWarnings("unchecked") final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("ua/hunky/dao");
        UserDAO userDAO = dao.get();
        User user = userDAO.getUserByLoginPassword(login,password);
        boolean isUserExist = user.isUserExist(user);

        final HttpSession session = req.getSession();

        //Logged user.
        if (nonNull(session) &&
                (session.getAttribute("login") == login && session.getAttribute("login") != null) &&
                (session.getAttribute("password") == password && session.getAttribute("password") != null)) {
            final ROLE role = (ROLE) session.getAttribute("role");
            moveToMenu(req, res, role);
        } else if (isUserExist) {
            final ROLE role = user.getUserRole();
            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            req.getSession().setAttribute("userID", user.getUserID());

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