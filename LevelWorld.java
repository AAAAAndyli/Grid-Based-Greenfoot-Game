import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
// for reading files
import java.util.StringTokenizer;
// for Files
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Write a description of class LevelWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelWorld extends ScrollingWorld
{
    private String levelName;
    ArrayList<String> world = new ArrayList<String>();
    /**
     * Constructor for objects of class LevelWorld.
     * 
     */
    public LevelWorld(String levelName)
    {
        super(800, 600, 1, false); 
        this.levelName = levelName;
        Greenfoot.setSpeed(51);
    }
    public void loadLevel()
    {
        Scanner scan = new Scanner (System.in);
        try
        {
            scan = new Scanner (new File(levelName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found");
        }
        while (scan.hasNext()) // loop until end of file
        {
            world.add(scan.nextLine());
        }
        StringTokenizer tokenizer;
        for(String tile : world)
        {
            tokenizer = new StringTokenizer(tile, ",");
            int sizeOfString = tokenizer.countTokens();
            try
            {
                String type = tokenizer.nextToken();
                int rotation = Integer.parseInt(tokenizer.nextToken());
                int xLocation = Integer.parseInt(tokenizer.nextToken());
                int yLocation = Integer.parseInt(tokenizer.nextToken());
                addObject(new Tile(type, rotation, xLocation, yLocation), xLocation, yLocation);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Bad File >:(");
            }
        }
    }
}
