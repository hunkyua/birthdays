package ua.hunky.servlets.servlet;

import ua.hunky.dao.DaoFactory;
import ua.hunky.dao.UserDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener {

    private AtomicReference<UserDAO> dao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DaoFactory daoFactory = new DaoFactory();
        dao = new AtomicReference<>(new UserDAO(daoFactory));
        //ua.hunky.dao.get().createUser(new User("admin", "admin", ADMIN ));
        //ua.hunky.dao.get().createUser(new User("user", "user", USER ));

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("ua/hunky/dao", dao);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao = null;
    }
}
