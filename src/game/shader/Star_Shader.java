package game.shader;

import com.ebet.cnge.engine.Shader;

public class Star_Shader extends Shader.M_VP_Shader
{
	public Star_Shader()
	{
		super(
			"res/shaders/star/vert.glsl", "res/shaders/star/frag.glsl",
			"back_tex_modif",
			"front_tex_modif"
		);
	}
	
	public void give(float[] back, float[] front)
	{
		give_vec4(back[0], back[1], back[2], back[3]);
		give_vec4(front[0], front[1], front[2], front[3]);
	}
	
}
