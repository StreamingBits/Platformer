
package platformer;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import handlers.Content;
import handlers.GameStateManager;
import handlers.MyInput;
import handlers.MyInputProcessor;

/**
 *
 * @author: 
 * StreamingBits
 */
public class Game implements ApplicationListener {

    public static final String TITLE = "Platformer Zero";
    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 620;
    public static final int SCALE = 1;

    public static final float STEP = 1 / 60f;
    private float accum;



    private SpriteBatch sb;
    private OrthographicCamera cam;
    private OrthographicCamera hudCam;
    private GameStateManager gsm;

    public static Content res;


    public void create(){
    //Texture.setEnforcePotImages(false);
    /*********INPUT PROCESSING*****************/
    Gdx.input.setInputProcessor(new MyInputProcessor());

    sb = new SpriteBatch();
    res = new Content();
    
    //***PLAYER SPRITE***//
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/"
    + "Platformer/src/res/sprites/elaine.png", "Pic 1");
    
     res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/"
    + "Platformer/src/res/sprites/elaineLEFT.png", "LEFT");
    //***CRYSTAL SPRITE***//
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/diamonds.png", "crystals");
    
    //** MOVING PLATFORM SPRITE ***//
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" 
    + "Platformer/src/res/sprites/mP.png", "mP");
    
     //** MOVING DOOR SPRITE ***//
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" 
    + "Platformer/src/res/sprites/movingDoor.png", "movingDoor");
    
    
    //** BOMB SPRITE **//
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" +
            "Platformer/src/res/sprites/bomb.png", "bomb");
    
    //* BULLETS **/
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" +
            "Platformer/src/res/sprites/bullet.png", "bullet");
   
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" +
            "Platformer/src/res/sprites/bulletPack.png", "BULLETPACK");
    
   
    //HUD
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/hudtest.png", "hud");
    
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" +
            "Platformer/src/res/sprites/hp.png", "hp");
    
    //BACKGROUND 
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/"
    + "Platformer/src/res/sprites/bgs.png", "bgs");

    
    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" 
    + "Platformer/src/res/sprites/jTest.png", "jTest");


    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/dialogBox.png", "dialog");

    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/menu.png", "menu");

    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/bunny.png", "bunny");

    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/climbingArea.png", "VAC");

    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/sensor.png", "sensor");


    res.loadTexture("C:/Users/alvarez/Documents/NetBeansProjects/" + 
    "Platformer/src/res/sprites/stopper.png", "stopper");
        //SOUND EFFECTS
        res.loadMusic("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/music/smb.mp3","main");
        res.getMusic("main").setLooping(true);
	res.getMusic("main").setVolume(0.07f);
	res.getMusic("main").play();
	
	res.loadMusic("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/music/jump.wav", "jumping");
        res.getMusic("jumping").setLooping(false);
        res.getMusic("jumping").setVolume(0.12f);
        
        //**** JET PACK****////////
	res.loadMusic("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/music/jetPack.wav", "jetPack");
        res.getMusic("jetPack").setLooping(false);
        //res.getMusic("jetPack").setPan(-10, 10);
        res.getMusic("jetPack").setVolume(20f);
        
        res.loadMusic("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/music/crystal.wav", "crystalPickup");
        res.getMusic("crystalPickup").setLooping(false);
        res.getMusic("crystalPickup").setVolume(0.12f);
        
	res.loadMusic("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/music/bomb.wav", "bomb");
        res.getMusic("bomb").setLooping(false);
        res.getMusic("bomb").setVolume(0.12f);
        
        /*** VAC ***/
        res.loadMusic("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/music/VAC.wav", "VAC");
        res.getMusic("VAC").setLooping(false);
        res.getMusic("VAC").setVolume(0.12f);
        
        /**********CAMERA**********************/
        cam = new OrthographicCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        
        
        
        /***********DIFFERENT STATES****************/
        gsm = new GameStateManager(this);
    }
    
    public void render(){
        
	Gdx.graphics.setTitle(TITLE + "          FPS:  " + Gdx.graphics.getFramesPerSecond() + "         DISPLAY SIZE:" + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());
       
     //   gsm.update(Gdx.graphics.getDeltaTime());
        accum += Gdx.graphics.getDeltaTime();
        while(accum >= STEP ){
        accum -= STEP;
        gsm.update(STEP);
        gsm.render();
        MyInput.update();
        
        }
        
        
        
//        sb.setProjectionMatrix(hudCam.combined);
//        sb.begin();
//        sb.draw(res.getTexture("Pic 1"), 0, 0);
//        sb.end();
//    
    }
    
    public void dispose(){
        res.removeAll();
    }
    
    public SpriteBatch getSpriteBatch()
    { return sb;}
    
    
    public OrthographicCamera getCamera()
    { return cam;}
    
    
    public OrthographicCamera getHUDCamera()
    { return hudCam;}
    
    public void resize(int w, int h)
    { }
    
    public void pause()
    {}
    
    public void resume()
    {}
    
    

}
