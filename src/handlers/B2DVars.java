
package handlers;

/**
 *
 * @author alvarez
 */
public class B2DVars {
    //pixel per meter ratio
    public static final float PPM = 100; //pixels per meter
    public static final short BIT_CRYSTAL = 3;
    public static final short BIT_BALL = 3; //100
   // public static final short BIT_MP = 2;
    public static final short BIT_BOMB = 3;
    public static final short BIT_FOOT = 1;
    public static final short BIT_PLAYER = 2;//010
    public static final short BIT_BLOCKS = 3; //011
    public static final short BIT_AMMOPICKUP = 3;
    public static final short BIT_BULLET = 1;
    public static final short BIT_VAC = 2;
    
    
    // 7 = 0111 three categories !valid
    //8 == 1000 one category = valid
    public static short BIT_SENSOR = 3;
    public static short BIT_DOOR = 2;


}
