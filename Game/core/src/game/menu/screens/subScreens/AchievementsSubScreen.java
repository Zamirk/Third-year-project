package game.menu.screens.subScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import game.menu.screens.utils.AbstractSubScreen;

public class AchievementsSubScreen extends AbstractSubScreen {
    public AchievementsSubScreen() {
        if(zoom) {
            setPosition(-1920, -30);
            exitAnimation.setPosition(-1920, -30);
        } else {
            setPosition(-1275, 100);
            exitAnimation.setPosition(-1275, 100);
        }

        Image achi = new Image(new Texture(Gdx.files.internal("img/achivements.png")));
        addActor(achi);
        achi.setSize(700, 300);
        achi.setPosition(275, 200);
        addActor(achi);
    }
}
