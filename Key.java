import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class KillBorder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Key extends TriggerTile
{   
    private String colour;
    public Key(String type, int rotations, int xPosition, int yPosition, int triggerNumber, String colour)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber, colour);
    }
    public Key(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber, String colour)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber, colour);
    }
    public Key(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber, String colour)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
        this.colour = colour;
        setImage("Tiles/" + "key/" +  colour + ".png");
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
            getWorld().removeObject(this);
        }
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber + "," + -1 + "," + colour);
    }
}
