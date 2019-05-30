package dao;

import model.ROLE;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

    private final List<User> store = new ArrayList<>();

    public User getUserById(int userID) {
        User result = new User();
        result.setUserID(-1);

        String sql = "SELECT * FROM birthdays.users WHERE userID =" + "'" + userID + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to get user by userID : " + userID);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                result.setLogin(rs.getString("Login"));
                result.setPassword(rs.getString("Password"));
                result.setUserID(rs.getInt("UserID"));
                LOGGER.info("User login is " + result.getLogin());
            }
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(rs);
            daoFactory.closePrepareStatement(ps);
        }

        return result;
    }

    public User getUserByLogin(final String login) {
        User result = new User();
        result.setUserID(-1);

        String sql = "SELECT * FROM birthdays.users WHERE Login =" + "'" + login + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to get user by login : " + login);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                result.setUserID(rs.getInt("UserID"));
                result.setLogin(rs.getString("Login"));
                result.setPassword(rs.getString("Password"));
                result.setRole(ROLE.getRoleByNumber(rs.getInt("Role")));
                LOGGER.info("User login is " + result.getLogin());
            }
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(rs);
            daoFactory.closePrepareStatement(ps);
        }

        return result;
    }

    public User getUserByLoginPassword(final String login, final String password) {
        User result = new User();
        result.setUserID(-1);

        String sql = "SELECT * FROM birthdays.users WHERE Login =" + "'" + login + "'" + " AND Password =" + "'" + password + "'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to get user by login : " + login + " and password : ***");
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                result.setUserID(rs.getInt("UserID"));
                result.setLogin(rs.getString("Login"));
                result.setPassword(rs.getString("Password"));
                result.setRole(ROLE.getRoleByNumber(rs.getInt("Role")));
            }
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(rs);
            daoFactory.closePrepareStatement(ps);
        }

        return result;
    }

    public boolean createUser(final User user) {
        User u = getUserByLogin(user.getLogin());
        if (u.getLogin() != null) {
            return false;
        }
        String sql = "INSERT INTO users(Login, Password, Role) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try (Connection connection = daoFactory.getConnection()) {
            LOGGER.info("Connect successful");
            LOGGER.info("Trying to create user : " + user.getLogin());
            int userID = Statement.RETURN_GENERATED_KEYS;
            ps = connection.prepareStatement(sql, userID);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole().ordinal());
            ps.execute();
            LOGGER.info("User was created");
        } catch (SQLException e) {
            LOGGER.error("Connect unsuccessfully");
            e.printStackTrace();
        } finally {
            daoFactory.closePrepareStatement(ps);
        }

        return true;
    }

    public ROLE getRoleByLoginPassword(final String login, final String password) {
        ROLE result = ROLE.UNKNOWN;
        User user = getUserByLoginPassword(login, password);
        if (user.getRole() != null) {
            result = user.getRole();
        }
        return result;
    }

    public boolean userIsExist(final String login, final String password) {
        User user = getUserByLoginPassword(login, password);

        if (user.getLogin() == null || user.getPassword() == null) {
            return false;
        }

        return user.getLogin().equals(login) && user.getPassword().equals(password);

    }

}
