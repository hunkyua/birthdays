package dao;

import domain.Person;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DaoFactory {
    private static final String URL = "jdbc:mariadb://192.168.1.60:3306/birthdays";
    private static final String USER = "birthdaysuser";
    private static final String PASS = "birthdayspass";

    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    /**
     * Get a connection to database
     * @return Connection object
     */
    public Connection getConnection(){
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
        UserCRUD userCRUD = new UserCRUD();
        Person person = new Person("Petr", "Petrov", "Petrov@gmail.com", "1990-08-16");
        User user = new User("test", "test");
        userCRUD.createUser(user.getLogin(), user.getPassword());
    }

    public static void printPersons(List<Person> personList) {
        //System.out.println("PersonID   Name   Surname   Email   DateOfBirth");
            personList.forEach(p -> System.out.println(
                    "PersonID : " + p.getId() + " | " +
                    "Name : " + p.getName() + " | " +
                    "Surname : " + p.getSurname() + " | " +
                    "Email : " + p.getEmail() + " | " +
                    "DateOfBirth : " + p.getDateOfBirth()
            ));
    }

}

//    CREATE TABLE persons(\n" +
//        " person_id serial PRIMARY KEY,\n" +
//        " name VARCHAR (50) NOT NULL,\n" +
//        " surname VARCHAR (50) NOT NULL,\n" +
//        " email VARCHAR (355) UNIQUE,\n" +
//        " dateOfBirth DATE NOT NULL\n" +
//        ");
//
