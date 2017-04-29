package game.menu.screens.subScreens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kotcrab.vis.ui.widget.VisTextButton;

import org.lwjgl.Sys;

import game.menu.asteroids.AnimatedImage;
import game.menu.screens.utils.AbstractSubScreen;

public class SelectShipScreen extends AbstractSubScreen {
    public SelectShipScreen(AssetManager manager) {
        setPosition(325, 100);

        b1 = new VisTextButton("");
        b2 = new VisTextButton("");
        b3 = new VisTextButton("");
        b4 = new VisTextButton("");
        b5 = new VisTextButton("");

        b1.setSize(500, 330);
        b2.setSize(500, 330);
        b3.setSize(500, 330);
        b4.setSize(500, 330);
        b5.setSize(1000, 660);

        b1.setPosition(100, 410);
        b2.setPosition(665, 410);
        b3.setPosition(100, 60);
        b4.setPosition(665, 60);
        b5.setPosition(100, 100);

        group.addActor(b1);
        group.addActor(b2);
        group.addActor(b3);
        group.addActor(b4);
        group.addActor(b5);

        shipOne = new AnimatedImage(new Animation(1/60f, manager.get("animations/ships/shipOne.pack", TextureAtlas.class).getRegions()));
        shipTwo = new AnimatedImage(new Animation(1/60f, manager.get("animations/ships/ship2.pack", TextureAtlas.class).getRegions()));
        shipThree = new AnimatedImage(new Animation(1/60f, manager.get("animations/ships/tri2.pack", TextureAtlas.class).getRegions()));
        shipFour = new AnimatedImage(new Animation(1/60f, manager.get("animations/ships/shipPlane.pack", TextureAtlas.class).getRegions()));

        shipOne.setTouchable(Touchable.disabled);
        shipTwo.setTouchable(Touchable.disabled);
        shipThree.setTouchable(Touchable.disabled);
        shipFour.setTouchable(Touchable.disabled);

        shipThree.setColor(Color.BLACK);

        shipOne.setPosition(120, 410);
        shipTwo.setPosition(685, 410);
        shipThree.setPosition(120, 60);
        shipFour.setPosition(685, 60);

        int size = 300;
        shipOne.setSize(size, size);
        shipTwo.setSize(size, size);
        shipThree.setSize(size, size);
        shipFour.setSize(size, size);

        group.addActor(shipOne);
        group.addActor(shipTwo);
        group.addActor(shipThree);
        group.addActor(shipFour);

        backToStart.setPosition(0, 0);
        b5.setText("Loading Level 1!");
        b5.setVisible(false);

        VisTextButton.VisTextButtonStyle ccc = new VisTextButton.VisTextButtonStyle();
        ccc.font = defaultButton.getStyle().font;
        b1.setStyle(ccc);
        b2.setStyle(ccc);
        b3.setStyle(ccc);
        b4.setStyle(ccc);
        b5.setStyle(ccc);
    }

    VisTextButton b1;
    VisTextButton b2;
    VisTextButton b3;
    VisTextButton b4;
    VisTextButton b5;

    Image shipOne;
    Image shipTwo;
    Image shipThree;
    Image shipFour;
    public void invisi(){
        shipOne.setVisible(false);
        shipTwo.setVisible(false);
        shipThree.setVisible(false);
        shipFour.setVisible(false);

        b1.setVisible(false);
        b2.setVisible(false);
        b3.setVisible(false);
        b4.setVisible(false);
        b5.setVisible(true);
    }
    public void visi(){
        shipOne.setVisible(true);
        shipTwo.setVisible(true);
        shipThree.setVisible(true);
        shipFour.setVisible(true);

        b1.setVisible(true);
        b2.setVisible(true);
        b3.setVisible(true);
        b4.setVisible(true);
        b5.setVisible(false);
    }

    public VisTextButton getB1() {
        return b1;
    }

    public VisTextButton getB2() {
        return b2;
    }

    public VisTextButton getB3() {
        return b3;
    }

    public VisTextButton getB4() {
        return b4;
    }
}
