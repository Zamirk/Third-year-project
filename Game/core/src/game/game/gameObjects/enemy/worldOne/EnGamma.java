package game.game.gameObjects.enemy.worldOne;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import game.Tri_X;
import game.game.gameObjects.Enemy;
import game.game.gameObjects.EnemyState;
import game.game.gameScreens.Level1;
import game.menu.screens.fullScreens.Difficulty;
import game.util.Constants;

public class EnGamma extends Enemy implements Constants {
    private float stateTime;

    private Animation normalANI;
    private Animation shieldANI;
    private Animation explosionANI;
    private Batch batch;

    private AssetManager manager;
    private Difficulty difficulty;
    public EnGamma(Difficulty difficulty, AssetManager manager, World world, Rectangle rect) {
        super(world, rect.getX() / Tri_X.ppm, rect.getY() /Tri_X.ppm);
        this.difficulty = difficulty;
        this.manager = manager;

        currentState = EnemyState.Normal;

        normalANI = new Animation(1/60f, manager.get("animations/asteroids/am2.pack", TextureAtlas.class).getRegions());
        //shieldANI = new Animation(1/60f, shieldA.getRegions());     //60 Times a seconds
        explosionANI = new Animation(1/30f, manager.get("animations/enemies/ex.pack", TextureAtlas.class).getRegions());     //60 Times a seconds

        stateTime = 0;
        setSize(240 / Tri_X.ppm, 240 / Tri_X.ppm);
        setOrigin(getWidth() / 2, getHeight() / 2);
        angle = b2body.getAngle();
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.9f,.9f);

        //What the ship can collide with
        fdef.filter.categoryBits = ENEMY001_BIT;
        fdef.filter.maskBits = SHIP_BIT | ENEMY_BARRIOR | WEAPON1_BIT;

        fdef.shape = shape;
        b2body.setAngularVelocity(1);
        b2body.setFixedRotation(true);
        b2body.createFixture(fdef).setUserData(this);

    }

    public void update(float dt){
        if(animationActive) {
            stateTime += dt;
            if(difficulty == Difficulty.EASY){
                b2body.setLinearVelocity(new Vector2(0, 0));
            } else if(difficulty == Difficulty.MEIDUM){
                b2body.setLinearVelocity(new Vector2(-2.5f, 0));
            } else if(difficulty == Difficulty.HARD){
                b2body.setLinearVelocity(new Vector2(-5f, 0));
            }
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            if (currentState == EnemyState.Normal) {
                setRegion(normalANI.getKeyFrame(stateTime, true));
            } else if (currentState == EnemyState.Shielded) {
                setRegion(shieldANI.getKeyFrame(stateTime, true));
            } else if (currentState == EnemyState.Exploded) {
                destroy();
                setRegion(explosionANI.getKeyFrame(stateTime, false));

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        animationActive = false;
                        destroyBody();
                    }
                }, .5f);
            }

                b2body.setAngularVelocity(1.2f);
                rotate(.955f);
        }
    }

    public void destroy(){
        //Stops input from keyboard/controller, turns physics off, resets timer for explosion
        if(reset){
            manager.get("audio/effects/explosion.mp3", Sound.class).play();
            stateTime = 0;
            reset = false;
            b2body.setActive(false);
        }
    }
    @Override
    public void setDirection(boolean y) {}

    @Override
    public void reverseDirection() {}


    @Override
    public void draw(Batch batch){
        super.draw(batch);
    }
}
