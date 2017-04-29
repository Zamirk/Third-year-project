package game.database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB extends ConnectDB {

    public CreateDB(){
        createConnection();
        System.out.print("1. Dropping Tables.\n");
        dropTables();
        System.out.print("2. Dropped.\n");
        System.out.print("3. Creating Tables.\n");
        createTables();
        System.out.print("4. Created.\n");
        System.out.print("5. Inserting Data.\n");
        insertData();
        System.out.print("6. Inserted.\n");
        System.out.print("7. Closing Connection.\n");
        closeConnection();
        System.out.print("8. Closed.\n");

    }
    public void createTables(){
        try {
            statement = connection.createStatement();
            String testCreate = "CREATE TABLE Game(" +
                    "GameID Varchar(255) NOT NULL," +
                    "Date Varchar(255)," +
                    "Score Integer, ShipType Varchar(255)," +
                    "EnemiesDestroyed Integer," +
                    "Difficulty Varchar(255)," +
                    "AnonName Varchar(255)," +
                    "Level Varchar(255)," +
                    "PlayerId Varchar(255)," +
                    "PRIMARY KEY (GameID)) ";

            statement.executeUpdate(testCreate);
        }catch (SQLException e ) {
            System.out.println("Tables could not create.");
        }
    }
    public void insertData(){
        try {
            statement = connection.createStatement();
            String testInsert1 = "INSERT INTO Game(GameID, Date, Score, ShipType, EnemiesDestroyed, Difficulty, Level, AnonName, PlayerId)VALUES('GameID2', 'Date', 100, 'Red', 10, 'Easy', 1 , 'Gerry', '') ";
            String testInsert2 = "INSERT INTO Game(GameID, Date, Score, ShipType, EnemiesDestroyed, Difficulty, Level, AnonName, PlayerId)VALUES('GameID3', 'Date', 400, 'Green', 5, 'Easy', 5 , 'Tom', '') ";
            String testInsert3 = "INSERT INTO Game(GameID, Date, Score, ShipType, EnemiesDestroyed, Difficulty, Level, AnonName, PlayerId)VALUES('GameID4', 'Date', 350, 'Blue', 2, 'Hard', 5 , 'Player123', '') ";
            String testInsert4 = "INSERT INTO Game(GameID, Date, Score, ShipType, EnemiesDestroyed, Difficulty, Level, AnonName, PlayerId)VALUES('GameID5', 'Date', 200, 'Red', 8, 'Medium', 10 , 'Zamir', '') ";

            statement.executeUpdate(testInsert1);
            statement.executeUpdate(testInsert2);
            statement.executeUpdate(testInsert3);
            statement.executeUpdate(testInsert4);
        }catch (SQLException e ) {
            System.out.println("Data could not insert.");
        }
    }
    public void dropTables(){
        try {
            Statement state=connection.createStatement();
            String testCreate = " drop table Game;";
            state.execute(testCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CreateDB createDB = new CreateDB();
    }
}
