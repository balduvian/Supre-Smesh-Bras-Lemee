package com.ebet.cnge.engine.loop;

public class Timing {
	public int frames;
	public long nanos;
	public double time;
	
	public Timing(int frames, long nanos, double time) {
		this.frames = frames;
		this.nanos = nanos;
		this.time = time;
	}
}
