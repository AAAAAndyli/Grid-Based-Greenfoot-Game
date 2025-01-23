import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * it goes to mouse.
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class Crosshair extends SuperSmoothMover
{
    /**
     * Act - do whatever the Cursor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null)
        {
            setLocation(mouse.getX(), mouse.getY());
        }
    }
}
