import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Parry sprites
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class ParryEffects extends Effect
{
    private GreenfootImage sprite = new GreenfootImage("Parried.png");
    public ParryEffects()
    {
        setImage(sprite);
        speed = 5;
        setRotation(Greenfoot.getRandomNumber(360));
        getImage().scale(75,75);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    /**
     * Act - do whatever the Effects wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        if(timer > 0)
        {
            getWorld().removeObject(this);
        }
    }
}
