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
        getImage().scale(width+1, height+1);
    }

    public String toString()
    {
        return "Laser: width: " + width + ", height: " + height;
    }
}
