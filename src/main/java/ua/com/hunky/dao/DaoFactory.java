package ua.com.hunky.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DaoFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/birthdays";
    private static final String USER = "birthdaysuser";
    private static final String PASS = "birthdayspass";

    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    Connection getConnection() {
        try {
            LOGGER.info("Try connect to database");
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connection to the database", ex);
        }
    }

    void closePrepareStatement(PreparedStatement ps) {
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

    void closeResultSet(ResultSet rs) {
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

