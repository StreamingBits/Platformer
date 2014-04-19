
package entities;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import handlers.B2DVars;
import platformer.Game;

public class HUD extends ScreenAdapter{
    private Player player;
    private Stage stage;
    private Skin skin;

    private TextureRegion container;
    private TextureRegion inventoryContainer;
    private TextureRegion healthBar;
    private TextureRegion[] blocks;
    private TextureRegion crystal;
    private TextureRegion[] font;
    private Texture health;
    public BitmapFont fontDialog = new BitmapFont();

	public HUD(Player player) {
		this.player = player;
		Texture tex = Game.res.getTexture("hud");
                Texture dialog = Game.res.getTexture("dialog");
		health = Game.res.getTexture("hp");
            
		container = new TextureRegion(tex, 0, 0, 32, 32);
                //ORIGINALLY 280, 40
                inventoryContainer = new TextureRegion(dialog, 0, 0, 280, 40);
		
		blocks = new TextureRegion[3];
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new TextureRegion(tex, 32 + i * 16, 0, 16, 16);
		}
		
		crystal = new TextureRegion(tex, 80, 0, 16, 16);
		
		font = new TextureRegion[11];
		for(int i = 0; i < 6; i++) {
			font[i] = new TextureRegion(tex, 32 + i * 9, 16, 9, 9);
		}
		for(int i = 0; i < 5; i++) {
			font[i + 6] = new TextureRegion(tex, 32 + i * 9, 25, 9, 9);
		}
		
	}
	
	public void render(SpriteBatch sb) {
		
		sb.begin();
		
		// draw container
                //32, 280
		//sb.draw(container, 32, 280);
                //150, 280
                sb.draw(inventoryContainer, 650, 30);
                //370, 30
                sb.draw(health, 880, 55);
		
		// draw blocks
		short bits = player.getBody().getFixtureList().first().getFilterData().maskBits;		
		// draw crystal
		sb.draw(crystal, 675, 40);
		
		// draw crystal amount
		drawString(sb, player.getNumCrystals() + "/" + player.getTotalCrystals(), 662, 55);
                //drawString(sb, player.getBulletsAvailable() + "/", 240, 295);
		drawString(sb, player.getMana() + "/" + player.getMaxMana(), 810, 30);
                drawString(sb, player.getHealth() +"/" + player.getMaxHealth(), 810, 55);
                //writeDialog(sb, "", 200, 100, player.getDialogStatus());
		sb.end();
		
	}
        
	
	public void drawString(SpriteBatch sb, String s, float x, float y) {
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '/') c = 10;
 			else if(c >= '0' && c <= '9') c -= '0';
			else continue;
			sb.draw(font[c], x + i * 9, y);
		}
             
	}
        
        public void writeDialog(SpriteBatch sb, String text, float x, float y, boolean shouldIWrite){
            fontDialog.draw(sb, text, x, y);
        }
        
        
        
	
}
