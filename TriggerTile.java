import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class KillBorder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TriggerTile extends Tile
{
    protected int triggerNumber;
    protected Trigger trigger;
    protected boolean collidable = false;
    public TriggerTile(String type, int rotations, int xPosition, int yPosition, int triggerNumber)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber);
    }
    public TriggerTile(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber);
    }
    public TriggerTile(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, false);
        this.triggerNumber = triggerNumber;
        collidable = false;
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        trigger = new Trigger(triggerNumber);
        if(TriggerCollection.searchTrigger(trigger))
        {
            trigger = TriggerCollection.returnTrigger(trigger);
        }
        else
        {
            TriggerCollection.addTrigger(trigger);
        }
    }
    public void act()
    {
        super.act();
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
