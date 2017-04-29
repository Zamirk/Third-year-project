package game.menu.screens.fullScreens;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.UBJsonReader;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

import game.util.Constants;
import game.menu.asteroids.AsteroidAnimations;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;

public class HollowDeckScreen implements Screen, AsteroidAnimations, Constants {
	//stage.setDebugAll(true);
	private Image bg, tableBg;
	private Stage stage;
	private int z = 0;
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance modelInstance,a,b,c,d;
	private Environment environment;
	private AnimationController controller;
	private UBJsonReader jsonReader;
	private G3dModelLoader modelLoader;
	private boolean hologram = false;
	private VisTextButton backToStart;
	private VisTable table;

	public HollowDeckScreen() {}
	public void show(){
		Gdx.input.setInputProcessor(stage);
		table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(.5f))); 	//Fading in
	}
	public boolean isHologram() {
		return hologram;
	}
	public void showMenu(){
		hologram = false;													//Stops Rendering
		table.clearActions();												//Clears Actions
		table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));	//Fade In
		table.setTouchable(Touchable.enabled);								//Enables Interactions
	}
	public void create() {
		stage = new Stage();												//Creates a new stage to put things on, and draw(),

		camera = new PerspectiveCamera(75,Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //FOV 75
		camera.position.set(10f, 10f, 10f);									//Camera position
		camera.lookAt(0, 0, 0);												//Direction of camera view
		camera.near = 1f;													//Max distance near
		camera.far = 300f;													//Max distance far
		camera.update();													//Updating the camera

		modelBatch = new ModelBatch();      								// A ModelBatch is like a SpriteBatch,
		jsonReader = new UBJsonReader();        							// Model loader needs a binary json reader to decode
		modelLoader = new G3dModelLoader(jsonReader);

		bg = new Image(new Texture( Gdx.files.internal("img/backgrounds/background_3.png")));		//Background image
		tableBg = new Image(new Texture(Gdx.files.internal("img/menu/Menu_Transparent_1.png")));				//Menu image
		tableBg.setSize(1920, 880);
		tableBg.setPosition(0, 100);

		backToStart = new VisTextButton("Back");									//Menu Button
		backToStart.setSize(250, 125);
		backToStart.setPosition(25, 25);

		table = new VisTable();
		table.setSize(1720, 880);
		table.setPosition(100, 100);
		table.align(Align.top);

		stage.addActor(bg);
		stage.addActor(tableBg);
		stage.addActor(table);
		stage.addActor(backToStart);
		// Finally we want some light, or we wont see our color.  The environment gets passed in during
		// the rendering process.  Create one, then create an Ambient ( non-positioned, non-directional ) light.
		TextureRegionDrawable objectImgs[] = new TextureRegionDrawable[]{
				new TextureRegionDrawable(new TextureRegion(new Texture("img/hollowDeckImgs/EnemyOne.png"))),
				new TextureRegionDrawable(new TextureRegion(new Texture("img/hollowDeckImgs/EnemyOne.png"))),
				new TextureRegionDrawable(new TextureRegion(new Texture("img/hollowDeckImgs/EnemyOne.png"))),
				new TextureRegionDrawable(new TextureRegion(new Texture("img/hollowDeckImgs/EnemyOne.png"))),
		};
		final ModelInstance objectModels[] = new ModelInstance[]{
				//new ModelInstance(modelLoader.loadModel(Gdx.files.getFileHandle("asd.g3db", Files.FileType.Internal))),
				new ModelInstance(modelLoader.loadModel(Gdx.files.getFileHandle("3D/Coin.g3db", Files.FileType.Internal))),
				new ModelInstance(modelLoader.loadModel(Gdx.files.getFileHandle("3D/Coin2.g3db", Files.FileType.Internal))),
				new ModelInstance(modelLoader.loadModel(Gdx.files.getFileHandle("3D/EnemyBeem.g3db", Files.FileType.Internal))),
				new ModelInstance(modelLoader.loadModel(Gdx.files.getFileHandle("3D/shiip1.g3db", Files.FileType.Internal)))
		};

		for(int i=0;i<5;i++) {												//Looping Columns
			table.row().minHeight(150).minWidth(150).maxHeight(150).maxWidth(150).center().expandX().top();
			for(int n=0;n<7;n++) {											//Looping Rows
				VisImageTextButton button;
				if(z<objectImgs.length) {									//If there are images left
					button = new VisImageTextButton("", objectImgs[z]);
					final int temp = z;
					button.addListener(										//Adding Listener
							new InputListener() {
								@Override
								public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
									modelInstance = objectModels[temp];		//Sets the new model
									table.clearActions();					//Clears Actions
									table.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.5f)));
									hologram = true;						//Allows Rendering
									table.setTouchable(Touchable.disabled);	//Stop Interactions
									return false;
								}
							});
				}else{														//else null image added
					button = new VisImageTextButton("", new Button().getBackground());
				}
				table.add(button);											//Add the button
				z++;														//Button Index
			}
	}
	}
	public VisTextButton getMenuButton() {
		return backToStart;
	}
	public void updateCamera(){
			//move the model down a bit on the screen ( in a z-up world, down is -z ).
			if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
				camera.position.add(0, 0, .1f);
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.E)) {
				camera.position.add(0, 0, -.1f);
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				camera.position.add(0, -.1f, 0);
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				camera.position.add(0, .1f, 0);
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				camera.position.add(.1f, 0, 0);
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				camera.position.add(-.1f, 0, 0);
				camera.update();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.T)) {
				modelInstance.transform.rotate(0, 11, 0, 1);
				camera.update();
			}
		}

	@Override public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	@Override public void hide() {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void render(float delta) {
		updateCamera();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

		if(hologram) {
			modelBatch.begin(camera);
			modelBatch.render(modelInstance, environment);
			modelBatch.end();
		}
	}
	@Override public void dispose() {
		modelBatch.dispose();
		model.dispose();
		stage.dispose();
		VisUI.dispose();
		System.out.print("Disposing Hollow Deck.\n");
	}
}