import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AsteroidOne extends Actor {

    private TextureRegion current_frame;
    private Texture texture1, texture2, texture3, texture4, texture5, texture6, texture7, texture8, texture9, texture10,
    texture11, texture12, texture13, texture14, texture15, texture16, texture17, texture18;

    private Animation asteroid;
    private float stateTime;

    public AsteroidOne() {
        texture1 = new Texture("img/asteroids/asteroid_1/1.gif");
        texture2 = new Texture("img/asteroids/asteroid_1/2.gif");
        texture3 = new Texture("img/asteroids/asteroid_1/3.gif");
        texture4 = new Texture("img/asteroids/asteroid_1/4.gif");
        texture5 = new Texture("img/asteroids/asteroid_1/5.gif");
        texture6 = new Texture("img/asteroids/asteroid_1/6.gif");
        texture7 = new Texture("img/asteroids/asteroid_1/7.gif");
        texture8 = new Texture("img/asteroids/asteroid_1/8.gif");
        texture9 = new Texture("img/asteroids/asteroid_1/9.gif");
        texture10 = new Texture("img/asteroids/asteroid_1/10.gif");
        texture11 = new Texture("img/asteroids/asteroid_1/11.gif");
        texture12 = new Texture("img/asteroids/asteroid_1/12.gif");
        texture13 = new Texture("img/asteroids/asteroid_1/13.gif");
        texture14 = new Texture("img/asteroids/asteroid_1/14.gif");
        texture15 = new Texture("img/asteroids/asteroid_1/15.gif");
        texture16 = new Texture("img/asteroids/asteroid_1/16.gif");
        texture17 = new Texture("img/asteroids/asteroid_1/17.gif");
        texture18 = new Texture("img/asteroids/asteroid_1/18.gif");

        asteroid = new Animation (.08f,
                new TextureRegion(new TextureRegion(texture1)),
                new TextureRegion(new TextureRegion(texture2)),
                new TextureRegion(new TextureRegion(texture3)),
                new TextureRegion(new TextureRegion(texture4)),
                new TextureRegion(new TextureRegion(texture5)),
                new TextureRegion(new TextureRegion(texture6)),
                new TextureRegion(new TextureRegion(texture7)),
                new TextureRegion(new TextureRegion(texture8)),
                new TextureRegion(new TextureRegion(texture9)),
                new TextureRegion(new TextureRegion(texture10)),
                new TextureRegion(new TextureRegion(texture11)),
                new TextureRegion(new TextureRegion(texture12)),
                new TextureRegion(new TextureRegion(texture13)),
                new TextureRegion(new TextureRegion(texture14)),
                new TextureRegion(new TextureRegion(texture15)),
                new TextureRegion(new TextureRegion(texture16)),
                new TextureRegion(new TextureRegion(texture17)),
                new TextureRegion(new TextureRegion(texture18)));
        asteroid.setPlayMode(Animation.PlayMode.LOOP);
    }


    @Override
    public void act(float delta) {
        current_frame = asteroid.getKeyFrame(stateTime += delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(current_frame, 10, 50);
    }
}