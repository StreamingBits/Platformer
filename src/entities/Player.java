
package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import platformer.Game;
import states.Play;

/**
 *
 * @author alvarez
 */
public class Player extends B2DSprite {
    private int numCrystals; 
    private int totalCrystals;
    private int numBulletsFired;
    private final int MAX_BULLETS_AT_ONCE = 5;
    private int bulletsAvailable;
    private ArrayList<Bullet> bullets;
    private float x;
    private float y;
    private float radians;
    private int health;
    private int MAX_HEALTH;
    private int mana;
    private int MAX_MANA;
  
    
    public Texture tex;
    
    
    //private ArrayList<TextureRegion[]> sprites;
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = { 8, 8 };
    
    
    //animation actions 
    public int currentAnimation;
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private boolean facingLeft;
    
            
    public Player(Body body, ArrayList<Bullet> bullets, int AN){
        super(body);
        Texture tex = Game.res.getTexture("Pic 1");
       
        this.bullets = bullets;
        radians = 3.145f / 2f;
    }
   
    public void Animate(TextureRegion[] currSprite, float delay){
        setAnimation(currSprite, 1/12f);
        
    }
    
    public void shoot(){
        if(numBulletsFired == MAX_BULLETS_AT_ONCE){return;}
      
    }
    public void setLeft(){
        facingLeft = true;
    }
    public void setRight(){
        facingLeft = false;
    }
    
    //** HEALTH STUFF****///
    public void minusHealth(int healthUsed){
        health -= healthUsed;
    }
    public void addHealth(int healthCollected){
        health += healthCollected;
    }
    public void setHealth(int healthStatus){
        health = healthStatus;
    }
    public void setMaxHealth(int healthAmt){
        MAX_HEALTH = healthAmt;
    }
    public int getHealth(){
        return health;
    }
    public int getMaxHealth(){
        return MAX_HEALTH;
    }
    
    /* MANA SHIT */
    public void minusMana(int manaUsed){
        mana -= manaUsed;
    }
    public void addMana(int manaCollected){
        mana+=manaCollected;
    }
    
    public void setMana(int manaStatus){
        mana = manaStatus;
    }
    
    public int getMana(){
        return mana;
    }
    
    
    /**MANA BAR STUFF **/
    public void increaseMaxMana(int manaCollected){ MAX_MANA+=manaCollected; }
    public void decreaseMana(int manaUsed){ 
        mana -= manaUsed;
    }
    public void setMaxMana(int max_mana){ 
        MAX_MANA = max_mana;
    }
    public int getMaxMana(){
        return MAX_MANA;
    }
    
    /* CRYSTAL METHODS */
    public void collectCrystal() {numCrystals++; }
    
    public int getNumCrystals() { return numCrystals; }
    
  
    public void setTotalCrystals(int i) { totalCrystals = i; }
    
    public int getTotalCrystals() { return totalCrystals; }

    
    /* BULLET METHODS */
    public void collectBulletPack(){ bulletsAvailable += 40; }
    
    public int getNumBulletsFired(){ return numBulletsFired; }
    
    public int getBulletsAvailable() { return bulletsAvailable; } 
    
    public void setBulletsAvailable(int bulletAmt) { bulletsAvailable = bulletAmt; }

    
    public void setCurrentAnimation(int animNumber){ 
        currentAnimation = animNumber;
    }
    private int getCurrentAnimation() {
        return currentAnimation;
    }
    
    
}