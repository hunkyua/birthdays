package servlets.servlet;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

import static model.ROLE.*;

@WebListener
public class ContextListener implements ServletContextListener {

    private AtomicReference<UserDAO> dao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dao = new AtomicReference<>(new UserDAO());
       // dao.get().createUser(new User("admin", "admin", ADMIN ));
       // dao.get().createUser(new User("user", "user", USER ));

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("dao", dao);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao = null;
    }
}
