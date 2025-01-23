import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ram here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ram extends GroundedEnemy
{
    //attack variables
    public Ram()
    {
        super();
        health = 20;
        bytesOnDeath = 25;
        setImage("Enemies/ram/Ram.png");
    }
    /**
     * Act - do whatever the WalMare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    public void hurt(int damage)
    {
        super.hurt(damage);
    }
    public void die()
    {
        for (int i = 0; i < bytesOnDeath; i++) 
        {
            getWorld().addObject(new Byte(getPosition().getX(), getPosition().getY()), getPosition().getX(), getPosition().getY());
        }
        getWorld().removeObject(this);
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        //System.out.println("Searching for images in: " + path + "/" + "");
        loadSingleAnimation(path, idleAnimR, "idle");
    }
}
