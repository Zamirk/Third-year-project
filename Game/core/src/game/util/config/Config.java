package game.util.config;

public interface Config {
    ReadConfigFile readData = new ReadConfigFile();
    int animationIn = 2;						            //Enter animation speed
    float animationOut = .5f;					            //Exit animation speed
    float fadeSpeed = .2f;                                  //Speed of fades
    int maxAsteroidAmount = 100;                            //Mas asteroids

    //Getting values from class,
    boolean colorsOn = readData.getColorsOn();				//Colored Asteroid
    boolean skipToGame = readData.getSkipToGameOn();        //Skip main menu to game
    boolean debugModeOn = readData.getDebugModeOn();        //View boundaries
    boolean animatedMenuOn = readData.getDebugModeOn();     //View boundaries
    boolean zoom = readData.getZoomOn();                     //Enlarging features
    int audioMenu = readData.getAudio_Menu();
    int audioGame = readData.getAudio_Game();
    int audioEffects = readData.getAudio_Effects();
    int audioMusic = readData.getAudio_Music();

    int V_WIDTH = 1920;
    int V_HEIGHT = 1080;
    float ppm = 100;			//Scaling Box2d
}
