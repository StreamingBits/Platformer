
package platformer;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Platformer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new
            LwjglApplicationConfiguration();
        
        cfg.title = Game.TITLE;
        cfg.width = Game.V_WIDTH * Game.SCALE;
        cfg.height = Game.V_HEIGHT * Game.SCALE;
        
        new LwjglApplication(new Game(), cfg);
    }
    
}
