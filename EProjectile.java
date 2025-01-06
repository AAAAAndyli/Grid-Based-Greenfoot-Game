import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EProjectile extends Projectile
{
    public EProjectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
    {
        super(target, speed, damage, spawner, sprite);
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
            if(player.getHurtable())
            {
                player.hurt(damage);
                markedForDeletion = true;
            }
        }
    }
    public void parried(int mouseX, int mouseY)
    {
        if(spawner.getWorld() != null)
        {
            Coordinate spawnerLocalCoordinate = new Coordinate(spawner.getX(), spawner.getY());
            getWorld().addObject(new PProjectile(spawnerLocalCoordinate, speed, 15, this, "ParriedProjectile"), getX(), getY());
            getWorld().removeObject(this);
        }
        else
        {
            getWorld().addObject(new PProjectile(new Coordinate(mouseX, mouseY), speed, 15, this, "ParriedProjectile"), getX(), getY());
            getWorld().removeObject(this);
        }
    }
}
