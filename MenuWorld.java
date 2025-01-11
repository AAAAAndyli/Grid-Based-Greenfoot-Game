import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MenuWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuWorld extends World
{

    /**
     * Constructor for objects of class MenuWorld.
     * 
     */
    public MenuWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        setBackground("images/menuPlaceholder.png");
        
        Button play = new Button(1.1, (World)new LevelWorld());
        addObject(play, 900, 200);
        Button settings = new Button(1.1);
        addObject(settings, 900, 400);
    }
    
    public void act(){
        
    }
}
