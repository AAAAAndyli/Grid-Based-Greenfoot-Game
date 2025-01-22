import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * HealthBlob holder. like a hamburger.
 * 
 * @author Andy
 * @version 1
 */
public class HealthPod extends Actor
{
    private HealthBlob healthBlob = new HealthBlob();
    public HealthPod()
    {
        setImage("HealthBar/HealthSlot.png");
    }
    public void addedToWorld(World world)
    {
        getWorld().addObject(healthBlob, getX(), getY());
    }
    public void setHealth(int health)
    {
        healthBlob.set(health);
    }
    public void lower(int hpLost)
    {
        for(int i = 0; i < hpLost ; i++)
        {
            healthBlob.lower();
        }
    }
    public void raise()
    {
        healthBlob.raise();
    }
}
