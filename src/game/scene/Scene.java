package game.scene;

import com.ebet.cnge.engine.window.Input;
import com.ebet.cnge.engine.window.Window;
import com.ebet.cnge.engine.loop.Timing;

public abstract class Scene
{
	/* constructor */
	
	public Scene() {}
	
	/* overrides */
	
	/**
	 * called during a frame if the window is resized (before update)
	 */
	public abstract void screenResized(Window window);
	
	/**
	 * called before render
	 * should do everything you want to do with game mechanics
	 * for this frame
	 */
	public abstract void update(Input input, Timing timing);
	
	/**
	 * put all opengl calls and stuff that puts stuff on the screen here
	 *
	 */
	public abstract void render();
	
	/**
	 * scenemanager calls this to check if a scene is going to be switched to
	 *
	 * @return the scene to be switched to, can be null for no switch
	 */
	public abstract Scene switchScene();
}
