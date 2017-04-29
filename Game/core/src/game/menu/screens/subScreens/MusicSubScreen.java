package game.menu.screens.subScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import game.util.Constants;
import game.menu.screens.utils.AbstractSubScreen;
import game.util.config.Config;

public class MusicSubScreen extends AbstractSubScreen implements Constants, Config {
    Thread threadB;
    public MusicSubScreen() {
        if(zoom){
            setPosition(1920, -30);
            exitAnimation.setPosition(1920, -30);
        } else {
            setPosition(1920, 100);
            exitAnimation.setPosition(1920, 100);
        }
        bg1.remove();
        //Music title
        VisTextButton titleA = new VisTextButton("Audio");
        titleA.getStyle().font = myFont;
        titleA.setSize(200, 80);
        titleA.setPosition(625, 750, Align.bottomLeft);
        titleA.setColor(Color.TEAL);

        sliderPreview = new VisLabel("Music");
        sliderPreview.setSize(200, 80);
        sliderPreview.setPosition(625, 750, Align.bottomLeft);
        sliderPreview.setColor(Color.TEAL);
        sliderPreview.setVisible(false);

        //Lists
        musicList = new ArrayList<SongData>();
        songBarList = new  ArrayList<VisImageTextButton>();

        //Song objects
        //Song objects
        musicList.add(0, new SongData("Telescope", "                                            Starset                          ", "Telescope.mp3", "5:31", 331));
        musicList.add(1, new SongData("My Demons", "                                         Starset                          ", "My Demons.mp3", "4:48", 284));
        musicList.add(2, new SongData("It Has Begun", "                                        Starset                          ", "It Has Begun.mp3", "5:16", 316));
        musicList.add(3, new SongData("Halo", "                                                      Starset                          ", "Halo.mp3", "3:45",225));
        musicList.add(4, new SongData("Down with the Fallen", "                          Starset                          ", "Down with the Fallen.mp3", "4:17", 257));
        musicList.add(5, new SongData("Dark on Me", "                                          Starset                          ", "Dark on Me.mp3", "5:38", 338));
        musicList.add(6, new SongData("Carnivore", "                                             Starset                          ", "Carnivore.mp3", "4:22", 262));
        musicList.add(7, new SongData("Antigravity", "                                           Starset                          ", "Antigravity.mp3", "6:09", 369));

        //Removing inherited assets


        backToStart.setPosition(950, 750, Align.bottomLeft);

        //Top background
        bg = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_5.png")));                //Menu image
        bg.setSize(1275, 640);
        bg.setPosition(0, 190, Align.bottomLeft);

        //Bottom background
        bg2 = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_1.png")));                //Menu image
        bg2.setSize(1275, 190);
        bg2.setPosition(0, 0, Align.bottomLeft);
        group.addActor(bg2);

        //Music Slider
        mySlider = new VisSlider(0, 10, 1, false);

        //Last timer indicating song length        //First timer indicating song progress
        finish = new VisLabel();
        current = new VisLabel();

        current.setText("0:00");
        finish.setText("5:31");

        //Default assets, getting style parts
        VisTextButton defaultButton = new VisTextButton("");
        VisSlider defaultSlider = new VisSlider(0,10,1,false);

        //Creating slider style
        VisSlider.SliderStyle myStyle = new VisSlider.SliderStyle();
        myStyle.knob = new Image(new Texture(Gdx.files.internal("img/musicPlayer/sliderNormal.png"))).getDrawable();
        myStyle.knobOver = new Image(new Texture(Gdx.files.internal("img/musicPlayer/sliderHover.png"))).getDrawable();
        myStyle.knobDown = new Image(new Texture(Gdx.files.internal("img/musicPlayer/sliderClick.png"))).getDrawable();
        myStyle.background = defaultSlider.getStyle().background;
        mySlider.setStyle(myStyle);

        //Styles for buttons, this includes images for normal, click, hover
        previousStyle = new VisTextButton.VisTextButtonStyle();
        previousStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/previousNormal.png"))).getDrawable();
        previousStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/previousHover.png"))).getDrawable();
        previousStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/previousClick.png"))).getDrawable();
        previousStyle.font = defaultButton.getStyle().font;

        pauseStyle = new VisTextButton.VisTextButtonStyle();
        pauseStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/pauseNormal.png"))).getDrawable();
        pauseStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/pauseHover.png"))).getDrawable();
        pauseStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/pauseClick.png"))).getDrawable();
        pauseStyle.font = defaultButton.getStyle().font;

        playStyle = new VisTextButton.VisTextButtonStyle();
        playStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/playNormal.png"))).getDrawable();
        playStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/playHover.png"))).getDrawable();
        playStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/playClick.png"))).getDrawable();
        playStyle.font = defaultButton.getStyle().font;

        nextStyle = new VisTextButton.VisTextButtonStyle();
        nextStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/nextNormal.png"))).getDrawable();
        nextStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/nextHover.png"))).getDrawable();
        nextStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/nextClick.png"))).getDrawable();
        nextStyle.font = defaultButton.getStyle().font;

        muteStyle = new VisTextButton.VisTextButtonStyle();
        muteStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/muteNormal.png"))).getDrawable();
        muteStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/muteHover.png"))).getDrawable();
        muteStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/muteClick.png"))).getDrawable();
        muteStyle.font = defaultButton.getStyle().font;


        shuffleStyle = new VisTextButton.VisTextButtonStyle();
        shuffleStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/shuffleNormal.png"))).getDrawable();
        shuffleStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/shuffleHover.png"))).getDrawable();
        shuffleStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/shuffleClick.png"))).getDrawable();
        shuffleStyle.font = defaultButton.getStyle().font;

        //Album Art
        albumArtStyle = new VisImageTextButton.VisImageTextButtonStyle();
        albumArtStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/AlbumArt1.png"))).getDrawable();
        albumArtStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/AlbumArt1.png"))).getDrawable();
        albumArtStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/AlbumArt1.png"))).getDrawable();
        albumArtStyle.font = defaultButton.getStyle().font;

        //Playing Icon style
        currentPlayStyle1 = new VisImageTextButton.VisImageTextButtonStyle();
        currentPlayStyle1.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/currentPlay1.png"))).getDrawable();
        currentPlayStyle1.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/currentPlay1.png"))).getDrawable();
        currentPlayStyle1.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/currentPlay1.png"))).getDrawable();
        currentPlayStyle1.font = defaultButton.getStyle().font;

        currentPlayStyle2 = new VisImageTextButton.VisImageTextButtonStyle();
        currentPlayStyle2.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/currentPlay2.png"))).getDrawable();
        currentPlayStyle2.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/currentPlay2.png"))).getDrawable();
        currentPlayStyle2.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/currentPlay2.png"))).getDrawable();
        currentPlayStyle2.font = defaultButton.getStyle().font;

        shuffleOffStyle = new VisImageTextButton.VisImageTextButtonStyle();
        shuffleOffStyle.up = new Image(new Texture(Gdx.files.internal("img/musicPlayer/shuffleOffNormal.png"))).getDrawable();
        shuffleOffStyle.over = new Image(new Texture(Gdx.files.internal("img/musicPlayer/shuffleOffHover.png"))).getDrawable();
        shuffleOffStyle.down = new Image(new Texture(Gdx.files.internal("img/musicPlayer/shuffleOffClick.png"))).getDrawable();
        shuffleOffStyle.font = defaultButton.getStyle().font;

        //Buttons with styles added
        previous = new VisTextButton("", previousStyle);
        pause = new VisTextButton("", pauseStyle);
        play = new VisTextButton("", playStyle);
        next = new VisTextButton("", nextStyle);
        mute = new VisTextButton("", muteStyle);
        shuffle = new VisTextButton("", shuffleStyle);
        shuffleOff = new VisTextButton("", shuffleOffStyle);
        albumArt = new VisTextButton("", albumArtStyle);
        currentSong = new VisLabel();
        currentPlaying = new VisTextButton("", currentPlayStyle2);

        //Table for displaying songs
        songTable = new VisTable();

        //Setting sizes
        previous.setSize(54, 54);
        pause.setSize(54, 54);
        play.setSize(54, 54);
        next.setSize(54, 54);
        mute.setSize(54, 54);
        shuffle.setSize(54, 54);
        shuffleOff.setSize(54, 54);

        albumArt.setSize(75,75);
        mySlider.setSize(975, 50);
        currentPlaying.setSize(40, 40);
        songTable.setSize(1225, 200);
        finish.setSize(50, 50);
        current.setSize(50, 50);

        //Set Focusable false
        previous.setFocusBorderEnabled(false);
        pause.setFocusBorderEnabled(false);
        play.setFocusBorderEnabled(false);
        next.setFocusBorderEnabled(false);
        shuffle.setFocusBorderEnabled(false);
        albumArt.setFocusBorderEnabled(false);
        currentPlaying.setFocusBorderEnabled(false);

        //Setting positions
        shuffle.setPosition(1075, 35, Align.bottomLeft);
        shuffleOff.setPosition(1075, 35, Align.bottomLeft);
        mute.setPosition(1000, 35, Align.bottomLeft);
        next.setPosition(925, 35, Align.bottomLeft);
        pause.setPosition(850, 35, Align.bottomLeft);
        play.setPosition(850, 35, Align.bottomLeft);
        previous.setPosition(775, 35, Align.bottomLeft);
        albumArt.setPosition(150,25, Align.bottomLeft);
        mySlider.setPosition(150, 110, Align.bottomLeft);
        current.setPosition(50, 110, Align.bottomLeft);
        finish.setPosition(1175, 110, Align.bottomLeft);
        currentSong.setPosition(250, 62, Align.bottomLeft);
        currentPlaying.setPosition(30,687, Align.bottomLeft);
        songTable.setPosition(25, 600, Align.topLeft);

        //Music bar styles, hover, click and normal images
        barStyle1 = new VisImageTextButton.VisImageTextButtonStyle();
        barStyle1.up = new Image(new Texture(Gdx.files.internal("img/button/button_1.png"))).getDrawable();
        barStyle1.over = new Image(new Texture(Gdx.files.internal("img/button/button_3.png"))).getDrawable();
        barStyle1.down = new Image(new Texture(Gdx.files.internal("img/button/button_3.png"))).getDrawable();
        barStyle1.font = defaultButton.getStyle().font;

        barStyle2 = new VisImageTextButton.VisImageTextButtonStyle();
        barStyle2.up = new Image(new Texture(Gdx.files.internal("img/button/button_2.png"))).getDrawable();
        barStyle2.over = new Image(new Texture(Gdx.files.internal("img/button/button_3.png"))).getDrawable();
        barStyle2.down = new Image(new Texture(Gdx.files.internal("img/button/button_3.png"))).getDrawable();
        barStyle2.font = defaultButton.getStyle().font;

        //Creating array-list of song bars(buttons), adding them to the table
        for(int i=0;i<8;i++) {
            final int pos = i;

            //Setting bar style
            if(pos % 2 == 0){
                songBar = new VisImageTextButton("", barStyle1);
            }else{
                songBar = new VisImageTextButton("", barStyle2);
            }

            final VisLabel songName;
            final VisLabel artist;
            final VisTextButton play2;
            final VisLabel songLength;

            songName = new VisLabel(musicList.get(i).getName());
            artist = new VisLabel(musicList.get(i).getArtist());
            songLength = new VisLabel("                           "+musicList.get(i).getLength());
            play2 = new VisTextButton("",playStyle);

            //Initial colour
            songName.setColor(Color.BLACK);
            artist.setColor(Color.BLACK);
            songLength.setColor(Color.BLACK);

            play2.setSize(54, 54);
            play2.setFocusBorderEnabled(false);

            songBar.setFocusBorderEnabled(false);
            songBar.align(Align.center);
            songBar.add(songName);
            songBar.add(artist);
            songBar.add(play2);
            songBar.add(songLength);
            songBar.setDisabled(true);
            //Listener
            play2.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    stopSong();
                    currentPlaying.setStyle(currentPlayStyle1);

                    //Updating current song position
                    previousSong = getSongPos(songName.getText().toString());
                    int barLevel = 0;

                    if (shuffleOff.isVisible()) {
                        //Setting current song title and length
                        currentSong.setText(musicList.get(pos).getName());
                        finish.setText(musicList.get(pos).getLength());

                        //Calculating position of play icon
                        barLevel = 687 - (pos * 60);

                        musicList.get(pos).playSong();
                    } else {
                        //Setting current song title and length
                        currentSong.setText(musicList.get(previousSong).getName());
                        finish.setText(musicList.get(previousSong).getLength());

                        //Calculating position of play icon
                        barLevel = 687 - (previousSong * 60);

                        musicList.get(previousSong).playSong();
                    }
                    currentPlaying.setPosition(30, barLevel, Align.bottomLeft);

                    play.setVisible(false);
                    pause.setVisible(true);
                }
            });

            //Hovering bar, exiting hover
            songBar.addListener(new InputListener() {
                boolean a = false;  //Used to prevent a touchdown from running the exit function

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    currentPlaying.setStyle(currentPlayStyle2);
                    a = true;
                    songName.setColor(Color.WHITE);
                    artist.setColor(Color.WHITE);
                    songLength.setColor(Color.WHITE);
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //When hovering the song bar, this happens
                    songName.setColor(Color.WHITE);
                    artist.setColor(Color.WHITE);
                    songLength.setColor(Color.WHITE);
                    if(currentSong.getText().equals( songName.getText())){
                        currentPlaying.setStyle(currentPlayStyle1);
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    //When exiting the bar(not hovering), this happens
                    //If a touchdown just occurred, does not run the exit function
                    //Sub if: depending on which style, changes style
                    if (!a) {
                        if(currentSong.getText().equals( songName.getText())) {
                            currentPlaying.setStyle(currentPlayStyle2);
                        }
                        if(!songBar.isOver()) {
                            songName.setColor(Color.BLACK);
                            artist.setColor(Color.BLACK);
                            songLength.setColor(Color.BLACK);
                        }
                    }
                    a = false;
                }

            });
            songBarList.add(songBar);

            //Creating table of songs, displaying the song bars as buttons, adding from song bar list
            songTable.row().minHeight(60).minWidth(1225).center().expandX().top();
            songTable.add(songBarList.get(i));

            for(int ii=0;ii<musicList.size();ii++) {
                musicList.get(ii).setMyListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        fireNext();
                        System.out.print("Playing next song!");
                    }
                });
            }

        }

        group.addActor(bg);
        group.addActor(bg2);
        group.addActor(titleA);
        group.addActor(backToStart);
        group.addActor(songTable);

        group.addActor(mySlider);
        group.addActor(current);
        group.addActor(finish);

        group.addActor(previous);
        group.addActor(pause);
        group.addActor(play);
        group.addActor(next);
        group.addActor(mute);
        group.addActor(shuffle);
        group.addActor(shuffleOff);
        group.addActor(albumArt);
        group.addActor(currentSong);
        group.addActor(currentPlaying);
        group.addActor(sliderPreview);

        currentSong.setText("Biomechanics");
        musicList.get(0).playSong();
        musicList.get(0).pauseSong();
        previousSong = 0;

        threadB = new Thread(new SongProgressTimer(), "Thread B");
        threadB.start();

        currentPlaying.setTouchable(Touchable.disabled);

        pause.setVisible(false);
        shuffle.setVisible(false);

        pause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                musicList.get(previousSong).pauseSong();
                pause.setVisible(false);
                play.setVisible(true);
            }
        });

        play.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                musicList.get(previousSong).playSong();
                play.setVisible(false);
                pause.setVisible(true);
            }
        });

        next.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                fireNext();
            }
        });

        shuffle.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shuffleOff.setVisible(true);
                shuffle.setVisible(false);

                //Using bubble sort to sort the songs and bars,
                int j;
                boolean flag = true;            // set flag to true to begin first pass
                SongData tempSong;              //holding variable
                VisImageTextButton tempBar;

                while (flag) {
                    flag = false;                //set flag to false awaiting a possible swap
                    for (j = 0; j < musicList.size() - 1; j++) {
                        if (musicList.get(j).getName().compareTo(musicList.get(j + 1).getName()) < 0) {
                            //swap elements
                            tempSong = musicList.get(j);
                            tempBar = songBarList.get(j);

                            musicList.set(j, musicList.get(j + 1));
                            songBarList.set(j, songBarList.get(j + 1));

                            musicList.set(j + 1, tempSong);
                            songBarList.set(j + 1, tempBar);
                            //shows a swap occurred
                            flag = true;
                        }
                    }
                }
                //Set play icon Y axis location after unShuffle
                previousSong = getSongPos(currentSong.getText().toString());
                int barLevel = 687 - (previousSong * 60);
                currentPlaying.setPosition(30, barLevel, Align.bottomLeft);
                System.out.println(30 + "--" + barLevel);

                //Refreshing table
                songTable.clear();
                for (int i = 0; i < songBarList.size(); i++) {
                    songTable.row().minHeight(60).minWidth(1225).center().expandX().top();

                    //Making sure style does not randomise
                    if (i % 2 == 0) {
                        songBarList.get(i).setStyle(barStyle1);
                    } else {
                        songBarList.get(i).setStyle(barStyle2);
                    }
                    songTable.add(songBarList.get(i));
                }
            }
        });
        shuffleOff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shuffleOff.setVisible(false);
                shuffle.setVisible(true);

                //Randomising both array-lists
                long seed = System.nanoTime();
                Collections.shuffle(songBarList, new Random(seed));
                Collections.shuffle(musicList, new Random(seed));

                //Refreshing table
                songTable.clear();
                for (int i = 0; i < songBarList.size(); i++) {
                    songTable.row().minHeight(60).minWidth(1225).center().expandX().top();

                    //Making sure style does not randomise
                    if (i % 2 == 0) {
                        songBarList.get(i).setStyle(barStyle1);
                    } else {
                        songBarList.get(i).setStyle(barStyle2);
                    }
                    songTable.add(songBarList.get(i));
                }

                //Set play icon Y axis location after shuffle
                previousSong = getSongPos(currentSong.getText().toString());
                int barLevel = 687 - (previousSong * 60);
                currentPlaying.setPosition(30, barLevel, Align.bottomLeft);
            }
        });
        previous.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stopSong();
                pause.setVisible(true);
                play.setVisible(false);

                //Playing previous song
                if (previousSong > 0) {
                    previousSong--;
                } else {
                    previousSong = 7;
                }
                musicList.get(previousSong).playSong();

                //Calculating play icon location
                int barLevel = 687 - (previousSong * 60);
                currentPlaying.setPosition(30, barLevel, Align.bottomLeft);

                //Setting current song label
                currentSong.setText(musicList.get(previousSong).getName());
                finish.setText(musicList.get(previousSong).getLength());

                //Setting slider to start
                mySlider.setValue(0);
            }
        });

        mySlider.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (mySlider.isDragging()) {
                    drag = true;
                    dropPosition = (int) mySlider.getValue();

                    //Audio preview label
                    int xAxis = (int) (146 + mySlider.getPercent() * 924);
                    int minutes = dropPosition / 60;
                    int seconds = dropPosition - minutes * 60;
                    String time;
                    if (seconds < 10) {
                        time = minutes + ":0" + seconds;
                    } else {
                        time = minutes + ":" + seconds;
                    }

                    sliderPreview.setPosition(xAxis, 170);
                    sliderPreview.setText(time);
                    sliderPreview.setVisible(true);
                } else {
                    if (drag) {
                        musicList.get(previousSong).setPosition(dropPosition);
                        sliderPreview.setText("");
                        sliderPreview.setVisible(false);
                        drag = false;
                    }
                }
            }
        });
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                musicList.get(previousSong).playSong();
                play.setVisible(false);
                pause.setVisible(true);
                fireNext();
                fireNext();
                fireNext();
                fireNext();
                fireNext();
                fireNext();
                fireNext();
                fireNext();

            }
        }, 2);

    }

    public void stopSong() {
        //Stops all songs
        for (int z = 0; z < musicList.size(); z++) {
            final int a = z;
            musicList.get(a).stopSong();
        }
    }

    public int getSongPos(String a) {
        //Gets current song new Y axis position, after shuffle
        for(int z=0;z<musicList.size();z++){
            if(musicList.get(z).getName().equals(a) ) {
                return z;
            }
        }
        return 0;
    }
    public void fireNext(){
        stopSong();
        pause.setVisible(true);
        play.setVisible(false);

        //Playing next song
        if(previousSong <= 6){
            previousSong++;
        } else {
            previousSong = 0;
        }
        musicList.get(previousSong).playSong();

        //Calculating play icon location
        int barLevel = 687 - (previousSong*60);
        currentPlaying.setPosition(30, barLevel, Align.bottomLeft);

        //Setting current song label
        currentSong.setText(musicList.get(previousSong).getName());
        finish.setText(musicList.get(previousSong).getLength());
        finish.setText(musicList.get(previousSong).getLength());

        //Setting slider to start
        mySlider.setValue(0);
    }


    final private VisImageTextButton.VisImageTextButtonStyle barStyle1;
    final private VisImageTextButton.VisImageTextButtonStyle barStyle2;
    final private VisTextButton.VisTextButtonStyle playStyle;
    private VisLabel currentSong;
    private VisTable songTable;
    private VisTextButton previous, pause, play, next, mute, shuffle, shuffleOff, albumArt, currentPlaying;
    private VisSlider mySlider;
    private Image bg2, bg;
    private VisLabel current,finish;
    private VisImageTextButton.VisImageTextButtonStyle currentPlayStyle1,currentPlayStyle2;
    private VisImageTextButton.VisImageTextButtonStyle albumArtStyle;
    private VisTextButton.VisTextButtonStyle shuffleStyle,shuffleOffStyle,muteStyle,nextStyle,pauseStyle,previousStyle;
    private ArrayList<SongData> musicList;
    private ArrayList<VisImageTextButton> songBarList;
    private VisImageTextButton songBar;
    private int previousSong = 0;
    private VisLabel sliderPreview;
    private boolean drag = false;
    private int dropPosition = 1;
    private VisLabel title;

    public void dispose(){
        threadB.interrupt();
    }
    class SongProgressTimer implements Runnable {
        int minutes, seconds = 0;
        int time = 0;
        int length;
        String timeStamp = "";

        public void run() {
            while(true) {
                //Amount of seconds progressed into the song
                time = (int)Math.floor(musicList.get(previousSong).getSongPosition());

                //Length of song
                length = musicList.get(previousSong).getSeconds();

                minutes = time / 60;
                seconds = time - minutes * 60;
                if(seconds < 10){
                    timeStamp = minutes + ":0" + seconds;
                } else {
                    timeStamp = minutes + ":" + seconds;
                }
                current.setText(timeStamp);

                if(time < 1){
                    mySlider.setRange(0,length-1);
                } else {
                    if(!mySlider.isDragging() && !drag){
                        mySlider.setValue((minutes*60)+seconds);
                    }
                }

                try {
                    Thread.currentThread();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted Exception");
                }
            }
        }
    }
}

class SongData{
    private String name;
    private String artist;
    private String length;
    private Music song;
    private String directory;
    private int seconds;
    private Music.OnCompletionListener myListener;

    //To check if memory must be free up
    boolean needsDisposing = false;
    boolean needsCreating = false;

    public SongData(String name, String artist, String fileName, String length, int seconds){
        this.name = name;
        this.artist = artist;
        directory = "audio/music/"+fileName;
        this.length = length;
        this.seconds = seconds;
        song = Gdx.audio.newMusic(Gdx.files.internal(directory));
    }

    public int getSeconds() {
        return seconds;
    }

    public void setMyListener(Music.OnCompletionListener myListener) {
        this.myListener = myListener;
        song.setOnCompletionListener(myListener);
    }

    public Music getSong() {
        return song;
    }

    public String getName() {
        return name;
    }

    public String getLength() {
        return length;
    }

    public String getArtist() {
        return artist;
    }

    public void playSong(){
        if(needsCreating){
            song = Gdx.audio.newMusic(Gdx.files.internal(directory));
            song.setOnCompletionListener(myListener);
        }
        song.play();
        needsDisposing = true;
    }

    public void pauseSong(){
        song.pause();
        needsDisposing = true;
        needsCreating = false;
    }

    public void stopSong(){
        if(needsDisposing){
            song.stop();
            song.dispose();
            needsDisposing = false;
            needsCreating = true;
        }
    }

    public void loopSong(){
        song.setLooping(true);
    }

    public void loopSongOff(){
        song.setLooping(false);
    }

    public void setVolume(float volume){
        song.setVolume(volume);
    }

    public float getSongPosition(){
        if(song.isPlaying()) {
            return song.getPosition();
            // TODO: 23/03/2016 Error OCCURS HERE, mostly on android, // FIXME: 23/03/2016
        }
        return 0;
    }

    public void setPosition(float position){
        System.out.println(position);
        song.setPosition(position);
    }
}

