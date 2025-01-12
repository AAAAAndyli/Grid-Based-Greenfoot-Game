import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.core.WorldHandler;
import java.awt.event.*;
import greenfoot.gui.*;

/**
 * Write a description of class SettingWorld here.
 * 
 * @author Darren T.
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
        
        Button test = new Button(0.8);
        addObject(test, 800, 130);
        
        Button test2 = new Button(0.8);
        addObject(test2, 800, 620);
        
        Button test3 = new Button(0.5);
        addObject(test3, 800, 310);
          
        ScrollingUI s = new ScrollingUI(0, 0, 200, 450);
        addObject(s, 800, 350);
        
        setPaintOrder(Button.class, ScrollingUI.class);
    }
    
    public void act(){
        
    }
}
