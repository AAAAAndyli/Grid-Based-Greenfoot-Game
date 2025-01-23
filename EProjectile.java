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
    public EProjectile(Coordinate target, double speed, int damage, String sprite)
    {
        super(target, speed, damage, sprite);
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
        else if(isTouching(Mail.class))
        {
            Mail player = (Mail)getOneIntersectingObject(Mail.class);
            player.hurt(damage);
        }
    }
    public void parried(int mouseX, int mouseY)
    {
        if(spawner != null && spawner.getWorld() != null)
        {
            Coordinate spawnerLocalCoordinate = new Coordinate(spawner.getX(), spawner.getY());
            getWorld().addObject(new PProjectile(spawnerLocalCoordinate, speed, 1, this, "ParriedProjectile"), getX(), getY());
            getWorld().addObject(new ParryEffects(), getX(), getY());
            getWorld().removeObject(this);
        }
        else
        {
            getWorld().addObject(new PProjectile(new Coordinate(mouseX, mouseY), speed, 1, this, "ParriedProjectile"), getX(), getY());
            getWorld().addObject(new ParryEffects(), getX(), getY());
            getWorld().removeObject(this);
        }
    }
}
