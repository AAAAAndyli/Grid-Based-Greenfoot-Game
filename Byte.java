import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Byte here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Byte extends ScrollingActor
{
    private double xVelocity = 0;
    private int xDirection = 0;
    private double yVelocity = 0;
    private double yGravity = 1;
    private int coyoteTimer = 0;
    private final double maximumYVelocity = 40;
    private boolean touchingFloor;
    private boolean isCollidingLeft, isCollidingRight, isCollidingUp;
    private int value = 1;

    private GreenfootImage image;

    private SimpleTimer timer = new SimpleTimer();
    private Wallet wallet;

    private boolean started, collected;

    public Byte(int x, int y) {
        super(x,y);

        int randomImage = Greenfoot.getRandomNumber(2);
        if (randomImage == 0) {
            image = new GreenfootImage("images/Byte/0.png");
        } else {
            image = new GreenfootImage("images/Byte/1.png");
        }
        setImage(image);
        getImage().scale(35,30);

        yVelocity = -( Greenfoot.getRandomNumber(6) + 6 );

        int randomXDirection = Greenfoot.getRandomNumber(2);
        if (randomXDirection == 0) {
            xDirection = 1;
            xVelocity = Greenfoot.getRandomNumber(4) + 3;
        } else {
            xDirection = -1;
            xVelocity = -( Greenfoot.getRandomNumber(4) + 3 );
        }

        started = true;
        collected = false;
    }

    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }

    public void act()
    {
        super.act();
        if (started) {
            ArrayList<Wallet> getWallet = (ArrayList<Wallet>)getWorld().getObjects(Wallet.class);
            if (getWallet.size() != 0) {
                wallet = getWallet.get(0);
            }

            started = false;
        }
        if(touchingFloor)
        {
            if(isTouching(Byte.class))
            {
                Byte touchingByte = (Byte)getOneIntersectingObject(Byte.class);
                int byteValue = touchingByte.getValue();
                value += byteValue;
                getWorld().removeObject(touchingByte);
                touchingByte = null;
            }
        }
        ArrayList<HealthPod> touchingHealth = (ArrayList<HealthPod>)getIntersectingObjects(HealthPod.class);        
        ArrayList<Wallet> touchingWallet = (ArrayList<Wallet>)getIntersectingObjects(Wallet.class);        
        if ((touchingWallet.size() > 0 || touchingHealth.size() > 0)&& collected){
            wallet.changeAmount(value);
            getWorld().removeObject(this);
            return;
        }
        if (!collected) {
            movement();
            pickUp();
        } 
        if (collected && timer.millisElapsed() >= 1500) {
            wallet.changeAmount(value);
            getWorld().removeObject(this);
            return;
        }
        if (collected && getWorld() != null) {
            //xVelocity += ((double) wallet.getX() - (double) getPosition().getX()) / 500;            
            yVelocity += ((double) wallet.getY() - (double) getPosition().getY()) / 200;
            globalPosition.setCoordinate(globalPosition.getX() + (int)xVelocity, globalPosition.getY() + (int)yVelocity);            
        }
    }

    public void pickUp() {
        ArrayList<Player> touchingPlayer = (ArrayList<Player>)getIntersectingObjects(Player.class);
        if (touchingPlayer.size() == 1) {
            collected = true;
            yVelocity = 15;
            xVelocity = -20;
            timer.mark();
        }
    }

    public void movement() {
        predictFloor();
        checkFloor();
        if (touchingFloor) {
            xVelocity = 0;
        }
        applyGravity();
        collision();
        globalPosition.setCoordinate(globalPosition.getX() + (int)xVelocity, globalPosition.getY() + (int)yVelocity);
    }
    
    public int getValue()
    {
        return value;
    }

    public void collision()
    {
        Tile rightTouching = getOneTileAtOffset(getImage().getWidth()/2 + (int)xVelocity + 5, 0);
        Tile leftTouching = getOneTileAtOffset(-getImage().getWidth()/2 + (int)xVelocity - 5, 0);
        Tile upLeftTouching = getOneTileAtOffset(-getImage().getWidth()/2 + 5, -getImage().getHeight()/2);
        Tile upRightTouching = getOneTileAtOffset(getImage().getWidth()/2 - 5, -getImage().getHeight()/2);
        if(rightTouching != null && (xDirection == 1||xVelocity > 0) && !rightTouching.isDiagonal())
        {
            isCollidingRight = true;
            xVelocity = 0;
            globalPosition.setCoordinate(rightTouching.globalPosition.getX() - getImage().getWidth()/2 - rightTouching.getImage().getWidth()/2, globalPosition.getY());
        }
        else if(rightTouching != null && (xDirection == 1||xVelocity > 0))
        {
            isCollidingRight = true;
            xVelocity = 0;
            globalPosition.setCoordinate(rightTouching.globalPosition.getX() - getImage().getWidth()/2 - rightTouching.getImage().getWidth()/2, globalPosition.getY());
        }
        else
        {
            isCollidingRight = false;
        }
        if(leftTouching != null && (xDirection == -1||xVelocity < 0) && !leftTouching.isDiagonal())
        {
            isCollidingLeft = true;
            xVelocity = 0;
            globalPosition.setCoordinate(leftTouching.globalPosition.getX() + getImage().getWidth()/2 + leftTouching.getImage().getWidth()/2, globalPosition.getY());
        }
        else if(leftTouching != null && (xDirection == -1||xVelocity < 0))
        {
            isCollidingLeft = true;
            xVelocity = 0;
            globalPosition.setCoordinate(leftTouching.globalPosition.getX() + getImage().getWidth()/2 + leftTouching.getImage().getWidth()/2, globalPosition.getY());
        }
        else
        {
            isCollidingLeft = false;
        }
        if((upLeftTouching != null || upRightTouching != null) && yVelocity < 0)
        {
            isCollidingUp = true;
            yVelocity = 0;
            int ceilingTileBottom = upLeftTouching != null ? upLeftTouching.globalPosition.getY() + getImage().getHeight()/2 + upLeftTouching.getImage().getHeight()/2 : upRightTouching.globalPosition.getY() + getImage().getHeight()/2 + upRightTouching.getImage().getHeight()/2;
            globalPosition.setCoordinate(globalPosition.getX(), ceilingTileBottom);
        }
        else
        {
            isCollidingUp = false;
        }
    }

    public void predictFloor()
    {
        Tile predictedMidTile = getOneTileAtOffset((int)xVelocity, getImage().getHeight()/2+(int)yVelocity);
        Tile predictedLeftTile = getOneTileAtOffset(-getImage().getWidth()/2+(int)xVelocity + 20, getImage().getHeight()/2+(int)yVelocity);
        Tile predictedRightTile = getOneTileAtOffset(getImage().getWidth()/2+(int)xVelocity - 20, getImage().getHeight()/2+(int)yVelocity);

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

    public boolean checkFloor()
    {
        Tile mid = getOneTileAtOffset(0, getImage().getHeight()/2);
        Tile left = getOneTileAtOffset(-getImage().getWidth()/2 + 15, getImage().getHeight()/2);
        Tile right = getOneTileAtOffset(getImage().getWidth()/2 - 15, getImage().getHeight()/2);
        boolean midTouching = mid != null;
        boolean leftTouching = left != null;
        boolean rightTouching = right != null;
        touchingFloor = midTouching || leftTouching || rightTouching;

        if(!touchingFloor)
        {
            coyoteTimer++;
        }
        else
        {
            coyoteTimer = 0;
        }
        return touchingFloor;
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