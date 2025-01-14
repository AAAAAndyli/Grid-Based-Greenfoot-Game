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
    
    public Trogun()
    {
        super();
        xSpeed = 5;
        health = 5;
        attackFrame = 4;
        loadAnimationFrames("images/Enemies/trogun");
    }
    /**
     * Act - do whatever the Trogun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!isAttacking)
        {
            attackIndex = 0;
            if(checkForPlayer())
            {
                Player player = (Player)getOneObjectAtOffset(playerDistance, 0, Player.class);
                //System.out.println("PlayerPos: " + player.getPosition().getX() + ", EnemyPosition: " +  getPosition().getX() + ", AttackRange: " + attackRange + ", DifferenceInRange: " +  Math.abs(player.getPosition().getX() - getPosition().getX()));
                if(Math.abs(player.getPosition().getX() - getPosition().getX()) > attackRange)
                {
                    walkIndex = animate(xDirection==1 ? walkAnimR : walkAnimL, walkIndex);
                    moveTo(player.getPosition().getX() - xDirection * attackRange);
                    faceTowards(player.getPosition().getX());
                    followTimer = 0;
                }
                else
                {
                    xVelocity = 0;
                    attack();
                }
            }
            else if(followTimer < 120)
            {
                Player player = (Player)getOneObjectAtOffset(-playerDistance, 0, Player.class);
                if(player != null)
                {
                    faceTowards(player.getPosition().getX());
                }
                else
                {
                    followTimer++;
                    walkIndex = animate(xDirection==1 ? walkAnimR : walkAnimL, walkIndex);
                    moveTo(xDirection * 50 + getPosition().getX());
                    if(getOneTileAtOffset(xDirection * (getImage().getWidth()/2 + 50), 0) != null)
                    {
                        followTimer = 2000;
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
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
        }
        else
        {
            attack();
        }
        super.act();
    }
    
    public void attack()
    {
        if(attackTimer > attackCooldown + attackLength)
        {
            isAttacking = false;
            getPosition().setCoordinate(getPosition().getX() - attackXOffset * xDirection, getPosition().getY() - attackYOffset);
            attackTimer = 0;
        }
        else if(attackCooldown == attackTimer)
        {
            isAttacking = true;
            attackAnimOver = false;
            getPosition().setCoordinate(getPosition().getX() + attackXOffset * xDirection, getPosition().getY() + attackYOffset);
            attackIndex = animate(xDirection==1 ? attackAnimR : attackAnimL, attackIndex);
            attackIndex = 1;
            attackTimer++;
        }
        else if(attackCooldown + attackFrame == attackTimer)
        {
            aiming(projectileSpeed);
            useProjectile(0, projectileSpeed, target);
            attackTimer++;
        }
        else if(isAttacking && !attackAnimOver)
        {
            int prevAttackIndex = attackIndex;
            attackIndex = animate(xDirection==1 ? attackAnimR : attackAnimL, attackIndex);
            if(prevAttackIndex != attackIndex)
            {
                attackTimer++;
            }
            if(attackIndex == 0)
            {
                attackAnimOver = true;
            }
        }
        else
        {
            attackTimer++;
        }
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
        loadSingleAnimation(path, attackAnimL, "attack", true);
        loadSingleAnimation(path, attackAnimR, "attack");
        loadSingleAnimation(path, walkAnimL, "walk", true);
        loadSingleAnimation(path, walkAnimR, "walk");
    }
}
