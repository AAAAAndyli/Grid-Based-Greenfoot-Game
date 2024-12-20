import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Entity
{
    public Enemy()
    {
        
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        globalPosition.setCoordinate(getX(), getY());
    }
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        barrier();
        if(willDie)
        {
            die();
        }
    }
    public void findAirPath()
    {
        
    }
    public void findlandPath()
    {
        
    }
}
