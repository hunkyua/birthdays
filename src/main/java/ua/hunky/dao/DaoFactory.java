package ua.hunky.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DaoFactory {
    private static final String URL = "jdbc:mariadb://91.200.232.73:3333/birthdays";
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

    static DaoFactory getInstance() {
        return new DaoFactory();
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

