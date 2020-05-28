package game;

public class Color
{
	public float r, g, b, a;
	
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(int r, int g, int b)
	{
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = 1;
	}
	
	public Color(int hex)
	{
		/* no alpha specified */
		if (hex <= 0xffffff) {
			this.r = ((hex >> 16) & 0xff) / 255f;
			this.g = ((hex >>  8) & 0xff) / 255f;
			this.b = ((hex      ) & 0xff) / 255f;
			this.a = 1;
			
		} else {
			this.r = ((hex >> 24) & 0xff) / 255f;
			this.g = ((hex >> 16) & 0xff) / 255f;
			this.b = ((hex >>  8) & 0xff) / 255f;
			this.a = ( hex        & 0xff) / 255f;
		}
	}
}
