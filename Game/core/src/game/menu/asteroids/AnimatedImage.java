package game.menu.asteroids;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image implements AsteroidAnimations {
    protected Animation animation = null;
    private float stateTime = 0;

    public AnimatedImage(Animation a) {                //Using dud animation,
        super(a.getKeyFrame(0));
            this.animation = a;
        }

    @Override
    public void act(float delta)
    {
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime+=delta, true));
        super.act(delta);
    }
}