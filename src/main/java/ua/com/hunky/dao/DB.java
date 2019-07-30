package ua.com.hunky.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ua.com.hunky.model.ROLE.*;

@Component("db")
public class DB {

    private final String CREATE_PERSONS = "CREATE TABLE birthdays.persons (" +
            "PersonID SERIAL PRIMARY KEY, " +
            "Name VARCHAR (30) NOT NULL, " +
            "Surname VARCHAR (30) NOT NULL, " +
            "Email VARCHAR (50), " +
            "DateOfBirth DATE NOT NULL," +
            "UserID INT (20) NOT NULL) CHARACTER SET utf8 COLLATE utf8_unicode_ci";

    private final String CREATE_USERS = "CREATE TABLE users (\n" +
            " UserID SERIAL PRIMARY KEY,\n" +
            " Login VARCHAR (50) NOT NULL UNIQUE ,\n" +
            " Password VARCHAR (50) NOT NULL ,\n" +
            " Role INT (15) NOT NULL) CHARACTER SET utf8 COLLATE utf8_unicode_ci";

    private final String DROP_PERSONS = "DROP TABLE persons";
    private final String DROP_USERS = "DROP TABLE users";

    private UserDAO userDAO;
    private PersonDAO personDAO;
    private DaoFactory daoFactory;
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    @Autowired
    DB (UserDAO userDAO, PersonDAO personDAO, DaoFactory daoFactory) {
        this.userDAO = userDAO;
        this.personDAO = personDAO;
        this.daoFactory = daoFactory;
    }

    public void prepareDB() {
        createTablePersons();
        createTableUsers();
        createDefaultUsers();
        createDefaultPersons();
    }

    private void createDefaultUsers() {
        userDAO.createUser(new User("admin", "admin", ADMIN));
        userDAO.createUser(new User("user", "user", USER));
        userDAO.createUser(new User("unknown", "unknown", UNKNOWN));
        userDAO.createUser(new User("userTest", "userTest", USER));
    }
    private void createDefaultPersons() {
        personDAO.addPerson(new Person(1L, "Petr", "Petrov", "Petrov@gmail.com", "1990-08-16", 1));
        personDAO.addPerson(new Person(1L, "Irina", "Petrovna", "Irina@gmail.com", "1980-08-16", 1));
        personDAO.addPerson(new Person(1L, "Test1", "Testovich1", "Test1@gmail.com", "1986-04-21", 4));
        personDAO.addPerson(new Person(2L, "Test2", "Testovich2", "Test2@gmail.com", "1976-02-11", 4));
    }

    public void dropDB() {
        dropPersons();
        dropUsers();
    }

    private void createTablePersons() {
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to addPerson table persons");
            ps = connection.prepareStatement(CREATE_PERSONS);
            ps.execute();
            LOGGER.info("Table \"persons\" was created");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
        }
    }

    private void createTableUsers() {
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to addPerson table users");
            ps = connection.prepareStatement(CREATE_USERS);
            ps.execute();
            LOGGER.info("Table \"users\" was created");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
        }
    }


    private void dropPersons() {
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to drop table persons");
            ps = connection.prepareStatement(DROP_PERSONS);
            ps.execute();
            LOGGER.info("Table \"persons\" was dropped");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
        }
    }

    private void dropUsers() {
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to drop table users");
            ps = connection.prepareStatement(DROP_USERS);
            ps.execute();
            LOGGER.info("Table \"users\" was dropped");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
        }
    }

}
