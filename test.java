import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test extends ScrollingActor
{
    /**
     * Act - do whatever the test wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int actTimer = 0;
    public void act()
    {
        actTimer++;
        if(actTimer == 60)
        {
            getWorld().removeObject(this);
            return;
        }
        super.act();
    }
}
