package game.game.gameHuds;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class LevelComplete implements Disposable{
    public Stage stage;
    private StretchViewport viewPort;

    private int worldTimer;
    private float timeCount;
    private static int score;
    BitmapFont hudFont;
    Label countdownLabel, levelLabel, worldLabel, marioLabel, timeLabel;
    static Label scoreLabel;

    public LevelComplete(SpriteBatch sb){
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
        scoreLabel =new Label(String.format("%06d", score), style);
        timeLabel = new Label("TIME", style);
        levelLabel = new Label("1-1", style);
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

    }
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }
    public void dispose(){
        stage.dispose();
    }
}
