package Server;
import Core.Colours;
import Server.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDao {
    public Connection getConnection() throws DAOException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/oop_repeat_2021_stephen_harding";
        String username = "root";
        String password = "";
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException cnfe) {
            System.out.println(Colours.RED + "Class was not found " + cnfe.getMessage() + Colours.RESET);
            System.exit(1);
        } catch (SQLException se) {
            System.out.println(Colours.RED + "Failed to get connection " + se.getMessage() + Colours.RESET);
            System.exit(2);
        }
        System.out.println(Colours.GREEN + "Connection was successful" + Colours.RESET);
        return connection;
    }
    public void freeConnection(Connection connection) throws DAOException
    {
        try
        {
            if(connection != null)
            {
                connection.close();
                connection = null;
            }
        }
        catch(SQLException se)
        {
            System.out.println(Colours.RED + "Failed to free the connection " + se.getMessage() + Colours.RESET);
            System.exit(1);
        }
    }
}
