package game.game.gameObjects.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import game.Tri_X;
import game.util.Constants;

public class FireBallBounce extends Sprite implements Constants {

    World world;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;

    Body b2body;
    public FireBallBounce(World world, float x, float y){
        this.world = world;

        setRegion(new Texture(Gdx.files.internal("animations/weapons/weapon1.png")));
        setSize(12 / Tri_X.ppm, 12 / Tri_X.ppm);
        setPosition(x+1,y);
        defineFireBall();
    }

    public void defineFireBall(){
        setColor(Color.RED);
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() - 12 /Tri_X.ppm, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / Tri_X.ppm);
        fdef.filter.categoryBits = WEAPON1_BIT;
        fdef.filter.maskBits = ENEMY001_BIT | ENEMY002_BIT;

        fdef.shape = shape;
        fdef.restitution = 4;
        fdef.friction = 5;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(5,0));
    }

    public void update(float dt){
        stateTime += dt;

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}
