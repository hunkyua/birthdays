package dao;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

class UserCRUD {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class.getName());

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
