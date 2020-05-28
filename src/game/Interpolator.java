package game;

public class Interpolator
{
	private boolean going;
	private float initial_value;
	private float final_value;
	private long time;
	private long timer;
	private double along;
	
	public Interpolator(float initial_value, float final_value, long time)
	{
		this.initial_value = initial_value;
		this.final_value = final_value;
		this.time = time;
		timer = 0;
		going = false;
		along = 0;
	}
	
	public void start()
	{
		timer = 0;
		going = true;
	}
	
	/**
	 * updates based on time
	 */
	public void update(long nanos)
	{
		if(going)
		{
			timer += nanos;
			if (timer >= time)
			{
				timer = time;
				going = false;
			}
			
			along = ((double)timer / time);
		}
	}
	
	public float get_along()
	{
		return (float)((final_value - initial_value) * along + initial_value);
	}
	
	public void reset()
	{
		timer = 0;
		going = false;
	}
}
