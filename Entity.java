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
    protected int maxHealth, health, speed;
    protected boolean canBeHurt = true;
    protected boolean isImmuneToExplosions = false;
    
    public Entity(int scrollX, int scrollY)
    {
        super(scrollX, scrollY);
    }
    
    public void act()
    {
        if(getWorld() != null)
        {
            barrier();
            super.act();
        }
    }
    public void die()
    {
        getWorld().removeObject(this);
        return;
    }
    public void barrier()
    {
        if(isTouching(Laser.class) && ((Laser)getOneIntersectingObject(Laser.class)).getVariant() == 2)
        {
            hurt(1);
        }
    }
    public void hurt(int damage)
    {
        hurt(damage, false);
    }
    public void hurt(int damage, boolean isExplosion)
    {
        if(canBeHurt && !(isImmuneToExplosions && isExplosion))
        {
            health -= damage;
            if(health <= 0)
            {
                willDie = true;
            }
        }
    }
    public int getHP()
    {
        return health;
    }
}
