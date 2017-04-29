package desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;

import game.Tri_X;

public class DesktopLauncher {
	public static void main (String[] arg) {

		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Tri_X myGame = new Tri_X();
		new LwjglApplication(myGame, config);

		config.vSyncEnabled = true;
		//config.fullscreen = true;
		config.width = 1920;
		config.height = 1080;

		config.foregroundFPS = 60; // Setting to 0 disables foreground fps throttling
		config.backgroundFPS = 0; // Setting to 0 disables background fps throttling
	}
}
