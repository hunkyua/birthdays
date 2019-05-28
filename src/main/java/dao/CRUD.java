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

    public void createPerson(Person person) throws DAOException {
        if (person.getUserID() == 0) {
            return;
        }
        String sql = "INSERT INTO persons(Name, Surname, Email, DateOfBirth, UserID) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to create person");
            ps = connection.prepareStatement(sql);
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getEmail());
            ps.setDate(4, Date.valueOf(person.getDateOfBirth()));
            ps.setLong(5, person.getUserID());
            ps.execute();
            LOGGER.info("Person created");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
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
            daoFactory.closePrepareStatement(ps);
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
            daoFactory.closePrepareStatement(ps);
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
                        rs.getLong("PersonID"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getString("Email"),
                        rs.getString("DateOfBirth"),
                        rs.getInt("UserID")
                );
                persons.add(person);
            }
            LOGGER.info("All persons selected");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(rs);
            daoFactory.closePrepareStatement(ps);
        }
        return persons;
    }

}
