import java.sql.*;

public class ConnectionFactory {
    private static final String URL = "jdbc:mariadb://192.168.1.60:3306/birthdays";
    private static final String USER = "birthdaysuser";
    private static final String PASS = "birthdayspass";

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connection to the database", ex);
        }
    }

    /**
     * Test Connection
     */
    public static void main(String[] args) throws SQLException {
        Person person = new Person("Petr", "Petrov", "Petrov@gmail.com", "1990-08-16");
        CRUD.createPerson("Griwa", "Griwkovec", "griwa@gmail.com", "1990-08-16");
        //CRUD.deletePerson(BigInteger.valueOf(3));

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
