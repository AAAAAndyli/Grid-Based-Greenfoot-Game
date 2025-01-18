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
    GreenfootSound background;
    /**
     * Constructor for objects of class SettingWorld.
     * 
     */
    public SettingWorld(MenuWorld world)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1, false); 
        background = new GreenfootSound("Settings.mp3");
        
        setBackground("images/settings.png");
        Button keybind = new Button("Buttons/keybindButton.png", 0.5);
        addObject(keybind, 280, 230);
        Button audio = new Button("Buttons/audioButton.png", 0.5);
        addObject(audio, 280, 335);
        ArrayList<Class> classList = new ArrayList<Class>();
        classList.add(Button.class);
        classList.add(SuperTextBox.class);
        classList.add(Label.class);
        classList.add(Slider.class);
        
        ScrollingUI scroll = new ScrollingUI(0, 0, 500, 1500, true, classList);
        addObject(scroll, 800, 350);
        
        int scrollRightEdge = scroll.getX() + (350 / 2);
        
        BindButton dashButton = new BindButton("Buttons/keyButton.png", 0.5, "Dash Keybind", "dash");
        addObject(dashButton, scrollRightEdge - 20, 510);
        
        BindButton parryButton = new BindButton("Buttons/keyButton.png", 0.5, "Parry Keybind", "parry");
        addObject(parryButton, scrollRightEdge - 20, 610);
        
        //movement WASD binds (basic movement)
        BindButton jumpButton = new BindButton("Buttons/keyButton.png", 0.5, "Jump Keybind", "jump");
        addObject(jumpButton, scrollRightEdge - 20, 110);
        BindButton downButton = new BindButton("Buttons/keyButton.png", 0.5, "Down Keybind", "down");
        addObject(downButton, scrollRightEdge - 20, 210);
        BindButton leftButton = new BindButton("Buttons/keyButton.png", 0.5, "Left Keybind", "left");
        addObject(leftButton, scrollRightEdge - 20, 310);
        BindButton rightButton = new BindButton("Buttons/keyButton.png", 0.5, "Right Keybind", "right");
        addObject(rightButton, scrollRightEdge - 20, 410);
        
        ResetButton resetButton = new ResetButton("Buttons/resetButton.png", 0.55);
        addObject(resetButton, 150, 650);
        
        WorldButton back = new WorldButton("Buttons/backButton.png", 0.5, world);
        addObject(back, 85, 50);
        
        Slider test = new Slider(2000, 1000, scroll, 300, 20, 350);
        addObject(test, 0, 0);
        
        setPaintOrder(Transition.class, Slider.class, Label.class, Button.class, ScrollingUI.class);
    }
    
    public void act(){
        background.playLoop();
    }
    
    
    public void stopped(){
        background.pause();
    }
    public void started(){
        background.playLoop();
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
