package game.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Enemy extends Sprite {
    protected EnemyState currentState;
    protected EnemyState previousState;

    protected World world;
    public Body b2body;
    protected boolean animationActive = false;
    protected boolean neverActivatedYet = true;
    protected boolean physicsBodyDestroyed = false;
    protected float angle;
    protected boolean reset = true;

    public Enemy(World world, float x, float y){
        this.world = world;
        setPosition(x, y);
        orix = x;
        oriy = y;
        defineEnemy();
        b2body.setActive(false);
    }
    protected float orix = 0;
    protected float oriy = 0;

    public void reset(){
            animationActive = false;
            neverActivatedYet = true;
            physicsBodyDestroyed = false;
            b2body.setActive(false);
        b2body.setTransform(orix, oriy, angle);
        reset = true;
    }

    protected abstract void defineEnemy();
    public abstract void setDirection(boolean y);
    public abstract void reverseDirection();
    public void destroyBody(){
        if(!physicsBodyDestroyed) {
            physicsBodyDestroyed = true;
            b2body.setTransform(0, 0, 0);
            b2body.setLinearVelocity(0, 0);
            b2body.setActive(false);
            reset = true;
        }
        }

    public abstract void update(float dt);
    public void draw(Batch batch){
        if(animationActive)
            super.draw(batch);
    }
    public void setState(EnemyState state) {
        previousState = currentState;
        currentState = state;
    }
    public EnemyState getState() {
        return currentState;
    }

    public boolean isNeverActivatedYet() {
        return neverActivatedYet;
    }

    public boolean isPhysicsBodyDestroyed() {
        return physicsBodyDestroyed;
    }

    public boolean isAnimationActive() {
        return animationActive;
    }

    public void setNeverActivatedYet(boolean neverActivatedYet) {
        this.neverActivatedYet = neverActivatedYet;
    }

    public void setPhysicsBodyDestroyed(boolean physicsBodyDestroyed) {
        this.physicsBodyDestroyed = physicsBodyDestroyed;
    }

    public void setAnimationActive(boolean animationActive) {
        this.animationActive = animationActive;
    }
}
