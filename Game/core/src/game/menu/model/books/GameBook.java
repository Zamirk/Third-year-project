package game.menu.model.books;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import game.database.AccessDB;
import game.menu.model.objects.Game;


public class GameBook {
    AccessDB myDatabaseConnection;
    ResultSet resultSet = null;
    ArrayList<Game> gamesList;
    int rows;

    public GameBook(AccessDB a){
        myDatabaseConnection = a;
    }

    public void refreshListLevel(){
        try {
            resultSet = myDatabaseConnection.getGamesLevel();
            ArrayList<Game> gamesList = new ArrayList<Game>();
            rows = 0;

            if(resultSet!=null){            //ResultSet did get data
                while (resultSet.next()) {
                    rows++;
                    Game tempGame = new Game(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(3)),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            Integer.parseInt(resultSet.getString(8)),
                            resultSet.getString(9));

                    gamesList.add(tempGame);
                }
            } else{                 //If no data has been received, place stub data
                gamesList.add(new Game("0000", "01,01,16", 0, "Red", 0, "Normal", "Player123", 0, "0"));
                rows++;
            }

            setGamesList(gamesList);
            myDatabaseConnection.closeConnection();                     //Ends Connection

        }catch (SQLException e ) {
            System.out.println("SQL Error 12323");
        }
    }

    public void refreshListShip(){
        try {
            resultSet = myDatabaseConnection.getGamesShip();
            ArrayList<Game> gamesList = new ArrayList<Game>();
            rows = 0;

            if(resultSet!=null){            //ResultSet did get data
                while (resultSet.next()) {
                    rows++;
                    Game tempGame = new Game(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(3)),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            Integer.parseInt(resultSet.getString(8)),
                            resultSet.getString(9));

                    gamesList.add(tempGame);
                }
            } else{                 //If no data has been received, place stub data
                gamesList.add(new Game("0000", "01,01,16", 0, "Red", 0, "Normal", "Player123", 0, "0"));
                rows++;
            }

            setGamesList(gamesList);
            myDatabaseConnection.closeConnection();                     //Ends Connection

        }catch (SQLException e ) {
            System.out.println("SQL Error 12323");
        }
    }

    public void refreshListDif(){
        try {
            resultSet = myDatabaseConnection.getGamesDif();
            ArrayList<Game> gamesList = new ArrayList<Game>();
            rows = 0;

            if(resultSet!=null){            //ResultSet did get data
                while (resultSet.next()) {
                    rows++;
                    Game tempGame = new Game(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(3)),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            Integer.parseInt(resultSet.getString(8)),
                            resultSet.getString(9));

                    gamesList.add(tempGame);
                }
            } else{                 //If no data has been received, place stub data
                gamesList.add(new Game("0000", "01,01,16", 0, "Red", 0, "Normal", "Player123", 0, "0"));
                rows++;
            }

            setGamesList(gamesList);
            myDatabaseConnection.closeConnection();                     //Ends Connection

        }catch (SQLException e ) {
            System.out.println("SQL Error 12323");
        }
    }
    public void refreshListRank(){
        try {
            resultSet = myDatabaseConnection.getGamesRankDec();
            ArrayList<Game> gamesList = new ArrayList<Game>();
            rows = 0;

            if(resultSet!=null){            //ResultSet did get data
                while (resultSet.next()) {
                    rows++;
                    Game tempGame = new Game(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(3)),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            Integer.parseInt(resultSet.getString(8)),
                            resultSet.getString(9));

                    gamesList.add(tempGame);
                }
            } else{                 //If no data has been received, place stub data
                gamesList.add(new Game("0000", "01,01,16", 0, "Red", 0, "Normal", "Player123", 0, "0"));
                rows++;
            }

            setGamesList(gamesList);
            myDatabaseConnection.closeConnection();                     //Ends Connection

        }catch (SQLException e ) {
            System.out.println("SQL Error 12323");
        }
    }

    public void refreshListNames(){
        try {
            resultSet = myDatabaseConnection.getGamesNames();
            ArrayList<Game> gamesList = new ArrayList<Game>();
            rows = 0;

            if(resultSet!=null){            //ResultSet did get data
                while (resultSet.next()) {
                    rows++;
                    Game tempGame = new Game(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Integer.parseInt(resultSet.getString(3)),
                            resultSet.getString(4),
                            Integer.parseInt(resultSet.getString(5)),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            Integer.parseInt(resultSet.getString(8)),
                            resultSet.getString(9));

                    gamesList.add(tempGame);
                }
            } else{                 //If no data has been received, place stub data
                gamesList.add(new Game("0000", "01,01,16", 0, "Red", 0, "Normal", "Player123", 0, "0"));
                rows++;
            }

            setGamesList(gamesList);
            myDatabaseConnection.closeConnection();                     //Ends Connection

        }catch (SQLException e ) {
            System.out.println("SQL Error 12323");
        }
    }
    public void refreshList(){
        try {
            resultSet = myDatabaseConnection.getGames();
        ArrayList<Game> gamesList = new ArrayList<Game>();
        rows = 0;

            if(resultSet!=null){            //ResultSet did get data
        while (resultSet.next()) {
            rows++;
            Game tempGame = new Game(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3)),
                    resultSet.getString(4),
                    Integer.parseInt(resultSet.getString(5)),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    Integer.parseInt(resultSet.getString(8)),
                    resultSet.getString(9));

            gamesList.add(tempGame);
        }
            } else{                 //If no data has been received, place stub data
                gamesList.add(new Game("0000", "01,01,16", 0, "Red", 0, "Normal", "Player123", 0, "0"));
                rows++;
            }

            setGamesList(gamesList);
        myDatabaseConnection.closeConnection();                     //Ends Connection

        }catch (SQLException e ) {
            System.out.println("SQL Error 12323");
        }
        }

    public ArrayList<Game> getGamesList() {
        return gamesList;
    }

    public void setGamesList(ArrayList<Game> gamesList) {
        this.gamesList = gamesList;
    }

    public int getRows() {
        return rows;
    }
}
