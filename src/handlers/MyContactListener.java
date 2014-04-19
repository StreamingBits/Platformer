/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author alvarez
 */
public class MyContactListener implements ContactListener {  
    //called when 2 fixtures collide
    private boolean playerOnGround;
    private boolean bombContact;
    private boolean VACContact;
    private boolean f2Crystal = false;
    private boolean sensorContact;
    private boolean doorHitsSensor;
    
    private int numFootContacts;
    private Array<Body> bodiesToRemove; //CRYSTAL PICKUPS TO REMOVE
    private Array<Body> bodiesToRemove2;//AMMO PICKUPS TO REMOVE
    private int numCrystalContacts;
   
    public MyContactListener(){
        super();
        bodiesToRemove = new Array<>();
        bodiesToRemove2 = new Array<>();
    }
    public void beginContact(Contact c){
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();
       
    if(fa == null || fb==null) return;
    
    if(fa.getUserData() != null && fa.getUserData().equals("foot")){
        playerOnGround = true;
        numFootContacts++;
    }
    
    if(fb.getUserData() != null && fb.getUserData().equals("foot")){
         playerOnGround = true;
         numFootContacts++;
    }
    
    if(fa.getUserData() != null && fa.getUserData().equals("sensor")){
        sensorContact = true;
    }
    
    if(fb.getUserData() != null && fb.getUserData().equals("sensor")){
        sensorContact = true;
    }

    if(fa.getUserData() != null && fa.getUserData().equals("crystals")){
        numCrystalContacts++;
        bodiesToRemove.add(fa.getBody());
    }

    if(fb.getUserData() != null && fb.getUserData().equals("crystals")){
        numCrystalContacts++;
        bodiesToRemove.add(fb.getBody());
    }


    

    if(fa.getUserData() != null && fa.getUserData().equals("BULLETPACK")){
        bodiesToRemove2.add(fa.getBody());
    }

    if(fb.getUserData() != null && fb.getUserData().equals("BULLETPACK")){
        bodiesToRemove2.add(fb.getBody());
    }

    
    if(fa.getUserData() != null && fb.getUserData() != null){
        if(fb.getUserData().equals("PLAYER") && fa.getUserData().equals("BOMB")){
            bombContact = true;
        }

        if(fa.getUserData().equals("PLAYER") && fb.getUserData().equals("BOMB")){
            bombContact = true;
        }
    }

    /** DOOR HITS SENSOR**/
     if(fa.getUserData() != null && fb.getUserData() != null){
        if(fb.getUserData().equals("mD") && fa.getUserData().equals("stopper")){
            doorHitsSensor = true;
        }

        if(fa.getUserData().equals("mD") && fb.getUserData().equals("stopper")){
            doorHitsSensor = true;
        }
    }
    
     if(fa.getUserData() != null && fb.getUserData() != null){
        if(fb.getUserData().equals("PLAYER") && fa.getUserData().equals("VAC")){
            VACContact = true;
        }

        if(fa.getUserData().equals("PLAYER") && fb.getUserData().equals("VAC")){
            VACContact = true;
        }
    }

      
    }
    //exact opposite of beginContact
    public void endContact(Contact c){
        
        Fixture fa = c.getFixtureA();
        Fixture fb = c.getFixtureB();
        
        if(fa == null || fb==null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("foot")){
            playerOnGround = false;
            numFootContacts--;
            numCrystalContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")){
            playerOnGround = false;
            numFootContacts--;
            numCrystalContacts--;
        }
        
    
        if(fa.getUserData() != null && fb.getUserData() != null){
        if(fb.getUserData().equals("PLAYER") && fa.getUserData().equals("VAC")){
            VACContact = false;
        }

        if(fa.getUserData().equals("PLAYER") && fb.getUserData().equals("VAC")){
            VACContact = false;
        }
        }
        
      if(fa.getUserData() != null && fb.getUserData() != null){
        if(fb.getUserData().equals("PLAYER") && fa.getUserData().equals("BOMB")){
            bombContact = false;
        }

        if(fa.getUserData().equals("PLAYER") && fb.getUserData().equals("BOMB")){
            bombContact = false;
        }
    }

     /** DOOR HITS SENSOR**/
     if(fa.getUserData() != null && fb.getUserData() != null){
        if(fb.getUserData().equals("mD") && fa.getUserData().equals("stopper")){
            doorHitsSensor = false;
        }

        if(fa.getUserData().equals("mD") && fb.getUserData().equals("stopper")){
            doorHitsSensor = false;
        }
    }
      
    if(fa.getUserData() != null && fa.getUserData().equals("sensor")){
        sensorContact = false;
    }
    
    if(fb.getUserData() != null && fb.getUserData().equals("sensor")){
        sensorContact = false;
    }


    }
    
    
    //collision is 2 step- 
//1.) Detection 
//presolve
//2.) Handling
    //postsolve
    public void preSolve(Contact c, Manifold m){}
    public void postSolve(Contact c, ContactImpulse ci){}

    public boolean isPlayerOnGround() { return playerOnGround; }
    public boolean isPlayerInVAC() { return VACContact; } 
    public Array<Body> getDeadBodies() { return bodiesToRemove; } 
    public Array<Body> removeCollectedAmmo() { return bodiesToRemove2; }
    public boolean footTouchingCrystal() { return f2Crystal; }
    public boolean isTouchingBomb() { return bombContact; } 
    public boolean sensorActivated(){ return sensorContact; } 
    public boolean stopDoor() { return doorHitsSensor; }
}

