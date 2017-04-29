package game.menu.screens.subScreens;

import game.menu.screens.utils.AbstractSubScreen;

public class AboutSubScreen extends AbstractSubScreen {
    public AboutSubScreen() {
        if(zoom){
            setPosition(-1920, -30);
            exitAnimation.setPosition(-1920, -30);
        } else {
            setPosition(-1275, 100);
            exitAnimation.setPosition(-1275, 100);
        }
    }
}
