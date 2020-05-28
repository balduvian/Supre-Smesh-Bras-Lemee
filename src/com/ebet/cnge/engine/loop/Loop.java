package com.ebet.cnge.engine.loop;

public class Loop
{
	public static final long BILLION = 1000000000;
	
	private int nominal_frames;
	private int frames_this_second;
	
	private long frame_time;
	private long last;
	private long now;
	private long delta;
	
	private long[] frame_history;
	private int frame_pointer;
	
	public Loop(int fps)
	{
		nominal_frames = fps;
		
		System.nanoTime();
	}
	
	/**
	 * will keep loopin and loopin until exit condition
	 * is satisfied,
	 * takes over from anything else on the main thread,
	 *
	 * if you try and call this twice then all hell will
	 * rain down upon you
	 */
	public void loop(IExitCondition exit_condition, IFrame frame)
	{
		// create frame history, using a maxiumum framerate
		// of which you set using nominal frames
		frame_history = new long[nominal_frames];
		frame_pointer = 0;
		
		System.out.println(nominal_frames);
		frame_time = BILLION / (nominal_frames);
		
		last = System.nanoTime();
		
		while (!exit_condition.will_exit())
		{
			now = System.nanoTime();
			delta = now - last;
			
			// if a frame should be rendered
			if(delta >= frame_time)
			{
				// inject delta into history
				frame_history[frame_pointer] = delta;
				
				// count the number of fps we have
				var temp_ptr = frame_pointer;
				var total_time = 0l;
				var frame_tally = 0;
				for(; frame_tally < nominal_frames; ++frame_tally)
				{
					// if the time we've collected so far
					// goes over a second, then we've
					// found how many frames
					total_time += frame_history[temp_ptr];
					if(total_time > BILLION) break;
					
					// go back in time
					--temp_ptr;
					if(temp_ptr < 0) temp_ptr = nominal_frames - 1;
				}
				
				// do frame and pass in timing info
				// calculate the delta time
				frame.frame(new Timing(frame_tally, delta, ((double)delta / BILLION)));
				
				// set the baseline for the next frame to be right now
				last = now;
				
				// increment and wrap frame pointer
				++frame_pointer;
				frame_pointer %= nominal_frames;
			}
		}
	}
	
}
