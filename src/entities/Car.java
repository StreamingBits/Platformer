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
public class Car extends B2DSprite{
    
    public Car(Body body){
        super(body);
        Texture tex = Game.res.getTexture("jTest");
        TextureRegion[] sprites = TextureRegion.split(tex, 19, 19)[0];
        setAnimation(sprites, 1 / 12f);
    }
    
}
