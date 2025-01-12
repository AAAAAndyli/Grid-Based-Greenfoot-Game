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

    public ScrollingActor()
    {
        this(0,0);
    }
    public ScrollingActor(int scrollX, int scrollY)
    {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
    }
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
        if(globalPosition != null)
        {
            setLocation(globalPosition.getX() + scrollX, globalPosition.getY() + scrollY);
        }
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
    public void setCoordinate(Coordinate newCoordinate)
    {
        globalPosition = newCoordinate;
    }
    public void setCoordinate(int x, int y)
    {
        globalPosition = new Coordinate(x, y);
    }
    public Coordinate getPosition()
    {
        return globalPosition;
    }
}
