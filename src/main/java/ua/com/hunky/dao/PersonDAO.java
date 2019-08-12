package ua.com.hunky.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.hunky.model.Person;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAO {

    private DaoFactory daoFactory;
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    @Autowired
    public PersonDAO (DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public boolean addPerson(Person person) {
        if (person.getUserID() == 0) {
            return false;
        }
        String sql = "INSERT INTO persons(Name, Surname, Email, DateOfBirth, UserID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to addPerson person : " + person.getName());
            int personID = Statement.RETURN_GENERATED_KEYS;
            ps = connection.prepareStatement(sql, personID);
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getEmail());
            ps.setDate(4, person.getDateOfBirth());
            ps.setLong(5, person.getUserID());
            ps.execute();
            LOGGER.info("Person was created");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
            return false;
        } finally {
            daoFactory.closePrepareStatement(ps);
        }

        return true;
    }

    public List<Person> getAllPersons() {
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
                        rs.getDate("DateOfBirth"),
                        rs.getLong("UserID")
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
