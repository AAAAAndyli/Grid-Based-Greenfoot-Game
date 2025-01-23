import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Effect for spawning things in
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class SpawnEffects extends Effect
{
    private GreenfootImage explosionSprite = new GreenfootImage("explosion.png");
    public SpawnEffects()
    {
        setImage(explosionSprite);
        speed = 5;
        getImage().scale(50,50);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        setLocation(getPosition().getX() - scrollX, getPosition().getY() - scrollY);
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
