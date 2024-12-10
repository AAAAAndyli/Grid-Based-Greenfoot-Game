import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends ScrollingActor
{
    private double xVelocity = 0;
    private int xDirection = 0;
    private final int xSpeed = 10;
    
    private final int slideSpeed = 15;
    private boolean isSliding;
    
    private double yVelocity = 0;
    private final double yGravity = 1;
    private final double maximumYVelocity = 40;
    private final double jumpSpeed = 20;
    
    private boolean touchingFloor;
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void addedToWorld()
    {
      //super.addedToWorld();
    }
    public void act()
    {
        if(isFirstAct)
        {
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
        checkFloor();
        applyGravity();
        jump();
        predictFloor();
        boundingBox();
    }
    public void boundingBox()
    {     
        boolean rightBounding = getX() > 600 && xDirection == 1 && (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"));
        boolean leftBounding = getX() < 200 && xDirection == -1 && (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"));
        boolean downBounding = getY() > 400;
        boolean upBounding = getY() < 200 && yVelocity < 0;
        if(rightBounding || leftBounding || downBounding || upBounding)
        {
            if(rightBounding)
            {
                setLocation(getX(),getY()+yVelocity);
                getWorldOfType(ScrollingWorld.class).forceScrollRight(10);
            }
            else if(leftBounding)
            {
                setLocation(getX(),getY()+yVelocity);
                getWorldOfType(ScrollingWorld.class).forceScrollLeft(10);
            }
            if(downBounding)
            {
                setLocation(getX()+xVelocity,getY()+yVelocity);
                getWorldOfType(ScrollingWorld.class).forceScrollDown(20);
            }
            else if(upBounding)
            {
                setLocation(getX()+xVelocity,getY());
                getWorldOfType(ScrollingWorld.class).forceScrollUp((int)-yVelocity);
            }
            getWorldOfType(ScrollingWorld.class).setScrollSpeed(10);
        }
        else
        {
            getWorldOfType(ScrollingWorld.class).setScrollSpeed(0);
            setLocation(getX()+xVelocity,getY()+yVelocity);
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
        boolean midTouching = getOneObjectAtOffset(0, getImage().getHeight()/2, Tile.class) != null;
        boolean leftTouching = getOneObjectAtOffset(-getImage().getWidth()/2, getImage().getHeight()/2, Tile.class) != null;
        boolean rightTouching = getOneObjectAtOffset(getImage().getWidth()/2, getImage().getHeight()/2, Tile.class) != null;
        touchingFloor = midTouching || leftTouching || rightTouching;
        return touchingFloor;
    }
    public void predictFloor()
    {
        Tile predictedMidTile = (Tile)getOneObjectAtOffset((int)xVelocity, getImage().getHeight()/2+(int)yVelocity, Tile.class);
        Tile predictedLeftTile = (Tile)getOneObjectAtOffset(-getImage().getWidth()/2+(int)xVelocity, getImage().getHeight()/2+(int)yVelocity, Tile.class);
        Tile predictedRightTile = (Tile)getOneObjectAtOffset(getImage().getWidth()/2+(int)xVelocity, getImage().getHeight()/2+(int)yVelocity, Tile.class);
        
        boolean midWillTouch = predictedMidTile != null;
        boolean leftWillTouch = predictedLeftTile != null;
        boolean rightWillTouch = predictedRightTile != null;
        
        if(leftWillTouch || rightWillTouch)
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
        if((Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))&&touchingFloor)
        {
            yVelocity -= jumpSpeed;
        }
    }
    public void slam()
    {
        if((Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))&&!touchingFloor)
        {
            for(int i = 0 ; i < 100 ; i++)
            {
                Tile predictedMidTile = (Tile)getOneObjectAtOffset(0, 50 * i, Tile.class);
                if(predictedMidTile != null)
                {
                    setLocation(getX(), predictedMidTile.getY() - getImage().getHeight()/2 - predictedMidTile.getImage().getHeight()/2);  
                }
            }
        }
    }
    public void slide()
    {
        if((Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))&&(touchingFloor||isSliding))
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
}
