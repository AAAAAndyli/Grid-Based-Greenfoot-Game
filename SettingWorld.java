import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SettingWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SettingWorld extends World
{

    /**
     * Constructor for objects of class SettingWorld.
     * 
     */
    public SettingWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        setBackground("images/settingsPlaceholder.png");
        
        Button keybind = new Button(0.5);
        addObject(keybind, 380, 230);
        
        Button audio = new Button(0.5);
        addObject(audio, 380, 305);
        
        Button placeholder = new Button(0.5);
        addObject(placeholder, 380, 380);
    }
}
