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
    protected GreenfootImage hitBox;
    
    public HurtBox(int width, int height, int damage)
    {
        this.width = width;
        this.height = height;
        this.damage = damage;
    }
    public void createHurtBox() 
    {
        hitBox = new GreenfootImage(width, height);
        hitBox.setColor(new Color(0, 255, 0, 255));
        hitBox.fillRect(0, 0, width, height);
        setImage(hitBox);
    }
    public void removeHurtBox()
    {
        if(getWorld() != null)
        {
            getWorld().removeObject(this);
        }
    }
    public abstract boolean collide();
}
