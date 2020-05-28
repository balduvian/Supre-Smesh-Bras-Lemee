package game.assets;

public abstract class Assets
{
	/*                                                                    */
	/*                             functionality                          */
	/*                                                                    */
	
	protected interface Loader { void load(); }
	
	protected Loader[] loader_list;
	protected int loader_pointer;
	
	/**
	 * init this to start getting ready to load
	 */
	public Assets()
	{
		loader_pointer = 0;
	}
	
	protected void setup_internal(Loader[] loader_list)
	{
		this.loader_list = loader_list;
	}
	
	/**
	 * will return true if loading is now done
	 */
	public boolean do_load()
	{
		// load one asset from our list
		// we then let the renderer keep doin it's good ol rendering job
		loader_list[loader_pointer].load();
		++loader_pointer;
		
		// if we have loaded everything
		return loader_pointer == loader_list.length;
	}
}
