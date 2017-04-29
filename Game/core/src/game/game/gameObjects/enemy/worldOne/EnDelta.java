package game.game.gameObjects.enemy.worldOne;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import game.Tri_X;
import game.game.gameObjects.Enemy;
import game.game.gameObjects.EnemyState;
import game.menu.screens.fullScreens.Difficulty;
import game.util.Constants;

public class EnDelta extends Enemy implements Constants {
    private float stateTime;
    private Difficulty difficulty;

    private Animation normalANI;
    private Animation shieldANI;
    private Animation explosionANI;

    FixtureDef fdef;
    AssetManager manager;

    public EnDelta(Difficulty difficulty, AssetManager manager, World world, float x, float y) {
        super(world, x, y);
        this.difficulty = difficulty;
        this.manager = manager;
        currentState = EnemyState.Normal;
        previousState = EnemyState.Normal;

        normalANI = new Animation(1/60f, manager.get("animations/enemies/tri.pack", TextureAtlas.class).getRegions());     //60 Times a seconds
        //shieldANI = new Animation(1/60f, shieldA.getRegions());     //60 Times a seconds
        explosionANI = new Animation(1/30f, manager.get("animations/enemies/ex.pack", TextureAtlas.class).getRegions());     //60 Times a seconds


        stateTime = 0;
        setSize(200 / Tri_X.ppm, 200 / Tri_X.ppm);
        angle = b2body.getAngle();
    }

    @Override
    protected void defineEnemy() {
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
        vertices[1] = new Vector2(-1.5f, 0);
        vertices[2] = new Vector2(0, -.3f);

        shape.set(vertices);

        fdef.filter.categoryBits = ENEMY001_BIT;
        fdef.filter.maskBits = SHIP_BIT | ENEMY_BARRIOR | WEAPON1_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt){
        if(animationActive) {
            stateTime += dt;
            if(difficulty == Difficulty.EASY){
                b2body.setLinearVelocity(new Vector2(0, 0));
            } else if(difficulty == Difficulty.MEIDUM){
                b2body.setLinearVelocity(new Vector2(-3, 0));
            } else if(difficulty == Difficulty.HARD){
                b2body.setLinearVelocity(new Vector2(-6, 0));
            }
            setPosition((b2body.getPosition().x - getWidth() / 2)-.8f, b2body.getPosition().y - getHeight() / 2);

            if (currentState == game.game.gameObjects.EnemyState.Normal) {
                setRegion(normalANI.getKeyFrame(stateTime, true));
            } else if (currentState == game.game.gameObjects.EnemyState.Shielded) {
                setRegion(shieldANI.getKeyFrame(stateTime, true));
            } else if (currentState == game.game.gameObjects.EnemyState.Exploded) {
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
        }
    }

    @Override
    public void setDirection(boolean y){}

    @Override
    public void reverseDirection() {}


    public void destroy(){
        //Stops input from keyboard/controller, turns physics off, resets timer for explosion
        if(reset){
            manager.get("audio/effects/explosion.mp3", Sound.class).play();
            stateTime = 0;
            reset = false;
            b2body.setActive(false);
        }
    }
}
