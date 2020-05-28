package game;

import com.ebet.cnge.engine.Camera;

import static org.lwjgl.opengl.GL11.glViewport;

public class Dynamic
{
	private float width;
	private float height;
	
	public Dynamic(float height)
	{
		this.height = height;
		this.width = 0;
	}
	
	/**
	 * you already know the set height, now set the height
	 */
	public void update(int window_width, int window_height)
	{
		var window_aspect = ((float)window_width / window_height);
		
		// sets width to be relative to height the same way the window dims are
		this.width = window_aspect * height;
	}
	
	// the methods below should be self explanatory
	
	public float get_width()
	{
		return width;
	}

	public float get_height()
	{
		return height;
	}
	
}
