import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ExplodingPProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BombPProjectile extends PProjectile
{
    private double gravity = 0.9, yVelocity = 0;
    public BombPProjectile(Coordinate target, double speed, ScrollingActor spawner)
    {
        super(target, speed, 1, spawner, "PlayerDefault");
    }
    public BombPProjectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
    {
        super(target, speed, damage, spawner, sprite);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    public void act()
    {
        yVelocity += gravity;
        distance += speed;
        globalPosition.setCoordinate(globalPosition.getX()+(int)Math.round(speed * Math.cos(Math.toRadians(getRotation()))), globalPosition.getY()+(int)yVelocity+(int)Math.round(speed * Math.sin(Math.toRadians(getRotation()))));
        collide();
        System.out.println(speed);
        if(globalPosition != null)
        {
            setLocation(globalPosition.getX() + scrollX, globalPosition.getY() + scrollY);
        }
        deleteConditions();
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
