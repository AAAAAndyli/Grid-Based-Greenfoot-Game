import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Projectile extends ScrollingActor
{
    protected Coordinate target;
    protected ScrollingActor spawner;
    protected int damage;
    protected double speed;
    protected double distance = 0;
    protected boolean markedForDeletion = false;
    protected final double MAX_DISTANCE = 2000;
    public Projectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
    {
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.spawner = spawner;
        setImage("Projectiles/" + sprite + ".png");
    }
    public Projectile(Coordinate target, double speed, int damage, String sprite)
    {
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        setImage("Projectiles/" + sprite + ".png");
    }
    public void addedToWorld(World world)
    {
        if(spawner != null)
        {
            globalPosition = new Coordinate(spawner.globalPosition.getX(), spawner.globalPosition.getY());
        }
        else
        {
            globalPosition = new Coordinate(getX(), getY());
        }
        turnTowards(target.getX(), target.getY());
    }
    public void act()
    {
        distance += speed;
        getWorld().addObject(new AfterImage(new GreenfootImage(getImage()), scrollX, scrollY, getRotation()), getPosition().getX(), getPosition().getY());
        super.act();
        globalPosition.setCoordinate(globalPosition.getX()+(int)Math.round(speed * Math.cos(Math.toRadians(getRotation()))), globalPosition.getY()+(int)Math.round(speed * Math.sin(Math.toRadians(getRotation()))));
        collide();
        //move(speed);
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
            getWorld().removeObject(this);
        }
        else if(markedForDeletion)
        {
            getWorld().removeObject(this);
        }
    }
    
    public abstract void collide();
    
    public void changeAim(Coordinate target)
    {
        turnTowards(target.getX(), target.getY());
    }
}
