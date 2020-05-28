package game.shader;

import com.ebet.cnge.engine.Shader;

public class Texture_Shader extends Shader.M_VP_Shader
{
	public Texture_Shader()
	{
		super(
			"res/shaders/texture_tint/vert.glsl", "res/shaders/texture_tint/frag.glsl",
			"in_color"
		);
	}
	
	public void give(float r, float g, float b, float a)
	{
		give_vec4(r, g, b, a);
	}
	
	public void give()
	{
		give_vec4(1, 1, 1, 1);
	}
	
}
