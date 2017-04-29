package game.game.gameObjects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import game.game.gameHuds.Hud;
import game.game.gameObjects.player.AlphaShip;
import game.game.gameObjects.player.ShipState;
import game.game.gameObjects.weapons.FireBall;
import game.util.Constants;


public class WorldContactListener implements ContactListener, Constants {
    Hud hud;
    public WorldContactListener(Hud hud) {
        super();
        this.hud = hud;
    }

    @Override
    public void beginContact(Contact contact) {
        //When two fixtures begin to collide
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //We have 2 fixtures, we don't know which is which
        //Find out by checking user data

        //Magic
        int cDef = fixA.getFilterData().categoryBits |
                fixB.getFilterData().categoryBits;

        switch (cDef) {
            case SHIP_BIT | BORDER_BIT:
                if(fixA.getUserData()instanceof WorldObject){
                    ((WorldObject) fixA.getUserData()).onHeadHit();
                    if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Normal);
                    } else if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Exploded);
                    }
                } else if(fixB.getUserData()instanceof WorldObject){
                    ((WorldObject) fixB.getUserData()).onHeadHit();
                    if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Normal);
                    } else if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Exploded);
                    }
                }
            break;
            case ENEMY002_BIT:
                ((Enemy) fixA.getUserData()).reverseDirection();
                ((Enemy) fixB.getUserData()).reverseDirection();
                break;
            case ENEMY002_BIT | ENEMY_BARRIOR:
                if(fixA.getUserData()instanceof Enemy){
                    ((Enemy) fixA.getUserData()).reverseDirection();
                } else if(fixB.getUserData()instanceof Enemy){
                    ((Enemy) fixB.getUserData()).reverseDirection();
                }
                break;
            case SHIP_BIT | ITEM_001:
                if(fixA.getFilterData().categoryBits == ITEM_001) {
                    ((AlphaShip) fixB.getUserData()).setState(ShipState.Shielded);
                    ((Item) fixA.getUserData()).destroy();
                } else {
                    ((AlphaShip) fixA.getUserData()).setState(ShipState.Shielded);
                    ((Item) fixB.getUserData()).destroy();
                }
                hud.addScore(250);
                break;
            case SHIP_BIT | ENEMY001_BIT:
                if(fixA.getFilterData().categoryBits == SHIP_BIT) {
                    if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Normal);
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                    } else if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Exploded);
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                    }
                } else {
                    if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Normal);
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                    } else if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Exploded);
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                    }
                }
                hud.addScore(200);
                break;
            case SHIP_BIT | ENEMY002_BIT:
                if(fixA.getFilterData().categoryBits == SHIP_BIT) {
                    if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Normal);
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                    } else if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Exploded);
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                    }
                } else {
                    if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Normal);
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                    } else if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Exploded);
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                    }
                }
                hud.addScore(350);
                break;
            case SHIP_BIT | ENEMY003_BIT:
                if(fixA.getFilterData().categoryBits == SHIP_BIT) {
                    if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Normal);
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                    } else if(((AlphaShip) fixA.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixA.getUserData()).setState(ShipState.Exploded);
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                    }
                } else {
                    if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Shielded){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Normal);
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                    } else if(((AlphaShip) fixB.getUserData()).getCurrentState() == ShipState.Normal){
                        ((AlphaShip) fixB.getUserData()).setState(ShipState.Exploded);
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                    }
                }
                break;
            case WEAPON1_BIT | ENEMY001_BIT:
                if(fixA.getFilterData().categoryBits == WEAPON1_BIT) {
                        if(((Enemy) fixB.getUserData()).getState() == EnemyState.Normal){
                            ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                            ((FireBall) fixA.getUserData()).setToDestroy();
                        }
                } else if(fixB.getFilterData().categoryBits == WEAPON1_BIT) {
                        if (((Enemy) fixA.getUserData()).getState() == EnemyState.Normal) {
                            ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                            ((FireBall) fixB.getUserData()).setToDestroy();
                        }
                    }
                hud.addScore(250);
                break;
            case WEAPON1_BIT | ENEMY002_BIT:
                if(fixA.getFilterData().categoryBits == WEAPON1_BIT) {
                    if(((Enemy) fixB.getUserData()).getState() == EnemyState.Normal){
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                        ((FireBall) fixA.getUserData()).setToDestroy();
                    }
                } else if(fixB.getFilterData().categoryBits == WEAPON1_BIT) {
                    if (((Enemy) fixA.getUserData()).getState() == EnemyState.Normal) {
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                        ((FireBall) fixB.getUserData()).setToDestroy();
                    }
                }
                hud.addScore(350);
                break;
            case WEAPON1_BIT | ENEMY003_BIT:
                if(fixA.getFilterData().categoryBits == WEAPON1_BIT) {
                    if(((Enemy) fixB.getUserData()).getState() == EnemyState.Normal){
                        ((Enemy) fixB.getUserData()).setState(EnemyState.Exploded);
                        ((FireBall) fixA.getUserData()).setToDestroy();
                    }
                } else if(fixB.getFilterData().categoryBits == WEAPON1_BIT) {
                    if (((Enemy) fixA.getUserData()).getState() == EnemyState.Normal) {
                        ((Enemy) fixA.getUserData()).setState(EnemyState.Exploded);
                        ((FireBall) fixB.getUserData()).setToDestroy();
                    }
                }
                break;
            case WEAPON1_BIT | BOSS_BIT:
                if(fixA.getFilterData().categoryBits == BOSS_BIT) {
                    if(fixA.getShape().getRadius() > .2f){
                        fixA.getShape().setRadius(fixA.getShape().getRadius() - 1f);
                    } else {
                        fixA.getShape().setRadius(0);
                    }
                    ((FireBall) fixB.getUserData()).setToDestroy();
                } else if(fixB.getFilterData().categoryBits == BOSS_BIT) {
                    if(fixB.getShape().getRadius() > .2f){
                        fixB.getShape().setRadius(fixA.getShape().getRadius() - 1f);
                    } else {
                        fixB.getShape().setRadius(0);
                    }
                    ((FireBall) fixA.getUserData()).setToDestroy();
                }
                break;
            case SHIP_BIT | LEVEL2_BIT:
                hud.showLevel2();
                break;
            case GODMODE_BIT | LEVEL2_BIT:
                System.out.println("Level 2");
                hud.showLevel2();
                break;
        }
        //Case 1:When the player collides with border
        //Case 2:When an enemy collides with an enemy barrier (invisible barrier)
        //Case 3:When an enemy2 collides with an enemy2
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End contact","");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Once it has collided,
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //What happens due to collision
    }
}
