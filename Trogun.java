import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Trogun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trogun extends GroundedEnemy
{
    //attack variables
    private int attackRange = 500;
    private int attackCooldown = 60;
    
    private int health = 5;
    
    public Trogun()
    {
        super();
        xSpeed = 5;
        loadAnimationFrames("images/Enemies/walmare");
    }
    /**
     * Act - do whatever the Trogun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(checkForPlayer())
        {
            Player player = (Player)getOneObjectAtOffset(playerDistance, 0, Player.class);
            //System.out.println("PlayerPos: " + player.getPosition().getX() + ", EnemyPosition: " +  getPosition().getX() + ", AttackRange: " + attackRange + ", DifferenceInRange: " +  Math.abs(player.getPosition().getX() - getPosition().getX()));
            if(Math.abs(player.getPosition().getX() - getPosition().getX()) > attackRange)
            {
                moveTo(player.getPosition().getX() - xDirection * attackRange);
                faceTowards(player.getPosition().getX());
            }
            else
            {
                xVelocity = 0;
                if(attackCooldown < attackTimer)
                {
                    aiming(projectileSpeed);
                    useProjectile(0, projectileSpeed, target);
                    attackTimer = 0;
                }
                else
                {
                    attackTimer++;
                }
            }
        }
        else
        {
            xVelocity = 0;
            if(checkTimer > 60)
            {
                xDirection *= -1;
                checkTimer = 0;
            }
            else
            {
                checkTimer++;
            }
        }
        super.act();
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        loadSingleAnimation(path, idleAnimL, "idle", true);
        loadSingleAnimation(path, idleAnimR, "idle");
    }
}
