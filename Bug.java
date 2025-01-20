import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Bug here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bug extends Bosses
{
    private boolean firstJumpFrame = true;
    private boolean touchingFloor;
    private int yVelocity;
    private int jumpStrength = -30;
    private int gravity = 1;
    private int playerDistance = 0;
    private Player player;
    private Camera camera;
    
    private boolean closeToPlayer = true;
    
    private boolean isMultitasking = false;
    private boolean secondaryAttackDone = true;
    private int secondaryState = -1;
    private int secondaryAttackCooldown;
    
    private int xVelocity = 0;
    private int maxXVelocity = 30; 
    
    private int lowestAttackXCoordinate, highestAttackXCoordinate;
    private int fullSweepIndex = 0;
    private int fullSweepSize = 48;
    private int fullSweepTimer;
    
    protected GreenfootImage hitBox;
    public Bug()
    {
        currentAttackDone = true;
        attackCooldown = 180;
        totalAttackCooldown = 60;
        bytesOnDeath = 200;
        health = 500;
        bossbar.setMaxVal(500);
        setImage("images/Enemies/bug/head/idleHead.png");
    }
    
    /**
     * Act - do whatever the Bug wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        bossActive = true;
        if(bossActive)
        {
            if(player == null)
            {
                player = getWorldOfType(LevelWorld.class).getPlayer();
                camera = getWorld().getObjects(Camera.class).get(0);
                camera.activateBossMode();
                getWorld().addObject(bossbar, 520, 680);
                getWorld().addObject(new BugLegSprite(this), getPosition().getX(), getPosition().getY());
                getWorld().addObject(new BugBody(this), getPosition().getX(), getPosition().getY());
                lowestAttackXCoordinate = getPosition().getX() - 720;
                highestAttackXCoordinate = getPosition().getX() + 720;
            }
            else if(player.getWorld() != null)
            {
                if(currentAttackDone && attackCooldown < 0)
                {
                    attackCooldown = totalAttackCooldown;
                    state = Greenfoot.getRandomNumber(3);
                    currentAttackDone = false;
                    closeToPlayer = Greenfoot.getRandomNumber(2) == 0;
                    playerDistance = Math.abs(player.getX() - this.getX());
                }
                else if(currentAttackDone)
                {
                    followPlayer();
                    attackCooldown--;
                    state = -1;
                }
                else
                {
                    switch(state)
                    {
                        case 0: 
                            currentAttackDone = summonSideLegs(player.getPosition().getX(), player.getPosition().getY());
                            break;
                        case 1:
                            currentAttackDone = summonUnderLegs(player.getPosition().getX(), player.getPosition().getY());
                            break;
                        case 2:
                            currentAttackDone = shootProjectiles();
                            break;
                    }
                }
                if(isMultitasking)
                {
                    if(secondaryAttackDone && secondaryAttackCooldown < 0)
                    {
                        secondaryAttackCooldown = totalAttackCooldown;
                        secondaryState = Greenfoot.getRandomNumber(5);
                        secondaryAttackDone = false;
                    }
                    else if(secondaryAttackDone)
                    {
                        secondaryAttackCooldown--;
                        secondaryState = -1;
                    }
                    else
                    {
                        switch(secondaryState)
                        {
                            case 0: 
                                secondaryAttackDone = summonCoverMapUnderLegs(player.getPosition().getY(), true);
                                break;
                            case 1:
                                secondaryAttackDone = summonUnderLegs(player.getPosition().getX(), player.getPosition().getY());
                                break;
                            case 2:
                                secondaryAttackDone = shootProjectiles();
                                break;
                            case 3:
                                secondaryAttackDone = summonSideLegs(player.getPosition().getX(), player.getPosition().getY());
                                break;
                            case 4: 
                                secondaryAttackDone = summonCoverMapUnderLegs(player.getPosition().getY(), false);
                                break;
                        }
                    }
                }
                if(closeToPlayer)
                {
                    retreatHead();
                }
                else
                {
                    getCloseHead();
                }
            }
            if(health < 250)
            {
                isMultitasking = true;
            }
        }
        super.act();
    }
    public void followPlayer()
    {
        int playerX = player.getPosition().getX();
        int bugX = this.getPosition().getX();
        int distance = playerX - bugX;
            
        getPosition().setCoordinate(getPosition().getX() + (int)(distance * 0.05), getPosition().getY());
    }
    public void retreatHead()
    {
        int playerY = player.getPosition().getY() - 400;
        int bugY = this.getPosition().getY();
        int distance = playerY - bugY;
        
        getPosition().setCoordinate(getPosition().getX(), getPosition().getY() + (int)(distance * 0.05));
    }
    public void getCloseHead()
    {
        int playerY = player.getPosition().getY() - 300;
        int bugY = this.getPosition().getY();
        int distance = playerY - bugY;
        
        getPosition().setCoordinate(getPosition().getX(), getPosition().getY() + (int)(distance * 0.05));
    }
    public void activateBoss()
    {
        bossActive = true;
    }
    public boolean summonUnderLegs(int x, int y)
    {
        getWorld().addObject(new BugLeg(x, y, true, camera), 100000, 100000);
        return true;
    }
    public boolean summonSideLegs(int x, int y)
    {
        getWorld().addObject(new BugLeg(x, y, false, camera), 100000, 100000);
        return true;
    }
    public boolean summonCoverMapUnderLegs(int y, boolean isLeftToRight)
    {
        if(fullSweepTimer < 10)
        {
            fullSweepTimer++;
            return false;
        }
        fullSweepTimer = 0;
        if(isLeftToRight)
        {
            getWorld().addObject(new BugLeg(lowestAttackXCoordinate + fullSweepIndex * 30, y, true, camera), 100000, 100000);
            if(fullSweepIndex > fullSweepSize)
            {
                fullSweepIndex = 0;
                return true;
            }
            else
            {
                fullSweepIndex++;
            }
        }
        else
        {
            getWorld().addObject(new BugLeg(highestAttackXCoordinate - fullSweepIndex * 30, y, true, camera), 100000, 100000);
            if(fullSweepIndex > fullSweepSize)
            {
                fullSweepIndex = 0;
                return true;
            }
            else
            {
                fullSweepIndex++;
            }
        }
        return false;
    }
    public boolean shootProjectiles()
    {
        if(attackTimer == 0)
        {
            setImage("images/Enemies/bug/head/attackPrepareHead.png");
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown + 5)
        {
            attackTimer = 0;
            setImage("images/Enemies/bug/head/idleHead.png");
            for(int i = 0; i < 5 ; i++)
            {
                Coordinate newCoordinate = new Coordinate(player.getPosition().getX(), player.getPosition().getY());
                newCoordinate.setX(newCoordinate.getX() + (i - 3) * 150);
                useProjectile(15, newCoordinate);
            }
            return true;
        }
        else
        {
            attackTimer++;
        }
        return false;
    }
    
    public void hurt(int damage)
    {
        bossbar.update(health);
        super.hurt(damage);
    }
    
    
    public void die()
    {
        super.die();
    }
    
    public void loadAnimationFrames(String path)
    {
    }
}
