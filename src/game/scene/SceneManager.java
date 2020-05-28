package game.scene;

import com.ebet.cnge.engine.window.Window;
import com.ebet.cnge.engine.loop.Timing;

public class SceneManager {
	private Scene currentScene;
	
	public SceneManager(Window window, Scene startingScene) {
		setCurrentScene(window, startingScene);
	}
	
	/**
	 * handles all of scene updating and rendering
	 *
	 * @param window the game window
	 * @param timing timing information from the game loop
	 */
	public void update(Window window, Timing timing) {
		/* resize the window */
		if(window.get_resized())
			currentScene.screenResized(window);
		
		/* update and render */
		currentScene.update(window.getInput(), timing);
		currentScene.render();
		
		/* attempt to switch the scene */
		var switchScene = currentScene.switchScene();
		if (switchScene != null)
			setCurrentScene(window, switchScene);
	}
	
	private void setCurrentScene(Window window, Scene currentScene) {
		this.currentScene = currentScene;
		currentScene.screenResized(window);
	}
}
