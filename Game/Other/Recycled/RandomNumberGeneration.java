import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.Random;

public class RandomNumberGeneration {
    public static void main (String[] arg) {

        for(int i=0;i<20;i++) {
            Random random = new Random();
            int number = random.nextInt(4 - 1 + 1) + 1;
            System.out.print(number + "\n");
        }
    }
}
//table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));

//VisCheckBox fullScreenBx = new VisCheckBox("Fullscreen");
//fullScreenBx.setPosition( 25, 750);
//optionsSc.addActor(fullScreenBx);

//LinkLabel myLink = new LinkLabel("Google.com, Click here","https://Google.com/");
//myLink.setPosition(25, 650);
//optionsSc.addActor(myLink);

//final VisProgressBar myProgressBar = new VisProgressBar(0,100,1,false);
//myProgressBar.setPosition(25, 600);
//myProgressBar.setValue(amountOfAsteroids);
//optionsSc.addActor(myProgressBar);

//VisRadioButton myRadioButton = new VisRadioButton("Option 1");
//myRadioButton.setPosition(25, 550);
//optionsSc.addActor(myRadioButton);

//Texture testTexture = new Texture("img/asteroids/asteroid_2/1.png");
//Image testImage = new Image(testTexture);
//testImage.setSize(500, 500);

//VisScrollPane myVisScrollPane = new VisScrollPane(testImage);
//myVisScrollPane.setPosition(600, 500);
//myVisScrollPane.setColor(Color.BLACK);
//myVisScrollPane.setSize(500, 300);
//optionsSc.addActor(myVisScrollPane);

//VisSelectBox myScBx = new VisSelectBox();
//myScBx.setPosition(25, 500);
//optionsSc.addActor(myScBx);

//final VisSlider mySlider = new VisSlider(0,100,1,false);
//mySlider.setPosition(25, 450);
//mySlider.setSize(400, 20);
//optionsSc.addActor(mySlider);

//VisTextArea myTxArea = new VisTextArea();
//myTxArea.setSize(500, 200);
//myTxArea.setPosition(600, 300);
//myTxArea.setText("This is a text area.");
//myTxArea.appendText("This is Appending text.");
//optionsSc.addActor(myTxArea);

//Color myColour = new Color(255, 11,11, 128 );
//.addAction(Actions.alpha(0));
//Need to reapear astroid after invisible


//asteroidArray[i].addAction(parallel(moveTo(250, 250, 2, bounceOut), rotateTo(180, 5, swingIn)));
//asteroidArray[i].addAction(parallel(moveTo(250, 250, 2, bounceOut), color(Color.RED, 6), delay(0.5f), rotateTo(180, 5, swing)));
//asteroidArray[i].addAction(forever(sequence(scaleTo(2, 2, 0.5f), scaleTo(1, 1, 0.5f), delay(0.5f))));

//Black Hole/Worm Hole
//asteroidArray[0].addAction(forever(sequence(scaleBy(2, 2, 10f))));
//asteroidArray[0].addAction(forever(rotateBy(-360, .1f)));

//Restarting an action
//buttonActionsArray[i].restart();								//Restart action

//buttonReverseActionsArray[i].setDuration(animationOut);					//Setting reverse movement speeds
//buttonReverseActionsArray[i].setInterpolation(Interpolation.sineIn);