import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by samir on 03/02/2016.
 */
public class AnimationWrapper extends Actor {
    int x,y,w,h;
    public AnimationWrapper(Animation a, int X, int Y, int W, int H){
        super();
        animation = a;x = X; y = Y;h = H; w = W;
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Animation animation;
    public TextureRegionDrawable frame = new TextureRegionDrawable();

    private float sT;

    @Override
    public void act(float delta){
        super.act(delta);
        sT += delta;
        if(animation.isAnimationFinished(sT)){
            sT = 0;
        }
        frame.setRegion(animation.getKeyFrame(sT));
        setDrawable(frame);
    }

    Drawable drawable = null;
    public void setDrawable(Drawable d){
        drawable = d;
    }
    public void draw(SpriteBatch b, float pA){
        super.draw(b, pA);
        drawable.draw(b, x, y, w, h);
    }

}

//TextButton.VisImageTextButtonStyle myButtonStyle = new VisImageTextButton.VisImageTextButtonStyle();
//myButtonStyle.font = myTxButton.getStyle().font;

//mySlider.addListener(new ChangeListener() {
//	public void changed(ChangeEvent event, Actor actor) {
//		System.out.println("Changed!"+mySlider.getValue());
//	}
//});

//TabbedPane myTabbedPane = new TabbedPane();
//myTabbedPane.add(new TestTab());

//// Creating a drag pane using an internal GridGroup:
//DragPane dragPane = new DragPane(new GridGroup());
//// Creating dragging listener:
//Draggable draggable = new Draggable();
//dragPane.setDraggable(draggable);
//// This listener filters actors dropped on the pane. Only actors that were originally pane's children are accepted:
//dragPane.setListener(new DragPane.DragPaneListener.AcceptOwnChildren());
//dragPane.setSize(300,300);
//addActor(dragPane);
//dragPane.addActor(xxx);
