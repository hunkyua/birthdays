package ua.hunky.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component("db")
public class DB {

    private final String CREATE_PERSONS = "CREATE TABLE birthdays.persons (" +
            "PersonID SERIAL PRIMARY KEY, " +
            "Name VARCHAR (30) NOT NULL, " +
            "Surname VARCHAR (30) NOT NULL, " +
            "Email VARCHAR (50), " +
            "DateOfBirth DATE NOT NULL," +
            "UserID INT (20) NOT NULL) ";

    private final String CREATE_USERS = "CREATE TABLE users (\n" +
            " UserID SERIAL PRIMARY KEY,\n" +
            " Login VARCHAR (50) NOT NULL UNIQUE ,\n" +
            " Password VARCHAR (50) NOT NULL ,\n" +
            " Role INT (15) NOT NULL)";

    private final String DROP_PERSONS = "DROP TABLE persons";
    private final String DROP_USERS = "DROP TABLE users";

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    public void prepareDB() {
        createPersons();
        createUsers();
    }

    public void dropDB() {
        dropPersons();
        dropUsers();
    }

    private void createPersons() {
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

    private void createUsers() {
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
