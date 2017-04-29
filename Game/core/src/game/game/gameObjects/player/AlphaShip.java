package game.game.gameObjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import game.Tri_X;
import game.game.gameHuds.Hud;
import game.game.gameObjects.weapons.FireBall;
import game.util.Constants;

public class AlphaShip extends Sprite implements Constants, InputProcessor{

    private float stateTimer;
    //TextureAtlas shipAtlasShielded = new TextureAtlas("animations/ships/ShipShielded.pack");
    private boolean lowGraphicsMode = false;
    private boolean reset = true;
    private boolean animationOff = false;
    private boolean killInput = false;
    private Array<FireBall> fireballs;

    private Animation shipNoShieldAni, shipShieldAni;
    private Animation explosion;

    public World world;
    public Body b2body;

    private ShipState currentState, previousState;
    private AssetManager manager;
    private TextureRegion normal[];
    private Hud hud;
    public AlphaShip(World world, AssetManager manager, Hud hud, boolean lowGraphicsModeIn){
        this.world = world;
        this.hud = hud;
        this.lowGraphicsMode = lowGraphicsModeIn;
        this.manager = manager;

        currentState = ShipState.Normal;
        previousState = ShipState.Normal;

        stateTimer = 0;

        shipNoShieldAni = new Animation(1/15f, manager.get("animations/ships/shipOne.pack", TextureAtlas.class).getRegions());

        explosion = new Animation(1/15f, manager.get("animations/enemies/ex.pack", TextureAtlas.class).getRegions());     //60 Times a seconds
        normal = shipNoShieldAni.getKeyFrames();

        setBounds(0, 0, 210 / Tri_X.ppm, 210 / Tri_X.ppm);
        defineShip(lowGraphicsMode);
        fireballs = new Array<FireBall>();
    }
    public void setType(int type){
        if(type == 1){
            shipNoShieldAni = new Animation(1/15f, manager.get("animations/ships/shipOne.pack", TextureAtlas.class).getRegions());
        } else if(type == 2){
            shipNoShieldAni = new Animation(1/15f, manager.get("animations/ships/ship2.pack", TextureAtlas.class).getRegions());
        } else if(type == 3){
            shipNoShieldAni = new Animation(1/15f, manager.get("animations/ships/tri2.pack", TextureAtlas.class).getRegions());
            flip = true;
        } else if(type == 4){
            shipNoShieldAni = new Animation(1/15f, manager.get("animations/ships/shipPlane.pack", TextureAtlas.class).getRegions());
        }
        normal = shipNoShieldAni.getKeyFrames();
    }

    CircleShape shape;
    FixtureDef fdef;
    public void defineShip(boolean lowGraphicsMode){

        if(!lowGraphicsMode) {
            BodyDef bdef = new BodyDef();
            bdef.position.set(64 / Tri_X.ppm, 64 / Tri_X.ppm);
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            fdef = new FixtureDef();
            shape = new CircleShape();
            shape.setRadius(60 / Tri_X.ppm);

            //What the ship can collide with
            fdef.filter.categoryBits = SHIP_BIT;
            fdef.filter.maskBits =
                    DEFAULT_BIT |
                            ENEMY001_BIT |
                            ENEMY002_BIT |
                            ENEMY002_BIT |
                            BORDER_BIT |
                            BREAKABLE_WALL_BIT |
                            LEVEL2_BIT |
                            ITEM_001;

            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);
        } else {
            BodyDef bdef = new BodyDef();
            bdef.position.set(getX(), getY());
            bdef.type = BodyDef.BodyType.DynamicBody;
            b2body = world.createBody(bdef);

            fdef = new FixtureDef();
            Rectangle rect = new Rectangle();
            rect.setSize(70/ Tri_X.ppm, 70/ Tri_X.ppm);

            PolygonShape shape = new PolygonShape();

            Vector2[] vertices = new Vector2[3];
            vertices[0] = new Vector2(0, .3f);
            vertices[1] = new Vector2(1.5f, 0);
            vertices[2] = new Vector2(0, -.3f);

            shape.set(vertices);

            fdef.filter.categoryBits = SHIP_BIT;
            fdef.filter.maskBits =
                    DEFAULT_BIT |
                            ENEMY001_BIT |
                            ENEMY002_BIT |
                            ENEMY002_BIT |
                            BORDER_BIT |
                            BREAKABLE_WALL_BIT |
                            ITEM_001 |
                            LEVEL2_BIT;

            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);
        }


    }
    public TextureRegion setFrame(float dt) {
        stateTimer += dt;

        if(currentState == ShipState.Normal || currentState == ShipState.Shielded) {
            //Animation synced to speed calculation
            if ((b2body.getLinearVelocity().y > -4.5) && (b2body.getLinearVelocity().y < 4.5)) {
                setRegion(normal[89 + (int) (Math.ceil(b2body.getLinearVelocity().y * 20.0) / 20.0 / 0.05)]);
            } else if (b2body.getLinearVelocity().y > -4.5) {
                setRegion( normal[179]);
            } else {
                setRegion( normal[0]);
            }
        }

        if(currentState == ShipState.Normal) {
            setColor(Color.WHITE);
        } else if (currentState == ShipState.Shielded) {
            setColor(Color.RED);
        } else if (currentState == ShipState.Exploded) {
            destroy();
            setRegion(explosion.getKeyFrame(stateTimer,false));  //Getting image from animations

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    animationOff = true;
                }
            }, .5f);
        }
        return normal[90];
    }

    public void useItem(){
        if(previousState == ShipState.Normal && currentState == ShipState.Shielded) {
            manager.get("audio/effects/powerup.wav", Sound.class).play();
        } else if(previousState == ShipState.Shielded && currentState == ShipState.Normal){
            manager.get("audio/effects/powerdown.wav", Sound.class).play();
        } else if(previousState == ShipState.Normal && currentState == ShipState.Exploded){
            manager.get("audio/effects/powerdown.wav", Sound.class).play();
        }
    }
    double a = 0;
    boolean flip = false;
    public void update(float dt){
        //Setting collision detection position, relative to the sprite position
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setFrame(dt);    //Setting current image of ship
        if(flip) {
            setFlip(true, false);
        }
        //Firewall weapon
        for(FireBall  ball : fireballs) {
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }
    }
    public void godModeOn(){
        Filter newFilter = new Filter();
        newFilter.categoryBits = GODMODE_BIT;
        newFilter.maskBits =
                DEFAULT_BIT |
                        ENEMY001_BIT |
                        ENEMY002_BIT |
                        ENEMY002_BIT |
                        BORDER_BIT |
                        BREAKABLE_WALL_BIT |
                        LEVEL2_BIT |
                        ITEM_001;

        b2body.getFixtureList().get(0).setFilterData(newFilter);
    }
    public void godModeOff(){
        Filter newFilter = new Filter();
        newFilter.categoryBits = SHIP_BIT;
        newFilter.maskBits =
                DEFAULT_BIT |
                        ENEMY001_BIT |
                        ENEMY002_BIT |
                        ENEMY002_BIT |
                        BORDER_BIT |
                        LEVEL2_BIT |
                        ITEM_001;

        b2body.getFixtureList().get(0).setFilterData(newFilter);
    }

    public void fire(){
        fireballs.add(new FireBall(world, manager, b2body.getPosition().x, b2body.getPosition().y));
    }

    public ShipState getCurrentState() {
        return currentState;
    }

    public void setState(ShipState newState){
        previousState = currentState;
        currentState = newState;
        useItem();
    }
    public void reset(){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
        currentState = ShipState.Normal;
        previousState = ShipState.Normal;
        b2body.setActive(true);
        reset = true;
        animationOff = false;
        killInput = false;
        stateTimer = 0;
            }
        }, .5f);
        currentState = ShipState.Normal;
        previousState = ShipState.Normal;
        b2body.setActive(true);
        reset = true;
        animationOff = false;
        killInput = false;
        stateTimer = 0;
        hud.startAgain();
    }
    public void destroy(){
        //Stops input from keyboard/controller, turns physics off, resets timer for explosion
        if(reset){
            stateTimer = 0;
            reset = false;
            killInput = true;
            b2body.setActive(false);
            hud.showGameOver();
        }

    }
    public boolean getkillInput(){
        return killInput;
    }
    public void draw(Batch batch){
        if(!animationOff) {
            super.draw(batch);
        }
        for(FireBall ball : fireballs)
            ball.draw(batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == 52){
            a = 0;
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
