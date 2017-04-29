package game.game.gameObjects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import game.Tri_X;
import game.game.gameScreens.Level1;

public abstract class WorldObject {
    protected Fixture fixture;
    protected World world;
    protected TiledMap map;
    protected TiledMap title;
    protected Rectangle bounds;
    protected Body body;

    public WorldObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;            //Immovable object
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Tri_X.ppm, (bounds.getY() + bounds.getHeight() / 2) / Tri_X.ppm);
        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Tri_X.ppm, bounds.getHeight() / 2 / Tri_X.ppm);
        fdef.shape = shape;
        //Capturing object in a fixture
        fixture = body.createFixture(fdef);
    }

    public abstract void onHeadHit();
    //Setting the filter to something else,
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    //Getting cell
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getCell(
                (int)(body.getPosition().x * 1f / 36),
                (int)(body.getPosition().y * 1f / 36));
    }
}
