import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bosses here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Bosses extends Enemy
{
    protected int state;
    protected boolean currentAttackDone, bossActive;
    protected int attackCooldown, totalAttackCooldown;
    protected int maxHealth;
    protected SuperStatBar bossbar = new SuperStatBar(health, maxHealth, null, 1000, 40, 0, Color.WHITE, Color.BLACK, false, Color.GREEN, 10);
    
    public int getState()
    {
        return state;
    }
    public int getFacing()
    {
        return xDirection;
    }
    public void die()
    {
        Coordinate playerPosition = getWorld().getObjects(Player.class).get(0).getPosition();
        getWorld().addObject(new NextWorld("NextWorld", 0, playerPosition.getX(), playerPosition.getY(), 100), playerPosition.getX(), playerPosition.getY());
        super.die();
    }
}
