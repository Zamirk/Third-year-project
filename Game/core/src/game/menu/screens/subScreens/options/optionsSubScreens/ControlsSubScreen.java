package game.menu.screens.subScreens.options.optionsSubScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.Constants;
import game.menu.screens.utils.AbstractOptionsSubScreen;
import game.util.config.Config;


public class ControlsSubScreen extends AbstractOptionsSubScreen implements Constants, Config {
    private final VisTextButton keyboard;
    private final VisTextButton handheld;
    private final VisTextButton onScreen;
    private final Image onSc;
    private final Image keyboarda;
    private final Image xb;

    public ControlsSubScreen(){
        VisImageTextButton.VisImageTextButtonStyle squareStyle = new VisImageTextButton.VisImageTextButtonStyle();
        squareStyle.font = myFont;
        xb = new Image(new Texture(Gdx.files.internal("img/controllersScreen/xboxController.png")));
        addActor(xb);
        xb.setSize(700, 300);
        xb.setPosition(275, 200);
        xb.setVisible(false);

        onSc = new Image(new Texture(Gdx.files.internal("img/controllersScreen/onScr.png")));
        addActor(onSc);
        onSc.setSize(700, 300);
        onSc.setPosition(275, 200);
        onSc.setVisible(false);

        keyboarda = new Image(new Texture(Gdx.files.internal("img/controllersScreen/keyboard.png")));
        addActor(keyboarda);
        keyboarda.setSize(700,300);
        keyboarda.setPosition(275,200);
        keyboarda.setVisible(false);

        VisImageTextButton.VisImageTextButtonStyle keyboardStyle = new VisImageTextButton.VisImageTextButtonStyle();
        keyboardStyle.up = new Image(new Texture(Gdx.files.internal("img/controllersScreen/1x.png"))).getDrawable();
        keyboardStyle.over = new Image(new Texture(Gdx.files.internal("img/controllersScreen/1y.png"))).getDrawable();
        keyboardStyle.down = new Image(new Texture(Gdx.files.internal("img/controllersScreen/1z.png"))).getDrawable();
        keyboardStyle.font = myFont;

        VisImageTextButton.VisImageTextButtonStyle handheldStyle = new VisImageTextButton.VisImageTextButtonStyle();
        handheldStyle.up = new Image(new Texture(Gdx.files.internal("img/controllersScreen/11.png"))).getDrawable();
        handheldStyle.over = new Image(new Texture(Gdx.files.internal("img/controllersScreen/12.png"))).getDrawable();
        handheldStyle.down = new Image(new Texture(Gdx.files.internal("img/controllersScreen/13.png"))).getDrawable();
        handheldStyle.font = myFont;

        VisImageTextButton.VisImageTextButtonStyle onScreenStyle = new VisImageTextButton.VisImageTextButtonStyle();
        onScreenStyle.up = new Image(new Texture(Gdx.files.internal("img/controllersScreen/1a.png"))).getDrawable();
        onScreenStyle.over = new Image(new Texture(Gdx.files.internal("img/controllersScreen/1b.png"))).getDrawable();
        onScreenStyle.down = new Image(new Texture(Gdx.files.internal("img/controllersScreen/1c.png"))).getDrawable();
        onScreenStyle.font = myFont;

        VisTextButton title = new VisTextButton("Controllers", squareStyle);
        keyboard = new VisTextButton("", keyboardStyle);
        handheld = new VisTextButton("", handheldStyle);
        onScreen = new VisTextButton("", onScreenStyle);

        title.setSize(200, 100);
        keyboard.setSize(450, 125);
        onScreen.setSize(200, 100);
        handheld.setSize(235, 160);

        title.setPosition(550, 650);
        keyboard.setPosition(25, 540);
        onScreen.setPosition(550, 540);
        handheld.setPosition(890, 540);

        keyboard.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                onSc.setVisible(false);
                keyboarda.setVisible(true);
                xb.setVisible(false);

            }
        });
        handheld.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                onSc.setVisible(false);
                keyboarda.setVisible(false);
                xb.setVisible(true);

            }
        });
        onScreen.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                onSc.setVisible(true);
                keyboarda.setVisible(false);
                xb.setVisible(false);

            }
        });

        //Buttons with styles added
        group.addActor(title);
        group.addActor(keyboard);
        group.addActor(handheld);
        group.addActor(onScreen);

        keyboarda.setVisible(true);
    }
}
