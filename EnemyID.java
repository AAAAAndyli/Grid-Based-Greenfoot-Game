import java.util.*;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * Write a description of class EnemyID here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyID  
{
    // instance variables - replace the example below with your own
    private static HashMap<Integer, Enemy> enemyHash = new HashMap<Integer, Enemy>();
    private static HashMap<Enemy, Integer> enemyIDHash = new HashMap<Enemy, Integer>();
    /**
     * Constructor for objects of class EnemyID
     */
    public EnemyID()
    {
        enemyHash.put(0, new WalMare());
        enemyHash.put(1, new BurstTurret());
        int value = 0;
        for(HashMap.Entry<Integer, Enemy> entry : enemyHash.entrySet())
        {
            Enemy key = entry.getValue();
            enemyIDHash.put(key, value);
            value++;
        }
    }
    public static void loadHash()
    {
        enemyHash.put(0, new WalMare());
        enemyHash.put(1, new BurstTurret());
        int value = 0;
        for(HashMap.Entry<Integer, Enemy> entry : enemyHash.entrySet())
        {
            Enemy key = entry.getValue();
            enemyIDHash.put(key, value);
            value++;
        }
    }
    public static Enemy getEnemy(int index)
    {
        Class<? extends Enemy> enemyClass;
        try
        {
            enemyClass = (enemyHash.get(index)).getClass();
        }
        catch(java.lang.NullPointerException e)
        {
            enemyClass = enemyHash.get(0).getClass();
        }
        try
        {
            return enemyClass.getDeclaredConstructor().newInstance();
        }
        catch(Exception e)
        {
            return null;
        }
    }
    public static int getID(Enemy index)
    {
        if(index == null)
        {
            System.out.println("Failed");
            return -1;
        }
        if(enemyIDHash.get(index.getClass()) != null)
        {
            int hashMapIndex = 0;
            for(HashMap.Entry<Integer, Enemy> entry : enemyHash.entrySet())
            {
                if(entry.getValue().getClass() == index.getClass())
                {
                    return hashMapIndex;
                }
                hashMapIndex++;
            }
        }
        return 0;
    }
}
