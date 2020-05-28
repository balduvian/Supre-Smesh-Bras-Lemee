package com.ebet.cnge.engine;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Util
{
	public static int rand_int(int low, int high)
	{
		return (int)((Math.random() * (high - low + 1)) + low);
	}
	
	public static float rand_float(float low, float high)
	{
		return (float)((Math.random() * (high - low)) + low);
	}
	
	/*                             */
	/*        opengl helper        */
	/*                             */
	
	public static void depth_test()
	{
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
	}
	
	public static void no_depth()
	{
		glDisable(GL_DEPTH_TEST);
	}
	
	public static void winding_order()
	{
		glFrontFace(GL_CCW);
	}
	
	public static void cull_face(boolean front, boolean back)
	{
		if(front | back)
		{
			glEnable(GL_CULL_FACE);
			glCullFace(front ? back ? GL_FRONT_AND_BACK : GL_FRONT : GL_BACK);
		}
		else
			glDisable(GL_CULL_FACE);
	}
	
	public static int mod(int a, int b)
	{
		return (((a % b) + b) % b);
	}
	
	public static void clear(float r, float g, float b, float a)
	{
		GL11.glClearColor(r, g, b, a);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clear_depth()
	{
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	/*                             */
	/*        matrix helper        */
	/*                             */
	
	public static float[] m16()
	{
		return new float[16];
	}
	
	public static float[] m4()
	{
		return new float[4];
	}
	
	public static float[] m2()
	{
		return new float[2];
	}
	
	/*                             */
	/*         ewwor helper        */
	/*                             */

	/**
	 * allows lambdas to capture a variable
	 */
	public static class Capture<T> {
		private T value;
		
		public Capture(T construct) {
			value = construct;
		}
		
		public Capture() {
			value = null;
		}
		
		public Capture<T> set(T set) {
			value = set;
			
			return this;
		}
		
		public T get() {
			return value;
		}
		
		public T release() {
			var ret = value;
			
			value = null;
			
			return ret;
		}
	}
}
