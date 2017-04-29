package game.game.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import game.Tri_X;
import game.game.gameHuds.Hud;
import game.game.gameObjects.EnemyState;
import game.game.gameObjects.Item;
import game.game.gameObjects.ItemDef;
import game.game.gameObjects.WorldContactListener;
import game.game.gameObjects.WorldObjectsGenerator;
import game.game.gameObjects.enemy.worldOne.EnCharlie;
import game.game.gameObjects.items.Shields;
import game.game.gameObjects.player.AlphaShip;
import game.game.gameObjects.player.ShipState;
import game.game.gameScreens.levelUtils.LevelAbstract;
import game.menu.screens.fullScreens.Difficulty;
import game.util.inputDevices.XboxController;
import game.util.inputDevices.XboxKeyMaps;

public class Level3 extends LevelAbstract {
    enum Direction { CENTRE, NORTH, SOUTH, EAST, WEST, NE, NW, SE, SW}
    //basic playscreen variables
    private boolean lowGraphicsMode = false;
    private boolean debugOn = false;

    private OrthographicCamera gameCamera;

    private Viewport gameViewport;
    final private Hud hud;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Box2d variables
    private World world;
    private WorldObjectsGenerator creator;
    private Box2DDebugRenderer debugLines;

    //Player Ship
    private AlphaShip player;
    private double maxSpeed = 6;
    private float accelaration = 0.4f;

    private SpriteBatch sb;
    private Direction myDir = Direction.CENTRE;
    private boolean halfSpeed = false;  //Controller half speed key
    private Controller myController;    //Controller
    private boolean controllerConnected = false;
    private AssetManager manager;
    private boolean fire = false;
    private boolean moveCamLeft = false;
    private boolean moveCamRight = false;
    private double fireRateReset = 0;
    private boolean aaa = false;
    private float aa = 16;
    private boolean intro = false;

    //Items
    private ArrayList<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    boolean retro = false;

    public void setType(int a){
        player.setType(a);
    }
    public Level3(Difficulty a, SpriteBatch sb, AssetManager managerIn, Hud hud, boolean retro) {
        manager = managerIn;
        this.retro = retro;
        this.hud = hud;
        this.sb = sb;
        lowGraphicsMode = retro;

       // Music music = manager.get("audio/music/Antigravity.mp3", Music.class);
        //music.play();

        //Connecting an xbox controller
        if (Controllers.getControllers().size != 0) {
            XboxController xxx = new XboxController();
            xxx.start();
            Controllers.addListener(this);
            myController = Controllers.getControllers().get(0);
            controllerConnected = true;
        }


        gameCamera = new OrthographicCamera();                  //create camera used to follow
        gameViewport = new StretchViewport(Tri_X.V_WIDTH / Tri_X.ppm, Tri_X.V_HEIGHT / Tri_X.ppm, gameCamera);

        mapLoader = new TmxMapLoader();                         //Load our map and setup our map renderer
        map = mapLoader.load("levels/level11.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Tri_X.ppm);

        world = new World(new Vector2(0, 0), true);             //Box2D world with no gravity
        debugLines = new Box2DDebugRenderer();                  //Debug lines

        gameCamera.position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);

        creator = new WorldObjectsGenerator(a,world, manager, hud, getMap());
        world.setContactListener(new WorldContactListener(hud));
        player = new AlphaShip(world, manager, hud, lowGraphicsMode);
        player.b2body.setTransform(12, 6, 0);
        player.b2body.setLinearDamping(3);          //Slowdown, inertia
        player.setOrigin(player.getHeight() / 2, player.getWidth() / 2);

        items = new ArrayList<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        //Item test
        spawnItem(new ItemDef(new Vector2(20, 5), Shields.class));
        intro = true;
    }

    public void playLevel1(){
        gameCamera.position.x = 9.6f;
        aaa = true;
        player.reset();
        player.b2body.setTransform(12, 6, 0);
        System.out.println("reset");
        for (int i = 0; i < creator.getEnemies().size; i++) {
            creator.getEnemies().get(i).destroyBody();
            creator.getEnemies().get(i).reset();
            creator.getEnemies().get(i).setState(EnemyState.Normal);
        }
        }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(hud.isShowLev2()){
            aaa = false;
        }
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Shields.class){
                items.add(new Shields(getWorld(), idef.position.x, idef.position.y));
            }
        }
    }

    public void update(float dt) {
        if (!player.getkillInput()) {
            handleInput(dt);                                //handle user input first
        }
        handleSpawningItems();

        //Introduction zoom
        if (intro) {
            aa -= 3 * dt * 3.5;
            player.setScale(aa);
            if (aa < 1.1) {
                intro = false;
                aa = 1;
                player.setScale(aa);
            }
        }
        //physics simulation(60 times per second)
        //Updating hud, score, timer
        //Updating player
        //Moving player
        //Moving camera position
        world.step(1 / 60f, 6, 2);
        hud.update(dt);
        player.update(dt);
        if(aaa) {
            if(player.getCurrentState()!= ShipState.Exploded) {
                player.b2body.setTransform(player.b2body.getPosition().x + .033f, player.b2body.getPosition().y, 0);
                gameCamera.position.x += 2 * dt;
            }
        }
            //Activating the enemies in groups,
            for (int i = 0; i < creator.getEnemies().size; i++) {
                if (creator.getEnemies().get(i).isNeverActivatedYet()
                        && creator.getEnemies().get(i).getX() < gameCamera.position.x + 11 + creator.getEnemies().get(i).getWidth()) {
                    //If enemy is just on the screen to the right, turn on its animation + physics
                    creator.getEnemies().get(i).b2body.setActive(true);
                    creator.getEnemies().get(i).setAnimationActive(true);
                    creator.getEnemies().get(i).setNeverActivatedYet(false);
                } else if (!creator.getEnemies().get(i).isPhysicsBodyDestroyed()
                        && !(creator.getEnemies().get(i)instanceof EnCharlie)
                        && !creator.getEnemies().get(i).isNeverActivatedYet()
                        && creator.getEnemies().get(i).getX() < gameCamera.position.x - (11 + creator.getEnemies().get(i).getWidth())
                        && creator.getEnemies().get(i).b2body.isActive()) {
                    //If enemy is off the screen to the left, + its width (as different enemies have different sizes), deactivate its animation + physics
                    creator.getEnemies().get(i).setAnimationActive(false);
                    creator.getEnemies().get(i).b2body.setActive(false);
                    creator.getEnemies().get(i).destroyBody();
                }
            }

            //If enemy animations are active
            //Activate enemies, update image and physics
            for (int i = 0; i < creator.getEnemies().size; i++) {
                if (creator.getEnemies().get(i).isAnimationActive()) {
                    creator.getEnemies().get(i).update(dt);
                }
            }

            for (int i = 0; i < items.size(); i++) {
                items.get(i).update(dt);
            }

            gameCamera.update();                            //update camera with correct coordinates after changes
            mapRenderer.setView(gameCamera);                //tell our renderer to draw only what our camera can see in our game world.
        }

    public SpriteBatch getSb() {
        return sb;
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);                                      //Updates the situation

        if(!lowGraphicsMode) {
            mapRenderer.render();                               //render our game map
        }
        if(lowGraphicsMode || debugOn) {
            debugLines.render(world, gameCamera.combined);
        }
        sb.setProjectionMatrix(gameCamera.combined);
            sb.begin();                                 //Open Batch (Assets)

        if(!lowGraphicsMode) {
            player.draw(sb);                            //Drawing the ship
        }

        //If the enemies animation activated
            //Drawing all enemies, updating their current image
            for (int i = 0; i < creator.getEnemies().size; i++) {
                if (creator.getEnemies().get(i).isAnimationActive()) {
                    if(!lowGraphicsMode) {
                        creator.getEnemies().get(i).draw(sb);
                    }
                }
            }
            //Rendering items
            for (int i = 0; i < items.size(); i++) {
                items.get(i).draw(sb);
            }
            sb.end();                                   //Close Batch
            hud.stage.draw();                                   //Draws HUD
    }
    public void handleInput(float dt) {

        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();

        if(accelZ>1||accelZ<-1) {
            //Up
            if (accelZ>1) {
                player.b2body.applyLinearImpulse(new Vector2(0, accelaration), player.b2body.getWorldCenter(), true);
            }
            //Down
            if (accelZ<-1) {
                player.b2body.applyLinearImpulse(new Vector2(0, -accelaration), player.b2body.getWorldCenter(), true);
            }
        }
        if(accelY>1||accelY<-1) {
            //Left
            if (accelY < 1) {
                player.b2body.applyLinearImpulse(new Vector2(-accelaration, 0), player.b2body.getWorldCenter(), true);
            }
            //Right
            if (accelY > -1) {
                player.b2body.applyLinearImpulse(new Vector2(accelaration, 0), player.b2body.getWorldCenter(), true);
            }
        }


        if ((Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) || halfSpeed) {
            maxSpeed = 3;
        } else {
            maxSpeed = 6;
        }

        //God-mode On/Off
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_0))) {
            player.godModeOn();
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_9))) {
            player.godModeOff();
        }
        //Player movements
        if ((Gdx.input.isKeyPressed(Input.Keys.NUM_8))) {
            player.b2body.setTransform(player.b2body.getPosition().x + 5f, player.b2body.getPosition().y, 0);
        }

        //If space-bar is pressed, OR fire is true
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || fire ||Gdx.input.isTouched()) {
            fireRateReset+=dt;
            if(fireRateReset>.14){
                player.fire();
                manager.get("audio/effects/fire.mp3", Sound.class).play();
                fireRateReset=0;
            }
        }

        //If 1 is pressed, OR moveCam right
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) || moveCamLeft) {
            //map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
            gameCamera.position.x -=100*dt;
        }

        //If 1 is pressed, OR moveCam right
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) || moveCamLeft) {
            //map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
            gameCamera.position.x +=100*dt;
        }

        //Keyboard Inputs
        if (Gdx.input.isKeyPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <= maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(0, accelaration), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && player.b2body.getLinearVelocity().y >= -maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(0, -accelaration), player.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(accelaration, 0), player.b2body.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -maxSpeed) {
            player.b2body.applyLinearImpulse(new Vector2(-accelaration, 0), player.b2body.getWorldCenter(), true);
        }

        if (controllerConnected) {
        //XBox Controller Inputs
        //Player movements, If the direction is not neutral(Centre), evaluate other if statements
        if (myDir != Direction.CENTRE) {
            //Up
            if (myDir == Direction.NORTH && player.b2body.getLinearVelocity().y <= maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, accelaration), player.b2body.getWorldCenter(), true);
            }
            //Down
            if (myDir == Direction.SOUTH && player.b2body.getLinearVelocity().y >= -maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, -accelaration), player.b2body.getWorldCenter(), true);
            }
            //Left
            if (myDir == Direction.WEST && player.b2body.getLinearVelocity().x >= -maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(-accelaration, 0), player.b2body.getWorldCenter(), true);
            }
            //Right
            if (myDir == Direction.EAST && player.b2body.getLinearVelocity().x <= maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(accelaration, 0), player.b2body.getWorldCenter(), true);
            }
            //Up-Left
            if (myDir == Direction.NW
                    && player.b2body.getLinearVelocity().y <= maxSpeed
                    && player.b2body.getLinearVelocity().x >= -maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, accelaration), player.b2body.getWorldCenter(), true);
                player.b2body.applyLinearImpulse(new Vector2(-accelaration, 0), player.b2body.getWorldCenter(), true);
            }
            //Up-Right
            if (myDir == Direction.NE
                    && player.b2body.getLinearVelocity().y <= maxSpeed
                    && player.b2body.getLinearVelocity().x <= maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, accelaration), player.b2body.getWorldCenter(), true);
                player.b2body.applyLinearImpulse(new Vector2(accelaration, 0), player.b2body.getWorldCenter(), true);
            }
            //Down-Right
            if (myDir == Direction.SE
                    && player.b2body.getLinearVelocity().y >= -maxSpeed
                    && player.b2body.getLinearVelocity().x <= maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, -accelaration), player.b2body.getWorldCenter(), true);
                player.b2body.applyLinearImpulse(new Vector2(accelaration, 0), player.b2body.getWorldCenter(), true);
            }
            //Down-Left
            if (myDir == Direction.SW
                    && player.b2body.getLinearVelocity().y >= -maxSpeed
                    && player.b2body.getLinearVelocity().x >= -maxSpeed) {
                player.b2body.applyLinearImpulse(new Vector2(0, -accelaration), player.b2body.getWorldCenter(), true);
                player.b2body.applyLinearImpulse(new Vector2(-accelaration, 0), player.b2body.getWorldCenter(), true);
            }
        }

        //Left joystick
            if(myController.getAxis(XboxKeyMaps.LEFT_STICK_X) > 0.1f  ||
                    myController.getAxis(XboxKeyMaps.LEFT_STICK_X) < -0.1f ||
                    myController.getAxis(XboxKeyMaps.LEFT_STICK_Y) < -0.1f ||
                    myController.getAxis(XboxKeyMaps.LEFT_STICK_Y) > 0.1f  ) {

                if (player.b2body.getLinearVelocity().y <= maxSpeed &&
                        player.b2body.getLinearVelocity().y >= -maxSpeed &&
                        player.b2body.getLinearVelocity().x <= maxSpeed &&
                        player.b2body.getLinearVelocity().x >= -maxSpeed) {
                    player.b2body.applyLinearImpulse(
                            new Vector2(
                                    myController.getAxis(XboxKeyMaps.LEFT_STICK_X) * 1f,
                                    myController.getAxis(XboxKeyMaps.LEFT_STICK_Y) * -1f), player.b2body.getWorldCenter(), true);
                }
        }
        }

    //If click mouse
   //    if(Gdx.input.isTouched()) {
   //    gameCamera.position.x -=3*dt;
   //}


}
    public AssetManager getManager(){
        return manager;
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.print("Disposing");
        map.dispose();
        mapRenderer.dispose();
        debugLines.dispose();
        world.dispose();
    }

    @Override
    public boolean buttonDown (Controller controller, int buttonIndex) {
        //XBox Controller Button press detection
        //Standard Buttons
        if(buttonIndex == (XboxKeyMaps.BUTTON_Y)) {
            System.out.println("Y Down");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_B)) {
            System.out.println("B Down");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_A)) {
            System.out.println("A Down");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_X)) {
            System.out.println("X Down");
            fire = true;
        }
        //Back Bumper Buttons
        if(buttonIndex == (XboxKeyMaps.LEFT_BUMBPER)) {
            System.out.println("LeftBack Down");
            moveCamLeft = true;
        }
        if(buttonIndex == (XboxKeyMaps.RIGHT_BUMPER)) {
            System.out.println("RightBack Down");
            moveCamRight = true;
        }
        //Special Buttons
        if(buttonIndex == (XboxKeyMaps.BUTTON_START)) {
            System.out.println("Start Down");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_BACK)) {
            System.out.println("Back Down");
        }
        if(buttonIndex == (XboxKeyMaps.LEFT_STICK)) {
            System.out.println("Left Stick Down");
        }
        if(buttonIndex == (XboxKeyMaps.RIGHT_STICK)) {
            System.out.println("Right Stick Down");
        }
        return false;
    }

    @Override
    public boolean buttonUp (Controller controller, int buttonIndex) {
        //XBox Controller Button release detection
        //Standard Buttons
        if(buttonIndex == (XboxKeyMaps.BUTTON_Y)) {
            System.out.println("Y Up");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_B)) {
            System.out.println("B Up");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_A)) {
            System.out.println("A Up");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_X)) {
            System.out.println("X Up");
            //Stops firing, resets fire rate
            fire = false;
            fireRateReset = .2;
        }
        //Back Buttons
        if(buttonIndex == (XboxKeyMaps.LEFT_BUMBPER)) {
            System.out.println("LeftBack Up");
            moveCamLeft = false;
        }
        if(buttonIndex == (XboxKeyMaps.RIGHT_BUMPER)) {
            System.out.println("RightBack Up");
            moveCamRight = false;
        }
        //Special Buttons
        if(buttonIndex == (XboxKeyMaps.BUTTON_START)) {
            System.out.println("Start Up");
        }
        if(buttonIndex == (XboxKeyMaps.BUTTON_BACK)) {
            System.out.println("Back Up");
        }
        if(buttonIndex == (XboxKeyMaps.LEFT_STICK)) {
            System.out.println("Left Stick Up");
        }
        if(buttonIndex == (XboxKeyMaps.RIGHT_STICK)) {
            System.out.println("Right Stick Up");
        }
        return false;
    }

    @Override
    public boolean povMoved (Controller controller, int povIndex, PovDirection value) {
        //XBox Controller Directional Pad position detection
        if(value == XboxKeyMaps.DPAD_CENTER) {
            myDir = Direction.CENTRE;
            System.out.println("DPad Center");
        }
        //Up
        if(value == XboxKeyMaps.DPAD_NORTH) {
            myDir = Direction.NORTH;
            System.out.println("DPad North");
        }
        //Down
        if(value == XboxKeyMaps.DPAD_SOUTH) {
            myDir = Direction.SOUTH;
            System.out.println("DPad South");
        }
        //Left
        if(value == XboxKeyMaps.DPAD_EAST) {
            myDir = Direction.EAST;
            System.out.println("DPad East");
        }
        //Right
        if(value == XboxKeyMaps.DPAD_WEST) {
            myDir = Direction.WEST;
            System.out.println("DPad West");
        }
        if(value == XboxKeyMaps.DPAD_NORTHEAST) {
            myDir = Direction.NE;
            System.out.println("DPad North East");
        }
        if(value == XboxKeyMaps.DPAD_NORTHWEST) {
            myDir = Direction.NW;
            System.out.println("DPad North West");
        }
        if(value == XboxKeyMaps.DPAD_SOUTHWEST) {
            myDir = Direction.SW;
            System.out.println("DPad South West");
        }
        if(value == XboxKeyMaps.DPAD_SOUTHEAST) {
            myDir = Direction.SE;
            System.out.println("DPad South East");
        }
        return false;
    }

    @Override
    public boolean axisMoved (Controller controller, int axisIndex, float value) {
         if(axisIndex == XboxKeyMaps.RIGHT_STICK_X){
             if(value > 0.3 || value < -0.3) {
                 System.out.println("Right Stick");
             }
         }
         if(axisIndex == XboxKeyMaps.RIGHT_STICK_Y){
             if(value > 0.3 || value < -0.3) {
                 System.out.println("Right Stick");
             }
         }
         if(axisIndex == XboxKeyMaps.TRIGGERS){
             System.out.println("Trigger");
         }

         return false;
     }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);  //Stops old screen being clickable
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                aaa = true;
            }
        }, 2f);
    }
    public World getWorld(){
        return world;
    }
    public TiledMap getMap(){
        return map;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    @Override
    public void connected(Controller controller) {
    }

    @Override
    public void disconnected(Controller controller) {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Space bar
        if(keycode == 62){
            fireRateReset = .2;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

