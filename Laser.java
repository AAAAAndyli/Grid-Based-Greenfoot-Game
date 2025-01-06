import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends ScrollingActor
{
    private int width, height, laserVariant;
    private boolean scrolling;
    public Laser(int width, int height, int laserVariant, boolean scrolling)
    {
        this.laserVariant = laserVariant;
        this.scrolling = scrolling;
        setImage("laser/laser"+laserVariant+".png");
        getImage().scale(width+1, height+1);
    }
    public void act()
    {
        if(scrolling)
        {
            super.act();
        }
    }
    
    public int getVariant()
    {
        return laserVariant;
    }
    
    public void setWidth(int width)
    {
        getImage().scale(width+1, getImage().getHeight());
    }
    
    public void setHeight(int height)
    {
        getImage().scale(getImage().getWidth(), height+1);
    }

    public String toString()
    {
        return "Laser: width: " + width + ", height: " + height;
    }
}
