import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Patch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Patch extends GroundedEnemy
{
    //attack variables
    private int attackRange = 500;
    private Player player;    
    private int attackCooldown = 180;
    public Patch()
    {
        super();
        isImmuneToExplosions = true;
        health = 20;
        loadAnimationFrames("images/Enemies/patch");
    }
    /**
     * Act - do whatever the WalMare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        attack();
        idleIndex = animate(idleAnimR, idleIndex);
        super.act();
    }
    public void hurt(int damage)
    {
        super.hurt(damage);
        //Player player = getWorld().getObjects(Player.class).get(0);
        //faceTowards(player.getPosition().getX());
    }
    public void attack()
    {
        if(attackCooldown < attackTimer)
        {
            attackTimer = 0;
            for(Enemy enemy: getObjectsInRange(600, Enemy.class))
            {
                if(enemy.getClass() != Patch.class)
                {
                    enemy.setHurtable(false);
                }
            }
        }
        else
        {
            attackTimer++;
        }
    }
    public void die()
    {
        for (int i = 0; i < bytesOnDeath; i++) 
        {
            getWorld().addObject(new Byte(getPosition().getX(), getPosition().getY()), getPosition().getX(), getPosition().getY());
        }
        for(Enemy enemy: getObjectsInRange(1000000, Enemy.class))
        {
            enemy.setHurtable(true);
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
