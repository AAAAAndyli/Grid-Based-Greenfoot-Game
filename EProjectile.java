import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EProjectile extends Projectile
{
    public EProjectile(Coordinate target, double speed, ScrollingActor spawner, String sprite)
    {
        super(target, speed, spawner, sprite);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    public void act()
    {
        super.act();
    }
    public void collide()
    {
        if(isTouching(Player.class))
        {
            Player player = (Player)getOneIntersectingObject(Player.class);
            player.hurt(1);
        }
    }
    public void parried()
    {
        Coordinate spawnerLocalCoordinate = new Coordinate(spawner.getX(), spawner.getY());
        getWorld().addObject(new PProjectile(spawnerLocalCoordinate, speed, this, "ParriedProjectile"), getX(), getY());
        getWorld().removeObject(this);
    }
}
