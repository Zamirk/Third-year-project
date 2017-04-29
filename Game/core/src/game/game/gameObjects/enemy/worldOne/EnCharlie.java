package game.game.gameObjects.enemy.worldOne;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

import game.Tri_X;
import game.game.gameObjects.Enemy;
import game.menu.screens.fullScreens.Difficulty;
import game.util.Constants;

public class EnCharlie extends Enemy implements Constants {
    private float stateTime;
    AssetManager manager;
    Texture circle;
    CircleShape ballShape;
    private Animation normalANI;
    TextureAtlas normalA = new TextureAtlas("animations/enemies/Hex/Hex.pack");
    private Difficulty difficulty;

    public EnCharlie(Difficulty difficulty, AssetManager manager, World world, float x, float y) {
        super(world, x, y);
        this.difficulty = difficulty;
        this.manager = manager;
        stateTime = 0;
        setSize(900 / Tri_X.ppm, 900 / Tri_X.ppm);
        setOrigin(getWidth() / 2, getHeight() / 2);

        circle = new Texture("animations/enemies/EnCharlie/cc.png");
        normalANI = new Animation(1/60f, normalA.getRegions());     //60 Times a seconds
    }
    @Override
    protected void defineEnemy() {
        float[] posX = {
                0.389301f, -3.540689f , 3.856987f, 3.505457f, 1.911257f, 2.522333f,
                1.186457f, 3.97265f, -0.302066f, -3.835258f, -3.982158f, -2.5645895f,
                -3.145943f, -1.178762f, 3.16426f};
        float[] posY = {
                3.98101f, 1.8610535f, 1.0600243f, -1.926597f, 3.513844f, -3.1044867f,
                -3.81999f, -0.466965f, -3.9885781f, -1.136133f, 0.3773904f, 3.069671f,
                -2.4704344f, 3.822371f, 2.446928f};

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;

        b2body = world.createBody(bdef);

        for(int i=0;i<15;i++){
            if(i!=8) {
                ballShape = new CircleShape();
                ballShape.setRadius(.55f);

                FixtureDef circleFixtureDef = new FixtureDef();
                circleFixtureDef.filter.categoryBits = ENEMY001_BIT;
                circleFixtureDef.filter.maskBits = SHIP_BIT | ENEMY_BARRIOR | WEAPON1_BIT;
                circleFixtureDef.shape = ballShape;
                ballShape.setPosition(new Vector2(posX[i], posY[i]));

                b2body.createFixture(circleFixtureDef).setUserData(this);
            }
        }
    }
    public void update(float dt){
        if(animationActive) {
            stateTime += dt;
            setRegion(circle);

            setPosition((b2body.getPosition().x - getWidth() / 2),
                    (b2body.getPosition().y - getHeight() / 2));

            if (clockWise) {
                b2body.setAngularVelocity(1f);
                rotate(.955f);
            } else {
                b2body.setAngularVelocity(-1f);
                rotate(-0.955f);
            }
        }
    }
    @Override
    public void reset(){

    }
    @Override
    public void destroyBody(){

    }
    boolean clockWise = true;
    @Override
    public void setDirection(boolean y){
        if(y) {
            clockWise = true;
        } else {
            clockWise = false;
        }
    }

    @Override
    public void reverseDirection() {}
}
