package game.util.inputDevices;

import com.badlogic.gdx.controllers.PovDirection;

public class XboxKeyMaps {

    //Main play buttons
    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;

    public static final int BUTTON_BACK = 6;
    public static final int BUTTON_START = 7;

    //Directional Pad on XBox Controller
    public static final PovDirection DPAD_CENTER = PovDirection.center;
    public static final PovDirection DPAD_NORTH = PovDirection.north;
    public static final PovDirection DPAD_SOUTH = PovDirection.south;
    public static final PovDirection DPAD_EAST = PovDirection.east;
    public static final PovDirection DPAD_WEST = PovDirection.west;
    public static final PovDirection DPAD_NORTHEAST = PovDirection.northEast;
    public static final PovDirection DPAD_NORTHWEST = PovDirection.northWest;
    public static final PovDirection DPAD_SOUTHWEST = PovDirection.southWest;
    public static final PovDirection DPAD_SOUTHEAST = PovDirection.southEast;

    //Bumper Buttons
    public static final int LEFT_BUMBPER = 4;
    public static final int RIGHT_BUMPER = 5;

    //Triggers
    public static final int TRIGGERS = 4; //value 0 to 1f

    //Joystick Buttons
    public static final int RIGHT_STICK = 9;
    public static final int LEFT_STICK = 8;

    //Joysticks Positions
    public static final int LEFT_STICK_X = 1; //-1 is left | +1 is right
    public static final int LEFT_STICK_Y = 0; //-1 is up | +1 is down

    public static final int RIGHT_STICK_X = 3; //-1 is left | +1 is right
    public static final int RIGHT_STICK_Y = 2; //-1 is up | +1 is down
}