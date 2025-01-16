import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AfterImage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AfterImage extends ScrollingActor
{
    private int transparency = 150;
    public AfterImage(GreenfootImage copy, int x, int y)
    {
        this(copy, x, y, 0);
    }
    public AfterImage(GreenfootImage copy, int x, int y, int angle)
    {
        super(x, y);
        setImage(copy);
        getImage().setTransparency(transparency);
        setRotation(angle);
    }
    public void addedToWorld(World world)
    {
        int tempScrollX = getWorldOfType(ScrollingWorld.class).getScrollX();
        int tempScrollY = getWorldOfType(ScrollingWorld.class).getScrollY();
        super.addedToWorld(world);
        setLocation(getPosition().getX() + tempScrollX, getPosition().getY() + tempScrollY);
    }
    /**
     * Act - do whatever the AfterImage wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        transparency -= 10;
        if(transparency < 0)
        {
            getWorld().removeObject(this);
            return;
        }
        getImage().setTransparency(transparency);
        super.act();
    }
}
