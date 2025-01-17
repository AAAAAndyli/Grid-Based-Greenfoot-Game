import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Piramire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Piramire extends GroundedEnemy
{
    //attack variables
    private int attackRange = 100;
    private int attackCooldown = 0;
    private Attack stab = new Attack(50, 150, 3, 0 , 0, 75);
    private int attackFrame = 4; 
    private int attackFrameHang = 0;
    private final int constantAttackFrameHang = 15;
    private boolean isHanging = false;
    
    
    public Piramire()
    {
        super();
        xSpeed = 8;
        health = 5;
        loadAnimationFrames("images/Enemies/piramire");
    }
    /**
     * Act - do whatever the WalMare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!isAttacking)
        {
            canBeHurt = false;
            attackIndex = 0;
            Player player = (Player)getOneIntersectingObject(Player.class);
            if(player != null)
            {
                attack();
            }
            if(idleIndex != 4)
            {
                idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
            }
        }
        else
        {
            canBeHurt = true;
            attack();
        }
        super.act();
    }
    public void hurt(int damage)
    {
        super.hurt(damage);
        //Player player = getWorld().getObjects(Player.class).get(0);
        //faceTowards(player.getPosition().getX());
    }
    public boolean checkFloor()
    {
        return true;
    }
    public void predictFloor()
    {
    }
    public void applyGravity()
    {
    }
    public void attack()
    {
        if(isHanging)
        {
            if(attackFrameHang == constantAttackFrameHang)
            {
                attackFrameHang = 0;
                isHanging = false;
            }
            else
            {
                attackFrameHang++;
                return;
            }
        }
        if(attackTimer > attackCooldown + attackLength + constantAttackFrameHang)
        {
            canBeHurt = false;
            isAttacking = false;
            attackTimer = 0;
        }
        else if(attackCooldown == attackTimer)
        {
            isAttacking = true;
            attackAnimOver = false;
            attackIndex = animate(attackAnimR, attackIndex);
            attackIndex = 1;
            attackTimer++;
        }
        else if(attackCooldown + attackFrame - 10 == attackTimer)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackCooldown + attackFrame + 1 == attackTimer)
        {
            stab.performAttack();
            attackTimer++;
            isHanging = true;
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
        loadSingleAnimation(path, idleAnimR, "hide");
        loadSingleAnimation(path, attackAnimR, "attack");
    }
}
