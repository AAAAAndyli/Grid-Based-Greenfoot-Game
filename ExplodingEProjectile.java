import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ExplodingEProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplodingEProjectile extends EProjectile
{
    private Player target;
    public ExplodingEProjectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
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
    public void deleteConditions()
    {
        if(distance > MAX_DISTANCE)
        {
            getWorld().removeObject(this);
        }
        else if(isTouching(Tile.class) && ((Tile)getOneIntersectingObject(Tile.class)).getCollidable() == true)
        {
            getWorld().addObject(new Explosion(), getPosition().getX(), getPosition().getY());
            getWorld().removeObject(this);
        }
        else if(markedForDeletion)
        {
            getWorld().addObject(new Explosion(), getPosition().getX(), getPosition().getY());
            getWorld().removeObject(this);
        }
    }
    public void collide()
    {
        super.collide();
    }
    public void parried(int mouseX, int mouseY)
    {
        super.parried(mouseX, mouseY);
    }
}
