package game.menu.screens.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.config.Config;
import game.util.Constants;

public class AbstractOptionsSubScreen extends Group implements Constants, Config{
    public AbstractOptionsSubScreen(){
        setSize(1275, 750);
        setPosition(-2000, -2000);

        //background = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_2.png")));				//Menu image
        //background.setSize(1275, 750);
        //addActor(background);

        group = new Group();
        group.setSize(getWidth(), getHeight());
        addActor(group);

        //Top background
        Image bg = new Image(new Texture(Gdx.files.internal("img/backgrounds/Menu_Transparent_1.png")));                //Menu image
        bg.setSize(1275, 560);
        bg.setPosition(0, 190, Align.bottomLeft);
        group.addActor(bg);

        temp = new VisTextButton("");
        audioLabelStyle = new VisImageTextButton.VisImageTextButtonStyle();
        audioLabelStyle.font = temp.getStyle().font;

        graphicsScExitLeft = new MoveToAction();
        graphicsScExitRight = new MoveToAction();
        graphicsScEnter = new MoveToAction();

        graphicsScExitLeft.setPosition(-1275, 0);
        graphicsScExitRight.setPosition(1275, 0);
        graphicsScEnter.setPosition(0,0);

        graphicsScExitLeft.setDuration(animationOut);
        graphicsScExitRight.setDuration(animationOut);
        graphicsScEnter.setDuration(animationOut);

        graphicsScExitLeft.setInterpolation(Interpolation.swingOut);
        graphicsScExitRight.setInterpolation(Interpolation.swingOut);
        graphicsScEnter.setInterpolation(Interpolation.swingIn);
    }

    public void enterAnimation(){
        clearActions();
        addAction(graphicsScEnter);
        addAction(Actions.fadeIn(.5f));
        graphicsScEnter.restart();
    }
    public void exitLeftAnimation(){
        clearActions();
        addAction(graphicsScExitLeft);
        addAction(Actions.fadeOut(.5f));
        graphicsScExitLeft.restart();
    }
    public void exitRightAnimation(){
        clearActions();									    //Removing old actions
        addAction(Actions.fadeOut(.5f));
        addAction(graphicsScExitRight);							//Adding Reverse action
        graphicsScExitRight.restart();
    }

    public VisTextButton temp;
    public VisImageTextButton.VisImageTextButtonStyle audioLabelStyle;
    public MoveToAction graphicsScExitLeft,graphicsScExitRight;
    public MoveToAction graphicsScEnter;
    public VisImageTextButton title;
    public Group group;
}
