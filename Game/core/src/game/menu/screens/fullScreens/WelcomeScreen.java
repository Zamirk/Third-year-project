package game.menu.screens.fullScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kotcrab.vis.ui.VisUI;

import game.Tri_X;

public class WelcomeScreen implements Screen {
	public WelcomeScreen() {
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		stage = new Stage(new StretchViewport(1920, 1080, new OrthographicCamera()));

		txtrBg = new Texture( Gdx.files.internal("img/backgrounds/background_1.jpg") );
		bg = new Image(txtrBg);
		stage.addActor(bg);
		stage.act();
		font = new BitmapFont(Gdx.files.internal("font/font2.fnt"),false); 	//** font **//

		skin = new Skin(Gdx.files.internal("font/uiskin.json"));
		dialog = new Dialog("Welcome to the game", skin);

		MoveToAction welcomeAction = new MoveToAction();
		welcomeAction.setInterpolation(Interpolation.bounceOut);
		welcomeAction.setDuration(3);
		welcomeAction.setPosition(750, 0);

		dialog.setOrigin(dialog.getWidth() / 2, dialog.getHeight() / 2);
		dialog.setSize(300, 300);
		dialog.setPosition(750, 300);
		dialog.addAction(welcomeAction);

		stage.addActor(dialog);

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				dialog.hide();
			}
		}, 1);
	}

	@Override public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	@Override public void hide() {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

	}
	public void dispose() {
		stage.dispose();
	}
	private Dialog dialog;
	private Stage stage;
	private Texture txtrBg;
	private Image bg;
	private Skin skin;
	private BitmapFont font;
}
