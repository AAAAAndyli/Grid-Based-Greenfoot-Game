import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.File;

/**
 * Write a description of class Firewall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Firewall extends TriggerTile
{
    private Enemy enemy;
    private int timer = 0;
    private Label triggerNumberDisplay;
    private ArrayList<GreenfootImage> animation = new ArrayList<GreenfootImage>();
    private int animationTimer;
    private int animationIndex;
    private String colour;
    
    private Label enemyNumberDisplay;
    private StillLabel buttonEnemyNumberDisplay;
    
    public Firewall(String type, int rotations, int xPosition, int yPosition, int triggerNumber, String colour)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber, colour);
    }
    public Firewall(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber, String colour)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber, colour);
    }
    public Firewall(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber, String colour)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
        loadSingleAnimation("images/Tiles/" + "firewall/" +  colour + "/", animation);
        this.triggerNumber = triggerNumber;
        this.colour = colour;
    }
    public void addedToWorld(World world)
    {
        collidable = true;
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
        collidable = false;
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
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber + "," + -1 + "," + colour);
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
        animationIndex = animate(animation, animationIndex);
        super.act();
    }
    
    protected void loadSingleAnimation(String path, ArrayList<GreenfootImage> animation)
    {
        for(int i = 0; i < new File(path + "/" ).listFiles().length - 1; i++)
        {
            animation.add(new GreenfootImage(path + "/" + i + ".png"));
        }
    }
    
    public boolean getCollidable()
    {
        return collidable;
    }

    protected int animate(ArrayList<GreenfootImage> animation, int index)
    {
        if(animationTimer < 5)
        {
            animationTimer ++;
            return index;
        }
        setImage(animation.get(index));
        index++;
        if(index > animation.size()-1)
        {
            index = 0;
        }
        animationTimer = 0;
        return index;
    }
}
