import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends ScrollingActor
{
    private GreenfootImage explosionSprite = new GreenfootImage("explosion.png");
    private int explodeTimer = 0;
    public Explosion()
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
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        explodeTimer++;
        if(explodeTimer < 15)
        {
            getWorld().getObjects(Camera.class).get(0).screenShake(1, 1);
            getImage().scale(50 + 15 * explodeTimer,50 + 15 * explodeTimer);
            if(getIntersectingObjects(Entity.class).size() != 0)
            {
                for(Entity entity : getIntersectingObjects(Entity.class))
                {
                    if(entity.getClass() == Player.class)
                    {
                        Player player = (Player)entity;
                        player.hurt(1);
                        player.launch(5);
                    }
                    else
                    {
                        entity.hurt(5);
                    }
                }
            }
        }
        else
        {
            if(getImage().getTransparency() - (explodeTimer - 15) * 10 > 0)
            {
                getImage().setTransparency(getImage().getTransparency() - (explodeTimer - 15) * 10);
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
