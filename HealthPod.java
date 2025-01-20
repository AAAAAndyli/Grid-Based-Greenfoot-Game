import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthPod here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
