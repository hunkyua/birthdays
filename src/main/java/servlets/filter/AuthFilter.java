package servlets.filter;

import model.ROLE;
import dao.UserDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static model.ROLE.*;
import static java.util.Objects.nonNull;

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

        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final HttpSession session = req.getSession();

        //Logged user.
        if (nonNull(session) && session.getAttribute("login") != null && session.getAttribute("password")  != null) {

            final ROLE role = (ROLE) session.getAttribute("role");

            moveToMenu(req, res, role);

        } else if (dao != null && dao.get().userIsExist(login, password)) {

            final ROLE role = dao.get().getRoleByLoginPassword(login, password);

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);

            moveToMenu(req, res, role);

        } else {
            moveToMenu(req, res, UNKNOWN);
        }
    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final ROLE role) throws ServletException, IOException {
        if (role.equals(ADMIN)) {
            req.getRequestDispatcher("/admin_menu.jsp").forward(req, res);
        } else if (role.equals(USER)) {
            req.getRequestDispatcher("/user_menu.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        }
    }


    @Override
    public void destroy() {
    }

}