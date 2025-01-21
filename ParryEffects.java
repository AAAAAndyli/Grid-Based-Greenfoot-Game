import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effects here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParryEffects extends Effect
{
    private GreenfootImage sprite = new GreenfootImage("Parried.png");
    public ParryEffects()
    {
        setImage(sprite);
        speed = 5;
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
    }
}
