import java.util.ArrayList;

/**
 * Write a description of class WorldOrder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldOrder  
{
    // instance variables - replace the example below with your own
    private static ArrayList<String> worldList = new ArrayList<String>();
    private static int worldIndex = 0;

    public static void createArrayList()
    {
        worldList.clear();
        worldList.add("BugEntrance.csv");
        worldList.add("ba.csv");
    }
    public static void setIndex(String worldName)
    {
        worldIndex = worldList.indexOf(worldName);
    }
    public static void setIndex(int index)
    {
        worldIndex = index;
    }
    public static String nextWorld()
    {
        worldIndex++;
        return worldList.get(worldIndex);
    }
}
