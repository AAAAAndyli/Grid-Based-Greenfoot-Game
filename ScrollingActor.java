import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollingActor extends SuperSmoothMover
{
    protected int scrollX;
    protected int scrollY;
    protected boolean isFirstAct = true;
    protected Coordinate globalPosition;
    
    public void addedToWorld(World world)
    {
        globalPosition = new Coordinate(getX()+scrollX, getY()+scrollY);
    }
    /**
     * Act - do whatever the ScrollingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(globalPosition.getX() + scrollX, globalPosition.getY() + scrollY);
    }
    public void changeScrollPosition(int x, int y)
    {
        scrollX = x;
        scrollY = y;
    }
    public int getScrollX()
    {
        return scrollX;
    }
    public int getScrollY()
    {
        return scrollY;
    }
}
