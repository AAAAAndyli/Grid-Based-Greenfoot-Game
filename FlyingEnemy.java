import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class ProjectileEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class FlyingEnemy extends Enemy
{
    private ArrayList<Coordinate> path = new ArrayList<Coordinate>();
    private int forcePathfindTimer = 0;
    
    protected int moveIndex;
    protected ArrayList<GreenfootImage> moveAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> moveAnimL = new ArrayList<GreenfootImage>();
    protected int attackIndex;
    protected ArrayList<GreenfootImage> attackAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> attackAnimL = new ArrayList<GreenfootImage>();
    protected int idleIndex;
    protected ArrayList<GreenfootImage> idleAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> idleAnimL = new ArrayList<GreenfootImage>();
    protected int deathIndex;
    protected ArrayList<GreenfootImage> deathAnimR = new ArrayList<GreenfootImage>();    
    protected ArrayList<GreenfootImage> deathAnimL = new ArrayList<GreenfootImage>();
    
    public FlyingEnemy()
    {
        attackTimer = 0;
    }
    /**
     * Act - do whatever the ProjectileEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        attack();
        if(path.size() > 0)
        {
            moveTowards(path.get(0));
        }   
        else
        {
            findPath();
        }
        /*
        if(forcePathfindTimer > 120)
        {
            path = new ArrayList<Coordinate>();
            findPath();
            forcePathfindTimer = 0;
        }
        else
        {
            forcePathfindTimer++;
        }
        */
        //System.out.println("size: " + path.size());
        super.act();
    }
    public void findPath()
    {
        Player player = getWorld().getObjects(Player.class).get(0);
        for(Coordinate coords : TheGrid.findPathAir(getPosition(), player.getPosition()))
        {
            path.add(coords);
        }
        //path = TheGrid.findPathAir(this.getPosition(), player.getPosition());
    }
    public void moveTowards(Coordinate location)
    {
        if(location.getString().equals(globalPosition.getString()))
        {
            path.remove(0);
            if(path.size() == 0)
            {
                findPath();
            }
        }
        else
        {
            double x = location.getX() - globalPosition.getX();
            double y = location.getY() - globalPosition.getY();
            double angle = Math.atan2(y, x);
            
            boolean closeEnoughX = (location.getX() < globalPosition.getX() + speed && location.getX() > globalPosition.getX() - speed);
            boolean closeEnoughY = (location.getY() < globalPosition.getY() + speed && location.getY() > globalPosition.getY() - speed);
            
            int speedX = (int)Math.round(speed * Math.cos(angle));
            int speedY = (int)Math.round(speed * Math.sin(angle));
             
            globalPosition.setCoordinate(globalPosition.getX() + speedX, globalPosition.getY() + speedY);
            
            if(closeEnoughX)
            {
                globalPosition.setX(location.getX());
            }
            if(closeEnoughY)
            {
                globalPosition.setY(location.getY());
            }
        }
    }
}
