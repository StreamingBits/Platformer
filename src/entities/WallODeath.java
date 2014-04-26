/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author alvarez
 */
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
public class WallODeath extends B2DSprite{
    
 
    
   public WallODeath(Body body){
        super(body);
        Texture tex = Game.res.getTexture("WOD");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 160)[0];
        setAnimation(sprites, 1 / 1f); 
    }
   
  
       
   
}

