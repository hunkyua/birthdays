package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;


class UserCRUD {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    void createUser(String login, String password) {
        createTableForNewUser(login);
        setPrivilegesOnTableInDB(login, password);
    }

    private void createTableForNewUser(String login) {
        String sql = "CREATE TABLE " + login + " (person_id serial PRIMARY KEY, name VARCHAR (50) NOT NULL, " +
                "surname VARCHAR (50) NOT NULL, email VARCHAR (355), dateOfBirth DATE NOT NULL)";
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to create table four user : " + login);
            ps = connection.prepareStatement(sql);
            ps.execute();
            LOGGER.info("Table created");
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

    private void setPrivilegesOnTableInDB(String login, String password) {
        String sql = "GRANT ALL PRIVILEGES ON birthdays." + login + " TO '" + login + "'@'%' IDENTIFIED BY '" + password + "'";
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to setPrivileges for user : " + login);
            ps = connection.prepareStatement(sql);
            ps.execute();
            LOGGER.info("Privileges set");
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
}
