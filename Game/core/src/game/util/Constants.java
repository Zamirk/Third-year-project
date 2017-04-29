package game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.FontUtils;
import game.menu.screens.subScreens.AboutSubScreen;
import game.menu.screens.subScreens.AchievementsSubScreen;
import game.menu.screens.subScreens.MusicSubScreen;
import game.menu.screens.subScreens.options.OptionsSubMenu;
import game.menu.screens.subScreens.HighScoresSubScreen;

public interface Constants {
    //Menu Constants
    //Default button
    VisTextButton defaultButton = new VisTextButton("");

    //Font generation
    BitmapFont myFont = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
    BitmapFont myFont2 = FontUtils.generateFont("font/minecraftia.ttf", 42, Color.WHITE);
    BitmapFont myFont3 = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
    BitmapFont myFont4 = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);

    AboutSubScreen myAboutScreen = new AboutSubScreen();
    OptionsSubMenu myOptionsMenu = new OptionsSubMenu();
    HighScoresSubScreen myHighScoresSubScreen = new HighScoresSubScreen();
    AchievementsSubScreen myAchievementsSubScreen = new AchievementsSubScreen();
    MusicSubScreen myMusicSubScreen = new MusicSubScreen();

    //Game Constants
    short DEFAULT_BIT = 1;
    short SHIP_BIT = 2;
    //Walls
    short BORDER_BIT = 4;
    short ENEMY_BARRIOR = 8;
    short BREAKABLE_WALL_BIT = 16;
    //Items
    short ITEM_001 = 256;
    //Enemies
    short DESTROYED_BIT = 32;
    short ENEMY001_BIT = 64;
    short ENEMY002_BIT = 128;
    short ENEMY003_BIT = 2048;
    short LEVEL2_BIT = 4096;
    short GODMODE_BIT = 8192;

    //Boss battles
    short BOSS_BIT = 1024;



    //Weapons
    short WEAPON1_BIT = 512;


}
