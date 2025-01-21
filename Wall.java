import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Wall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wall extends Bosses
{
    private Attack hitbox = new Attack(100, 200, 1, 0 , 0, 0, this);
    
    private int xVelocity;
    
    private boolean firstJumpFrame = true;
    private boolean touchingFloor;
    private int yVelocity;
    private int jumpStrength = -30;
    private int gravity = 1;
    private int playerDistance = 0;
    private Player player;
    private Camera camera;
    private WallSprites wallsprite = new WallSprites(this);
    private int hardlockPreventionTimer = 0;
    
    protected GreenfootImage hitBox;
    public Wall()
    {
        currentAttackDone = true;
        totalAttackCooldown = 60;
        bytesOnDeath = 75;
        health = 200;
        bossbar.setMaxVal(200);
        createHitBox();
        setImage(hitBox);
    }
    
    /**
     * Act - do whatever the Wall wants to do. This method is called whenever
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
                getWorld().addObject(wallsprite, getPosition().getX(), getPosition().getY());
            }
            else if(player.getWorld() != null)
            {
                if(currentAttackDone && attackCooldown < 0)
                {
                    attackCooldown = totalAttackCooldown;
                    state = Greenfoot.getRandomNumber(3);
                    currentAttackDone = false;
                    playerDistance = Math.abs(player.getX() - this.getX());
                    faceTowards(player.getPosition().getX());
                    hardlockPreventionTimer = 0;
                    return;
                }
                else if(currentAttackDone)
                {
                    attackCooldown--;
                    state = -1;
                }
                else
                {
                    switch(state)
                    {
                        case 0: 
                            currentAttackDone = jump(playerDistance);
                            break;
                        case 1:
                            currentAttackDone = runToOneSide();
                            break;
                        case 2:
                            currentAttackDone = shootProjectiles();
                            break;
                    }
                    hardlockPreventionTimer++;
                }
            }
            if(health < 50)
            {
                totalAttackCooldown = 10;
            }
            else if(health < 100)
            {
                totalAttackCooldown = 30;
            }
        }
        if(hardlockPreventionTimer > 600)
        {
            willDie = true;
        }
        checkFloor();
        predictFloor();
        super.act();
    }
    public void activateBoss()
    {
        bossActive = true;
    }
    public void faceTowards(int xPositionToFace)
    {
        if(xPositionToFace > getPosition().getX())
        {
            xDirection = 1;
        }
        else if(xPositionToFace < getPosition().getX())
        {
            xDirection = -1;
        }
    }
    public boolean jump(int playerDistance)
    {
        if(touchingFloor && !firstJumpFrame)
        {
            firstJumpFrame = true;
            camera.screenShake(3, 10);
            return true;
        }
        if(touchingFloor)
        {
            yVelocity = jumpStrength;
            firstJumpFrame = false;
        }
        if(getOneTileAtOffset(100 * xDirection, 0) != null)
        {
            xDirection = -xDirection;
            camera.screenShake(3, 10);
        }
        xVelocity = xDirection * playerDistance / 60;
        yVelocity += gravity;
        hitbox.performAttack();
        getPosition().setCoordinate(getPosition().getX() + xVelocity, getPosition().getY() + yVelocity);
        return false;
    }
    public boolean runToOneSide()
    {
        if(attackTimer == 0)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown + 5)
        {
            xVelocity = 20 * xDirection;
            hitbox.performAttack();
            if(getOneTileAtOffset(100 * xDirection, 0) != null)
            {
                attackTimer = 0;
                camera.screenShake(3, 10);
                return true;
            }
            getPosition().setCoordinate(getPosition().getX() + xVelocity, getPosition().getY());
        }
        else
        {
            attackTimer++;
        }
        return false;
    }
    public boolean shootProjectiles()
    {
        if(attackTimer == 0)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown + 5)
        {
            aiming(15);
            attackTimer = 0;
            useExplosiveProjectile(5, player.getPosition());
            useHomingProjectile(10, player.getPosition());
            useProjectile(15, player.getPosition());
            return true;
        }
        else
        {
            attackTimer++;
        }
        return false;
    }
    
    public Tile getOneTileAtOffset(int xOffset, int yOffset)
    {
        Tile tile = (Tile)getOneObjectAtOffset(xOffset, yOffset, Tile.class);
        if(tile  == null || tile.getButton() || tile.getCollidable() == false)
        {
            return null;
        }
        else
        {
            return tile;
        }
    }
    
    public boolean checkFloor()
    {
        Tile mid = getOneTileAtOffset(0, getImage().getHeight()/2);
        Tile left = getOneTileAtOffset(-getImage().getWidth()/2 + 15, getImage().getHeight()/2);
        Tile right = getOneTileAtOffset(getImage().getWidth()/2 - 15, getImage().getHeight()/2);
        boolean midTouching = mid != null;
        boolean leftTouching = left != null;
        boolean rightTouching = right != null;
        touchingFloor = midTouching || leftTouching || rightTouching;
        return touchingFloor;
    }
    
    public void predictFloor()
    {
        Tile predictedMidTile = getOneTileAtOffset((int)xVelocity, getImage().getHeight()/2+(int)yVelocity);
        boolean midWillTouch = predictedMidTile != null;
        if(midWillTouch)
        {
            yVelocity = -1;
            touchingFloor = true;
            globalPosition.setCoordinate(globalPosition.getX(), predictedMidTile.globalPosition.getY() - getImage().getHeight()/2 - predictedMidTile.getImage().getHeight()/2);
        }
    }
    
    public void hurt(int damage)
    {
        bossbar.update(health);
        super.hurt(damage);
    }
    
    
    public void createHitBox() 
    {
        hitBox = new GreenfootImage(100, 150);
        hitBox.setColor(new Color(0, 255, 0, 0));
        hitBox.fillRect(0, 0, 100, 150);
        setImage(hitBox);
    }
    
    public void die()
    {
        super.die();
    }
    
    public void loadAnimationFrames(String path)
    {
    }
}
