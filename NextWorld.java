import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class KillBorder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NextWorld extends CollisionTrigger
{   
    private Transition transition = new Transition(false);
    public NextWorld(String type, int rotations, int xPosition, int yPosition, int triggerNumber)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber);
    }
    public NextWorld(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber);
    }
    public NextWorld(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        getImage().scale(50, 100);
    }
    public void act()
    {
        super.act();
        activateTrigger();
    }
    public void activateTrigger()
    {
        if(isTouching(Player.class) && Greenfoot.isKeyDown("space"))
        {
            trigger.activateTrigger();
            getWorld().addObject(transition, 540, 360);
            if(transition.fadedOnce())
            {
                if(WorldOrder.isArSYS())
                {
                    Greenfoot.setWorld(new ArsysWorld(WorldOrder.nextWorld()));
                }
                else
                {
                    Greenfoot.setWorld(new Shop(new LevelWorld(WorldOrder.nextWorld())));
                }
            }
        }
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber);
    }
}
