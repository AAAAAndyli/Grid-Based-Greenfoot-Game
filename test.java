import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test extends ScrollingActor
{
    private int timer = 0;
    public test(boolean good)
    {
        if(good)
        {
            setImage("blue-draught-king.png");
        }
        else
        {
            setImage("red-draught-king.png");
        }
    }
    /**
     * Act - do whatever the test wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if(timer > 60)
        {
            getWorld().removeObject(this);
        }
        else
        {
            timer++;
        }
    }
}
