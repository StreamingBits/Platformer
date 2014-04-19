


package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import platformer.Game;

/**
 *
 * @author alvarez
 */
public class Enemy extends B2DSprite{
    protected int currHealth;
    protected int maxHealth;
    protected boolean dead;
    protected int damageAmount;
    protected boolean flinching;
    protected long flinchTimer;
    
    public Enemy(Body body){
        super(body);
        Texture tex = Game.res.getTexture("Pic 1");
        TextureRegion[] sprites = TextureRegion.split(tex, 30, 44)[0];
        setAnimation(sprites, 1 / 12f);
    }
    
    
    public void attack(int damage){
        if(dead || flinching) {
            return;
        }
        currHealth -= damageAmount;
        if(currHealth <= 0){
            currHealth = 0;
            dead = true;
            flinching = true;
            flinchTimer = System.nanoTime();
        }
      
        
    }
    
    public int getMaxHealth(){
        return maxHealth;
    }
    
    public int getDamageAmount(){
        return damageAmount;
    }
    
    public boolean flincher(){
        return flinching;
    }
    
    public boolean isDead(){
        return currHealth <= 0;
    }
    
}
