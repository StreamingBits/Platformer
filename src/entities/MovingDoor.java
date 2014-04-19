package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import platformer.Game;


public class MovingDoor extends B2DSprite{
    
    protected Vector2 velocity;
    protected Vector2 position;
    
    
   public MovingDoor(Body body){
        super(body);
        Texture tex = Game.res.getTexture("movingDoor");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 96)[0];
        setAnimation(sprites, 1 /1f); 
    }
   
   private void setVelocity(Vector2 velocity1){
       velocity = velocity1;
   }
   
   private void setPosition(Vector2 position1){
       position = position1;
   }
   
   
   private Vector2 getVelocity(){
    return velocity;   
   }
       
   
}
