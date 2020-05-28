package game;

import com.ebet.cnge.engine.loop.Loop;
import com.ebet.cnge.engine.window.Window;
import com.ebet.cnge.sound.AL_Management;
import game.assets.Game_Assets;
import game.scene.FightScene;
import game.scene.Scene;
import game.scene.SceneManager;
import game.smesh.stage.battlefield.Battlefield;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;

public class Game
{
	public static final float GAME_HEIGHT = 100;
	
	public static final int SCENE_MENU = 0;
	public static final int SCENE_GAME = 1;
	
	public static final int KEY_SPACE = 0;
	
	private interface Scene_Switch
	{
		void switch_scene();
	}
	
	private Scene scene;
	
	private void setScene(Scene s) {
		scene = s;
	}
	
	public Game() {
		Window.init();
		
		var window = new Window(3, 3, true, true, "CNGE Game Test");
		
		window.set_context(true);
		createCapabilities();
		
		window.show();
		window.set_resize();
		window.initKeyCallback();
		
		var monitors = Window.monitor_list();
		window.set_monitor(monitors[monitors.length - 1], false);
		
		var loop = new Loop(60);
		
		// setup openAL
		//var al = new AL_Management();
		
		// load in assets
		var game_assets = new Game_Assets();
		while (!game_assets.do_load());
		
		// enable alotta stuff
		glEnable(GL30.GL_CLIP_DISTANCE0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		var sceneManager = new SceneManager(window, new FightScene(new Battlefield()));
		
		loop.loop(
			window::get_close, (timing) -> {
				window.update();
				
				sceneManager.update(window, timing);
				
				window.swap();
			}
		);
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
