import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.util.ArrayList;

/**
 * Write a description of class MeleeEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class GroundedEnemy extends Enemy
{
    protected double xVelocity = 0;
    protected int xSpeed = 10;
    protected int detectionRange = 1080;
    protected int attackRange = 100;
    
    protected double yVelocity = 0;
    protected double yGravity = 1;
    protected final double maximumYVelocity = 40;
    protected boolean touchingFloor;
    
    protected int playerDistance;
    
    protected int checkTimer = 0;
    protected int trackTimer = 0;
    
    protected int walkIndex;
    protected ArrayList<GreenfootImage> walkAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> walkAnimL = new ArrayList<GreenfootImage>();
    protected int attackIndex;
    protected ArrayList<GreenfootImage> attackAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> attackAnimL = new ArrayList<GreenfootImage>();
    protected int idleIndex;
    protected ArrayList<GreenfootImage> idleAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> idleAnimL = new ArrayList<GreenfootImage>();
    protected int deathIndex;
    protected ArrayList<GreenfootImage> deathAnimR = new ArrayList<GreenfootImage>();    
    protected ArrayList<GreenfootImage> deathAnimL = new ArrayList<GreenfootImage>();
    
    /**
     * Act - do whatever the MeleeEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        applyGravity();
        checkFloor();
        predictFloor();
        
        animationTimer++;
        //idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
        
        globalPosition.setCoordinate(globalPosition.getX() + (int)xVelocity, globalPosition.getY() + (int)yVelocity);
        super.act();
    }
    
    public boolean checkForPlayer()
    {
        for(int i = 0; i < detectionRange / 20; i++)
        {
            if(getOneObjectAtOffset(i*20*xDirection, 0, Player.class) != null)
            {
                playerDistance = i*20*xDirection;
                return true;
            }
        }
        return false;
    }
    
    
    public void moveTo(int xPositionToMoveTo)
    {
        if(xPositionToMoveTo > getPosition().getX())
        {
            //xDirection = 1;
            xVelocity = xSpeed;
        }
        else if(xPositionToMoveTo < getPosition().getX())
        {
            //xDirection = -1;
            xVelocity = -xSpeed;
        }
        else
        {
            xVelocity = 0;
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
        Tile predictedLeftTile = getOneTileAtOffset(-getImage().getWidth()/2, getImage().getHeight()/2+(int)yVelocity);
        Tile predictedRightTile = getOneTileAtOffset(getImage().getWidth()/2, getImage().getHeight()/2+(int)yVelocity);
        
        boolean midWillTouch = predictedMidTile != null;
        boolean leftWillTouch = predictedLeftTile != null;
        boolean rightWillTouch = predictedRightTile != null;
        
        if((midWillTouch || leftWillTouch || rightWillTouch))
        {
            yVelocity = -1;
            touchingFloor = true;
            if(midWillTouch)
            {
                globalPosition.setCoordinate(globalPosition.getX(), predictedMidTile.globalPosition.getY() - getImage().getHeight()/2 - predictedMidTile.getImage().getHeight()/2);
            }
            else if(leftWillTouch)
            {
                globalPosition.setCoordinate(globalPosition.getX(), predictedLeftTile.globalPosition.getY() - getImage().getHeight()/2 - predictedLeftTile.getImage().getHeight()/2);
            }
            else if(rightWillTouch)
            {
                globalPosition.setCoordinate(globalPosition.getX(), predictedRightTile.globalPosition.getY() - getImage().getHeight()/2 - predictedRightTile.getImage().getHeight()/2);
            }
        }
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
    public void applyGravity()
    {
        if(touchingFloor == false)
        {
            yVelocity += yGravity;
        }
        else
        {
            yVelocity = 0;
        }
        if(yVelocity > maximumYVelocity)
        {
            yVelocity = maximumYVelocity;
        }
    }
}
