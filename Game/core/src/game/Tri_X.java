package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;


import org.lwjgl.Sys;

import game.database.AccessDB;
import game.game.gameHuds.Hud;
import game.game.gameScreens.Level1;
import game.game.gameScreens.Level2;
import game.game.gameScreens.Level3;
import game.game.gameScreens.Level4;
import game.game.gameScreens.LevelState;
import game.menu.model.books.GameBook;
import game.menu.screens.fullScreens.HollowDeckScreen;
import game.menu.screens.fullScreens.MainMenuScreen;
import game.util.Constants;
import game.util.config.Config;

public class Tri_X extends Game implements Constants, Config {
	public SpriteBatch batch;
	Hud hud;
	AssetManager manager;
	Level1 level1;
	Level2 level2;
	Level3 level3;
	Level4 level4;
	MainMenuScreen myMenuScreen;

	LevelState levelState;

	public Hud getHud() {
		return hud;
	}

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {
		if(!VisUI.isLoaded()) {
			VisUI.load(VisUI.SkinScale.X2);
		}

		//Asset Manager
		manager = new AssetManager();
		manager.load("animations/asteroids/aster1.pack", TextureAtlas.class);
		manager.load("animations/asteroids/am1.pack", TextureAtlas.class);
		manager.load("animations/asteroids/am2.pack", TextureAtlas.class);

		//manager.load("audio/music/Antigravity.mp3", Music.class);
		manager.load("audio/effects/bump.wav", Sound.class);
		manager.load("audio/effects/breakblock.wav", Sound.class);
		manager.load("audio/effects/coin.wav", Sound.class);
		manager.load("audio/effects/powerup.wav", Sound.class);
		manager.load("audio/effects/powerdown.wav", Sound.class);
		manager.load("audio/effects/fire.mp3", Sound.class);
		manager.load("audio/effects/explosion.mp3", Sound.class);
		//Enemies
		manager.load("animations/enemies/en1.pack", TextureAtlas.class);    //Alpha
		manager.load("animations/enemies/lop.pack", TextureAtlas.class);    //Alpha
		manager.load("animations/enemies/lop3.pack", TextureAtlas.class);    //Beta
		manager.load("animations/enemies/cubee.pack", TextureAtlas.class);    //Beta
		manager.load("animations/enemies/tri.pack", TextureAtlas.class);    //Beta
		//Ships
		manager.load("animations/ships/shipOne.pack", TextureAtlas.class);    //Beta
		manager.load("animations/ships/ship2.pack", TextureAtlas.class);    //Beta
		manager.load("animations/ships/shipPlane.pack", TextureAtlas.class);    //Beta
		manager.load("animations/ships/tri2.pack", TextureAtlas.class);    //Beta

		manager.load("animations/enemies/Hex/Hex.pack", TextureAtlas.class);    //Alpha
		manager.load("animations/enemies/ex.pack", TextureAtlas.class);    //Alpha ex
		manager.load("animations/weapons/weapon1.png", Texture.class);    //Alpha ex

		manager.finishLoading();

		batch = new SpriteBatch();
		hud = new Hud(batch);

		int a = 2;
		if(a != 2) {
			//final PlayScreen myPlayScreen = new PlayScreen(batch);
			//setScreen(myPlayScreen);
		} else {
			//final WelcomeScreen myWelcomeScreen = new WelcomeScreen();
			//setScreen(myWelcomeScreen);									//Initially setting the screen

			myMenuScreen = new MainMenuScreen(this);
			final HollowDeckScreen myHollowDeckScreen = new HollowDeckScreen();

			myMenuScreen.create();
			myHollowDeckScreen.create();

			setScreen(myMenuScreen);

			//ShipSelection--Starting level one from the ship selection screen
			myMenuScreen.getB1a().addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.shipScInvisi();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.setShipType(1);
					level1 = new Level1(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
					level1.setType(1);
					setScreen(level1);
					levelState = LevelState.LEVEL_1;
				}
			});
			myMenuScreen.getB2a().addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.shipScInvisi();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.setShipType(2);
					level1 = new Level1(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
					level1.setType(2);
					setScreen(level1);
					levelState = LevelState.LEVEL_1;
				}
			});
			myMenuScreen.getB3a().addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.shipScInvisi();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.setShipType(3);
					level1 = new Level1(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
					level1.setType(3);
					setScreen(level1);
					levelState = LevelState.LEVEL_1;
				}
			});
			myMenuScreen.getB4a().addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.shipScInvisi();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.setShipType(4);
					level1 = new Level1(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
					level1.setType(4);
					setScreen(level1);
					levelState = LevelState.LEVEL_1;
				}
			});

			//HUD--Going back to menu from level, disposing that level
			hud.getMenuButton().addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					myMenuScreen.shipScInvisi();
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					menuGo();
				}
			});

			//HUD--Replaying current level
			hud.getPlayAgain().addListener(new InputListener() {
				@Override
				final public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					hud.getPlayAgain().setChecked(true);
					return true;
				}
				@Override
				final public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					replayCurrent();
				}
			});

			//Playing next level
			hud.getPlayLevel2().addListener(new InputListener() {
				@Override
				final public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}
				@Override
				final public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(hud.isShowLev2())
					playNextLevel();
				}
			});
			//Playing next level
			hud.getSubmitHighScore().addListener(new InputListener() {
				@Override
				final public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}
				@Override
				final public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					AccessDB myDatabaseConnection = new AccessDB();       //Creates Connection
					myDatabaseConnection.insert(hud.getScore(), hud.getMyText(), myMenuScreen.getDifficulty());
					myDatabaseConnection.closeConnection();                     //Ends Connection
					menuGo();
				}
			});
		//Menu to holo-deck
		myMenuScreen.getHollowDeckButton().addListener(new InputListener() {
			@Override
			final public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			final public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				myMenuScreen.exitMenuCentre();    //Centres Menu buttons
				setScreen(myHollowDeckScreen);
			}
		});

		//Holo-deck to menu
		myHollowDeckScreen.getMenuButton().addListener(new InputListener() {
			@Override
			final public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			@Override
			final public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(myHollowDeckScreen.isHologram()) {	//If Rendering
					myHollowDeckScreen.showMenu();		//Stop Rendering
				}else{
					setScreen(myMenuScreen);
				}
			}
		});
		}
	}

	public void disposeCurrent(){
		if(levelState == LevelState.LEVEL_1){
			level1.dispose();
		} else if(levelState == LevelState.LEVEL_2){
			level2.dispose();
		} else if(levelState == LevelState.LEVEL_3){
			level3.dispose();
		} else if(levelState == LevelState.LEVEL_4){
			level4.dispose();
		}
	}

	public void replayCurrent(){
		if(levelState == LevelState.LEVEL_1){
			level1.playLevel1();
		} else if(levelState == LevelState.LEVEL_2){
			level2.playLevel1();
		} else if(levelState == LevelState.LEVEL_3){
			level3.playLevel1();
		} else if(levelState == LevelState.LEVEL_4){
			level4.playLevel1();
		}
	}

	public void menuGo(){
		myMenuScreen.setStart();
		//Removing from memory
		disposeCurrent();
		setScreen(myMenuScreen);
	}
	public void playNextLevel(){
		if(levelState == LevelState.LEVEL_1){
			level2 = new Level2(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
			level2.setType(myMenuScreen.getShipType());
			setScreen(level2);
			hud.hideL2Hud();
			//Disposing of old memory
			disposeCurrent();
			levelState = LevelState.LEVEL_2;
			hud.setAsLevel2();
			hud.setPlayNextLevel3();
		} else if(levelState == LevelState.LEVEL_2){
			level3 = new Level3(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
			level3.setType(myMenuScreen.getShipType());
			setScreen(level3);
			hud.hideL2Hud();
			//Disposing of old memory
			disposeCurrent();
			levelState = LevelState.LEVEL_3;
			hud.setAsLevel3();
			hud.setPlayNextLevel4();
		} else if(levelState == LevelState.LEVEL_3){
			level4 = new Level4(myMenuScreen.getDifficulty(), batch, manager, hud, myMenuScreen.isRetroGame());
			level4.setType(myMenuScreen.getShipType());
			setScreen(level4);
			hud.hideL2Hud();
			//Disposing of old memory
			disposeCurrent();
			levelState = LevelState.LEVEL_4;
			hud.disableNext();
		} else if(levelState == LevelState.LEVEL_4){
		}
	}
	//public VisTextButton getRetro() {
	//	return myOptionsMenu.getRetro();
	//}
	public TextButton getFullscreen() {
		return myOptionsMenu.getFullscreen();
	}
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}