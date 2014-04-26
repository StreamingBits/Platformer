/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package states;

/**
 *
 * @author alvarez
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import handlers.GameButton;
import handlers.GameStateManager;
import platformer.Game;

public class Store extends GameState {
	
	private TextureRegion reg;
	
	private GameButton[][] buttons;
	private GameButton okButton;
        
	public Store(GameStateManager gsm) {
		
		super(gsm);
		
                
		reg = new TextureRegion(Game.res.getTexture("storePage"), 0, -40, 1200, 620);
		
		//TextureRegion buttonReg = new TextureRegion(Game.res.getTexture("storePage"), 0, 0, 32, 32);
		
                Texture tex2 = Game.res.getTexture("ok");
                okButton = new GameButton(new TextureRegion(tex2, 0,0, 200, 47), 620, 270, cam);
                
//                buttons = new GameButton[5][5];
//		for(int row = 0; row < buttons.length; row++) {
//			for(int col = 0; col < buttons[0].length; col++) {
//				buttons[row][col] = new GameButton(buttonReg, 80 + col * 40, 200 - row * 40, cam);
//				buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
//			}
//		}
//		
		cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
	}
	
	public void handleInput() {
             if(okButton.isClicked()){
                    gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void update(float dt) {
		
		handleInput();
		okButton.update(dt);
                
//		for(int row = 0; row < buttons.length; row++) {
//			for(int col = 0; col < buttons[0].length; col++) {
//				buttons[row][col].update(dt);
//				if(buttons[row][col].isClicked()) {
//					Play.level = row * buttons[0].length + col + 1;
////					Game.res.getSound("levelselect").play();
//					gsm.setState(GameStateManager.PLAY);
//				}
//			}
//		}
		
	}
	
	public void render() {
		sb.setProjectionMatrix(cam.combined);
		okButton.render(sb);
		sb.begin();
		sb.draw(reg, 0, 0);
		sb.end();
		
//		for(int row = 0; row < buttons.length; row++) {
//			for(int col = 0; col < buttons[0].length; col++) {
//				buttons[row][col].render(sb);
//			}
//		}
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}