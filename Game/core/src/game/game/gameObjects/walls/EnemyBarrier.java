package game.game.gameObjects.walls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import game.game.gameObjects.WorldObject;
import game.game.gameScreens.Level1;
import game.util.Constants;
//An enemy barrier is a barrier which stops an enemy, or causes it to change direction
public class EnemyBarrier extends WorldObject implements Constants {
    AssetManager manager;
    
    public EnemyBarrier(AssetManager manager, World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(ENEMY_BARRIOR);
        this.manager = manager;
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Border", "Collision");
        manager.get("audio/effects/bump.wav", Sound.class).play();
    }

    public void dispose(){

    }
}

