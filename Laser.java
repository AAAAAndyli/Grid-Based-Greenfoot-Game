import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends ScrollingActor
{
    private int width, height;
    public Laser(int width, int height, int laserVariant)
    {
        setImage("laser"+laserVariant+".png");
        getImage().scale(width, height);
    }
    /**
     * Act - do whatever the laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public String toString()
    {
        return "Laser: width: " + width + ", height: " + height;
    }
}
