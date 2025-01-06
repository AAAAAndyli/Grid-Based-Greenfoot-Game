import java.util.ArrayList;

/**
 * Write a description of class TriggerCollection here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TriggerCollection  
{
    // instance variables - replace the example below with your own
    private static ArrayList<Trigger> triggerList = new ArrayList<Trigger>();

    /**
     * Constructor for objects of class TriggerCollection
     */
    public TriggerCollection()
    {
        ArrayList<Trigger> triggerList = new ArrayList<Trigger>();
    }
    public static void resetList()
    {
        triggerList.clear();
    }
    public static void addTrigger(Trigger trigger)
    {
        triggerList.add(trigger);
    }
    public static boolean searchTrigger(Trigger trigger)
    {
        for(Trigger inListTrigger : triggerList)
        {
            if(trigger.toString().equals(inListTrigger.toString()))
            {
                return true;
            }
        }
        return false;
    }
    public static Trigger returnTrigger(Trigger trigger)
    {
        for(Trigger inListTrigger : triggerList)
        {
            if(trigger.toString().equals(inListTrigger.toString()))
            {
                return inListTrigger;
            }
        }
        return null;
    }
}
