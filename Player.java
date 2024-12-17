import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class Player extends ScrollingActor
{
    private double xVelocity = 0;
    private int xDirection = 0;
    private final int xSpeed = 10;
    
    private final int slideSpeed = 25;
    private boolean isSliding;
    
    private boolean isSlamming = false;
    private boolean isCollidingLeft, isCollidingRight, isCollidingUp;
    
    private double yVelocity = 0;
    private double storedJump = 0;
    private final double yGravity = 1;
    private final double maximumYVelocity = 40;
    private final double jumpSpeed = 20;
    private int coyoteTimer = 0;
    private boolean isJumpKeyDown = false;
    
    private boolean touchingFloor;
    public void addedToWorld()
    {
      //super.addedToWorld();
    }
    public void act()
    {
        if(isFirstAct)
        {
            //I'm pretty sure this isn't useful anymore lol
            isFirstAct = false;
            originalX = getX();
            originalY = getY();
        }
        
        slide();
        
        if(!isSliding)
        {
            horizontalMovement();
            slam();
        }
        
        predictFloor();
        
        checkFloor();
        applyGravity();
        jump();
        collision();
        boundingBox();
    }
    public void boundingBox()
    {     
        boolean rightBounding = getX() > 500 && (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"));
        boolean leftBounding = getX() < 300 && (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"));
        boolean downBounding = getY() > 350;
        boolean upBounding = getY() < 250 && !isCollidingUp && yVelocity < 0;
        if(rightBounding || leftBounding)
        {
            if(rightBounding)
            {
                getWorldOfType(ScrollingWorld.class).forceScrollRight((int)xVelocity);
            }
            if(leftBounding)
            {
                getWorldOfType(ScrollingWorld.class).forceScrollLeft(-(int)xVelocity);
            }
        }
        else
        {
            setLocation(getX()+xVelocity,getY());
        }
        if(downBounding || upBounding)
        {
            if(downBounding)
            {
                getWorldOfType(ScrollingWorld.class).forceScrollDown((int)yVelocity);
                if(yVelocity == 0)
                {
                    getWorldOfType(ScrollingWorld.class).forceScrollDown(20);
                }
            }
            if(upBounding)
            {
                getWorldOfType(ScrollingWorld.class).forceScrollUp(-(int)yVelocity);
            }
        }
        else
        {
            setLocation(getX(),getY()+yVelocity);
        }
    }
    public void horizontalMovement()
    {
        if((Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))&&(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")))
        {
            xVelocity = 0;
        }
        else
        {
            if(Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
            {
                xVelocity = -xSpeed;
                xDirection = -1;
            }
            else if(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
            {
                xVelocity = xSpeed;
                xDirection = 1;
            }
            else
            {
                xVelocity = 0;
            }
        }
    }
    public boolean checkFloor()
    {
        Tile mid = getOneTileAtOffset(0, getImage().getHeight()/2);
        Tile left = getOneTileAtOffset(-getImage().getWidth()/2 + 10, getImage().getHeight()/2);
        Tile right = getOneTileAtOffset(getImage().getWidth()/2 - 10, getImage().getHeight()/2);
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
    public void predictFloor()
    {
        if(!touchingFloor)
        {
            Tile predictedMidTile = getOneTileAtOffset((int)xVelocity, getImage().getHeight()/2+(int)yVelocity);
            Tile predictedLeftTile = getOneTileAtOffset(-getImage().getWidth()/2+(int)xVelocity + 20, getImage().getHeight()/2+(int)yVelocity);
            Tile predictedRightTile = getOneTileAtOffset(getImage().getWidth()/2+(int)xVelocity - 20, getImage().getHeight()/2+(int)yVelocity);
            
            boolean midWillTouch = predictedMidTile != null;
            boolean leftWillTouch = predictedLeftTile != null;
            boolean rightWillTouch = predictedRightTile != null;
            
            if(midWillTouch || leftWillTouch || rightWillTouch)
            {
                yVelocity = 0;
                if(midWillTouch)
                {
                    setLocation(getX(), predictedMidTile.getY() - getImage().getHeight()/2 - predictedMidTile.getImage().getHeight()/2);
                }
                else if(leftWillTouch)
                {
                    setLocation(getX(), predictedLeftTile.getY() - getImage().getHeight()/2 - predictedLeftTile.getImage().getHeight()/2);
                }
                else if(rightWillTouch)
                {
                    setLocation(getX(), predictedRightTile.getY() - getImage().getHeight()/2 - predictedRightTile.getImage().getHeight()/2);                
                }
            }
        }
    }
    public Tile getOneTileAtOffset(int xOffset, int yOffset)
    {
        Tile tile = (Tile)getOneObjectAtOffset(xOffset, yOffset, Tile.class);
        if(tile  == null || tile.getButton())
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
    public void jump()
    {
        if((Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up")) && coyoteTimer < 10 && !isJumpKeyDown)
        {
            yVelocity -= (jumpSpeed + storedJump);
            coyoteTimer = 100;
            storedJump = 0;
            isJumpKeyDown = true;
        }
        else if(!Greenfoot.isKeyDown("w") && !Greenfoot.isKeyDown("up"))
        {
            isJumpKeyDown = false;
        }
    }
    public void slam()
    {
        if((Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))&&!touchingFloor)
        {
            for(int i = 0 ; i < 100 ; i++)
            {
                Tile predictedMidTile = getOneTileAtOffset(0, 10 * i);
                if(predictedMidTile != null)
                {
                    setLocation(getX(), predictedMidTile.getY() - getImage().getHeight()/2 - predictedMidTile.getImage().getHeight()/2);  
                    storedJump += 5;
                    break;
                }
                isSlamming = true;
            }
        }
        else if(!(Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")) && isSlamming)
        {
            isSlamming = false;
        }
    }
    public void slide()
    {
        if((Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")) && (touchingFloor||isSliding) && !isSlamming)
        {
            switch(xDirection)
            {
                case -1:
                    if(!isSliding)
                    {
                        xVelocity = -slideSpeed;
                    }
                    if(xVelocity < 0)
                    {
                        xVelocity += 0.2;
                    }
                    else
                    {
                        xVelocity = 0;
                    }
                    break;
                case 1:
                    if(!isSliding)
                    {
                        xVelocity = slideSpeed;
                    }
                    if(xVelocity > 0)
                    {
                        xVelocity -= 0.2;
                    }
                    else
                    {
                        xVelocity = 0;
                    }
                    break;
            }
            isSliding = true;
        }
        else
        {
            isSliding = false;
        }
    }
    public void collision()
    {
        Tile rightTouching = getOneTileAtOffset(getImage().getWidth()/2 + (int)xVelocity, 0);
        Tile leftTouching = getOneTileAtOffset(-getImage().getWidth()/2 + (int)xVelocity, 0);
        Tile upTouching = getOneTileAtOffset(0, -getImage().getHeight()/2);
        if(rightTouching != null && xDirection == 1)
        {
            isCollidingRight = true;
            xVelocity = 0;
            setLocation(rightTouching.getX() - getImage().getWidth()/2 - rightTouching.getImage().getWidth()/2, getY());
        }
        else
        {
            isCollidingRight = false;
        }
        if(leftTouching != null && xDirection == -1)
        {
            isCollidingLeft = true;
            xVelocity = 0;
            setLocation(leftTouching.getX() + getImage().getWidth()/2 + leftTouching.getImage().getWidth()/2, getY());
        }
        else
        {
            isCollidingLeft = false;
        }
        if(upTouching != null)
        {
            isCollidingUp = true;
            yVelocity = 1;
            setLocation(getX(), upTouching.getY() + getImage().getHeight()/2 + upTouching.getImage().getHeight()/2);
        }
        else
        {
            isCollidingUp = false;
        }
    }
}
