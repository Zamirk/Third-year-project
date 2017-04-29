package game.game.gameObjects.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import game.Tri_X;
import game.game.gameObjects.Item;
import game.util.Constants;

public class Shields extends Item implements Constants {
    private TextureAtlas enAtlas;
    private Animation itemAnimation;
    private float stateTime;

    public Shields(World world, float x, float y) {
        super(world, x, y);
        defineItem();

        stateTime = 0;
        enAtlas = new TextureAtlas("animations/enemies/Hex/Hex.pack");
        itemAnimation = new Animation(1/60f, enAtlas.getRegions());
        setColor(Color.MAGENTA);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Tri_X.ppm);
        fdef.filter.categoryBits = ITEM_001;
        fdef.filter.maskBits = SHIP_BIT;
        shape.setRadius(50 / Tri_X.ppm);
        setSize(90/Tri_X.ppm,90/Tri_X.ppm);

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        body.setLinearVelocity(-1,0);
    }

    @Override
    public void update(float dt){
        super.update(dt);
        stateTime += dt;
        setRegion(itemAnimation.getKeyFrame(stateTime, true));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
