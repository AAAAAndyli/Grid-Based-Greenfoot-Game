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
    private boolean triggerActivated;
    private boolean triggerCanBeActivated = true;

    /**
     * Constructor for objects of class Trigger
     * If two objects share the same trigger
     */
    public Trigger(int TriggerID)
    {
        this.triggerID = triggerID;
    }
    
    public boolean getTrigger()
    {
        return triggerActivated;
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
}
