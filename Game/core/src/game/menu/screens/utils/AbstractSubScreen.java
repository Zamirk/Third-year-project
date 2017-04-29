package game.menu.screens.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.config.Config;
import game.util.Constants;

public class AbstractSubScreen extends Group implements Constants, Config {
    public AbstractSubScreen(){

        group = new Group();
        group.setSize(1275, 830);
        addActor(group);

        //Moving Animations
        enterAnimation = new MoveToAction();
        exitAnimation = new MoveToAction();

        if(zoom) {
            scaleBy(.3f);
            enterAnimation.setPosition(115, -30);
            exitAnimation.setPosition(1920, -30);
        } else {
            enterAnimation.setPosition(325, 100);
            exitAnimation.setPosition(1920, 100);
        }

        bg1 = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_2.png")));                //Menu image
        bg1.setSize(1275, 830);
        group.addActor(bg1);

        enterAnimation.setDuration(.5f);
        exitAnimation.setDuration(animationOut);

        enterAnimation.setInterpolation(Interpolation.swingOut);

        //Back Button
        backToStart = new VisTextButton("Back");
        group.addActor(backToStart);
        backToStart.setSize(200, 50);
        backToStart.setPosition(25, 25);

    }
    public void enterAnimation(){
        clearActions();
        addAction(enterAnimation);
        enterAnimation.restart();
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(fadeSpeed)));
    }
    public void exitAnimation(){
        clearActions();									    //Removing old actions
        addAction(exitAnimation);							//Adding Reverse action
        exitAnimation.restart();
        addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(fadeSpeed)));
    }
    public VisTextButton getBackToStart() {
        return backToStart;
    }
    public MoveToAction enterAnimation,exitAnimation;
    public VisTextButton backToStart;
    public Image bg1;          //Background image
    public Group group;
}
