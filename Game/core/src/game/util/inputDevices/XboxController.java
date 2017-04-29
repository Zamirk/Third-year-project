package game.util.inputDevices;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class XboxController {
    Controller controller;

    public void start(){
        if (Controllers.getControllers().size == 0) {
            System.out.println("No controllers attached.");
        } else {
            Array<Controller> controllerArray = Controllers.getControllers();            //Array of Controllers
            System.out.println(controllerArray.size + " controllers attached.");

            for(int i=0;i<controllerArray.size; i++){                                   //Loops Controllers
                if(controllerArray.get(0).getName().contains("Xbox 360")) {             //Checks if XBox controller
                    controller = controllerArray.get(0);
                    System.out.println("Connected: " + controller + ".");
                }
            }
        }
    }
}