package game.menu.screens.subScreens.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.FontUtils;
import game.util.config.Config;
import game.util.Constants;
import game.menu.screens.subScreens.options.optionsSubScreens.AudioSubScreen;
import game.menu.screens.subScreens.options.optionsSubScreens.ControlsSubScreen;
import game.menu.screens.subScreens.options.optionsSubScreens.GameSubScreen;
import game.menu.screens.subScreens.options.optionsSubScreens.GraphicsSubScreen;


public class OptionsSubMenu extends Group implements Constants, Config {
    public OptionsSubMenu(){
        //Square font
        VisImageTextButton.VisImageTextButtonStyle squareStyle = new VisImageTextButton.VisImageTextButtonStyle();
        squareStyle.font = myFont;

        currentSc = "Graphics";
        setSize(1275, 830);

        //Background
        Image background = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_1.png")));				//Menu image
        background.setSize(1275, 830);
        addActor(background);

        //Top background
        Image bga = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_4.png")));                //Menu image
        bga.setSize(1275, 570);
        bga.setPosition(0, 185, Align.bottomLeft);
        addActor(bga);

        BitmapFont myFontGreen = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
        VisImageTextButton.VisImageTextButtonStyle squareGreenRed = new VisImageTextButton.VisImageTextButtonStyle();
        squareGreenRed.font = myFontGreen;
        squareGreenRed.checkedFontColor = Color.WHITE;
        squareGreenRed.checkedOverFontColor = Color.FOREST;
        squareGreenRed.downFontColor = Color.FIREBRICK;

        backButton = new VisTextButton("Back", squareGreenRed);
        applyButton = new VisTextButton("Apply", squareGreenRed);

        backButton.setChecked(true);
        applyButton.setChecked(true);

        backButton.setFocusBorderEnabled(false);
        applyButton.setFocusBorderEnabled(false);

        op1 = new VisImageTextButton("Graphics",squareStyle);
        op2 = new VisImageTextButton("Game",squareStyle);
        op3 = new VisImageTextButton("Controls",squareStyle);
        op4 = new VisImageTextButton("Audio",squareStyle);

        op1.setTouchable(Touchable.disabled);
        op2.setTouchable(Touchable.disabled);
        op3.setTouchable(Touchable.disabled);
        op4.setTouchable(Touchable.disabled);

        opL = new VisTextButton("L");
        opR = new VisTextButton("R");

        addActor(op1);
        addActor(op2);
        addActor(op3);
        addActor(op4);

        op1.setSize(250, 125);
        op2.setSize(250, 125);
        op3.setSize(250, 125);
        op4.setSize(250, 125);
        opL.setSize(80, 500);
        opR.setSize(80, 500);
        backButton.setSize(250, 125);
        applyButton.setSize(250, 125);

        op1.setPosition(25, 855, Align.topLeft);
        op2.setPosition(350, 855, Align.topLeft);
        op3.setPosition(675, 855, Align.topLeft);
        op4.setPosition(1000, 855, Align.topLeft);
        opL.setPosition(0, 437, Align.center);
        opR.setPosition(1275, 437, Align.center);
        backButton.setPosition(250, 30);
        applyButton.setPosition(750, 30);

        opL.setColor(Color.RED);
        opR.setColor(Color.RED);

        graphicsScAction = new MoveToAction();
        graphicsScActionRev = new MoveToAction();

        if(zoom) {
            scaleBy(.3f);
            setPosition(115, -1080);
            graphicsScAction.setPosition(115, -30);
            graphicsScActionRev.setPosition(115, -1080);
        } else {
            graphicsScAction.setPosition(325, 100);
            graphicsScActionRev.setPosition(325, -830);
            setPosition(325, -830);
        }

        graphicsScAction.setDuration(animationIn);
        graphicsScActionRev.setDuration(animationOut);

        graphicsScAction.setInterpolation(Interpolation.swingOut);
        op1.getLabel().setColor(Color.FIREBRICK);

        myGraphicsScreen = new GraphicsSubScreen();
        myAudioScreen = new AudioSubScreen();
        myControlScreen = new ControlsSubScreen();
        myGameScreen = new GameSubScreen();

        addActor(myGraphicsScreen);
        addActor(myAudioScreen);
        addActor(myControlScreen);
        addActor(myGameScreen);

        addActor(opL);                  //Left Button
        addActor(opR);                  //Right Button
        addActor(backButton);           //Back Button
        addActor(applyButton);

        applyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                applyButton.setChecked(true);
                readData.overRideFileOnPC(
                        "true",
                        "false",
                        myAudioScreen.getOne(),
                        myAudioScreen.getTwo(),
                        myAudioScreen.getThree(),
                        myAudioScreen.getFour());
            }
        });
        opR.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (currentSc == "Graphics") {
                            currentSc = "Game";
                            myGraphicsScreen.exitLeftAnimation();
                            myGameScreen.setPosition(1275, 0);
                            myGameScreen.enterAnimation();
                            op1.getLabel().setColor(Color.WHITE);
                            op2.getLabel().setColor(Color.FIREBRICK);
                        } else if (currentSc == "Game") {
                            currentSc = "Controls";
                            myGameScreen.exitLeftAnimation();
                            myControlScreen.setPosition(1275, 0);
                            myControlScreen.enterAnimation();
                            op2.getLabel().setColor(Color.WHITE);
                            op3.getLabel().setColor(Color.FIREBRICK);
                        } else if (currentSc == "Controls") {
                            currentSc = "Audio";
                            myControlScreen.exitLeftAnimation();
                            myAudioScreen.setPosition(1275, 0);
                            myAudioScreen.enterAnimation();
                            op3.getLabel().setColor(Color.WHITE);
                            op4.getLabel().setColor(Color.FIREBRICK);
                        } else if (currentSc == "Audio") {
                            currentSc = "Graphics";
                            myAudioScreen.exitLeftAnimation();
                            myGraphicsScreen.setPosition(1275, 0);
                            myGraphicsScreen.enterAnimation();
                            op4.getLabel().setColor(Color.WHITE);
                            op1.getLabel().setColor(Color.FIREBRICK);
                        }
                    }
                });

        opL.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (currentSc == "Graphics") {
                            op1.getLabel().setColor(Color.WHITE);
                            op4.getLabel().setColor(Color.FIREBRICK);
                            currentSc = "Audio";
                            myGraphicsScreen.exitRightAnimation();
                            myAudioScreen.setPosition(-1275, 0);
                            myAudioScreen.enterAnimation();
                        } else if (currentSc == "Audio") {
                            op4.getLabel().setColor(Color.WHITE);
                            op3.getLabel().setColor(Color.FIREBRICK);
                            currentSc = "Controls";
                            myAudioScreen.exitRightAnimation();
                            myControlScreen.setPosition(-1275, 0);
                            myControlScreen.enterAnimation();
                        } else if (currentSc == "Controls") {
                            op3.getLabel().setColor(Color.WHITE);
                            op2.getLabel().setColor(Color.FIREBRICK);
                            currentSc = "Game";
                            myControlScreen.exitRightAnimation();
                            myGameScreen.setPosition(-1275, 0);
                            myGameScreen.enterAnimation();
                        } else if (currentSc == "Game") {
                            op2.getLabel().setColor(Color.WHITE);
                            op1.getLabel().setColor(Color.FIREBRICK);
                            currentSc = "Graphics";
                            myGameScreen.exitRightAnimation();
                            myGraphicsScreen.setPosition(-1275, 0);
                            myGraphicsScreen.enterAnimation();
                        }
                    }
                });
    }



    public void forwardGraphicsScreen(){
        clearActions();
        addAction(graphicsScAction);
        graphicsScAction.restart();
    }
    public void exitGraphicsScreenDown(){
        clearActions();									    //Removing old actions
        addAction(graphicsScActionRev);							//Adding Reverse action
        graphicsScActionRev.restart();
    }
    public boolean isRetroGame() {
        return myGameScreen.isRetroGame();
    }
    public VisTextButton getMyTxButton() {
        return backButton;
    }
    public VisTextButton getRetro() {
        return myGameScreen.getRetro();
    }
    public TextButton getFullscreen() {
        return myGraphicsScreen.getFullscreen();
    }
    MoveToAction graphicsScAction,graphicsScActionRev;
    VisTextButton applyButton;
    VisTextButton backButton;
    VisImageTextButton op1;
    VisImageTextButton op2;
    VisImageTextButton op3;
    VisImageTextButton op4;
    VisTextButton opL;
    VisTextButton opR;
    String currentSc;
    GraphicsSubScreen myGraphicsScreen;
    GameSubScreen myGameScreen;
    ControlsSubScreen myControlScreen;
    AudioSubScreen myAudioScreen;
}
