import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class ProjectileEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FlyingEnemy extends Enemy
{
    private ArrayList<Coordinate> path = new ArrayList<Coordinate>();
    private int forcePathfindTimer = 0;
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
        if(forcePathfindTimer > 120)
        {
            findPath();
            forcePathfindTimer = 0;
        }
        else
        {
            forcePathfindTimer++;
        }
        System.out.println("size: " + path.size());
        super.act();
    }
    public void findPath()
    {
        path = new ArrayList<Coordinate>();
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
            System.out.println("LOCATING");
            System.out.println("Position: " + getPosition().getString());
        }
        else
        {
            double x = location.getX() - globalPosition.getX();
            double y = location.getY() - globalPosition.getY();
            double angle = Math.atan2(y, x);
            
            boolean closeEnoughX = (location.getX() < globalPosition.getX() + speed && location.getX() > globalPosition.getX() - speed);
            boolean closeEnoughY = (location.getY() < globalPosition.getY() + speed && location.getY() > globalPosition.getY() - speed);
            
            int speedX = closeEnoughX ? 0 : (int)Math.round(speed * Math.cos(angle));
            int speedY = closeEnoughY ? 0 : (int)Math.round(speed * Math.sin(angle));
                        
            if(closeEnoughX)
            {
                globalPosition.setX(location.getX());
                System.out.println("TELEPORT X");
            }
            if(closeEnoughY)
            {
                globalPosition.setY(location.getY());
                System.out.println("TELEPORT Y");
            }
            
            globalPosition.setCoordinate(globalPosition.getX() + speedX, globalPosition.getY() + speedY);
            
            System.out.println("MOVING to " + path.get(0).getString());
            System.out.println("Position: " + getPosition().getString());
        }
    }
}
