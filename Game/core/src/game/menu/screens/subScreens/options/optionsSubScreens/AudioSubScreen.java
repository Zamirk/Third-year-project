package game.menu.screens.subScreens.options.optionsSubScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.ArrayList;

import game.util.Constants;
import game.menu.screens.utils.AbstractOptionsSubScreen;


public class AudioSubScreen extends AbstractOptionsSubScreen implements Constants {
    final ArrayList<VisTextButton> volumeLabelList;
    public AudioSubScreen(){
        //Font: Square, red
        VisImageTextButton.VisImageTextButtonStyle squareRed = new VisImageTextButton.VisImageTextButtonStyle();
        squareRed.font = myFont;
        squareRed.fontColor = Color.FOREST;
        //Font: Square, green
        VisImageTextButton.VisImageTextButtonStyle squareGreen = new VisImageTextButton.VisImageTextButtonStyle();
        squareGreen.font = myFont;
        squareGreen.fontColor = Color.FOREST;
        //Font: Pixel, green
        VisImageTextButton.VisImageTextButtonStyle pixelGreen = new VisImageTextButton.VisImageTextButtonStyle();
        pixelGreen.font = myFont2;
        pixelGreen.fontColor = Color.FOREST;

        VisTextButton title = new VisTextButton("Audio Settings", squareRed);
        title.setPosition(520, 666);

        VisSlider.SliderStyle lightSaber = new VisSlider.SliderStyle();
        lightSaber.background = new Image(new Texture(Gdx.files.internal("img/audioScreen/beem.png"))).getDrawable();
        lightSaber.knob = new Image(new Texture(Gdx.files.internal("img/audioScreen/handle.png"))).getDrawable();
        lightSaber.knobOver = new Image(new Texture(Gdx.files.internal("img/audioScreen/handle.png"))).getDrawable();

        final ArrayList<VisSlider> sliderList = new ArrayList<VisSlider>();
        volumeLabelList = new ArrayList<VisTextButton>();
        final ArrayList<VisTextButton> sliderTitleList = new ArrayList<VisTextButton>();
        final String titlesArray[] = {"Game","Effects","Music","Menu"};

        for(int i=0;i<4;i++){
            sliderList.add(new VisSlider(0, 100, 5, false, lightSaber));
            volumeLabelList.add(new VisTextButton("100", pixelGreen));
            sliderTitleList.add(new VisTextButton(titlesArray[i], squareGreen ));

            sliderList.get(i).setValue(100);
            sliderList.get(i).setSize(500, 100);

            volumeLabelList.get(i).setPosition(960, yAxis2);
            sliderList.get(i).setPosition(415, yAxis);
            sliderTitleList.get(i).setPosition(230,yAxis2);

            group.addActor(sliderTitleList.get(i));
            group.addActor(sliderList.get(i));
            group.addActor(volumeLabelList.get(i));

            yAxis -= 100;
            yAxis2 -= 100;

            final int sI = i;
            sliderList.get(sI).addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    volumeLabelList.get(sI).setText("" + (int) sliderList.get(sI).getValue());
                }
            });
        }
        sliderList.get(0).setValue(audioGame);
        sliderList.get(1).setValue(audioEffects);
        sliderList.get(2).setValue(audioMusic);
        sliderList.get(3).setValue(audioMenu);

        volumeLabelList.get(0).setText(""+audioGame);
        volumeLabelList.get(1).setText(""+audioEffects);
        volumeLabelList.get(2).setText(""+audioMusic);
        volumeLabelList.get(3).setText(""+audioMenu);

        group.addActor(title);
    }

    public String getOne(){
        return volumeLabelList.get(0).getText().toString();
    }
    public String getTwo(){
        return volumeLabelList.get(1).getText().toString();
    }
    public String getThree(){
        return volumeLabelList.get(2).getText().toString();
    }
    public String getFour(){
        return volumeLabelList.get(3).getText().toString();
    }
    int yAxis = 547;
    int yAxis2 = 580;
}
