package game.game.gameObjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import game.Tri_X;
import game.game.gameHuds.Hud;
import game.game.gameObjects.enemy.worldOne.EnAlpha;
import game.game.gameObjects.enemy.worldOne.EnBeta;
import game.game.gameObjects.enemy.worldOne.EnCharlie;
import game.game.gameObjects.enemy.worldOne.EnEcho;
import game.game.gameObjects.enemy.worldOne.EnFanta;
import game.game.gameObjects.enemy.worldOne.EnGamma;
import game.game.gameObjects.enemy.worldOne.boss.EnBossOne;
import game.game.gameObjects.enemy.worldOne.EnDelta;
import game.game.gameObjects.walls.Border;
import game.game.gameObjects.walls.EnemyBarrier;
import game.game.gameObjects.walls.Level2;
import game.game.gameScreens.Level1;
import game.menu.screens.fullScreens.Difficulty;

public class WorldObjectsGenerator {
    private Array<EnAlpha> enemies1;
    private Array<EnBeta> enemies2;
    private Array<EnCharlie> enemies3;
    private Array<EnDelta> enemies4;
    private Array<EnEcho> enemies5;
    private Array<EnFanta> enemies6;
    private Array<EnGamma> enemies7;

    private Array<EnBossOne> enemiesBoss1;

    public WorldObjectsGenerator(Difficulty difficulty, World world, AssetManager manager, Hud hud, TiledMap map){
            generateEnemies(difficulty, world, manager, hud, map);
    }

    public void generateEnemies(Difficulty difficulty, World world, AssetManager manager, Hud hud, TiledMap mapIn){
        TiledMap map = mapIn;

        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Creating borders
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Border(manager, world, map, rect);
        }

        //Creating enemy barriers
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new EnemyBarrier(manager, world, map, rect);
        }


        //Creating finish wall
        for(MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Level2(manager, world, map, rect);
        }

        //Creating enemies 1
        enemies1 = new Array<EnAlpha>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies1.add(new EnAlpha(difficulty, manager, world, rect));
        }
        //Creating enemies 2
        int pos = 0;
        enemies2 = new Array<EnBeta>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies2.add(new EnBeta(difficulty, manager, world, rect.getX() / Tri_X.ppm, rect.getY() / Tri_X.ppm));
            //Setting direction
            if(rect.getY()>540){
                enemies2.get(pos).setColor(Color.RED);
                enemies2.get(pos).setDirection(true);
            } else {
                enemies2.get(pos).setColor(Color.GREEN);
                enemies2.get(pos).setDirection(false);
            }
            pos++;
        }

        pos = 0;
        boolean rotateL = true;
        //Creating enemies 3
        enemies3 = new Array<EnCharlie>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies3.add(new EnCharlie(difficulty, manager, world, rect.getX() / Tri_X.ppm, rect.getY() / Tri_X.ppm));
            if(rotateL){
                enemies3.get(pos).setDirection(true);
                rotateL = false;
            } else {
                enemies3.get(pos).setDirection(false);
                rotateL = true;
            }
            pos++;
        }

        enemies4 = new Array<EnDelta>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies4.add(new EnDelta(difficulty, manager, world,  rect.getX() / Tri_X.ppm, rect.getY() / Tri_X.ppm));
        }

        enemies5 = new Array<EnEcho>();
        for(MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies5.add(new EnEcho(difficulty, manager, world, rect));
        }

        enemies6 = new Array<EnFanta>();
        for(MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies6.add(new EnFanta(difficulty, manager, world, rect));
        }

        enemies7 = new Array<EnGamma>();
        for(MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies7.add(new EnGamma(difficulty, manager, world, rect));
        }

        //enemiesBoss1 = new Array<EnBossOne>();
        //for(MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){
        //    Rectangle rect = ((RectangleMapObject) object).getRectangle();
        //    enemiesBoss1.add(new EnBossOne(manager, screen, rect.getX() / Tri_X.ppm, rect.getY() / Tri_X.ppm));
        //}

        enemies = new Array<Enemy>();
        enemies.addAll(enemies1);
        enemies.addAll(enemies2);
        enemies.addAll(enemies3);
        enemies.addAll(enemies4);
        enemies.addAll(enemies5);
        enemies.addAll(enemies6);
        enemies.addAll(enemies7);
    }

    Array<Enemy> enemies;
    public Array<Enemy> getEnemies(){
        return enemies;
    }
}
