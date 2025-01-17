import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class WalMare here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WalMare extends GroundedEnemy
{
    //attack variables
    private int attackRange = 100;
    private int attackCooldown = 25;
    private Attack pierce = new Attack(attackRange + 50, 10, 1, 0 , attackRange/2 + 10, 0, this);
    private int attackFrame = 6; 
    private int attackXOffset = 27;
    
    
    public WalMare()
    {
        super();
        xSpeed = 8;
        health = 5;
        loadAnimationFrames("images/Enemies/walmare");
    }
    /**
     * Act - do whatever the WalMare wants to do. This method is called whenever
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
                if(Math.abs(player.getPosition().getX() - getPosition().getX()) > attackRange && getOneTileAtOffset(getImage().getWidth()/2 * xDirection, 0) == null)
                {
                    walkIndex = animate(xDirection==1 ? walkAnimR : walkAnimL, walkIndex);
                    moveTo(player.getPosition().getX() - xDirection * attackRange);
                    faceTowards(player.getPosition().getX());
                    followTimer = 0;
                }
                else if(player != null)
                {
                    xVelocity = 0;
                    pierce.changeDirection(xDirection);
                    attack();
                }
                else
                {
                    xVelocity = 0;
                }
            }
            else if(followTimer < 120)
            {
                Player player = (Player)getOneObjectAtOffset(playerDistance, 0, Player.class);
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
    public void hurt(int damage)
    {
        super.hurt(damage);
        Player player = getWorld().getObjects(Player.class).get(0);
        faceTowards(player.getPosition().getX());
    }
    public void attack()
    {
        isAttacking = true;
        if(attackTimer > attackCooldown + attackLength)
        {
            isAttacking = false;
            getPosition().setCoordinate(getPosition().getX() - attackXOffset * xDirection, getPosition().getY() - attackYOffset);
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
            attackTimer = 0;
        }
        else if(attackCooldown == attackTimer)
        {
            attackAnimOver = false;
            getPosition().setCoordinate(getPosition().getX() + attackXOffset * xDirection, getPosition().getY() + attackYOffset);
            attackIndex = animate(xDirection==1 ? attackAnimR : attackAnimL, attackIndex);
            attackIndex = 1;
            attackTimer++;
        }
        else if(attackCooldown + attackFrame == attackTimer)
        {
            pierce.performAttack();
            attackTimer++;
        }
        else if(attackCooldown + attackFrame - 20 == attackTimer)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
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
        //System.out.println("Searching for images in: " + path + "/" + "");
        loadSingleAnimation(path, idleAnimL, "idle", true);
        loadSingleAnimation(path, idleAnimR, "idle");
        loadSingleAnimation(path, attackAnimL, "attack", true);
        loadSingleAnimation(path, attackAnimR, "attack");
        loadSingleAnimation(path, walkAnimL, "walk", true);
        loadSingleAnimation(path, walkAnimR, "walk");
    }
}
