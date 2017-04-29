package game.menu.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface AsteroidAnimations {

        Animation asteroidTwoAni = ( new Animation (.08f,
                                                  new TextureRegion(new Texture("img/asteroids/asteroid_2/1.png"))));

        Animation asteroidThreeAni = ( new Animation (.08f,
                                                    new TextureRegion(new Texture("img/asteroids/asteroid_3/1.png"))));

        Animation asteroidFourAni = ( new Animation (.08f,
                                                   new TextureRegion(new Texture("img/asteroids/asteroid_4/1.png"))));

}
