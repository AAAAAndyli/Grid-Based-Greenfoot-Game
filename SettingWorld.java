import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.core.WorldHandler;
import java.awt.event.*;
import greenfoot.gui.*;
import java.util.ArrayList;

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
        super(1080, 720, 1, false); 
        setBackground("images/settingsPlaceholder.png");
        
        Button keybind = new Button("button1.png", 0.5);
        addObject(keybind, 380, 230);
        
        Button audio = new Button("button1.png", 0.5);
        addObject(audio, 380, 305);
        
        Button placeholder = new Button("button1.png", 0.5);
        addObject(placeholder, 380, 380);
        
        ArrayList<Class> classList = new ArrayList<Class>();
        classList.add(Button.class);
        classList.add(SuperTextBox.class);
        
        ScrollingUI s = new ScrollingUI(0, 0, 400, 900, true, classList);
        addObject(s, 800, 350);
        
        int scrollRightEdge = s.getX() + (350 / 2);
        
        Button scrollTest = new Button("button1.png", 0.5, "Jump Keybind");
        addObject(scrollTest, scrollRightEdge - 20, 210);
        
        Button scrollTest2 = new Button("button1.png", 0.5, "Attack Keybind");
        addObject(scrollTest2, scrollRightEdge - 20, 310);
        
        Button scrollTest3 = new Button("button1.png", 0.5, "Parry Keybind");
        addObject(scrollTest3, scrollRightEdge - 20, 410);
        
        setPaintOrder(Button.class, ScrollingUI.class);
    }
    
    public void act(){
        
    }
}
