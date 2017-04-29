package game.menu.screens.subScreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.menu.screens.utils.AbstractSubScreen;


public class AboutSubScreen extends AbstractSubScreen {
    public AboutSubScreen() {
        setPosition(-1275, 100);

        aboutScreenRev.setPosition(-1275, 100);

        test();
        dnd = new DragAndDrop();
        aaa();
    }

    public void aaa() {
        VisTextButton game = new VisTextButton("Game");
        game.setOrigin(game.getWidth(),game.getHeight());
        game.align(Align.center);
        addActor(game);
        game.setSize(200, 50);
        game.setPosition(80, 80);
        game.setOrigin(50, 25);
        dnd.addSource(bbb(game));
    }
    public DragAndDrop.Source bbb(VisTextButton butt) {

        final VisTextButton myButt = butt;
        myButt.setText("Buttt");
        addActor(butt);
        myButt.setSize(200, 50);
        myButt.setPosition(80, 80);
        myButt.setOrigin(myButt.getWidth(), myButt.getHeight());

        mySource = new DragAndDrop.Source(myButt) {
            final DragAndDrop.Payload myPayload = new DragAndDrop.Payload();

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                myPayload.setObject(myButt);
                myPayload.setDragActor(myButt);
                return myPayload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                super.dragStop(event, x, y, pointer, payload, target);
                if (target == null) {

                aaa();
                } else {
                aaa();
            dnd.setDragActorPosition(2,2);
                }
            }

        };
        return mySource;
    }
    public void test(){
        final Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        skin.add("badlogic", new Texture("data/badlogic.jpg"));

        Image sourceImage = new Image(skin, "badlogic");
        sourceImage.setBounds(50, 125, 100, 100);
        addActor(sourceImage);

        Image validTargetImage = new Image(skin, "badlogic");
        validTargetImage.setBounds(200, 50, 100, 100);
        addActor(validTargetImage);

        Image invalidTargetImage = new Image(skin, "badlogic");
        invalidTargetImage.setBounds(200, 200, 100, 100);
        addActor(invalidTargetImage);

        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new DragAndDrop.Source(sourceImage) {
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("Some payload!");

                payload.setDragActor(new Label("Some payload!", skin));

                Label validLabel = new Label("Some payload!", skin);
                validLabel.setColor(0, 1, 0, 1);
                payload.setValidDragActor(validLabel);

                Label invalidLabel = new Label("Some payload!", skin);
                invalidLabel.setColor(1, 0, 0, 1);
                payload.setInvalidDragActor(invalidLabel);

                return payload;
            }
        });
        dragAndDrop.addTarget(new DragAndDrop.Target(validTargetImage) {
            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GREEN);
                return true;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (DragAndDrop.
                                      Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
            }
        });
        dragAndDrop.addTarget(new DragAndDrop.Target(invalidTargetImage) {
            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.RED);
                return false;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
            }
        });


    }

}
.setOnCompletionListener(new Music.OnCompletionListener() {
@Override
public void onCompletion(Music music) {

        }
        });