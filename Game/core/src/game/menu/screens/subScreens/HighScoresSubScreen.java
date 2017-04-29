package game.menu.screens.subScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.sql.SQLException;
import java.util.ArrayList;

import game.database.AccessDB;
import game.menu.model.books.GameBook;
import game.menu.model.objects.Game;
import game.menu.screens.utils.AbstractSubScreen;
import game.util.FontUtils;
import game.util.config.Config;


public class HighScoresSubScreen extends AbstractSubScreen implements Config{
    VisTable table = new VisTable();
    Label.LabelStyle myStyle3;
    Label.LabelStyle myStyle;

    public HighScoresSubScreen() {
        VisImageTextButton.VisImageTextButtonStyle squareGreenRed = new VisImageTextButton.VisImageTextButtonStyle();
        squareGreenRed.up = new Image(new Texture(Gdx.files.internal("img/button/button1.png"))).getDrawable();

        BitmapFont myFontGreena = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
        squareGreenRed.font = myFontGreena;
        squareGreenRed.checkedFontColor = Color.WHITE;
        squareGreenRed.checkedOverFontColor = Color.FOREST;
        squareGreenRed.downFontColor = Color.FIREBRICK;

        if(zoom) {
            setPosition(1920, -30);
        } else {
            setPosition(1920, 100);
        }
        BitmapFont myFontGreen = FontUtils.generateFont("font/square.ttf", 35, Color.FIREBRICK);
        myStyle3 = new Label.LabelStyle();
        myStyle3.font = myFontGreen;

        //Title
        VisLabel title = new VisLabel("High Scores");
        title.setSize(200, 80);
        title.setPosition(538, 750);
        title.setStyle(myStyle3);

        addActor(title);

        //Table for high scores
        addActor(table);
        table.setSize(1275, 600);
        table.setPosition(0, 150);
        table.align(Align.top);
        //table.debug();                                              //Colors boundaries

        refreshTable();
        backToStart.setSize(200, 70);
        backToStart.setPosition(400, 20);

        final VisTextButton refresh = new VisTextButton("Refresh");
        refresh.setSize(200, 70);
        refresh.setPosition(650, 20);
        addActor(refresh);
        refresh.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                refreshTable();
                refresh.setChecked(true);
            }
        });
        refresh.setStyle(squareGreenRed);
        backToStart.setStyle(squareGreenRed);
        refresh.setFocusBorderEnabled(false);
        backToStart.setFocusBorderEnabled(false);
        refresh.setChecked(true);

        VisLabel scorel;
        VisLabel difl;
        VisLabel shipl;
        VisLabel levell;


    }
    boolean temp = true;
    GameBook myBook;
    AccessDB myDatabaseConnection;
    boolean dRank = false;
    public void refreshTable(){
        BitmapFont myFontBlack = FontUtils.generateFont("font/square.ttf", 42, Color.BLACK);
        myStyle = new Label.LabelStyle();
        myStyle.font = myFontBlack;

        BitmapFont myFontWhite = FontUtils.generateFont("font/square.ttf", 35, Color.WHITE);
        Label.LabelStyle myStyle2 = new Label.LabelStyle();
        myStyle2.font = myFontWhite;

        if(temp) {
            myDatabaseConnection = new AccessDB();       //Creates Connection
            myBook = new GameBook(myDatabaseConnection);
            myBook.refreshList();
        }
        ArrayList<Game> gamesList = myBook.getGamesList();
        int rows = myBook.getRows();
        table.clear();

        rankl = new VisLabel("Rank");
        namel = new VisLabel("Name");
        scorel = new VisLabel("Score");
        difl = new VisLabel("Difficulty");
        shipl = new VisLabel("Ship");
        levell = new VisLabel("Level");

        namel.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                temp = false;
                myDatabaseConnection = new AccessDB();       //Creates Connection
                myBook = new GameBook(myDatabaseConnection);
                myBook.refreshListNames();
                refreshTable();
                myDatabaseConnection.closeConnection();                     //Ends Connection
                temp = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        rankl.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                temp = false;
                dRank = true;
                myDatabaseConnection = new AccessDB();       //Creates Connection
                myBook = new GameBook(myDatabaseConnection);
                myBook.refreshListRank();
                refreshTable();
                myDatabaseConnection.closeConnection();                     //Ends Connection
                temp = true;
                dRank = false;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        scorel.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                refreshTable();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        difl.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                temp = false;
                myDatabaseConnection = new AccessDB();       //Creates Connection
                myBook = new GameBook(myDatabaseConnection);
                myBook.refreshListDif();
                refreshTable();
                myDatabaseConnection.closeConnection();                     //Ends Connection
                temp = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        shipl.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                temp = false;
                myDatabaseConnection = new AccessDB();       //Creates Connection
                myBook = new GameBook(myDatabaseConnection);
                myBook.refreshListShip();
                refreshTable();
                myDatabaseConnection.closeConnection();                     //Ends Connection
                temp = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        levell.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                temp = false;
                myDatabaseConnection = new AccessDB();       //Creates Connection
                myBook = new GameBook(myDatabaseConnection);
                myBook.refreshListLevel();
                refreshTable();
                myDatabaseConnection.closeConnection();                     //Ends Connection
                temp = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        rankl.setColor(Color.BLACK);
        namel.setColor(Color.BLACK);
        scorel.setColor(Color.BLACK);
        difl.setColor(Color.BLACK);
        shipl.setColor(Color.BLACK);
        levell.setColor(Color.BLACK);

        rankl.setStyle(myStyle);
        namel.setStyle(myStyle);
        scorel.setStyle(myStyle);
        difl.setStyle(myStyle);
        shipl.setStyle(myStyle);
        levell.setStyle(myStyle);

        table.add(rankl).center().expandX().top();
        table.add(namel).center().expandX().top();
        table.add(scorel).center().expandX().top();
        table.add(difl).center().expandX().top();
        table.add(shipl).center().expandX().top();
        table.add(levell).center().expandX().top();
        String rank;

        for(int i=0;i<rows;i++) {
            if(!dRank) {
                rank = "" + (i + 1);
            } else {
                rank = "" + (rows - i);
            }
            table.row();

            a = new VisLabel(""+rank);
            b = new VisLabel(""+gamesList.get(i).getAnonName());
            c = new VisLabel(""+gamesList.get(i).getScore());
            d = new VisLabel(gamesList.get(i).getDifficulty());
            e = new VisLabel(gamesList.get(i).getShipType());
            f = new VisLabel(""+gamesList.get(i).getLevel());

            a.setStyle(myStyle);
            b.setStyle(myStyle2);
            c.setStyle(myStyle2);
            d.setStyle(myStyle2);
            e.setStyle(myStyle2);
            f.setStyle(myStyle2);

            table.add(a);
            table.add(b);
            table.add(c);
            table.add(d);
            table.add(e);
            table.add(f);
        }
        if(temp) {
            myDatabaseConnection.closeConnection();                     //Ends Connection
        }
    }
    VisLabel a;
    VisLabel b;
    VisLabel c;
    VisLabel d;
    VisLabel e;
    VisLabel f;

    VisLabel rankl;
    VisLabel namel;
    VisLabel scorel;
    VisLabel difl;
    VisLabel shipl;
    VisLabel levell;
}