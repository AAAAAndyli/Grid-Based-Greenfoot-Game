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
    private int xSpeed = 10;
    
    private double yVelocity = 0;
    private double yGravity = 1;
    private double jumpSpeed = 20;
    
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
        horizontalMovement();
        checkFloor();
        applyGravity();
        jump();
        predictFloor();
        boundingBox();
    }
    public void boundingBox()
    {
        if((getX() > 600 && xDirection == 1)||(getX() < 200 && xDirection == -1)||getY() > 500||getY() < 100)
        {
            if(getX() > 600 && xDirection == 1)
            {
                setLocation(getX(),getY()+yVelocity);
            }
            else if(getX() < 200 && xDirection == -1)
            {
                setLocation(getX(),getY()+yVelocity);
            }
            else if(getY() > 300)
            {
                setLocation(getX()+xVelocity,getY()+yVelocity);
                getWorldOfType(ScrollingWorld.class).forceScrollDown(20);
            }
            else if(getY() < 100)
            {
                setLocation(getX()+xVelocity,getY()+yVelocity);
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
    }
    public void jump()
    {
        if((Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))&&touchingFloor)
        {
            yVelocity -= jumpSpeed;
        }
    }
}
