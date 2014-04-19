/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import platformer.Game;

/**
 *
 * @author alvarez
 */
public class Bullet extends B2DSprite {
    protected int numBulletsFired;
    protected int remainingBullets;
    private float lifeTime;
    private float lifeTimer;
    private boolean remove;
    private float x;
    private float y;
    private float radians;
    private float dx;
    private float dy;
    
    
    public Bullet(Body body, float x, float y, float radians){
        super(body);
        this.x = x;
        this.y = y;
        this.radians = radians;
        
        float speed = 2f;
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;
        lifeTimer = 0;
        lifeTime = 10;
        
        Texture tex2 = Game.res.getTexture("bullet");
        TextureRegion[] sprites = TextureRegion.split(tex2, 8, 8)[0];
        setAnimation(sprites, 1 / 12f); 
    }
    
    public void updateBullet(float dt){
        x += dx * dt;
        y += dy * dt;
        
        lifeTimer += dt;
        
        if(lifeTimer > lifeTime){
            remove = true;
        }
        
    }
    
	public void draw(ShapeRenderer sr) {
		sr.setColor(1, 1, 1, 1);
                CircleShape cs = new CircleShape();
                cs.setRadius(2);
		//sr.begin(ShapeType.);
		sr.circle(x - width / 2, y - height / 2, width / 2);
		sr.end();
	}
	
    public float getXPos() { return x; }
    public float getYPos() { return y; }
   
    
    
}
