import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BurstTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BurstTurret extends RapidTurret
{
    private int numberOfProjectiles = 0;
    public BurstTurret()
    {
        attackCooldown = 120;
    }
    /**
     * Act - do whatever the BurstTurret wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    public void attack()
    {
        if(((numberOfProjectiles > 0 && numberOfProjectiles < 10) && attackTimer > 5)||(numberOfProjectiles == 0 && attackTimer > attackCooldown) && getObjectsInRange(500, Player.class).size() != 0)
        {
            //System.out.println("attacking");
            numberOfProjectiles ++ ;
            attackTimer = 0;
            useProjectile(0, speed, target);
        }
        else if(attackTimer < attackCooldown - 5 && getObjectsInRange(500, Player.class).size() != 0)
        {
            aiming(speed);
        }
        else if(numberOfProjectiles != 0)
        {
            numberOfProjectiles = 0;
        }
        if(attackTimer < attackCooldown + 5)
        {
            attackTimer++;
        }
        else
        {
            attackTimer = 0;
        }
    }
}
