import java.math.BigInteger;
import java.sql.*;


public class CRUD {
    public static void createPerson(String name, String surname, String email, String dateOfBirth) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO persons(name, surname, email, dateOfBirth)" +
                    " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, email);
            ps.setDate(4, java.sql.Date.valueOf(dateOfBirth));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ps != null) {
            ps.execute();
        }
    }

    public static void deletePerson(BigInteger personId) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("DELETE FROM persons WHERE person_id =" + personId);
          } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ps != null) {
            ps.execute();
        }
    }


}
