import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Cytotoxin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cytotoxin extends FlyingEnemy
{
    private int numberOfProjectiles = 0;
    private Player player;
    protected int duplicateIndex;
    protected ArrayList<GreenfootImage> dupAnim = new ArrayList<GreenfootImage>();
    protected int spawnIndex;
    protected ArrayList<GreenfootImage> spawnAnim = new ArrayList<GreenfootImage>();
    private boolean hasDuplicated = false;
    private boolean isSpawning = true;

    public Cytotoxin()
    {
        attackCooldown = 30;
        bytesOnDeath = 1;
        health = 1;
        speed = 5;
        attackFrame = 3;
        loadAnimationFrames("images/Enemies/cytotoxin");
    }
    /**
     * Act - do whatever the BurstTurret wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(player == null)
        {
            player = getWorldOfType(LevelWorld.class).getPlayer();
        }
        else
        {
            if(!isAttacking)
            {
                idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
                if(path.size() > 0)
                {
                    moveTowards(path.get(0));
                }   
                else
                {
                    findPath();
                }
            }
            if(getDistance(player) < 100 || isAttacking)
            {
                isAttacking = true;
                attack();
            }
        }
        super.act();
    }
    public void attack()
    {
        if(attackTimer > attackCooldown + attackLength)
        {
            getPosition().setCoordinate(getPosition().getX() - attackXOffset * xDirection, getPosition().getY() - attackYOffset);
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
            isAttacking = false;
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
            getWorld().addObject(new EnemyExplosion(), getPosition().getX(), getPosition().getY());
            willDie = true;
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
            attackIndex = animate(xDirection == 1 ? attackAnimR : attackAnimL, attackIndex);
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
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
            attackTimer++;
        }
    }
    public void findPath()
    {
        if(player != null)
        {
            Coordinate hoverCoordinate = new Coordinate(player.getPosition().getX() + Greenfoot.getRandomNumber(300) - 150, player.getPosition().getY() - 150 + Greenfoot.getRandomNumber(300));
            for(Coordinate coords : TheGrid.aStarfindPath(getPosition(), hoverCoordinate))
            {
                path.add(coords);
            }
        }
        //path = TheGrid.findPathAir(this.getPosition(), player.getPosition());
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        //System.out.println("Searching for images in: " + path + "/" + "hover");
        loadSingleAnimation(path, idleAnimR, "hover");
        loadSingleAnimation(path, attackAnimR, "attack");
    }
    protected int animate(ArrayList<GreenfootImage> animation, int index)
    {
        if(animationTimer < 5){
            return index;
        }
        createAfterImage();
        setImage(animation.get(index));
        index++;
        if(index > animation.size()-1)
        {
            index = 0;
        }
        animationTimer = 0;
        return index;
    }
}
