import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MovingScrollingActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Entity extends ScrollingActor
{
    protected boolean willDie = false;
    protected int health, speed;
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
        if(isTouching(Laser.class) && ((Laser)getOneIntersectingObject(Laser.class)).getVariant() == 2)
        {
            willDie = true;
        }
    }
    public void hurt(int damage)
    {
        health -= damage;
        if(health < 0)
        {
            willDie = true;
        }
    }
}
