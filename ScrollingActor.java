import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollingActor extends SuperSmoothMover
{
    protected int originalX, originalY;
    protected int scrollX;
    protected int scrollY;
    protected boolean isFirstAct = true;
    /**
     * Act - do whatever the ScrollingActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(isFirstAct)
        {
            isFirstAct = false;
            originalX = getX();
            originalY = getY();
        }
        setLocation(originalX + scrollX, originalY + scrollY);
    }
    public void changeScrollPosition(int x, int y)
    {
        scrollX = x;
        scrollY = y;
    }
}
