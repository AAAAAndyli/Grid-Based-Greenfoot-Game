import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AttackIndicator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AttackIndicator extends ScrollingActor
{
    private int transparency = 250;
    public AttackIndicator(int x, int y)
    {
        this(x, y, 0, 0);
    }
    public AttackIndicator(int x, int y, int angle, int direction)
    {
        super(x, y);
        String fileName = "attackIndicator" + (direction == -1 ? "R" : direction == 1 ? "L" : "") + ".png";
        setImage(fileName);
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
     * Act - do whatever the AttackIndicator wants to do. This method is called whenever
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
        getPosition().setY(getPosition().getY() - 5);
        getImage().setTransparency(transparency);
        super.act();
    }
}
