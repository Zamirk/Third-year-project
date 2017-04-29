package game.menu.screens.fullScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTextButton;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;
import java.util.Random;

import game.Tri_X;
import game.game.gameHuds.Hud;
import game.game.gameScreens.Level1;
import game.game.gameScreens.LevelState;
import game.menu.screens.subScreens.SelectShipScreen;
import game.util.FontUtils;
import game.util.config.Config;
import game.util.Constants;
import game.menu.asteroids.AnimatedImage;
import game.menu.asteroids.AsteroidAnimations;

public class MainMenuScreen implements Screen, AsteroidAnimations, Constants, Config {
	Tri_X game;
	Hud hud;
	Difficulty difficulty;

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public MainMenuScreen(Tri_X game) {
		difficulty = Difficulty.MEIDUM;
		this.game = game;
		hud = game.getHud();
		manager = game.getManager();
	}
	AssetManager manager;
	public void show(){
		Gdx.input.setInputProcessor(stage);
	}
	public void create() {
		stage = new Stage();

		Random random = new Random();                                        	//Random number generator
		buttonClick = Gdx.audio.newSound(Gdx.files.internal("audio/menu/buttonClick.ogg"));
		asteroidLaser = Gdx.audio.newSound(Gdx.files.internal("audio/menu/buttonClick.ogg"));

		//Random amount of asteroids
		//Randomising amount of asteroid versions (1-4)
		amountOfAsteroids = random.nextInt(50)+5;
		amountOfAsteroidTypes = 4;

		asteroidArray = new Image[amountOfAsteroids];							//Storing most items in arrays
		asteroidActions = new MoveToAction[amountOfAsteroids];
		buttonActionsArray = new MoveToAction[9];
		buttonReverseActionsArray = new MoveToAction[9];
		selectScActions = new MoveToAction[7];
		selectScButtons = new VisImageTextButton[7];
		selectScActionsRev = new MoveToAction[7];
		startToOptions = new MoveToAction[7];
		exitLeftAnimation = new MoveToAction[9];
		exitRightAnimation = new MoveToAction[9];

		//background image
		bg = new Image(new Texture(Gdx.files.internal("img/backgrounds/background_1.jpg")));        //Background image
		System.out.print("Asteroid Types: " + amountOfAsteroidTypes + ".\n");
		System.out.print("Asteroid Count:\t" + amountOfAsteroids + ".\n");

		//menu image
		menu = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_1.png")));                //Menu image
		menu.setTouchable(Touchable.disabled);
		menu.setPosition(0, 100);
		menu.setSize(1920, 830);

		squareGreenRed = new VisImageTextButton.VisImageTextButtonStyle();
		squareGreenRed.up = new Image(new Texture(Gdx.files.internal("img/button/button1.png"))).getDrawable();

		BitmapFont myFontGreen = FontUtils.generateFont("font/square.ttf", 42, Color.WHITE);
		squareGreenRed.font = myFontGreen;
		squareGreenRed.checkedFontColor = Color.WHITE;
		squareGreenRed.checkedOverFontColor = Color.FOREST;
		squareGreenRed.downFontColor = Color.FIREBRICK;

		textButtonArray = new VisImageTextButton[]{                                		//Menu Buttons
				new VisImageTextButton("Single Player", squareGreenRed),
				new VisImageTextButton("Multiplayer", squareGreenRed),
				new VisImageTextButton("Achievements", squareGreenRed),
				new VisImageTextButton("HighScores", squareGreenRed),
				new VisImageTextButton("Options", squareGreenRed),
				new VisImageTextButton("About", squareGreenRed),
				new VisImageTextButton("Quit", squareGreenRed),
				new VisImageTextButton("Music", squareGreenRed),
				new VisImageTextButton("3D Asset viewer", squareGreenRed)};

		selectScButtons = new VisImageTextButton[]{                                		//Select Buttons
				new VisImageTextButton("Easy", squareGreenRed),
				new VisImageTextButton("Medium", squareGreenRed),
				new VisImageTextButton("Hard", squareGreenRed),
				new VisImageTextButton("New Game", squareGreenRed),
				new VisImageTextButton("Level Select", squareGreenRed),
				new VisImageTextButton("Back", squareGreenRed),
				new VisImageTextButton("Tutorial", squareGreenRed)};

		//Array of colours
		Color[] colors = new Color[]{
				Color.CYAN, Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED};

		//Looping Asteroids
		for (int i = 0; i < asteroidArray.length; i++) {
			//Choosing random type, (0-3) positions, random speed, rotation, size, position
			randomTypes = random.nextInt(amountOfAsteroidTypes) + 1;
			randomSizes = random.nextInt(200) + 1;
			randomRotate = random.nextInt(100);
			randomPosX = random.nextInt(2220) + 1;
			randomPosY = random.nextInt(1580) + 1;

			//Far away asteroids are SLOWER and SMALL, Closer asteroids are BIGGER and FASTER!
			//Animated Asteroids
			if(randomTypes == 1){
				asteroidArray[i] = new AnimatedImage(new Animation(1/15f, manager.get("animations/asteroids/aster1.pack", TextureAtlas.class).getRegions()));
			}else if(randomTypes == 2) {
				asteroidArray[i] = new AnimatedImage(new Animation (.08f,new TextureRegion(new Texture("img/asteroids/asteroid_2/1.png"))));
			}else if(randomTypes == 3) {
				asteroidArray[i] = new AnimatedImage(new Animation (.08f,new TextureRegion(new Texture("img/asteroids/asteroid_3/1.png"))));
			}else if(randomTypes == 4) {
				asteroidArray[i] = new AnimatedImage(new Animation(1/30f, manager.get("animations/asteroids/am1.pack", TextureAtlas.class).getRegions()));
		}

			//Colors asteroids for sorting,
			if (randomSizes < 40) {
				asteroidArray[i].setColor(Color.CYAN);
				randomSpeed = 2 * (random.nextInt((400 - 360) + 1) + 360);
			} else if (randomSizes < 80) {
				asteroidArray[i].setColor(Color.BLUE);
				randomSpeed = 2 * (random.nextInt((360 - 240) + 1) + 240);
			} else if (randomSizes < 120) {
				asteroidArray[i].setColor(Color.YELLOW);
				randomSpeed = 2 * (random.nextInt((240 - 160) + 1) + 160);
			} else if (randomSizes < 160) {
				asteroidArray[i].setColor(Color.GREEN);
				randomSpeed = 2 * (random.nextInt((160 - 80) + 1) + 80);
			} else {
				asteroidArray[i].setColor(Color.RED);
				randomSpeed = 2 * (random.nextInt((80 - 40) + 1) + 40);
			}
			asteroidActions[i] = new MoveToAction();                        	//Creating Movements
			asteroidActions[i].setDuration(randomSpeed);                    	//Speed
			asteroidActions[i].setPosition(-4000, 25);                        	//Future position
			asteroidArray[i].addListener(getNewAsteroidListener(i));        	//Adding listener
			asteroidArray[i].setRotation(randomRotate);                        	//Rotation
			asteroidArray[i].setSize(randomSizes, randomSizes);                	//Size
			asteroidArray[i].setOrigin(randomSizes / 2, randomSizes / 2);    	//Setting centre
			asteroidArray[i].setPosition(randomPosX, randomPosY);            	//Initial positions
			asteroidArray[i].addAction(asteroidActions[i]);                    	//Adding Movements

			if ((i & 1) == 0) {                                            		//Rotation
				asteroidArray[i].addAction(forever(rotateBy(360, 100)));    	//Clockwise
			} else {
				asteroidArray[i].addAction(forever(rotateBy(-360, 100)));    	//Counter Clockwise
			}
		}

		for (int i = 0; i < buttonActionsArray.length; i++) {                  		 //Looping Button Actions
			if(i < buttonActionsArray.length-2) {
				textButtonArray[i].setHeight(height);                                	//Height
				textButtonArray[i].setWidth(width);                                    //Width
				textButtonArray[i].setOrigin(width / 2, height / 2);               		 //Centre
				textButtonArray[i].setPosition(750, zPos);                            //Initial positions

				buttonActionsArray[i] = new MoveToAction();                        		//Initial movements
				buttonActionsArray[i].setPosition(750, xPos);                        //Future position
				buttonActionsArray[i].setDuration(animationIn);                    		//Movement speed
				buttonActionsArray[i].setInterpolation(Interpolation.pow5Out);      	//Slow stop

				buttonReverseActionsArray[i] = new MoveToAction();                    //Reverse movements
				buttonReverseActionsArray[i].setInterpolation(Interpolation.pow5Out);
				buttonReverseActionsArray[i].setDuration(animationOut);                //Reverse movement speeds

				selectScButtons[i].setHeight(height);
				selectScButtons[i].setWidth(width);
				selectScButtons[i].setOrigin(width / 2, height / 2);

				selectScActions[i] = new MoveToAction();                            //Initial movements
				selectScActions[i].setInterpolation(Interpolation.pow5Out);
				selectScActions[i].setDuration(animationIn);

				startToOptions[i] = new MoveToAction();                                //Initial movements
				startToOptions[i].setInterpolation(Interpolation.pow5Out);
				startToOptions[i].setDuration(animationOut);
				startToOptions[i].setPosition(750, zPos);

				exitLeftAnimation[i] = new MoveToAction();
				exitLeftAnimation[i].setInterpolation(Interpolation.pow5Out);
				exitLeftAnimation[i].setDuration(2);
				exitLeftAnimation[i].setPosition(-2500, xPos);

				exitRightAnimation[i] = new MoveToAction();
				exitRightAnimation[i].setInterpolation(Interpolation.pow5Out);
				exitRightAnimation[i].setDuration(2);
				exitRightAnimation[i].setPosition(3250, xPos);

				selectScActionsRev[i] = new MoveToAction();                            //Reverse movements
				selectScActionsRev[i].setDuration(animationOut);                    //Setting reverse movement speeds
				selectScActionsRev[i].setInterpolation(Interpolation.sineIn);

				if ((i & 1) == 0) {
					buttonReverseActionsArray[i].setPosition(-420, bPos);            //Reverse action
					bPos -= 300;
				} else {
					buttonReverseActionsArray[i].setPosition(1920, vPos);
					vPos -= 300;
				}
				if (i < 3) {
					if (i == 1) {                                                   //Easy,hard offset
						selectScButtons[i].setPosition(hPos, gPos);
						selectScActionsRev[i].setPosition(hPos, gPos);                //Reverse action locations
					} else {
						selectScButtons[i].setPosition(hPos, gPos + 200);
						selectScActionsRev[i].setPosition(hPos, gPos + 200);        //Reverse action locations
					}
					dPos += 425;
					selectScActions[i].setPosition(dPos, sPos);
					hPos += 425;
				} else {
					offSet += 50;                                                   //New game, level select offset
					gPos -= 150;
					sPos -= 150;
					selectScButtons[i].setPosition(750, gPos + offSet);
					selectScActionsRev[i].setPosition(750, gPos + offSet);
					selectScActions[i].setPosition(dPos - 425, sPos);
				}
				zPos -= 150;
				xPos -= 150;
			} else {
				//Last 2 buttons
				textButtonArray[i].setHeight(height);
				textButtonArray[i].setWidth(width);
				textButtonArray[i].setOrigin(width / 2, height / 2);

				buttonActionsArray[i] = new MoveToAction();
				buttonActionsArray[i].setDuration(animationIn);
				buttonActionsArray[i].setInterpolation(Interpolation.pow5);

				buttonReverseActionsArray[i] = new MoveToAction();
				buttonReverseActionsArray[i].setInterpolation(Interpolation.pow5Out);
				buttonReverseActionsArray[i].setDuration(animationOut);

				exitLeftAnimation[i] = new MoveToAction();
				exitLeftAnimation[i].setInterpolation(Interpolation.pow5Out);
				exitLeftAnimation[i].setDuration(2);
				exitLeftAnimation[i].setPosition(-2500, xPos);

				exitRightAnimation[i] = new MoveToAction();
				exitRightAnimation[i].setInterpolation(Interpolation.pow5Out);
				exitRightAnimation[i].setDuration(2);
				exitRightAnimation[i].setPosition(3250, xPos);
			}
		}
		textButtonArray[5].setPosition(125, -100);                            	//Quit Button initial location
		textButtonArray[6].setPosition(1375, -100);                            	//About Button initial location
		startToOptions[5].setPosition(50, 1920);
		startToOptions[6].setPosition(1475, 1920);

		buttonActionsArray[5].setPosition(325, lowHeight);                   	//Quit Button action position
		buttonActionsArray[6].setPosition(1175, lowHeight);                    	//About Button action position
		buttonActionsArray[7].setPosition(1500, 240);
		buttonActionsArray[8].setPosition(0, 240);

		buttonReverseActionsArray[4].setPosition(750, -400);
		buttonReverseActionsArray[5].setPosition(325, -400);                	//Quit Button reverse action position
		buttonReverseActionsArray[6].setPosition(1175, -400);                	//About Button reverse action position
		buttonReverseActionsArray[7].setPosition(1500, -100);
		buttonReverseActionsArray[8].setPosition(0, -100);

		selectScButtons[5].setPosition(-420, lowHeight);
		selectScButtons[6].setPosition(1920, lowHeight);
		selectScActions[5].setPosition(325, lowHeight);
		selectScActions[6].setPosition(1175, lowHeight);
		selectScActionsRev[5].setPosition(-420, lowHeight);
		selectScActionsRev[6].setPosition(1920, lowHeight);
		exitLeftAnimation[5].setPosition(-2920, lowHeight);
		exitLeftAnimation[6].setPosition(-1875, lowHeight);
		exitRightAnimation[5].setPosition(2830, lowHeight);
		exitRightAnimation[6].setPosition(3680, lowHeight);
		textButtonArray[7].setPosition(1625, -100);
		textButtonArray[8].setPosition(0, -100);
		selectScButtons[1].setColor(Color.BLUE);                                //Medium is set
		selectScButtons[1].setDisabled(true);

		asteroidPositionChecker();                                                //Checks if asteroids goes off-screen

		stage.addActor(bg);                                                    	//Adding Background

		for (int n = 0; n < colors.length; n++) {                               //Loops Colors
			for (int i = 0; i < asteroidArray.length; i++) {                	//Adds to screen based on color,
				if (asteroidArray[i].getColor().equals(colors[n])) {        	//Each color is tied to a speed and size
					if (!colorsOn) {
						asteroidArray[i].setColor(Color.WHITE);             	//Removes color
					}
					stage.addActor(asteroidArray[i]);							//Adding Asteroids
				}
			}
		}

		//Adding buttons to the main menu screen
		for (int i = 0; i < textButtonArray.length-2; i++) {
			stage.addActor(textButtonArray[i]);
			stage.addActor(selectScButtons[i]);
			textButtonArray[i].setChecked(true);
			selectScButtons[i].setChecked(true);
		}
		stage.addActor(textButtonArray[7]);
		stage.addActor(textButtonArray[8]);

		//Adding sub-screens onto the stage
		stage.addActor(myOptionsMenu);											//Options screen
		stage.addActor(myAboutScreen);											//About screen
		stage.addActor(myHighScoresSubScreen);									//High score screen
		stage.addActor(myAchievementsSubScreen);								//Achievement screen
		stage.addActor(myMusicSubScreen);										//Music screen

		//Adding ship selection screen
		shipScreen = new SelectShipScreen(manager);
		shipScreen.addAction(Actions.sequence(Actions.alpha(0)));
		shipScreen.setTouchable(Touchable.disabled);
		stage.addActor(shipScreen);

		//Small delay before enter animation
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				enterAnimation();                                                //Enter Animation
			}
		}, .5f);
		addingListeners();                                                    	//Adding action listeners
	}

	public VisTextButton getRetro() {
		return myOptionsMenu.getRetro();
	}
	public TextButton getFullscreen() {
		return myOptionsMenu.getFullscreen();
	}
	public void reverseMenuAnimation() {
		for (int i = 0; i < buttonReverseActionsArray.length; i++) {            //Looping reverse movements
			textButtonArray[i].clearActions();                                	//Removing old actions
			textButtonArray[i].addAction(buttonReverseActionsArray[i]);			//Adding Reverse action
			buttonReverseActionsArray[i].restart();
		}
	}
	public void enterAnimation() {
		for (int i = 0; i < textButtonArray.length; i++) {                      //Looping trough all Buttons
			textButtonArray[i].clearActions();                                	//Removing old actions
			textButtonArray[i].addAction(buttonActionsArray[i]);            	//Adding Actions to Buttons
			buttonActionsArray[i].restart();
		}
	}
	public void reverseSelectScreen() {
		for (int i = 0; i < selectScButtons.length; i++) {                      //Looping trough all Buttons
			selectScButtons[i].clearActions();                                	//Removing old actions
			selectScButtons[i].addAction(selectScActionsRev[i]);                //Adding Actions to Buttons
			selectScActionsRev[i].restart();
		}
	}
	public void forwardSelectScreen() {
		for (int i = 0; i < selectScButtons.length; i++) {                     	//Looping trough all Buttons
			selectScButtons[i].clearActions();                                	//Removing old actions
			selectScButtons[i].addAction(selectScActions[i]);               	//Adding Actions to Buttons
			selectScActions[i].restart();
		}
	}
	public void exitMenuUp() {
		for (int i = 0; i < startToOptions.length; i++) {						//Looping reverse movements
			textButtonArray[i].clearActions();									//Removing old actions
			textButtonArray[i].addAction(startToOptions[i]);					//Adding Reverse action
			startToOptions[i].restart();
		}
	}
	public void exitMenuCentre() {
		for(int i=0;i<textButtonArray.length;i++){
			textButtonArray[i].setPosition(750,500);
			textButtonArray[i].clearActions();
			textButtonArray[i].addAction(buttonActionsArray[i]);            	//Adding Actions to Buttons
			textButtonArray[i].addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
			buttonActionsArray[i].restart();
		}
	}
	public void exitRightAnimation() {
		for (int i = 0; i < buttonReverseActionsArray.length; i++) {            //Looping reverse movements
			textButtonArray[i].clearActions();                                	//Removing old actions
			textButtonArray[i].addAction(exitRightAnimation[i]);        		//Adding Reverse action
			exitRightAnimation[i].restart();
		}
	}
	public void exitLeftAnimation() {
		for (int i = 0; i < buttonReverseActionsArray.length; i++) {            //Looping reverse movements
			textButtonArray[i].clearActions();                               	//Removing old actions
			textButtonArray[i].addAction(exitLeftAnimation[i]);        			//Adding Reverse action
			exitLeftAnimation[i].restart();
		}
	}
	boolean temp = false;
	public void addingListeners() {                                            //Setting listeners

		myHighScoresSubScreen.getBackToStart().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				myHighScoresSubScreen.exitAnimation();
				enterAnimation();
				myHighScoresSubScreen.getBackToStart().setChecked(true);
			}
		});
		myAboutScreen.getBackToStart().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				myAboutScreen.exitAnimation();
				enterAnimation();
			}
		});
		myMusicSubScreen.getBackToStart().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				myMusicSubScreen.exitAnimation();
				enterAnimation();
			}
		});
		myOptionsMenu.getMyTxButton().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				enterAnimation();
				myOptionsMenu.exitGraphicsScreenDown();
				myOptionsMenu.getMyTxButton().setChecked(true);
			}
		});
		myAchievementsSubScreen.getBackToStart().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				myAchievementsSubScreen.exitAnimation();
				enterAnimation();
			}
		});

		textButtonArray[0].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				reverseMenuAnimation();
				forwardSelectScreen();
				textButtonArray[0].setChecked(true);
				System.out.println("Loading level 1");
			}
		});
		textButtonArray[1].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				menu.setVisible(false);
				textButtonArray[1].setChecked(true);
			}
		});
		textButtonArray[2].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				exitRightAnimation();
				myAchievementsSubScreen.enterAnimation();
				textButtonArray[2].setChecked(true);
			}
		});
		textButtonArray[3].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				exitLeftAnimation();
				myHighScoresSubScreen.enterAnimation();
				textButtonArray[3].setChecked(true);
			}
		});
		textButtonArray[4].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				exitMenuUp();
				myOptionsMenu.forwardGraphicsScreen();
				textButtonArray[4].setChecked(true);
			}
		});
		textButtonArray[5].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				exitRightAnimation();
				myAboutScreen.enterAnimation();
				textButtonArray[5].setChecked(true);
			}
		});
		textButtonArray[6].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				textButtonArray[6].setChecked(true);
				Gdx.app.exit();
			}
		});

		selectScButtons[0].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				playClick();
				selectScButtons[0].setChecked(true);
				if (selectScButtons[1].isDisabled()) {                //Medium to ety
					for (int i = 0; i < asteroidArray.length; i++) {
						asteroidActions[i].setDuration(asteroidActions[i].getDuration() * 10);
						asteroidActions[i].restart();
						System.out.print("Asteroid: " + i + "Speed: " + asteroidActions[i].getDuration() + " \n");
					}
				} else if (selectScButtons[2].isDisabled()) {            //Hard to easy
					for (int i = 0; i < asteroidArray.length; i++) {
						asteroidActions[i].setDuration(asteroidActions[i].getDuration() * 100);
						asteroidActions[i].restart();
						System.out.print("Asteroid: " + i + "Speed: " + asteroidActions[i].getDuration() + " \n");
					}
				}

				selectScButtons[0].setDisabled(true);
				selectScButtons[1].setDisabled(false);
				selectScButtons[2].setDisabled(false);

				selectScButtons[0].setColor(Color.BLUE);
				selectScButtons[1].setColor(Color.WHITE);
				selectScButtons[2].setColor(Color.WHITE);
				difficulty = Difficulty.EASY;
			}
		});
		selectScButtons[1].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				selectScButtons[1].setChecked(true);
				playClick();
				if (selectScButtons[0].isDisabled()) {                //Easy to medium
					for (int i = 0; i < asteroidArray.length; i++) {
						asteroidActions[i].setDuration(asteroidActions[i].getDuration() / 10);
						asteroidActions[i].restart();
						System.out.print("Asteroid: " + i + "Speed: " + asteroidActions[i].getDuration() + " \n");
					}
				} else if (selectScButtons[2].isDisabled()) {            //Hard to medium
					for (int i = 0; i < asteroidArray.length; i++) {
						asteroidActions[i].setDuration(asteroidActions[i].getDuration() * 10);
						asteroidActions[i].restart();
						System.out.print("Asteroid: " + i + "Speed: " + asteroidActions[i].getDuration() + " \n");
					}
				}

				selectScButtons[0].setDisabled(false);
				selectScButtons[1].setDisabled(true);
				selectScButtons[2].setDisabled(false);

				selectScButtons[0].setColor(Color.WHITE);
				selectScButtons[1].setColor(Color.BLUE);
				selectScButtons[2].setColor(Color.WHITE);
				difficulty = Difficulty.MEIDUM;
			}
		});
		selectScButtons[2].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				selectScButtons[2].setChecked(true);
				playClick();
				if (selectScButtons[0].isDisabled()) {                    //Easy to hard
					for (int i = 0; i < asteroidArray.length; i++) {
						asteroidActions[i].setDuration(asteroidActions[i].getDuration() / 100);
						asteroidActions[i].restart();
						System.out.print("Asteroid: " + i + "Speed: " + asteroidActions[i].getDuration() + " \n");
					}
				} else if (selectScButtons[1].isDisabled()) {                //Medium to hard
					for (int i = 0; i < asteroidArray.length; i++) {
						asteroidActions[i].setDuration(asteroidActions[i].getDuration() / 10);
						asteroidActions[i].restart();
						System.out.print("Asteroid: " + i + "Speed: " + asteroidActions[i].getDuration() + " \n");
					}
				}

				selectScButtons[0].setDisabled(false);
				selectScButtons[1].setDisabled(false);
				selectScButtons[2].setDisabled(true);

				selectScButtons[0].setColor(Color.WHITE);
				selectScButtons[1].setColor(Color.WHITE);
				selectScButtons[2].setColor(Color.BLUE);
				difficulty = Difficulty.HARD;
			}
		});
		selectScButtons[3].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				reverseSelectScreen();
				forwardShipScreen();
				selectScButtons[3].setChecked(true);
			}
		});
		selectScButtons[4].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				selectScButtons[4].setChecked(true);
			}
		});
		selectScButtons[5].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				selectScButtons[4].setChecked(true);
				playClick();
				reverseSelectScreen();
				enterAnimation();
				selectScButtons[5].setChecked(true);

			}
		});
		selectScButtons[6].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				reverseSelectScreen();
				forwardShipScreen();
				selectScButtons[6].setChecked(true);
			}
		});
		textButtonArray[7].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				textButtonArray[7].setChecked(true);
				playClick();
				exitLeftAnimation();
				myMusicSubScreen.enterAnimation();
			}
		});

		shipScreen.getBackToStart().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				shipScreen.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.5f)));
				shipScreen.setTouchable(Touchable.disabled);
				forwardSelectScreen();
				textButtonArray[7].setChecked(false);
			}
		});

	}
	public void shipScInvisi(){
		shipScreen.invisi();
	}
	public VisTextButton getB1a(){
		return shipScreen.getB1();
	}
	public VisTextButton getB2a(){
		return shipScreen.getB2();
	}
	public VisTextButton getB3a(){
		return shipScreen.getB3();
	}
	public VisTextButton getB4a(){
		return shipScreen.getB4();
	}
	VisImageTextButton.VisImageTextButtonStyle squareGreenRed;

	SelectShipScreen shipScreen;

	public void forwardShipScreen(){
		shipScreen.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
		shipScreen.setTouchable(Touchable.enabled);

	}
	VisTextButton aaa;
	public VisTextButton getMenuButton() {
		return aaa;
	}

	private int shipType = 0;

	public void setShipType(int shipType) {
		this.shipType = shipType;
	}

	public int getShipType() {
		return shipType;
	}

	public VisImageTextButton getHollowDeckButton() {
		return textButtonArray[8];
	}
	Thread threadA;
	public void asteroidPositionChecker() {
		threadA = new Thread(new AsteroidDetection(), "Thread A");
		threadA.start();
	}

	public void playLaser() {
		asteroidLaser.play();
	}

	public void playClick() {
		buttonClick.play();
	}
	public void setStart(){
		playClick();
		reverseSelectScreen();
		enterAnimation();
		shipScreen.visi();
		shipScreen.addAction(Actions.sequence(Actions.alpha(0)));
		shipScreen.setTouchable(Touchable.disabled);
		hud.startAgain();
	}
	public InputListener getNewAsteroidListener(int a) {
		final int position = a;
		final float vaporiseTime = .1f;
		InputListener asteroidListener = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				playLaser();
				asteroidArray[position].setColor(Color.BLACK);
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						asteroidArray[position].setColor(Color.RED);
						Timer.schedule(new Timer.Task() {
							@Override
							public void run() {
								asteroidArray[position].addAction(Actions.fadeOut(1));        //Fading
								asteroidArray[position].setColor(Color.BLACK);
								Timer.schedule(new Timer.Task() {
									@Override
									public void run() {
										asteroidArray[position].setColor(Color.RED);
										Timer.schedule(new Timer.Task() {
											@Override
											public void run() {
												asteroidArray[position].setColor(Color.BLACK);
												Timer.schedule(new Timer.Task() {
													@Override
													public void run() {
														asteroidArray[position].setColor(Color.RED);
														Timer.schedule(new Timer.Task() {
															@Override
															public void run() {
																asteroidArray[position].setVisible(false);
																asteroidArray[position].setColor(Color.WHITE);
															}
														}, vaporiseTime);
													}
												}, vaporiseTime);
											}
										}, vaporiseTime);
									}
								}, vaporiseTime);
							}
						}, vaporiseTime);
					}
				}, vaporiseTime);
				return true;
			}
		};
		return asteroidListener;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	public boolean isRetroGame() {
		return myOptionsMenu.isRetroGame();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		VisUI.dispose();
		System.out.print("Disposing menu.\n");
	}

	public void setChecked(){
		for(int i=0;i<textButtonArray.length;i++){
			textButtonArray[i].setChecked(true);
			selectScButtons[i].setChecked(true);
		}
	}

	private VisImageTextButton[] textButtonArray, selectScButtons = {};                	//Buttons (Menu)
	private MoveToAction[] buttonActionsArray, selectScActions, selectScActionsRev,
			startToOptions, buttonReverseActionsArray, asteroidActions,
			exitLeftAnimation, exitRightAnimation = {};    						//Button actions
	private Image[] asteroidArray;                                        		//Asteroids
	private Image bg, menu;
	private Skin buttonSkin;                                                	//Images are used as skins of the button
	private TextButton.TextButtonStyle style;
	private BitmapFont font;
	private TextureAtlas buttonsAtlas;                                        	//Images
	private int amountOfAsteroids, amountOfAsteroidTypes, randomSpeed, randomSizes, randomRotate,
			randomTypes, randomPosX, randomPosY;
	private int height = 150;                                                	//Button Height
	private int width = 420;                                                	//Button Width
	private int lowHeight = 105;
	private int offSet = 0;
	private int bPos = 780;
	private int vPos = 630;
	private int xPos = 780;
	private int dPos = -100;
	private int sPos = 550;
	private int gPos = 1550;
	private int hPos = 325;
	private int zPos = 1600;
	private Sound buttonClick, asteroidLaser;
	private Stage stage;
	boolean finished = false;

	class AsteroidDetection implements Runnable {
		Random random = new Random();                                                    //Random number generator

		public void run() {
			while (true) {
				for (int i = 0; i < asteroidArray.length; i++) {
					int decide = random.nextInt(2);
					int randomPosX = random.nextInt(2400 - 2120) + 2120;                    //Loop generates new location
					int randomPosY = random.nextInt(1380) + 1;

					int randomPosL = random.nextInt(2000) + 1;                            //Loop generates new location
					int randomPosM = random.nextInt(1450 - 1200) + 1200;

					if ((asteroidArray[i].getX() < -300) || (asteroidArray[i].getY() < -200)) {
						System.out.print("Asteroid " + i + " " + " is off screen. ");
						if (decide == 1) {
							asteroidArray[i].setPosition(randomPosX, randomPosY);       //Setting Initial positions
							System.out.print(" Moved to [" + randomPosX + "]" + "[" + randomPosY + "].\n");
						} else {
							asteroidArray[i].setPosition(randomPosL, randomPosM);       //Setting Initial positions
							System.out.print(" Moved to [" + randomPosL + "]" + "[" + randomPosM + "].\n");
						}
						asteroidArray[i].setVisible(true);
						asteroidArray[i].addAction(Actions.fadeIn(1));                    //Fading
						asteroidActions[i].restart();                                    //Restarts the action
					}
				}
				try {
					Thread.currentThread();
					Thread.sleep(500);                                                    //Checks once every second
				} catch (InterruptedException e) {

				}
			}
		}
	}
}

