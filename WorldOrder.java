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
    private static int worldIndex = SaveFile.getInt("worldIndex");

    public static void createArrayList()
    {
        worldList.clear();
        worldList.add("level1.csv");
        worldList.add("wa.csv");
        worldList.add("level2.csv");
        worldList.add("BugEntrance.csv");
        worldList.add("ba.csv");
        worldList.add("level3.csv");
        worldList.add("sa.csv");
        worldList.add("Arsys");
    }
    public static void createArSYSList()
    {
        worldList.clear();
        worldList.add("0.csv");
        worldList.add("1.csv");
        worldList.add("2.csv");
        worldList.add("3.csv");
        worldList.add("4.csv");
        worldList.add("5.csv");
        worldList.add("6.csv");
        worldList.add("7.csv");
        worldList.add("8.csv");
        worldList.add("9.csv");
        worldList.add("Win");
    }
    public static boolean isArSYS()
    {
        return worldList.get(0).equals("0.csv");
    }
    public static void setIndex(String worldName)
    {
        worldIndex = worldList.indexOf(worldName);
        SaveFile.setInfo("worldIndex", worldIndex);
    }
    public static void setIndex(int index)
    {
        worldIndex = index;
    }
    public static int getIndex()
    {
        return worldIndex;
    }
    public static String nextWorld()
    {
        return worldList.get(worldIndex + 1);
    }
    public static String currentWorld()
    {
        if(worldIndex < worldList.size())
        {
            return worldList.get(worldIndex);
        }
        return null;
    }
}
