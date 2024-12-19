import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MovingScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovingScrollingActor extends ScrollingActor
{
    /**
     * Act - do whatever the MovingScrollingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(globalPosition.getX() + scrollX, globalPosition.getY() + scrollY);
    }
}
