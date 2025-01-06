import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class KillBorder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollisionTrigger extends TriggerTile
{   
    public CollisionTrigger(String type, int rotations, int xPosition, int yPosition, int triggerNumber)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber);
    }
    public CollisionTrigger(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber);
    }
    public CollisionTrigger(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        if(TriggerCollection.searchTrigger(triggerNumber))
        {
            trigger = TriggerCollection.returnTrigger(triggerNumber);
            trigger.setTrigger(triggerNumber);
        }
        else
        {
            trigger = new Trigger(triggerNumber);
            trigger.setTrigger(triggerNumber);
            TriggerCollection.addTrigger(trigger);
        }
    }
    public void act()
    {
        super.act();
        activateTrigger();
    }
    public void activateTrigger()
    {
        if(isTouching(Player.class))
        {
            trigger.activateTrigger();
        }
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber);
    }
}
