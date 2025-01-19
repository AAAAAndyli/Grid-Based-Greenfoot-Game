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
    //volume level of music
    private int musicLevel = 50;
    //to compare previous vs current volume level
    private int previousVolume, currentVolume;
    
    /**
     * Constructor for objects of class SettingWorld.
     * 
     */
    public SettingWorld(MenuWorld world)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1, false); 
        background = new GreenfootSound("Settings.mp3");
        background.setVolume(musicLevel);
        
        setBackground("images/settings.png");
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
        
        Button funImage = new Button("settingsScream.png",.4,true);
        addObject(funImage,300,350);
        
        Slider musicVolume = new Slider(1000, SaveFile.getInt("musicVolume") * 10, null, 350, 20, 350, "musicVolume", "Music Volume");
        addObject(musicVolume, scroll.getX() + 40, 710);
        
        Slider effectVolume = new Slider(1000, SaveFile.getInt("effectVolume") * 10, null, 350, 20, 350, "effectVolume", "SFX Volume");
        addObject(effectVolume, scroll.getX() + 40, 810);
        
        //create binary strings as decoration
        for(int i = 0; i < 14; i++){   
            BinaryString binary = new BinaryString();
            int ySpawn = binary.getDirection() == 1 ? -100 - Greenfoot.getRandomNumber(100) : Greenfoot.getRandomNumber(100) + 100 + getHeight();
            addObject(binary, Greenfoot.getRandomNumber(800) + 100, ySpawn);
        }
        
        setPaintOrder(Transition.class, Slider.class, Label.class, Button.class, ScrollingUI.class, SuperTextBox.class, BinaryString.class);
    }
    
    public void act(){
        background.playLoop();
        
        currentVolume = (int)(musicLevel * SaveFile.getInt("musicVolume") / 100.0);
        if(previousVolume != currentVolume){
            background.setVolume(currentVolume);
            previousVolume = currentVolume;
        }
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
