import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HurtBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class HurtBox extends ScrollingActor
{
    protected int damageDuration;
    protected int damage;
    protected int width, height;
    protected GreenfootImage hitbox;
    
    public HurtBox(int width, int height, int damage)
    {
        this.width = width;
        this.height = height;
        this.damage = damage;
    }
    public void createHurtBox() 
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(new Color(0, 255, 0, 100));
        image.fillRect(0, 0, width, height);
        setImage(image);
    }
    public void removeHurtBox()
    {
        if(getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }

}
