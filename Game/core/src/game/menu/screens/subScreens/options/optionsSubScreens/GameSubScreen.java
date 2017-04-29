package game.menu.screens.subScreens.options.optionsSubScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.Constants;
import game.menu.screens.utils.AbstractOptionsSubScreen;
import game.util.FontUtils;


public class GameSubScreen extends AbstractOptionsSubScreen implements Constants {
    public GameSubScreen() {

        VisImageTextButton.VisImageTextButtonStyle squareGreenRed = new VisImageTextButton.VisImageTextButtonStyle();
        BitmapFont myFontGreena = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
        squareGreenRed.font = myFontGreena;
        squareGreenRed.checkedFontColor = Color.WHITE;
        squareGreenRed.checkedOverFontColor = Color.FOREST;
        squareGreenRed.downFontColor = Color.FIREBRICK;

        retro = new VisTextButton("Retro Mode is off");
        retro.setSize(300,300);
        retro.setPosition(300, 300);
        retro.setStyle(squareGreenRed);
        retro.setFocusBorderEnabled(false);
        addActor(retro);

        retro.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(retroGame){
                    retroGame = false;
                    retro.setText("Retro Mode is off");
                } else if(!retroGame){
                    retroGame = true;
                    retro.setText("Retro Mode is on");
                }
            }
        });
    }

    public boolean isRetroGame() {
        return retroGame;
    }

    VisTextButton retro;
    boolean retroGame = false;
    public VisTextButton getRetro() {
        return retro;
    }
}
