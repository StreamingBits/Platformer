/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import platformer.Game;

/**
 *
 * @author alvarez
 */
public class FloorSensor extends B2DSprite{
    
    
    public FloorSensor(Body body){
        super(body);
        Texture tex = Game.res.getTexture("sensor");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
        setAnimation(sprites, 1 / 6f);
        
    }
}
