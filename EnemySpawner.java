import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemySpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemySpawner extends TriggerTile
{
    private Enemy enemy;
    private int timer = 0;
    public EnemySpawner(String type, int rotations, int xPosition, int yPosition, int triggerNumber, Enemy enemy)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber, enemy);
    }
    public EnemySpawner(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber, Enemy enemy)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber, enemy);
    }
    public EnemySpawner(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber, Enemy enemy)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
        this.triggerNumber = triggerNumber;
        this.enemy = enemy;
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
    public void whenTriggered()
    {
        getWorld().addObject(enemy, x, y);
        if(timer > 0)
        {
            trigger.permanentlyDeactivateTrigger();
        }
        else
        {
            timer++;
        }
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber + "," + EnemyID.getID(enemy));
    }
    /**
     * Act - do whatever the EnemySpawner wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(trigger.getTrigger())
        {
            whenTriggered();
        }
        super.act();
    }
}
