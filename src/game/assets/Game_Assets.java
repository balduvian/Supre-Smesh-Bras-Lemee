package game.assets;

import com.ebet.cnge.core.Rect;
import com.ebet.cnge.engine.Texture;
import game.shader.Color_Shader;
import game.shader.Texture_Shader;
import game.shader.Tile_Shader;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Game_Assets extends Assets
{
	/*                                                                    */
	/*              stuff that we load in in our game ol assets           */
	/*                                                                    */
	
	public static Rect rect;
	public static Tile_Shader tile_shader;
	public static Texture_Shader texture_shader;
	public static Color_Shader color_shader;
	
	public static Texture mayro_neutral;
	public static Texture mayro_walk0;
	public static Texture mayro_jump;
	public static Texture mayro_hurt;
	public static Texture mayro_attack0;
	public static Texture mayro_special0;
	
	public static Texture battlefield_floor;
	
	public static Texture sky;
	
	/*                                                                    */
	/*                             constructorio                          */
	/*                                                                    */
	
	public Game_Assets()
	{
		super();
		
		setup_internal(new Loader[]
        {
			() -> rect = new Rect(),
			() -> tile_shader = new Tile_Shader(),
			() -> texture_shader = new Texture_Shader(),
			() -> color_shader = new Color_Shader(),
			() -> Texture.set_parameters(GL_CLAMP_TO_EDGE, GL_CLAMP_TO_EDGE, GL_NEAREST, GL_NEAREST),
			
			/* mayro block */
			
	        () -> mayro_neutral = new Texture("res/image/mayro/mayro_neutral.png"),
	        () -> mayro_walk0 = new Texture("res/image/mayro/mayro_walk_0.png"),
	        () -> mayro_jump = new Texture("res/image/mayro/mayro_jump.png"),
	        () -> mayro_hurt = new Texture("res/image/mayro/mayro_hurt.png"),
	        () -> mayro_attack0 = new Texture("res/image/mayro/mayro_attack_0.png"),
	        () -> mayro_special0 = new Texture("res/image/mayro/mayro_special_0.png"),
	
	        () -> battlefield_floor = new Texture("res/image/stage/battlefield/floor.png"),
	
	        () -> sky = new Texture("res/image/sky.png"),
		});
	}
}
