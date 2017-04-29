package game.menu.screens.subScreens.options.optionsSubScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.Constants;
import game.menu.screens.utils.AbstractOptionsSubScreen;
import game.util.FontUtils;


public class GraphicsSubScreen extends AbstractOptionsSubScreen implements Constants {
    public GraphicsSubScreen(){
        setPosition(0, 0);                                      //Initial Screen

        VisImageTextButton.VisImageTextButtonStyle squareGreenRed = new VisImageTextButton.VisImageTextButtonStyle();
        BitmapFont myFontGreena = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
        squareGreenRed.font = myFontGreena;
        squareGreenRed.checkedFontColor = Color.WHITE;
        squareGreenRed.checkedOverFontColor = Color.FOREST;
        squareGreenRed.downFontColor = Color.FIREBRICK;

        fullscreen = new VisTextButton("Fullscreen On/Off");
        fullscreen.setSize(300,300);
        fullscreen.setPosition(300, 300);
        fullscreen.setStyle(squareGreenRed);
        fullscreen.setStyle(new VisTextButton("").getStyle());

        addActor(fullscreen);

        fullscreen.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setWindowedMode(1920, 1080);
            }
        });
    }
    TextButton fullscreen;

    public TextButton getFullscreen() {
        return fullscreen;
    }
}
