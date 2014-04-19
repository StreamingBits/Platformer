/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author alvarez
 */
public class MyInputProcessor extends InputAdapter{
    

    
    public boolean keyDown(int k){

        if(k == Keys.DOWN){
            MyInput.setKey(MyInput.BUTTON1, true);
        }
        if(k == Keys.UP) { 
            MyInput.setKey(MyInput.BUTTON2, true);
        }   

        if(k == Keys.RIGHT) {
            MyInput.setKey(MyInput.BUTTON3, true);
        }

        if(k == Keys.LEFT){
            MyInput.setKey(MyInput.BUTTON4, true);
        }
        if(k == Keys.SPACE){
            MyInput.setKey(MyInput.BUTTON5, true);
        }
        
       

        return true;
    } 

    public boolean keyUp(int k){
        if(k == Keys.DOWN){
            MyInput.setKey(MyInput.BUTTON1, false);
        }
        if(k == Keys.UP) { 
            MyInput.setKey(MyInput.BUTTON2, false);
        }   

        if(k == Keys.RIGHT) {
            MyInput.setKey(MyInput.BUTTON3, false);
        }

        if(k == Keys.LEFT){
            MyInput.setKey(MyInput.BUTTON4, false);
        }
        
        if(k == Keys.SPACE){
            MyInput.setKey(MyInput.BUTTON5, false);
        }
        return false;
    }

        public boolean mouseMoved(int x, int y) {
		MyInput.x = x;
		MyInput.y = y;
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = true;
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = true;
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		MyInput.x = x;
		MyInput.y = y;
		MyInput.down = false;
		return true;
	}

}
