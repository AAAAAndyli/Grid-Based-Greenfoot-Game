import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effects here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpawnEffects extends ScrollingActor
{
    private GreenfootImage explosionSprite = new GreenfootImage("explosion.png");
    private int explodeTimer = 0;
    public SpawnEffects()
    {
        setImage(explosionSprite);
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
        explodeTimer++;
        if(explodeTimer < 15)
        {
            getImage().scale(getImage().getWidth() + 15,getImage().getHeight() + 15);
        }
        else
        {
            if(getImage().getTransparency() - (explodeTimer - 15) * 10 > 0)
            {
                getImage().setTransparency(getImage().getTransparency() - (explodeTimer - 15) * 10);
                if(getImage().getWidth() - 15 > 0)
                {
                    getImage().scale(getImage().getWidth() - 15,getImage().getHeight() - 15);
                }
            }
            else
            {
                getWorld().removeObject(this);
                return;
            }
        }
        super.act();
    }
}
