import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Trigger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trigger
{
    // instance variables - replace the example below with your own
    private int triggerID;
    private boolean triggerActivated = false;
    private boolean triggerCanBeActivated = true;
    private boolean toBeDeactivated = false;
    
    private ArrayList<EnemySpawner> enemySpawnerArrayList = new ArrayList<EnemySpawner>();
    
    /**
     * Constructor for objects of class Trigger
     * If two objects share the same trigger
     */
    public Trigger(int triggerID)
    {
        //System.out.println("TriggerID:" + TriggerID);
        this.triggerID = triggerID;
        triggerActivated = false;
    }
    
    public void setTrigger(int triggerID)
    {
        this.triggerID = triggerID;
        //System.out.println("TriggerID owo:" + (this.TriggerID));
        triggerActivated = false;
    }
    
    public boolean getTrigger()
    {
        return triggerActivated;
    }
    
    public int getID()
    {
        return triggerID;
    }
    
    public void addSpawner(EnemySpawner spawner)
    {
        enemySpawnerArrayList.add(spawner);
    }
    
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void activateTrigger()
    {
        if(triggerCanBeActivated)
        {
            triggerActivated = true;
            if(enemySpawnerArrayList.size() != 0)
            {
                int delay = 0;
                for(EnemySpawner spawner : enemySpawnerArrayList)
                {
                    spawner.setSpawnDelay(delay);
                    delay += 15;
                }
            }
        }
    }
    public void deactivateTrigger()
    {
        triggerActivated = false;
    }
    public void permanentlyDeactivateTrigger()
    {
        deactivateTrigger();
        triggerCanBeActivated = false;
    }
    public String toString()
    {
        return "Trigger Number: " + triggerID;
    }
    /*
    public void deactivateAtEndOfAct()
    {
        toBeDeactivated = true;
    }
    */
}
