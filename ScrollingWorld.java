import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScrollingWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class ScrollingWorld extends World
{
    protected static int scrollX = 0, scrollY = 0;
    private int scrollSpeed = 10;
    private int timer = 0;
    /**
     * Constructor for objects of class ScrollingWorld.
     * 
     */
    public ScrollingWorld(int xSize, int ySize, int cellSize, boolean bounding)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(xSize, ySize, cellSize, bounding); 
        scrollX = 0;
        scrollY = 0;
    }
    public void act()
    {
        timer++;
        if(timer > -1)
        {
            /*
             * 
             *
            if(Greenfoot.isKeyDown("right"))
            {
                scrollX -= scrollSpeed;
                timer = 0;
            }
            else if(Greenfoot.isKeyDown("left"))
            {
                scrollX += scrollSpeed;
                timer = 0;
            }
            if(Greenfoot.isKeyDown("down"))
            {
                forceScrollDown(scrollSpeed);
            }
            else if(Greenfoot.isKeyDown("up"))
            {
                scrollY += scrollSpeed;
                timer = 0;
            }
            */
            for(ScrollingActor actor : getObjects(ScrollingActor.class))
            {
                actor.changeScrollPosition(scrollX, scrollY);
            }
        }
    }
    public void setScrollSpeed(int scrollSpeed)
    {
        this.scrollSpeed = scrollSpeed;
    }
    public void forceScrollUp(int speed)
    {
        scrollY += speed;
    }
    public void forceScrollDown(int speed)
    {
        scrollY -= speed;
    }
    public void forceScrollLeft(int speed)
    {
        scrollX += speed;
    }
    public void forceScrollRight(int speed)
    {
        scrollX -= speed;
    }
}
