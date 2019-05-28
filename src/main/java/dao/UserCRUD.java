package dao;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

class UserCRUD {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    void createUser(User user) {
        String sql = "INSERT INTO users(Login, Password) VALUES (?, ?)";
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to create user : " + user.getLogin());
            int userID = Statement.RETURN_GENERATED_KEYS;
            ps = connection.prepareStatement(sql, userID);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.execute();
            LOGGER.info("User was created");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
        }
    }

    int getUserID(User user) {
        String sql = "SELECT UserID FROM birthdays.users WHERE Login =" + "'" + user.getLogin() + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to get userID by " + user.getLogin());
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                LOGGER.info("UserID is " + rs.getInt("UserID"));
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(rs);
            daoFactory.closePrepareStatement(ps);
        }
        return 0;
    }

}
