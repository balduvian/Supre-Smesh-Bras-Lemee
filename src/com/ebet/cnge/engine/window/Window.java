package com.ebet.cnge.engine.window;

import static com.ebet.cnge.engine.Util.*;

import com.ebet.cnge.engine.Camera;
import com.ebet.cnge.engine.Util;
import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class Window
{
	private static double[] mouse_double_x = new double[1];
	private static double[] mouse_double_y = new double[1];
	private static float[] mouse_coords = m2();
	
	private boolean mouse_down;
	private float mouse_x;
	private float mouse_y;
	
	private long window;
	
	private int width;
	private int height;
	
	private boolean resized;
	
	private int refresh;
	
	private Input input;
	
	/*                                                     */
	/*                 window pipeline                     */
	/*                                                     */
	
	/**
	 * starts glfw working,
	 * you MUST call this before calling any window functions
	 */
	public static void init() {
		if (!glfwInit())
			System.out.println("glfw failed to init p");
	}
	
	/**
	 * creates the window, but doesn't do much else,
	 * use the rest of the window pipeline to complete the init
	 */
	public Window(int version_major, int version_minor, boolean resizable, boolean decorated, String title)
	{
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, version_major);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, version_minor);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
		glfwWindowHint(GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);
		
		window = glfwCreateWindow(1, 1, title, 0, 0);
		
		input = new Input();
	}
	
	/**
	 * positions the window on the screen,
	 * also gives it refresh rate information from the montitor you pass in,
	 * it MUST be a legit monitor and not just 0,
	 * if you don't want fullscreen then use the full param,
	 */
	public void set_monitor(long monitor, int x, int y, int width, int height, boolean full)
	{
		this.width = width;
		this.height = height;
		
		var v_m = glfwGetVideoMode(monitor);
		
		this.refresh = v_m.refreshRate();
		
		glfwSetWindowMonitor(window, full ? monitor : 0, x, y, width, height, refresh);
	}
	
	/**
	 * positions the window on the screen, but centered and takes up half
	 * the screen area,
	 * use this if you are lazy
	 *
	 * {@link Window#set_monitor(long, int, int, int, int, boolean)} for further details
	 */
	public void set_monitor(long monitor, boolean full)
	{
		var v_m = glfwGetVideoMode(monitor);
		var width = v_m.width();
		var height = v_m.height();
		set_monitor(monitor, width / 4, height / 4, width / 2, height / 2, full);
	}
	
	/**
	 * makes the window bound to the current opengl context,
	 * also you get to set vsync for this context
	 */
	public void set_context(boolean v_sync)
	{
		glfwMakeContextCurrent(window);
		glfwSwapInterval(v_sync ? GLFW_TRUE : GLFW_FALSE);
	}
	
	public void set_resize() {
		resized = false;
		
		glfwSetWindowSizeCallback(window, (window, width, height) -> {
			this.resized = true;
			this.width = width;
			this.height = height;
		});
	}
	
	public void fullViewport() {
		glViewport(0, 0, width, height);
	}
	
	/*               mouse                    */
	
	public void updateMouse(Camera camera)
	{
		glfwGetCursorPos(window, mouse_double_x, mouse_double_y);
		mouse_x = (float)mouse_double_x[0];
		mouse_y = (float)mouse_double_y[0];
		
		mouse_x /= width;
		mouse_x *= camera.width;
		
		mouse_y /= height;
		mouse_y *= camera.height;
		
		mouse_down = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS;
	}
	
	public float get_mouse_x()
	{
		return mouse_x;
	}
	
	public float get_mouse_y()
	{
		return mouse_y;
	}
	
	public boolean get_mouse_down()
	{
		return mouse_down;
	}
	
	/*               end mouse                    */
	
	public void initKeyCallback() {
		glfwSetKeyCallback(
			window, (window, key, scan_code, action, mods
		) -> input.onKey(key, action));
	}
	
	public Input getInput() {
		return input;
	}
	
	/**
	 * finishes off the window pipeline,
	 * displays the window finna
	 */
	public void show() {
		glfwShowWindow(window);
	}
	
	public void hide() {
		glfwHideWindow(window);
	}
	
	/*                                                     */
	/*                     Running                         */
	/*                                                     */
	
	/**
	 * polls for events and such
	 */
	public void update()
	{
		resized = false;
		glfwPollEvents();
		input.update();
	}
	
	/**
	 * do this at the end of an update
	 * gets next frame ready
	 */
	public void swap()
	{
		glfwSwapBuffers(window);
	}
	
	/**
	 * and then when we're ready to go
	 */
	public void close()
	{
		glfwSetWindowShouldClose(window, true);
	}
	
	/*                                                     */
	/*                   creationism                       */
	/*                                                     */
	
	/**
	 * creates a glfw image with the string provided,
	 * and you also get to specify where the actual pointer bit
	 * is on the cursor,
	 *
	 * NOTE this ain't set the cursor so you gotta do that with
	 * {@link Window#set_cursor(long)}
	 */
	public long create_cursor(String cursor_path, int x, int y)
	{
		try {
			return glfwCreateCursor(make_glfw_image(cursor_path), x, y);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * creates a buffer filled with different sizes of an icon
	 *
	 * and yet again this doesn't set, use the setter method
	 */
	public GLFWImage.Buffer create_icon_buffer(String[] icon_paths)
	{
		var len = icon_paths.length;
		
		var buffer = GLFWImage.malloc(len);
		
		try {
			for (int i = 0; i < len; ++i)
				buffer.put(i, make_glfw_image(icon_paths[i]));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return buffer;
	}
	
	/*                                                     */
	/*                     setters                         */
	/*                                                     */
	
	public void set_cursor_shown()
	{
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
	
	public void set_cursor_hidden()
	{
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	}
	
	public void set_cusor_infinite()
	{
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	
	public void set_icon(GLFWImage.Buffer images)
	{
		glfwSetWindowIcon(window, images);
	}
	
	public void set_cursor(long cursor)
	{
		glfwSetCursor(window, cursor);
	}
	
	public void set_size(int width, int height)
	{
		glfwSetWindowSize(window, width, height);
	}
	
	public void set_title(String title)
	{
		glfwSetWindowTitle(window, title);
	}
	
	/*                                                     */
	/*                      getters                        */
	/*                                                     */
	
	public int get_width()
	{
		return width;
	}
	
	public int get_height()
	{
		return height;
	}
	
	public int get_refresh()
	{
		return refresh;
	}
	
	public boolean get_close()
	{
		return glfwWindowShouldClose(window);
	}
	
	public boolean get_resized()
	{
		return resized;
	}
	
	/*                                                     */
	/*                   static stuff                      */
	/*                                                     */
	
	/**
	 * gets the main monitor
	 */
	public static long default_monitor()
	{
		return glfwGetPrimaryMonitor();
	}
	
	/**
	 * a list of monitors on your system
	 * that glfw can use
	 */
	public static long[] monitor_list()
	{
		var monitors = glfwGetMonitors();
		long[] mon_arr = new long[monitors.capacity()];
		monitors.get(mon_arr, 0, monitors.capacity());
		return mon_arr;
	}
	
	/**
	 * makes an image usable by glfw for:
	 * cursors,
	 * icons
	 */
	public static GLFWImage make_glfw_image(String image_path) throws Exception
	{
		// load in the image
		// **will probably throw something**
		BufferedImage b_i = ImageIO.read(new File(image_path));
		
		// gather information about our buffered image
		int width = b_i.getWidth();
		int height = b_i.getHeight();
		int len = width * height;
		
		int[] rgb_arr = new int[len];
		b_i.getRGB(0, 0, width, height, rgb_arr, 0, width);
		
		// the rgb ints of from the buffered image
		// need to be deconstructed into byte components
		ByteBuffer buffer = BufferUtils.createByteBuffer(len * 4);
		
		for(int i = 0; i < len; ++i)
		{
			int rgb = rgb_arr[i];
			// A R G B is a kek
			buffer.put((byte)(rgb >> 16 & 0xff));
			buffer.put((byte)(rgb >>  8 & 0xff));
			buffer.put((byte)(rgb       & 0xff));
			buffer.put((byte)(rgb >> 24 & 0xff));
		}
		
		buffer.flip();
		
		// create the glfw image object
		GLFWImage img = GLFWImage.create();
		
		// and pass in params to make it
		img.width(width);
		img.height(height);
		img.pixels(buffer);
		
		return img;
	}
}
