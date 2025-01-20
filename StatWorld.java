import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class StatWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StatWorld extends World
{    
    
    ArrayList<Class> classList = new ArrayList<Class>();
    
    /**
     * Constructor for objects of class StatWorld.
     * 
     */
    public StatWorld(World world)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 

        WorldButton back = new WorldButton("Buttons/backButton.png", 0.5, world);
        addObject(back, 85, 50);
        
        ScrollingUI scroll = new ScrollingUI(0, 0, 500, 1500, true, classList);
        addObject(scroll, getWidth() / 2, 350);
    }
}
