package states;

/**
 *
 * @author alvarez
 */
import handlers.B2DVars;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;

import entities.BasicPowerUp;
import entities.Bullet;
import entities.BulletPackage;
import entities.Car;
import entities.Enemy;
import entities.FallingBomb;
import entities.FloorSensor;
import entities.HUD;
import entities.MovingDoor;
import entities.MovingPlatform;
import entities.Player;
import entities.Stopper;
import entities.verticalAcceleratorArea;
import static handlers.B2DVars.PPM;
import handlers.Background;
import handlers.BoundedCamera;
import handlers.ConeLight;
import handlers.GameStateManager;
import handlers.Light;
import handlers.MyContactListener;
import handlers.MyInput;
import handlers.PointLight;
import handlers.PositionalLight;
import handlers.RayHandler;
import java.awt.Graphics;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import platformer.Game;


public class Play extends GameState {
       
       private final boolean debugMODE = false;  
       public static float MAX_VELOCITY = 3f;
   
       private World world;
       private Box2DDebugRenderer b2dr;
       private OrthographicCamera b2dCam;
       private HUD hud;
       
       private MyContactListener cl;
       private TiledMap tileMap;
       private float tileSize;
       private OrthogonalTiledMapRenderer tmr;
       private int tileMapWidth;
       private int tileMapHeight;
       
       //** INITIALIZE THE ENTITIES **//
       private Player player;
       private FloorSensor sensor;
       private Stopper stopper;
       private MovingPlatform movingPlatform;
       private MovingDoor movingDoor;
       private Enemy enemy;
       private Car car1;
       private Car car2;
       
       
       /** LIGHTING ENTITIES **/
       private  Light light;
       private ConeLight coneLight;
       private PointLight pointLight;
       private PointLight pL5;
       private PointLight pL1;
       ConeLight cL1;
       ConeLight cL2;
       ConeLight cL3;
       ConeLight cL4;
       
       private float TIMER;
       private boolean dropItem;
       
       private final Vector2 platformVelocity = new Vector2(-.26f, 0f);
       private final Vector2 platformForwardVelocity = new Vector2(.16f, 0f);
       private final Vector2 fallOffScreenPosition = new Vector2(0, -10);
      
       private Array<BasicPowerUp> crystals;
       private Array<MovingPlatform> movingPlatforms;
       private Array<FallingBomb> fallingBombs;
       private Array<Bullet> amtBullets;
       private Array<BulletPackage> bulletPackages;
       private Array<verticalAcceleratorArea> verticalAccelerationChambers;
       private Array<FloorSensor> sensors;
       private Array<MovingDoor> movingDoors;
       private Array<Stopper> stoppers;
       
       /** LIGHTING LISTS**/
       private Array<Light> regLights;
       private Array<ConeLight> coneLights;
       private Array<PointLight> pointLightsList;
       
       public static int level;
       public float xBulletCoord;
       public float yBulletCoord;
       private Background[] backgrounds;
       public SpriteBatch sb2;
       
       private RayHandler rh;
       
    public Play(GameStateManager gsm){
        super(gsm);
       
        TIMER = 0f;
        dropItem = false;
        //** TO DO IMPLEMENT AUTOMATIC BACKGROUND TEXTURE LOADER SIMILAR TO LEVEL**/
        if(level == 3){
            Texture bgs = Game.res.getTexture("bgs");
            TextureRegion sky = new TextureRegion(bgs, 0, 0, 320, 560);
            TextureRegion clouds = new TextureRegion(bgs, 0, 560, 320, 240*2);
            TextureRegion mountains = new TextureRegion(bgs, 0, 400, 320, 240*2);
            backgrounds = new Background[3];
            backgrounds[0] = new Background(sky, cam, 0f);
            backgrounds[1] = new Background(clouds, cam, 0.1f);
            backgrounds[2] = new Background(mountains, cam, 0.2f);
        } 
        
        
        
        //Set up box2d shit
        world = new World(new Vector2(0, -9.81f), true);
        cl = new MyContactListener();
        
        world.setContactListener(cl);
        b2dr = new Box2DDebugRenderer();
        // set up box2d cam
        BoundedCamera b2dCam = new BoundedCamera();
        b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
        b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
         
        rh = new RayHandler(world);
        rh.setWorld(world);
        
        
        createPlayer();
        player.setMana(900);
        player.setMaxMana(1200);
        player.setHealth(100);
        player.setMaxHealth(100);
        
        
        /** LIGHTING STUFF***/
        rh.setShadows(true);
        rh.setBlur(true);
        rh.setAmbientLight(0.2f, 0.2f, 0.22f, 0.6f);
/**ConeLight(RayHandler rayHandler, int rays, Color color,
			float distance, float x, float y, float directionDegree,
			float coneDegree) {*/
        

        cL2 = new ConeLight(rh, 1200, Color.BLUE, 1200f, 755f, 50f , 90f, 10);
        cL3 = new ConeLight(rh, 1200, Color.GREEN, 1200f, 755f, 50f, 90f, 5);
        pL1 = new PointLight(rh, 2000, Color.RED, 150f, 755f, 20f);
        pL5 = new PointLight(rh, 2000, Color.DARK_GRAY, 330f, 770f, 20f);
        cL4 = new ConeLight(rh, 1200, Color.MAGENTA, 600f, 755f, 400f, 470f, 1.5f);

         
        // createEnemy();
        createTiles();
        hud = new HUD(player);
        createCrystals();
        
        
        createMovingPlatform();
        createFallingBomb();
        
        if(level == 4){
            createVACS();
            createSensors();
            createStoppers();
            createMovingDoor();
        }
        // set up box2dcam
        // b2dCam = new OrthographicCamera();
        //b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
        
    }
    
    //BUTTON1 - DOWN
    //BUTTON2 - UP
    //BUTTON3 - RIGHT
    //BUTTON4 - LEFT
    //BUTTON5 - SPACE
    public void handleInput(){
        
        
        if(MyInput.isDown(MyInput.BUTTON1) && cl.isPlayerInVAC()){
//           car2.getBody().applyForce(0, 100, 0, 0, true);
            // player.getBody().applyLinearImpulse(0f, .60f, 16, 16, true);
             //player.getBody().setGravityScale(0f);
          
        }
        
        
        if(MyInput.isPressed(MyInput.BUTTON2)){
           if(cl.isPlayerOnGround()){
               Game.res.getMusic("jumping").play();
               player.getBody().applyForceToCenter(0,325f, true);  
           }
          
           
        }
        
        if(MyInput.isDown(MyInput.BUTTON2) && cl.isPlayerInVAC()){
               Game.res.getMusic("VAC").play();
               player.getBody().applyLinearImpulse(0f, 1.1f, 16, 16, true);
        }
        
       
       
        if(MyInput.isDown(MyInput.BUTTON3)){
             
              player.getBody().applyForce(8f, 0, 0, 0, true);  
              player.update(.002f);
              
        }
        
        if(MyInput.isDown(MyInput.BUTTON4)){
         
              player.getBody().applyForce(-8f, 0, 16, 16, true);     
              player.update(.005f);
            
        }
        
        /******JETPACK****/
       
        if(MyInput.isDown(MyInput.BUTTON5)){
            if(player.getMana() > 0){
            Game.res.getMusic("jetPack").play();
            float XVelocity = player.getBody().getLinearVelocity().x;
            player.getBody().setLinearVelocity(XVelocity, 1.5f);
            player.decreaseMana(1);
            }
        } 
    }
    
    
    public void update(float dt){
        handleInput();
        
        world.step(dt, 8, 3);
        

        Array<Body> bodies = cl.getDeadBodies();
        Array<Body> removableBP = cl.removeCollectedAmmo();
         
         if(level == 4){
            //verticalAccelerationChambers.clear();
            for(int i = 0; i < verticalAccelerationChambers.size; i++){
                verticalAccelerationChambers.get(i).update(dt);
            }
            for(int ctr = 0; ctr < sensors.size; ctr++){
                sensors.get(ctr).update(dt);
            }
            
            for(int a = 0; a < stoppers.size; a++){
                stoppers.get(a).update(dt);
            }
            
            
            for(int door = 0; door < movingDoors.size; door++){
                float yPos = movingDoors.get(0).getBody().getPosition().y;
                
                if(cl.sensorActivated()){
                    if(yPos > 300/PPM){
                    movingDoors.get(0).getBody().setLinearVelocity(0, -.1f);
                    }
                }
                if(yPos < 270/PPM){
                    movingDoors.get(0).getBody().setType(BodyType.StaticBody);
                }
                movingDoors.get(0).update(dt);
            }
           
        }
           

        if(cl.isTouchingBomb()){
            Game.res.getMusic("bomb").play();
            player.minusHealth(1);
        }

        if(cl.sensorActivated()){
            createBulletPackages();
        }

        if(cl.isPlayerInVAC()){
             player.getBody().setGravityScale(0f);
        }
        else{
             player.getBody().setGravityScale(1f);
        }
        ///*****CRYSTAL COLLECTION/REMOVAL *********///
        for(int c = 0; c < bodies.size; c++)
        {
                Body b = bodies.get(c);
                crystals.removeValue((BasicPowerUp) b.getUserData(), true);
                world.destroyBody(bodies.get(c));
                int numCrystals = player.getNumCrystals();
                player.collectCrystal();

                if(player.getMana() < player.getMaxMana())
                {
                        player.addMana(100);
                        
                        if(player.getMana() > player.getMaxMana()){
                            player.setMana(player.getMaxMana());
                        }
                }

                    Game.res.getMusic("crystalPickup").play();
                   
        }//END FOR LOOP
        
        bodies.clear();
       
        for(int i = 0; i < crystals.size; i++){
            crystals.get(i).update(dt);
        }
        
        if(dropItem == true){
            /**** BULLET PACK REMOVAL AND COLLECTION ****/
            for(int bPCount = 0; bPCount < removableBP.size; bPCount++){
                Body removableBulletPacks = removableBP.get(bPCount);
                bulletPackages.removeValue((BulletPackage) removableBulletPacks.getUserData(), true);
                world.destroyBody(removableBP.get(bPCount));
                player.collectBulletPack();


            }

            removableBP.clear();

            for(int bPitr = 0; bPitr < bulletPackages.size; bPitr++){
                bulletPackages.get(bPitr).update(dt);
            }
        }
       
        /***POSITIONING OF MOVING PLATFORMS!!!****/
        levelThreeMP();
        for(int a = 0; a < movingPlatforms.size; a++){
             
          movingPlatforms.get(a).update(dt);
        }
        
        /***FALLING BOMBS *////
        for(int itr = 0; itr < fallingBombs.size; itr++){
            fallingBombs.get(itr).update(dt);
        }
        
        TIMER += dt;
        if(TIMER > 20){
            TIMER = 0;
            player.addHealth(5);
        }
        if(player.getHealth() >= player.getMaxHealth()){
            player.setHealth(player.getMaxHealth());
        }
        System.out.println(TIMER);
    }
    
       @Override
    public void render(){
        Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT);
        cam.position.set(player.getPosition().x * PPM + Game.V_WIDTH /8,
                player.getPosition().y * PPM + Game.V_HEIGHT /8 , 0);
        
        sb.setProjectionMatrix(hudCam.combined);
          
        if(level == 3){
        for (Background background : backgrounds) {
               background.render(sb);
        }
        }
        //draw tile map
        cam.update();
        tmr.setView(cam);
        
        tmr.render();
        sb.setProjectionMatrix(cam.combined);
        rh.setCombinedMatrix(cam.combined);
        
        Vector2 vel = player.getBody().getLinearVelocity();
        Vector2 pos = player.getBody().getPosition();
      
        
        player.render(sb);

        rh.updateAndRender();
    
        for(int i = 0; i < crystals.size; i++){
            crystals.get(i).render(sb);
        }
        
        
        if(dropItem == true){
        
            for(int bPitr = 0; bPitr < bulletPackages.size; bPitr++){
                bulletPackages.get(bPitr).render(sb);
            }
        }
        for(int a = 0; a < movingPlatforms.size; a++){
            MovingPlatform current = movingPlatforms.get(a);
            movingPlatforms.get(a).render(sb);
        }
        
        for(int b = 0; b < fallingBombs.size; b++){
            FallingBomb bomb = fallingBombs.get(b);
            fallingBombs.get(b).render(sb);
        }
        
        if(level == 4){
            for(int i = 0; i < verticalAccelerationChambers.size; i++){
                verticalAcceleratorArea VAC = verticalAccelerationChambers.get(i);
                verticalAccelerationChambers.get(i).render(sb);
            }
            for(int c = 0; c < sensors.size; c++){
                sensors.get(c).render(sb);
            }
            
            for(int mDi = 0; mDi < movingDoors.size; mDi++){
                movingDoors.get(mDi).render(sb);
            }
        }
        
        if(Math.abs(vel.x) > MAX_VELOCITY){			
            vel.x = Math.signum(vel.x) * MAX_VELOCITY;
	    player.getBody().setLinearVelocity(vel.x, vel.y);
        }
 
       /**** THIS HAPPENS WHEN YOU FALL OFF THE SCREEN!!! 
        GO BACK TO THE MAIN MENU AND LET PLAYER MAKE DECISION***/
       if(pos.y <= fallOffScreenPosition.y || player.getHealth() <= 0){
           gsm.setState(83774392);
       }
  
        //draw box2d world outline
       if(debugMODE == true){
           b2dr.render(world, b2dCam.combined);
       }
       
       // draw hud
	sb.setProjectionMatrix(hudCam.combined);
	hud.render(sb);
        
        
    }
    
    
    
    private void createPlayer() {
   
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef(); 
       
        bdef.position.set(90 / PPM, 100 / PPM);
        bdef.type = BodyType.DynamicBody;
        Body body = world.createBody(bdef);
        shape.setAsBox( 14 / PPM, 14 / PPM);
        
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        MassData md = new MassData();
        md.mass = 1f;
        body.setMassData(md);
        fdef.restitution = .15f;
        fdef.filter.maskBits  = B2DVars.BIT_BLOCKS;
        body.createFixture(fdef).setUserData("PLAYER");
        
 /*create foot sensor**/
        fdef.shape = shape;
        shape.setAsBox(13 / PPM, 2 /PPM, new Vector2(0, -14 / PPM), 0);
       
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_FOOT;
        fdef.filter.maskBits  = B2DVars.BIT_BLOCKS;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");
        
      
        
//create rectangular arms
        player = new Player(body, null);    
        body.setUserData(player);
        shape.dispose();
    }
    
   
  
        //1. Load map
    private void createTiles() {
        tileMap = new TmxMapLoader().load("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/res/sprites/levels/test" + level + ".tmx");
        tmr = new OrthogonalTiledMapRenderer(tileMap);
        tileMapWidth = (int) tileMap.getProperties().get("width");
	tileMapHeight = (int) tileMap.getProperties().get("height");
        tileSize = (int) tileMap.getProperties().get("tilewidth");
        TiledMapTileLayer layer;
        layer = (TiledMapTileLayer) tileMap.getLayers().get("T3");
        createLayer(layer, B2DVars.BIT_BLOCKS);
       
    }
    
    
    private void createLayer(TiledMapTileLayer layer, short bits){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        for(int row = 0; row < layer.getTileHeight(); row++){
            for(int col = 0; col < layer.getWidth(); col++){
                Cell cell = layer.getCell(col, row);
                if(cell==null)continue;
                if(cell.getTile() == null) continue;
                
               //create body + fixture from cell
                bdef.type = BodyType.StaticBody;
                bdef.position.set(
                (col + 0.5f)* tileSize / PPM, 
                (row + 0.5f)* tileSize /PPM      
                );
                
                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[4];
                v[0] = new Vector2(-tileSize /2 /PPM, -tileSize /2 /PPM);
                //top left
                v[1] = new Vector2(-tileSize /2 /PPM, tileSize /2 /PPM);
                v[2] =  new Vector2(tileSize /2 /PPM, tileSize /2 /PPM);
                v[3] = new Vector2(tileSize/2 /PPM, -tileSize/2/PPM);
                
               
                cs.createChain(v);
                fdef.friction = 1.2f;
                fdef.shape = cs;
                fdef.filter.categoryBits = bits;
                fdef.filter.maskBits = -1;
                fdef.isSensor = false;
                world.createBody(bdef).createFixture(fdef);
                cs.dispose();
            }   
        }
    
    }

    private void createCrystals(){
        crystals = new Array<>();
        MapLayer layer = tileMap.getLayers().get("crystals");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        int i = 0;
        for(MapObject mObj : layer.getObjects()){
            bdef.type = BodyType.StaticBody;
            float x = ((float) mObj.getProperties().get("x") + 20)/ PPM;
            float y = (float) mObj.getProperties().get("y") / PPM;
            
            bdef.position.set(x,y);
            CircleShape cshape = new CircleShape();
            cshape.setRadius(8 / PPM);
            fdef.shape = cshape;
            
            //COLLECTABLES SHOULD BE SENSORS
            fdef.isSensor = true;
            fdef.filter.categoryBits = B2DVars.BIT_CRYSTAL;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;
            Body body =  world.createBody(bdef);
            BasicPowerUp bpu = new BasicPowerUp(body);
            body.createFixture(fdef).setUserData("crystals");
            crystals.add(bpu);
            body.setUserData(bpu);
            cshape.dispose();
            i++;
        }
        player.setTotalCrystals(i);
    }
    
    /** CREATE SENSORS **/
    private void createSensors(){
        sensors = new Array<>();
        MapLayer layer = tileMap.getLayers().get("sensors");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        
        for(MapObject mObj : layer.getObjects()){
            bdef.type = BodyType.StaticBody;
            float x = ((float) mObj.getProperties().get("x") + 20)/ PPM;
            float y = (float) mObj.getProperties().get("y") / PPM;
            bdef.position.set(x,y);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(8/PPM, 4/PPM);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = B2DVars.BIT_SENSOR;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;
            Body body =  world.createBody(bdef);
            FloorSensor floorSensor = new FloorSensor(body);
            body.createFixture(fdef).setUserData("sensor");
            sensors.add(floorSensor);
            body.setUserData(floorSensor);
            shape.dispose();
        }
    }
    
    
     /** CREATE SENSORS **/
    private void createStoppers(){
        stoppers = new Array<>();
        MapLayer layer = tileMap.getLayers().get("stopper");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        
        for(MapObject mObj : layer.getObjects()){
            bdef.type = BodyType.StaticBody;
            float x = ((float) mObj.getProperties().get("x") + 17)/ PPM;
            float y = (float) mObj.getProperties().get("y") / PPM;
            bdef.position.set(x,y);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(12/PPM, 12/PPM);
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = B2DVars.BIT_SENSOR;
            //fdef.filter.maskBits = B2DVars.BIT_PLAYER;
            Body body =  world.createBody(bdef);
            Stopper stopIT = new Stopper(body);
            body.createFixture(fdef).setUserData("stopper");
            stoppers.add(stopIT);
            body.setUserData(stopIT);
            shape.dispose();
        }
    }
    
    /** VERTICAL ACCELERATOR CHAMBERS **/
    private void createVACS(){ 
        verticalAccelerationChambers = new Array<>();
        MapLayer layer = tileMap.getLayers().get("VAC");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        
        for(MapObject mObj : layer.getObjects()){
             bdef.type = BodyType.StaticBody;
            float x = (float) mObj.getProperties().get("x") / PPM;
            float y = (float) mObj.getProperties().get("y") / PPM;
            
            bdef.position.set(x-(15/PPM),y);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(10/PPM, 55/PPM);
            fdef.shape = shape;
            
            //COLLECTABLES SHOULD BE SENSORS
            fdef.isSensor = true;
            
            fdef.filter.categoryBits = B2DVars.BIT_VAC;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;
            Body body =  world.createBody(bdef);
            verticalAcceleratorArea VAC = new verticalAcceleratorArea(body);
            body.createFixture(fdef).setUserData("VAC");
            verticalAccelerationChambers.add(VAC);
            body.setUserData(VAC);
            shape.dispose();
            
           
        }
    }
    
    /** NAMING CONVENTIONS IN THIS METHOD **/
    /** MP = Moving Platform **/
    private void createMovingPlatform(){
        
        movingPlatforms = new Array<>();
        MapLayer layer = tileMap.getLayers().get("mP");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
       // PolygonShape shape = new PolygonShape();
        Vector2 platformStartVelocity = new Vector2(.16f, 0f);
        
            for(MapObject mapObj : layer.getObjects()){
                PolygonShape shape = new PolygonShape();
                float x = (float) mapObj.getProperties().get("x") / PPM;
                float y = (float) mapObj.getProperties().get("y") / PPM;
                bdef.position.set(x, y);
                bdef.type = BodyType.KinematicBody;
                bdef.gravityScale  = 0f;
                shape.setAsBox( 32 / PPM, 14 / PPM);
                fdef.shape = shape;
                fdef.friction = 1.5f;
                fdef.filter.categoryBits = B2DVars.BIT_BALL;
                Body body = world.createBody(bdef);
                body.setLinearVelocity(platformStartVelocity);
                MovingPlatform mp2 = new MovingPlatform(body);
                body.createFixture(fdef).setUserData("MP");
                movingPlatforms.add(mp2);
                shape.dispose();
        }
    }
    
     private void createMovingDoor(){
        
        movingDoors = new Array<>();
        MapLayer layer = tileMap.getLayers().get("mDoor");
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
       // PolygonShape shape = new PolygonShape();
      
            for(MapObject mapObj : layer.getObjects()){
                PolygonShape shape = new PolygonShape();
                float x = ((float) mapObj.getProperties().get("x") + 15)/ PPM;
                float y = ((float) mapObj.getProperties().get("y") + 25) / PPM;
                bdef.position.set(x, y);
                bdef.type = BodyType.KinematicBody;
       
                shape.setAsBox( 14/PPM , 44/PPM );
                fdef.shape = shape;
                fdef.isSensor= false;
                fdef.friction = 1.5f;
                fdef.filter.categoryBits = B2DVars.BIT_DOOR;
                Body body = world.createBody(bdef);
                MovingDoor movingDoorTOADD = new MovingDoor(body);
                body.createFixture(fdef).setUserData("mD");
                movingDoors.add(movingDoorTOADD);
                shape.dispose();
        }
    }
     
     
   /**ConeLight(RayHandler rayHandler, int rays, Color color,
			float distance, float x, float y, float directionDegree,
			float coneDegree)*/
    
   
    
    //***** FALLING BOMBS THE PLAYER MUST AVOID****//
    public void createFallingBomb(){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        MapLayer bLayer = tileMap.getLayers().get("bombs");
        fallingBombs = new Array<>();
        //Vector2 initFallSpeed = new Vector2(0, -.1f);
        
            for(MapObject mapObj : bLayer.getObjects()){
                CircleShape shape = new CircleShape();
                shape.setRadius(9f/PPM);
                float x = (float) mapObj.getProperties().get("x") / PPM;
                float y = (float) mapObj.getProperties().get("y") / PPM;
                
                bdef.position.set(x,y);
                bdef.type = BodyType.DynamicBody;
                bdef.gravityScale = 1f;
                
                fdef.density = 1f;
                fdef.friction = .3f;
                bdef.gravityScale = 1f;
                fdef.shape = shape;
                fdef.restitution = .67f;
                fdef.filter.categoryBits = B2DVars.BIT_BALL;
                
                Body body = world.createBody(bdef);
                //body.setLinearVelocity(initFallSpeed);
                FallingBomb bombObject = new FallingBomb(body);
                body.createFixture(fdef).setUserData("BOMB");
                fallingBombs.add(bombObject);
                shape.dispose();
                
            }
        
    }
    
    public void createBulletPackages(){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        MapLayer bulletPackagesLayer = tileMap.getLayers().get("bulletPacks");
        bulletPackages = new Array<>();
        
            for(MapObject mObj : bulletPackagesLayer.getObjects()){
                bdef.type = BodyType.StaticBody;
                float x = (float) mObj.getProperties().get("x") / PPM;
                float y = (float) mObj.getProperties().get("y") / PPM;

                bdef.position.set(x,y);
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(32/PPM, 32/PPM);
                fdef.shape = shape;

                //COLLECTABLES SHOULD BE SENSORS
                fdef.isSensor = true;
                fdef.filter.categoryBits = B2DVars.BIT_AMMOPICKUP;
                fdef.filter.maskBits = B2DVars.BIT_PLAYER;
                Body body =  world.createBody(bdef);
                BulletPackage bulletPICKUP = new BulletPackage(body);
                body.createFixture(fdef).setUserData("BULLETPACK");
                bulletPackages.add(bulletPICKUP);
                body.setUserData(bulletPICKUP);
                shape.dispose();
            }
        dropItem = true;
        
    }
    
    public void createEnemy(){
        Vector2 enemyVector = new Vector2(.16f, 0f);
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef(); 
       
        bdef.position.set(120 / PPM, 140 / PPM);
        bdef.type = BodyType.KinematicBody;
        Body body = world.createBody(bdef);
        shape.setAsBox( 32 / PPM, 24 / PPM);
        
        fdef.shape = shape;
        fdef.friction = 1f;
        fdef.filter.categoryBits = B2DVars.BIT_BLOCKS;
        MassData md = new MassData();
        md.mass = 1f;
        body.setMassData(md);
        
        fdef.restitution = .15f;
        fdef.filter.maskBits  = B2DVars.BIT_PLAYER;
        
        body.createFixture(fdef).setUserData("BLOCKS");
        body.setLinearVelocity(enemyVector);
 
        enemy = new Enemy(body);
        body.setUserData(enemy);
        shape.dispose();
    }
  
     public void createBullets(float xCoord, float yCoord){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        xBulletCoord = player.getBody().getPosition().x;
        yBulletCoord = player.getBody().getPosition().y;
        shape.setAsBox(4/PPM, 4/PPM);
        fdef.restitution = .1f;
        fdef.shape = shape;
        bdef.position.set(player.getBody().getPosition().x + 1f/PPM, player.getBody().getPosition().y);
        fdef.filter.categoryBits = B2DVars.BIT_BULLET;
        Body bulletBody = world.createBody(bdef);
        bulletBody.createFixture(fdef).setUserData("BULLET");
        float x = xCoord;
        float y = yCoord;
        float radians = 3.145f/2f;
        
        Bullet bullet = new Bullet(bulletBody,x, y, radians);
    }
     
     
    public void createJoint(){
        /* DEFINITIONS */
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.DynamicBody;
        bdef.gravityScale = 0f;
        FixtureDef fdef = new FixtureDef();
        fdef.restitution = .8f;
     
        //TWO SHAPES TO CONNECT
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(4/PPM,4/PPM);
        CircleShape cShape = new CircleShape();
        cShape.setRadius(3f/PPM);
        
        //CREATE BOX TO LEFT
        bdef.position.set(950 / PPM, 158 / PPM);
        fdef.shape = cShape;
        fdef.density = 1f;
       
        fdef.restitution = .7f;
        fdef.filter.categoryBits = B2DVars.BIT_BALL; //BALL COLLIDES 
        Body body1 = world.createBody(bdef);
        body1.createFixture(fdef).setUserData("BLOCKS");
        car1 = new Car(body1);
        body1.setUserData(car1);
        
        
        //wheeel
        BodyDef b2 = new BodyDef();
        b2.type = BodyType.StaticBody;
        FixtureDef f2 = new FixtureDef();
        b2.position.set(955/PPM, 160 / PPM);
        f2.shape = cShape;
        f2.filter.categoryBits = B2DVars.BIT_BLOCKS;
        
        f2.restitution = .8f;
        Body body2 = world.createBody(b2);
        body2.createFixture(f2).setUserData("BLOCKS");
        car2 = new Car(body2);
        body2.setUserData(car2);
        
        
        RevoluteJointDef rjd = new RevoluteJointDef();
        
        rjd.bodyA = body1;
        rjd.bodyB = body2;
       
        rjd.collideConnected = true;
        
        rjd.localAnchorA.set(460/PPM,160/PPM);
        rjd.localAnchorB.set(345/PPM, 180/PPM);
        Joint j;
       
        j = world.createJoint(rjd);       
        cShape.dispose();
        polyShape.dispose();
        
    }
    
    
    
    
    public void levelThreeMP(){
        if(level ==3){
             Vector2 platformPos = movingPlatforms.get(0).getBody().getPosition();
             Vector2 platformPos2 = movingPlatforms.get(1).getBody().getPosition();

                //P1
                if(platformPos.x >= 375/PPM && platformPos.x < 420/PPM && movingPlatforms.get(0).getBody().getLinearVelocity().x >0){
                    movingPlatforms.get(0).getBody().setLinearVelocity(-.5f, 0);
                }
                 if(platformPos.x < 220 / PPM && movingPlatforms.get(0).getBody().getLinearVelocity().x < 0){
                    movingPlatforms.get(0).getBody().setLinearVelocity(.75f, 0);
                }
                 if(platformPos.x > 225/PPM && platformPos.x < 254/PPM && movingPlatforms.get(0).getBody().getLinearVelocity().x > 0){
                    movingPlatforms.get(0).getBody().setLinearVelocity(.75f,0);
                }

                 //P2
                 if(platformPos2.x >= 1160/PPM && platformPos2.x < 1400/PPM && movingPlatforms.get(1).getBody().getLinearVelocity().x >0){
                    movingPlatforms.get(1).getBody().setLinearVelocity(-.5f, 0);
                 }

                 if(platformPos2.x < 1040 / PPM && movingPlatforms.get(1).getBody().getLinearVelocity().x < 0){
                    movingPlatforms.get(1).getBody().setLinearVelocity(.75f, 0);
                 }

                 if(platformPos2.x > 850/PPM && platformPos2.x < 900/PPM && movingPlatforms.get(1).getBody().getLinearVelocity().x > 0){
                    movingPlatforms.get(1).getBody().setLinearVelocity(.75f,0);
                 }

            }
        
        
    }
       @Override
    public void dispose(){}
    
}










                
