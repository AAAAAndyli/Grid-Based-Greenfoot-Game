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
    
    //private Label triggerNumberDisplay;
    private StillLabel buttonTriggerNumberDisplay;
    
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
        //triggerNumberDisplay = new Label(triggerNumber, 25, this);
        buttonTriggerNumberDisplay = new StillLabel(triggerNumber, 25, this);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        //trigger.setTrigger(triggerNumber);
        if(!isButton)
        {
            //getWorld().addObject(triggerNumberDisplay, getPosition().getX()-15, getPosition().getY()-15);
        }
        else
        {
            getWorld().addObject(buttonTriggerNumberDisplay, getPosition().getX()-15, getPosition().getY()-15);
        }
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
    }
    public StillLabel getLabel()
    {
        return buttonTriggerNumberDisplay;
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber);
    }
}
