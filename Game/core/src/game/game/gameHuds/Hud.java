package game.game.gameHuds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;

import org.lwjgl.Sys;

import game.game.gameObjects.player.ShipState;
import game.util.FontUtils;


public class Hud implements Disposable{
    public Stage stage;
    VisTextButton playLevel2;
    private StretchViewport viewPort;
    private VisTextButton fin;
    private int worldTimer;
    private float timeCount;
    private int score;
    private BitmapFont hudFont;
    private Label countdownLabel, levelLabel, worldLabel, marioLabel, timeLabel;
    private Label scoreLabel;
    private Group gameOver;
    private boolean countdown = true;
    private VisTextButton finalScore;
    private VisTextButton playAgain;
    private VisTextButton submitHighScore;
    private VisTextButton menu;
    public Hud(SpriteBatch sb){
        VisImageTextButton.VisImageTextButtonStyle squareGreenRed = new VisImageTextButton.VisImageTextButtonStyle();
        squareGreenRed.up = new Image(new Texture(Gdx.files.internal("img/button/button1.png"))).getDrawable();

        BitmapFont myFontGreena = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
        squareGreenRed.font = myFontGreena;
        squareGreenRed.checkedFontColor = Color.WHITE;
        squareGreenRed.checkedOverFontColor = Color.FOREST;
        squareGreenRed.downFontColor = Color.FIREBRICK;

        worldTimer = 180;
        timeCount = 0;
        score = 0;

        viewPort = new StretchViewport(1920,1080, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        Table table = new Table();
        table.top();

        table.setFillParent(true);

        //Creating label
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE);
        hudFont = game.util.FontUtils.generateFont("font/square.ttf", 36, com.badlogic.gdx.graphics.Color.WHITE);
        style.font = hudFont;

        countdownLabel = new Label(String.format("%03d", worldTimer), style);
        scoreLabel = new Label(String.format("%06d", score), style);
        timeLabel = new Label("TIME", style);
        levelLabel = new Label("One", style);
        worldLabel = new Label("Level", style);
        marioLabel = new Label("Score", style);

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);

        gameOver = new Group();

        gameOver.setSize(400, 400);

        gameOver.setPosition(330, stage.getHeight() / 2 - gameOver.getHeight());

        Image bg1 = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_2.png")));                //Menu image

        bg1.setSize(1275, 830);

        gameOver.addActor(bg1);

        gameOver.setVisible(false);

        stage.addActor(gameOver);

        finalScore = new VisTextButton("");
        finalScore.setSize(400, 200);
        finalScore.setFocusBorderEnabled(false);
        finalScore.setPosition(100, 500);
        gameOver.addActor(finalScore);

        playAgain = new VisTextButton("Play Again");
        playAgain.setSize(400, 200);
        playAgain.setFocusBorderEnabled(false);
        playAgain.setPosition(200, 200);
        gameOver.addActor(playAgain);

        submitHighScore = new VisTextButton("Submit High Score");
        submitHighScore.setSize(400, 200);
        submitHighScore.setFocusBorderEnabled(false);
        submitHighScore.setPosition(700, 500);
        gameOver.addActor(submitHighScore);

        menu = new VisTextButton("Menu");
        menu.setSize(400, 200);
        menu.setFocusBorderEnabled(false);
        menu.setPosition(350, 0);
        gameOver.addActor(menu);

        menu.setChecked(true);
        finalScore.setChecked(true);
        playAgain.setChecked(true);
        submitHighScore.setChecked(true);

        fin = new VisTextButton("");
        fin.setSize(400, 200);
        fin.setFocusBorderEnabled(false);
        fin.setPosition(425, 500);

        playLevel2 = new VisTextButton("Click for Level 2");
        playLevel2.setSize(400, 200);
        playLevel2.setFocusBorderEnabled(false);
        playLevel2.setPosition(700, 200);
        gameOver.addActor(playLevel2);

        finalScore.setStyle(squareGreenRed);
        menu.setStyle(squareGreenRed);
        playAgain.setStyle(squareGreenRed);
        playLevel2.setStyle(squareGreenRed);
        submitHighScore.setStyle(squareGreenRed);
        playLevel2.setDisabled(true);

        myText = new VisTextField();
        myText.setText("Enter here");
        myText.setSize(200,50);
        myText.setPosition(500, 700);
        gameOver.addActor(myText);
    }
    VisTextField myText;

    public String getMyText() {
        return myText.getText();
    }

    public int getScore() {
        return score;
    }

    boolean isD(){
        return playLevel2.isDisabled();
    }
    public VisTextButton getPlayLevel2() {
        return playLevel2;
    }

    public VisTextButton getMenuButton() {
        return menu;
    }

    public void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
    public void showGameOver(){
        Gdx.input.setInputProcessor(stage);  //Stops old screen being clickable
        playLevel2.setDisabled(true);
        gameOver.setVisible(true);
        finalScore.setText(scoreLabel.getText().toString());
        countdown = false;
    }
    public void setAsLevel2(){
        levelLabel.setText("Two");
        worldTimer = 180;
        countdownLabel.setText(String.format("%03d", worldTimer));
    }
    public void setAsLevel3(){
        levelLabel.setText("Three");
        worldTimer = 180;
        countdownLabel.setText(String.format("%03d", worldTimer));
    }
    public void setAsLevel4(){
        levelLabel.setText("Four");
        worldTimer = 180;
        countdownLabel.setText(String.format("%03d", worldTimer));
    }
    public void disableNext(){
        playLevel2.setDisabled(true);
        playLevel2.setText("Congratulations!");
    }
    public void setPlayNextLevel2(){
        playLevel2.setDisabled(true);
        playLevel2.setText("Click for level 2");
    }
    public void setPlayNextLevel3(){
        playLevel2.setDisabled(true);
        playLevel2.setText("Click for level 3");
    }
    public void setPlayNextLevel4(){
        playLevel2.setDisabled(true);
        playLevel2.setText("Click for level 4");
    }

    public void startAgain(){
        submitHighScore.setChecked(true);
        gameOver.setVisible(false);
        finalScore.setText("");
        worldTimer = 180;
        score = 0;
        scoreLabel.setText(String.format("%06d", score));
        countdownLabel.setText(String.format("%03d", worldTimer));
        countdown = true;
    }
    public void showLevel2(){
        Gdx.input.setInputProcessor(stage);  //Stops old screen being clickable
        playLevel2.setDisabled(true);
        gameOver.setVisible(true);
        finalScore.setText(scoreLabel.getText().toString());
        fin.setText("" + score);
        showLev2 = true;
    }
    public void hideL2Hud(){
        showLev2 = false;
        gameOver.setVisible(false);
        playLevel2.setDisabled(true);
    }
    public void setEn(){
        playLevel2.setDisabled(false);
    }
    private boolean showLev2 = false;

    public boolean isShowLev2() {
        return showLev2;
    }

    public VisTextButton getPlayAgain() {
        return playAgain;
    }

    public VisTextButton getSubmitHighScore() {
        return submitHighScore;
    }

    public void update(float dt){
        timeCount += dt;
        if(countdown) {
            if (timeCount > 1) {
                worldTimer--;
                countdownLabel.setText(String.format("%03d", worldTimer));
                timeCount = 0;
            }
        }
    }
    public void dispose(){
        stage.dispose();
    }
}
