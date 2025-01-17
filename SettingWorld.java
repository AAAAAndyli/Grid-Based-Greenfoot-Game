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
    
        BindButton dashButton = new BindButton("button1.png", 0.5, "Dash Keybind", "dash");
        addObject(dashButton, scrollRightEdge - 20, 510);
        
        BindButton parryButton = new BindButton("button1.png", 0.5, "Parry Keybind", "parry");
        addObject(parryButton, scrollRightEdge - 20, 610);
        
        //movement WASD binds (basic movement)
        BindButton jumpButton = new BindButton("button1.png", 0.5, "Jump Keybind", "jump");
        addObject(jumpButton, scrollRightEdge - 20, 110);
        BindButton downButton = new BindButton("button1.png", 0.5, "Down Keybind", "down");
        addObject(downButton, scrollRightEdge - 20, 210);
        BindButton leftButton = new BindButton("button1.png", 0.5, "Left Keybind", "left");
        addObject(leftButton, scrollRightEdge - 20, 310);
        BindButton rightButton = new BindButton("button1.png", 0.5, "Right Keybind", "right");
        addObject(rightButton, scrollRightEdge - 20, 410);

        ResetButton resetButton = new ResetButton("button1.png", 0.55);
        Label resetLabel = new Label("Reset Binds", 35);
        addObject(resetButton, 150, 650);
        addObject(resetLabel, 150, 650);
        
        Label backLabel = new Label("Back", 40);
        addObject(backLabel, 85, 50);
        WorldButton back = new WorldButton("button1.png", 0.5, world, backLabel);
        addObject(back, 85, 50);
        
        setPaintOrder(Transition.class, Label.class, Button.class, ScrollingUI.class);
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
