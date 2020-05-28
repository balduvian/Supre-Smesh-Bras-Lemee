package game.scene;

import com.ebet.cnge.engine.Camera;
import com.ebet.cnge.engine.loop.Timing;
import com.ebet.cnge.engine.window.Input;
import com.ebet.cnge.engine.window.Window;
import game.Dynamic;
import game.smesh.character.Mayro;
import game.smesh.stage.Stage;

import static com.ebet.cnge.engine.Util.clear;

public class FightScene extends Scene {
	private Dynamic dynamic;
	private Camera camera;
	
	private Mayro mayro;
	
	private Stage stage;
	
	public FightScene(Stage stage) {
		this.stage = stage;
		
		dynamic = new Dynamic(32);
		camera = new Camera();
		
		var player1Spawn = stage.getPlayerSpawn(0);
		mayro = new Mayro(player1Spawn.x, player1Spawn.y);
	}
	
	@Override
	public void screenResized(Window window) {
		dynamic.update(window.get_width(), window.get_height());
		camera.set_orthographic(dynamic.get_width(), dynamic.get_height());
		window.fullViewport();
	}
	
	@Override
	public void update(Input input, Timing timing) {
		stage.update();
		
		mayro.update(input, stage);
		
		camera.setCenter(mayro.getX(), mayro.getY());
		camera.update();
	}
	
	@Override
	public void render() {
		clear(0, 0.5f, 0, 1);
		
		stage.render(camera);
		
		mayro.render(camera);
	}
	
	@Override
	public Scene switchScene() {
		return null;
	}
}
