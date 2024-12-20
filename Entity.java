import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MovingScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Entity extends ScrollingActor
{
    public void act()
    {
        barrier();
        super.act();
    }
    public void die()
    {
        getWorld().removeObject(this);
    }
    public void barrier()
    {
        if(isTouching(Laser.class))
        {
            die();
        }
    }
}
