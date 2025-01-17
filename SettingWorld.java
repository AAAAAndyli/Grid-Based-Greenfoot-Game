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
    private Label test, test2, test3;
    
    /**
     * Constructor for objects of class SettingWorld.
     * 
     */
    public SettingWorld(MenuWorld world)
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
        classList.add(Label.class);
        
        ScrollingUI s = new ScrollingUI(0, 0, 400, 1500, true, classList);
        addObject(s, 800, 350);
        
        int scrollRightEdge = s.getX() + (350 / 2);
        
        test = new Label("Default", 40);
        addObject(test, scrollRightEdge - 20, 210);
        
        test2 = new Label("Default", 40);
        addObject(test2, scrollRightEdge - 20, 310);
        
        test3 = new Label("Default", 40);
        addObject(test3, scrollRightEdge - 20, 410);
        
        BindButton scrollTest = new BindButton("button1.png", 0.5, "Jump Keybind", "jump", test);
        addObject(scrollTest, scrollRightEdge - 20, 210);
    
        BindButton scrollTest2 = new BindButton("button1.png", 0.5, "Dash Keybind", "dash", test2);
        addObject(scrollTest2, scrollRightEdge - 20, 310);
        
        BindButton scrollTest3 = new BindButton("button1.png", 0.5, "Parry Keybind", "parry", test3);
        addObject(scrollTest3, scrollRightEdge - 20, 410);
        
        Label backLabel = new Label("Back", 40);
        addObject(backLabel, 85, 50);
        WorldButton back = new WorldButton("button1.png", 0.5, world, backLabel);
        addObject(back, 85, 50);
        
        setPaintOrder(Label.class, Button.class, ScrollingUI.class);
    }
    
    public void act(){

    }
    
    public void removeBindBox(){
        for(SuperTextBox b : (ArrayList<SuperTextBox>)getObjectsAt(getWidth() / 2, getHeight() - 50, SuperTextBox.class)){
            //if a bindingButton was prev activated but a new one
            //has been activated, turn previous one off
            b.getCreator().setBindingActive(false);
            //remove prev text box
            removeObject(b);
        }
    }
}
