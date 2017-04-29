package game.database;

import java.sql.*;
import java.util.Random;

import game.menu.screens.fullScreens.Difficulty;

public class AccessDB extends ConnectDB {

    public AccessDB(){
        createConnection();
    }
    public ResultSet getGames(){
        try {
            String s37 = "Select * From Game order by Score desc";
            statement = connection.createStatement();
            resultSet= statement.executeQuery(s37);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
        return resultSet;
    }
    public ResultSet getGamesLevel(){
        try {
            String s37 = "Select * From Game order by Level";
            statement = connection.createStatement();
            resultSet= statement.executeQuery(s37);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
        return resultSet;
    }
    public ResultSet getGamesShip(){
        try {
            String s37 = "Select * From Game order by ShipType";
            statement = connection.createStatement();
            resultSet= statement.executeQuery(s37);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
        return resultSet;
    }
    public ResultSet getGamesDif(){
        try {
            String s37 = "Select * From Game order by Difficulty";
            statement = connection.createStatement();
            resultSet= statement.executeQuery(s37);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
        return resultSet;
    }
    public ResultSet getGamesNames(){
        try {
            String s37 = "Select * From Game order by AnonName";
            statement = connection.createStatement();
            resultSet= statement.executeQuery(s37);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
        return resultSet;
    }
    public ResultSet getGamesRankDec(){
        try {
            String s37 = "Select * From Game order by Score";
            statement = connection.createStatement();
            resultSet= statement.executeQuery(s37);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
        return resultSet;
    }
    public void insert(int score, String name, Difficulty difficulty){
        try {
            Random generator = new Random();
            int i = generator.nextInt(100000) + 1;
            String diff = "";
            if(difficulty == Difficulty.EASY){
                diff = "Easy";
            } else if(difficulty == Difficulty.MEIDUM){
                diff = "Medium";
            } else if(difficulty == Difficulty.HARD){
                diff = "Hard";
            }
            String testInsert1 = "INSERT INTO Game(GameID, Date, Score, ShipType, EnemiesDestroyed, Difficulty, Level, AnonName, PlayerId)VALUES('GameID"+i+"', 'Date',"+score+", 'Red', 10, '"+diff+"', 1 , '"+name+"', '') ";
            statement = connection.createStatement();
            statement.executeUpdate(testInsert1);
        }catch (SQLException e ) {
            System.out.print("\nAccess not possible. Error 1234a");
        }catch (java.lang.NullPointerException ex) {
            System.out.print("Null Pointer Error. Error 1425b");
        }
    }

}
