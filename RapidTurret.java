import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RapidTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RapidTurret extends Turret
{
    public RapidTurret()
    {
        attackCooldown = 20;
    }
    /**
     * Act - do whatever the RapidTurret wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        attack();
        super.act();
    }
}
