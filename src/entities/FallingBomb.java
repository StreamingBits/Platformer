/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import platformer.Game;

/**
 *
 * @author alvarez
 */
public class FallingBomb extends B2DSprite {
    
    protected Vector2 velocity;
    protected Vector2 position;
    protected BodyDef bdef;
    protected PolygonShape poly;
    protected FixtureDef fdef;
    
    
   public FallingBomb(Body body){
        super(body);
        Texture tex2 = Game.res.getTexture("bomb");
        TextureRegion[] sprites = TextureRegion.split(tex2, 32, 32)[0];
        setAnimation(sprites, 1 / 9f); 
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

