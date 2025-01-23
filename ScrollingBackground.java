import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class scrollingBackground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollingBackground extends ScrollingActor
{
    private double scrollMultiplier;
    private int trueX;
    public ScrollingBackground(GreenfootImage image, double scrollMultiplier, int startingX)
    {
        setImage(image);
        trueX = startingX;
        this.scrollMultiplier = scrollMultiplier;
    }
    /**
     * Act - do whatever the scrollingBackground wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(globalPosition != null)
        {
            setLocation(trueX + (int)(scrollX * scrollMultiplier), getY());
        }
        if(getX() <= -getImage().getWidth() / 2) 
        {
            trueX += 2160;
        }
        else if(getX() >= 1080 + getImage().getWidth() / 2) 
        {
            trueX -= 2160;
        }
    }
}
