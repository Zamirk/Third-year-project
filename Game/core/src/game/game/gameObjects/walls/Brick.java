package game.game.gameObjects.walls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import game.game.gameObjects.WorldObject;
import game.game.gameScreens.Level1;
import game.util.Constants;

//public class Brick extends WorldObject implements Constants {
//    AssetManager manager;
//    public Brick(Level1 screen, Rectangle bounds){
//        super(screen, bounds);
//        fixture.setUserData(this);
//        setCategoryFilter(BREAKABLE_WALL_BIT);
//        manager = screen.getManager();
//    }
//
//    @Override
//    public void onHeadHit() {
//        Gdx.app.log("Item", "Collision");
//        setCategoryFilter(DESTROYED_BIT);
//        //Removing cell
//        getCell().setTile(null);
//        //Hud.addScore(200);
//        manager.get("audio/effects/coin.wav", Sound.class).play();
//    }
//}
