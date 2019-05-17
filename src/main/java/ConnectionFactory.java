import java.sql.*;

public class ConnectionFactory {
    public static final String URL = "jdbc:postgresql://localhost:5432/birthdaysdb";
    public static final String USER = "birthdaysuser";
    public static final String PASS = "birthdayspass";

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
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM persons"
        );

        ps.execute();
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
//    INSERT INTO persons(person_id, name, surname, email, dateOfBirth) VALUES (" +
//        "'1', 'Griwa', 'Griwkovec', 'griwa@gmail.com', '12-03-1990')