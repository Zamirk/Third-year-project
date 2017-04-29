package game.game.gameObjects.enemy.worldOne;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

import game.Tri_X;
import game.game.gameObjects.*;
import game.menu.screens.fullScreens.Difficulty;
import game.util.Constants;

public class EnBeta extends Enemy implements Constants {
    boolean UP = true;
    private float stateTime;
    boolean oriDir = false;

    private Animation normalANI;
    private Animation shieldANI;
    private Animation explosionANI;
    private Difficulty difficulty;

    AssetManager manager;
    public EnBeta(Difficulty difficulty, AssetManager manager,World world, float x, float y) {
        super(world, x, y);
        this.difficulty = difficulty;
        this.manager = manager;
        currentState = game.game.gameObjects.EnemyState.Normal;
        previousState = game.game.gameObjects.EnemyState.Normal;

        normalANI = new Animation(1/60f, manager.get("animations/enemies/lop3.pack", TextureAtlas.class).getRegions());     //60 Times a seconds
        explosionANI = new Animation(1/30f, manager.get("animations/enemies/ex.pack", TextureAtlas.class).getRegions());     //60 Times a seconds

        stateTime = 0;
        setSize(150 / Tri_X.ppm, 150 / Tri_X.ppm);
        }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        CircleShape shape = new CircleShape();
        shape.setRadius(70 / Tri_X.ppm);

        //What the ship can collide with
        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = ENEMY002_BIT;
        fdef.filter.maskBits = SHIP_BIT | ENEMY_BARRIOR | ENEMY002_BIT | WEAPON1_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        angle = b2body.getAngle();
    }

    public void update(float dt){
        if(animationActive) {
            stateTime += dt;
            setDirection(oriDir);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            if (currentState == game.game.gameObjects.EnemyState.Normal) {
                setRegion(normalANI.getKeyFrame(stateTime, true));
            } else if (currentState == game.game.gameObjects.EnemyState.Shielded) {
                setRegion(normalANI.getKeyFrame(stateTime, true));
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
    public void setDirection(boolean y){
        if(y){
            if(difficulty == Difficulty.EASY){
                b2body.setLinearVelocity(new Vector2(0, 0));
            } else if(difficulty == Difficulty.MEIDUM){
                b2body.setLinearVelocity(new Vector2(0, 2f));
            } else if(difficulty == Difficulty.HARD){
                b2body.setLinearVelocity(new Vector2(0, 4f));
            }
            UP = true;
            oriDir = true;
        }else {
            if(difficulty == Difficulty.EASY){
                b2body.setLinearVelocity(new Vector2(0, 0));
            } else if(difficulty == Difficulty.MEIDUM){
                b2body.setLinearVelocity(new Vector2(0, -2f));
            } else if(difficulty == Difficulty.HARD){
                b2body.setLinearVelocity(new Vector2(0, -4f));
            }
            UP = false;
            oriDir = false;
        }
    }

    @Override
    public void reverseDirection() {
        if(UP){
            b2body.setLinearVelocity(new Vector2(0, -2f));
            UP = false;
            oriDir = false;
        } else {
            b2body.setLinearVelocity(new Vector2(0, 2f));
            UP = true;
            oriDir = true;
        }
    }

    public void destroy(){
        if(reset){
            manager.get("audio/effects/explosion.mp3", Sound.class).play();
            stateTime = 0;
            reset = false;
            b2body.setActive(false);
        }
    }
}
