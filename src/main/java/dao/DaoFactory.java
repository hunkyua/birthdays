package dao;

import domain.Person;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class DaoFactory {
    private static final String URL = "jdbc:mariadb://91.200.232.73:3306/birthdays";
    private static final String USER = "birthdaysuser";
    private static final String PASS = "birthdayspass";

    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    /**
     * Get a connection to database
     *
     * @return Connection object
     */
    public Connection getConnection() {
        try {
            LOGGER.info("Try connect to database");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connection to the database", ex);
        }
    }

    public static DaoFactory getInstance() {
        return new DaoFactory();
    }

    public static void main(String[] args) throws DAOException {
//        DB db = new DB();
//        db.dropDB();
//        db.prepareDB();

        UserCRUD userCRUD = new UserCRUD();
        User user = new User("test2", "test");
        //userCRUD.createUser(user);
        user.setUserID(userCRUD.getUserID(user));

        Person person = new Person("Petr", "Petrov", "Petrov@gmail.com", "1990-08-16", user.getUserID());
        CRUD crud = new CRUD();
        crud.createPerson(person);
    }

    public static void printPersons(List<Person> personList) {
        //System.out.println("PersonID   Name   Surname   Email   DateOfBirth");
        personList.forEach(p -> System.out.println(
                "PersonID : " + p.getPersonID() + " | " +
                        "Name : " + p.getName() + " | " +
                        "Surname : " + p.getSurname() + " | " +
                        "Email : " + p.getEmail() + " | " +
                        "DateOfBirth : " + p.getDateOfBirth()
        ));
    }

    protected void closePrepareStatement(PreparedStatement ps) {
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

    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Result set was closed");
            } catch (SQLException e) {
                LOGGER.error("Result set wasn't closed", e);
                e.printStackTrace();
            }
        }
    }
}

