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
        
        ArrayList<Class> classList = new ArrayList<Class>();
        classList.add(Button.class);
        classList.add(SuperTextBox.class);
        classList.add(Label.class);
        classList.add(Slider.class);
        
        ScrollingUI scroll = new ScrollingUI(0, 0, 400, 1500, true, classList);
        addObject(scroll, 800, 350);
        
        int bindRightEdge = scroll.getX() + 175;
        
        //combat buttons
        BindButton dashButton = new BindButton("button1.png", 0.5, "Dash Keybind", "dash");
        addObject(dashButton, bindRightEdge - 20, 510);
        BindButton parryButton = new BindButton("button1.png", 0.5, "Parry Keybind", "parry");
        addObject(parryButton, bindRightEdge - 20, 610);
        //movement WASD binds (basic movement)
        BindButton jumpButton = new BindButton("button1.png", 0.5, "Jump Keybind", "jump");
        addObject(jumpButton, bindRightEdge - 20, 110);
        BindButton downButton = new BindButton("button1.png", 0.5, "Down Keybind", "down");
        addObject(downButton, bindRightEdge - 20, 210);
        BindButton leftButton = new BindButton("button1.png", 0.5, "Left Keybind", "left");
        addObject(leftButton, bindRightEdge - 20, 310);
        BindButton rightButton = new BindButton("button1.png", 0.5, "Right Keybind", "right");
        addObject(rightButton, bindRightEdge - 20, 410);
        
        Slider test = new Slider(2000, 1000, scroll, 300, 20, 350);
        addObject(test, 0, 0);
        
        Label resetLabel = new Label("Reset Binds", 35);
        ResetButton resetButton = new ResetButton("button1.png", 0.55, resetLabel);
        addObject(resetButton, 150, 650);
        addObject(resetLabel, 150, 650);
        
        Label backLabel = new Label("Back", 40);
        addObject(backLabel, 85, 50);
        WorldButton backButton = new WorldButton("button1.png", 0.5, world, backLabel);
        addObject(backButton, 85, 50);
        
        setPaintOrder(Transition.class, Slider.class, Label.class, Button.class, ScrollingUI.class);
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
