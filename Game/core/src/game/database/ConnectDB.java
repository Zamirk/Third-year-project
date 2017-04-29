package game.database;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    // Declare the JDBC objects.
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public void createConnection() {
        try {
            String connectionString =
                "jdbc:jtds:sqlserver://zamirLink.net:1433/zamirDB;"
                        + "USER;"
                        + "PASSWORD;";


            System.out.print("Test 1, Driver about to be used.\n");
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            //Class.forName("com.mysql.jdbc.Driver");
            System.out.print("Test 2, Driver Used.\n");

            connection = DriverManager.getConnection(connectionString);
            System.out.print("Test 3, Connection made.\n");
        }
        //catch (UnknownHostException e) {
        //    e.printStackTrace();
        //}
        catch (SQLException e) {
            System.out.print("\nThe Connection could not be made. Error 1234");
            //e.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("A ClassNotFoundException was caught. Error 1425");
            //ex.printStackTrace();
        }
    }
    public void closeConnection(){
        // Close the connections after the data has been handled.
        if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
        if (statement != null) try { statement.close(); } catch(Exception e) {}
        if (connection != null) try { connection.close(); } catch(Exception e) {}
    }
}
