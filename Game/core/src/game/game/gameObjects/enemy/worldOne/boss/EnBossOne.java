package game.game.gameObjects.enemy.worldOne.boss;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

import java.util.ArrayList;
import java.util.Random;

import game.Tri_X;
import game.game.gameObjects.Enemy;
import game.game.gameScreens.Level1;
import game.util.Constants;

public class EnBossOne extends Enemy implements Constants {
    private float stateTime;
    ArrayList<Body> defsList;
    FixtureDef fdef;
    AssetManager manager;
    ArrayList<Texture> circleList;
    boolean draw;
    Batch batch;
    public EnBossOne(AssetManager manager, Level1 screen, float x, float y) {
        super(screen.getWorld(), x, y);
        this.manager = manager;
        batch = screen.getSb();

        stateTime = 0;
        setSize(150 / Tri_X.ppm, 150 / Tri_X.ppm);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }
    @Override
    protected void defineEnemy() {

        circleList = new ArrayList<Texture>();

        //Main Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bodyDef);

        //Centre box
        //Body 1
        BodyDef coreDef = new BodyDef();
        coreDef.position.set(getX(), getY());
        coreDef.type = BodyDef.BodyType.DynamicBody;

        //CoreBox
        PolygonShape coreBox = new PolygonShape();
        coreBox.setAsBox(.5f, .5f);

        FixtureDef coreFdef = new FixtureDef();
        coreFdef.filter.categoryBits = DESTROYED_BIT;
        coreFdef.filter.maskBits = SHIP_BIT | ENEMY_BARRIOR | WEAPON1_BIT;
        coreFdef.shape = coreBox;
        Body coreBody = world.createBody(coreDef);
        coreBody.createFixture(coreFdef).setUserData(this);
        coreBody.setActive(true);
        coreBody.setAngularVelocity(-1);

        Random random = new Random();
        defsList = new ArrayList<Body>();
        for(int i=0;i<100;i++){
            // breakable ball
            CircleShape ballShape = new CircleShape();

            BodyDef ballBodyDef = new BodyDef();
            double randomX = random.nextDouble() * 8.0 - 4.0;
            double randomY = random.nextDouble() * 8.0 - 4.0;

            if(i%20==0){
                ballShape.setRadius(.6f);
                circleList.add(new Texture("animations/enemies/BossOne/TEMP2.png"));
                ballBodyDef.position.set(getX(), getY());
            } else {
                ballShape.setRadius(.2f);
                circleList.add(new Texture("animations/enemies/BossOne/TEMP.png"));
                ballBodyDef.position.set(((float) randomX) + getX(), ((float) randomY) + getY());
            }

            ballBodyDef.type = BodyDef.BodyType.DynamicBody;

            fdef = new FixtureDef();
            fdef.filter.categoryBits = BOSS_BIT;
            fdef.filter.maskBits = SHIP_BIT | ENEMY_BARRIOR | WEAPON1_BIT | BOSS_BIT;
            fdef.shape = ballShape;

            Body ball = world.createBody(ballBodyDef);

            defsList.add(ball);
            defsList.get(i).createFixture(fdef).setUserData(this);
            // joint between box and ball
            RopeJointDef ropeJointDef = new RopeJointDef();
            ropeJointDef.bodyA = b2body;
            ropeJointDef.bodyB = ball;
            ropeJointDef.maxLength = 4;
            ropeJointDef.localAnchorA.set(Vector2.Zero);
            ropeJointDef.localAnchorB.set(Vector2.Zero);
            //Creating objects in world
            RopeJoint joint = (RopeJoint) world.createJoint(ropeJointDef);
            ballShape.dispose();
        }
        draw = true;
        b2body = world.createBody(bodyDef);
        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }


    Texture circle = new Texture("animations/enemies/EnCharlie/Circle.png");

    @Override
    public void draw(Batch batch){
           for (int i = 0; i < defsList.size(); i++) {
               if(defsList.get(i).getFixtureList().get(0).getShape().getRadius() > .4f) {
                   batch.draw(circleList.get(i),
                           defsList.get(i).getPosition().x - (defsList.get(i)).getFixtureList().get(0).getShape().getRadius(),
                           defsList.get(i).getPosition().y - (defsList.get(i)).getFixtureList().get(0).getShape().getRadius(),
                           1.2f, 1.2f);
               } else if (defsList.get(i).getFixtureList().get(0).getShape().getRadius() != 0) {
                   batch.draw(circleList.get(i),
                           defsList.get(i).getPosition().x - (defsList.get(i)).getFixtureList().get(0).getShape().getRadius(),
                           defsList.get(i).getPosition().y - (defsList.get(i)).getFixtureList().get(0).getShape().getRadius(),
                           .4f, .4f);
               } else if(defsList.get(i).getFixtureList().get(0).getShape().getRadius() == 0){
                   defsList.get(i).setActive(false);
               }

               //settings velocities
               if(defsList.get(i).getPosition().x-getX()>0 && defsList.get(i).getPosition().y-getY()<0) { //down
                   defsList.get(i).setLinearVelocity(0, -5);
               } else if(defsList.get(i).getPosition().x-getX()<0 && defsList.get(i).getPosition().y-getY()>0) { //up
                   defsList.get(i).setLinearVelocity(0, 5);
               } else if(defsList.get(i).getPosition().x-getX()<0 && defsList.get(i).getPosition().y-getY()<0) { //left
                   defsList.get(i).setLinearVelocity(-5, 0);
               } else if(defsList.get(i).getPosition().x-getX()>0 && defsList.get(i).getPosition().y-getY()>0) { //right
                   defsList.get(i).setLinearVelocity(5, 0);
               }

           }

        super.draw(batch);
    }
    @Override
    public void update(float dt){
        stateTime += dt;
        setTexture(circle);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
    @Override
    public void setDirection(boolean y){}

    @Override
    public void reverseDirection() {}
}