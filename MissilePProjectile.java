import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MissilePProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MissilePProjectile extends PProjectile
{
    public MissilePProjectile(Coordinate target, double speed, ScrollingActor spawner)
    {
        super(target, speed, 1, spawner, "PlayerDefault");
    }
    public MissilePProjectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
    {
        super(target, speed, damage, spawner, sprite);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    public void act()
    {
        speed += 0.5;
        super.act();
    }
    public void deleteConditions()
    {
        if(distance > MAX_DISTANCE)
        {
            getWorld().removeObject(this);
        }
        else if(isTouching(Tile.class) && ((Tile)getOneIntersectingObject(Tile.class)).getCollidable() == true)
        {
            getWorld().addObject(new PlayerExplosion(), getPosition().getX(), getPosition().getY());
            getWorld().removeObject(this);
        }
        else if(markedForDeletion)
        {
            getWorld().addObject(new PlayerExplosion(), getPosition().getX(), getPosition().getY());
            getWorld().removeObject(this);
        }
    }
    public void collide()
    {
        super.collide();
    }
}
