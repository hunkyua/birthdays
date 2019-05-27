package dao;

import domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CRUD {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    public void createPerson(String name, String surname, String email, String dateOfBirth) throws DAOException {
        String sql = "INSERT INTO persons(name, surname, email, dateOfBirth) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to create person");
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setDate(4, Date.valueOf(dateOfBirth));
            ps.execute();
            LOGGER.info("Person created");
        } catch (SQLException e) {
                LOGGER.error("Connect unsuccessfully");
                e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    LOGGER.info("Prepare statement was closed");
                } catch (SQLException e) {
                    LOGGER.error("Prepare statement wasn't closed", e);
                    e.printStackTrace();
                }
            }
        }


    }

    public void deletePersonById(BigInteger personId) throws DAOException {
        String sql = "DELETE FROM persons WHERE person_id =" + personId;

        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to delete a person by ID = " + personId);
            ps = connection.prepareStatement(sql);
            ps.execute();
            LOGGER.info("Person deleted");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    LOGGER.info("Prepare statement was closed");
                } catch (SQLException e) {
                    LOGGER.error("Prepare statement wasn't closed", e);
                    e.printStackTrace();
                }
            }
        }
    }

    public void updatePersonById(BigInteger personId) throws DAOException {
        String sql = "UPDATE persons SET WHERE person_id = " + personId;

        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to update a person by ID = " + personId);
            ps = connection.prepareStatement(sql);
            ps.execute();
            LOGGER.info("Person updated");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    LOGGER.info("Prepare statement was closed");
                } catch (SQLException e) {
                    LOGGER.error("Prepare statement wasn't closed", e);
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Person> getAllPersons() throws DAOException {
        String sql = "SELECT * FROM persons";
        List<Person> persons = new ArrayList<>();

        Person person;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to select all persons");
            ps = connection.prepareStatement(sql);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
               person = new Person(
                       rs.getLong("person_id"),
                       rs.getString("name"),
                       rs.getString("surname"),
                       rs.getString("email"),
                       rs.getString("dateOfBirth")
                       );
               persons.add(person);
            }
            LOGGER.info("All persons selected");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    LOGGER.info("Result set was closed");
                } catch (SQLException e) {
                    LOGGER.error("Result set wasn't closed", e);
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                    LOGGER.info("Prepare statement was closed");
                } catch (SQLException e) {
                    LOGGER.error("Prepare statement wasn't closed", e);
                    e.printStackTrace();
                }
            }
        }
        return persons;
    }


}
