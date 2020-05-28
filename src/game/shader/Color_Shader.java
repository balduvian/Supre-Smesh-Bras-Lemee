package game.shader;

import com.ebet.cnge.engine.Shader;

public class Color_Shader extends Shader.M_VP_Shader
{
	public Color_Shader()
	{
		super(
			"res/shaders/color/vert.glsl", "res/shaders/color/frag.glsl",
			"in_color"
		);
	}
	
	public void give(float r, float g, float b, float a)
	{
		give_vec4(r, g, b, a);
	}
	
}
