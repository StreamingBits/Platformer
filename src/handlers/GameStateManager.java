package handlers;
/**
 *
 * @author alvarez
 */
import java.util.Stack;
import platformer.Game;
import states.GameState;
import states.LevelSelect;
import states.Menu;
import states.Play;
import states.Store;

public class GameStateManager {
	
	private Game game;
	
	private Stack<GameState> gameStates;
	
	public static final int MENU = 83774392;
	public static final int PLAY = 388031654;
	public static final int LEVEL_SELECT = 9238732;
        public static final int STORE = 999038023;
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(MENU);
	}
	
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	public Game game() { return game; }
	
	private GameState getState(int state) {
		if(state == MENU) return new Menu(this);
		if(state == PLAY) return new Play(this);
		if(state == LEVEL_SELECT) return new LevelSelect(this);
                if(state == STORE) return new Store(this);
		return null;
	}
	
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	public void pushState(int state) {
		gameStates.push(getState(state));
	}
	
	public void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}
	
}
