package game.util.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadConfigFile {
    //Not in file values
    int animationIn = 2;
    float animationOut = .5f;
    BufferedReader bufferedReader;

    //Default values
    boolean colorsOn = false;
    boolean skipToGameOn = false;
    boolean debugModeOn = false;
    boolean animatedMenuOn = true;
    boolean zoom = true;
    int audio_Game = 100;
    int audio_Effects = 100;
    int audio_Music = 100;
    int audio_Menu = 100;

    String pathOnPc = "C:/config/config.txt";

    public ReadConfigFile(){
        checkOS();
    }

    public void checkOS(){
            String osType = System.getProperty("os.name");
            if(osType.contains("Linux") || osType.contains("Android")){
                checkIfExistsOnAndroid();
            } else {
                checkIfExistsOnPC();
            }
    }

    public void checkIfExistsOnPC(){
        if(new File(pathOnPc).isFile()) {
            getDetailsOnPC();
        } else {
            writeNewFileOnPC();
            getDetailsOnPC();
        }
    }

    public void writeNewFileOnPC(){
        try{
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(pathOnPc)));
            //Default values being written to a file
            bw.write("This is a configuration file.\r\n");
            bw.write("Settings and other data is stored here.\r\n");
            bw.write("\r\n");
            bw.write("false\r\n");
            bw.write("false\r\n");
            bw.write("false\r\n");
            bw.write("true\r\n");
            bw.write("false\r\n");
            bw.write("50\r\n");
            bw.write("50\r\n");
            bw.write("50\r\n");
            bw.write("50\r\n");

            bw.close();
    } catch (IOException e) {
        System.out.println("Error write a config file, loading default values, ERROR 76543");
        e.printStackTrace();
    }
    }

    public void checkIfExistsOnAndroid(){

    }

    public void getDetailsOnPC(){
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(pathOnPc)));
            //Skip first 3 lines,
        bufferedReader.readLine();
        bufferedReader.readLine();
        bufferedReader.readLine();

        colorsOn = Boolean.valueOf(bufferedReader.readLine());
        skipToGameOn = Boolean.valueOf(bufferedReader.readLine());
        debugModeOn = Boolean.valueOf(bufferedReader.readLine());
        animatedMenuOn = Boolean.valueOf(bufferedReader.readLine());
        zoom = Boolean.valueOf(bufferedReader.readLine());

            audio_Game = Integer.parseInt(bufferedReader.readLine());
            audio_Effects = Integer.parseInt(bufferedReader.readLine());
            audio_Music = Integer.parseInt(bufferedReader.readLine());
            audio_Menu = Integer.parseInt(bufferedReader.readLine());

        bufferedReader.close();
    } catch (IOException e) {
        System.out.println("Error reading config file, loading default values, ERROR 134431");
        e.printStackTrace();
    }
    }

    public void overRideFileOnPC(
            String animatedMenu,
            String zoom,
            String audio_game,
            String audio_effects,
            String audio_music,
            String audio_menu){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(pathOnPc)));
            //Default values being written to a file
            bw.write("This is a configuration file.\r\n");
            bw.write("Settings and other data is stored here.\r\n");
            bw.write("\r\n");
            bw.write("false\r\n");
            bw.write("false\r\n");
            bw.write("false\r\n");
            bw.write(animatedMenu+"\r\n");
            bw.write(zoom+"\r\n");
            bw.write(audio_game+"\r\n");
            bw.write(audio_effects+"\r\n");
            bw.write(audio_music+"\r\n");
            bw.write(audio_menu+"\r\n");

            bw.close();
        } catch (IOException e) {
            System.out.println("Error write a config file, loading default values, ERROR 76543");
            e.printStackTrace();
        }

    }


    public boolean getSkipToGameOn(){
        return skipToGameOn;
    }
    public boolean getColorsOn(){
        return colorsOn;
    }
    public boolean getDebugModeOn(){
        return debugModeOn;
    }
    public boolean getAnimatedMenuOn(){
        return animatedMenuOn;
    }
    public boolean getZoomOn() { return zoom;}
    public int getAudio_Effects() {
        return audio_Effects;
    }
    public int getAudio_Game() {
        return audio_Game;
    }
    public int getAudio_Menu() {
        return audio_Menu;
    }
    public int getAudio_Music() {
        return audio_Music;
    }
}
