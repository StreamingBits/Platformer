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
import com.badlogic.gdx.physics.box2d.Body;
import platformer.Game;

/**
 *
 * @author alvarez
 */
public class BulletPackage extends B2DSprite {
   
    
    public BulletPackage(Body body){
        super(body);
        Texture tex2 = Game.res.getTexture("BULLETPACK");
        TextureRegion[] sprites = TextureRegion.split(tex2, 32, 32)[0];
        setAnimation(sprites, 1 / 12f); 
    }
    
}
