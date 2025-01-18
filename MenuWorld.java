import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootSound;

/**
 * Write a description of class MenuWorld here.
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class MenuWorld extends World
{
    Label playLabel = new Label("Play", 40);
    GreenfootSound background;
    /**
     * Constructor for objects of class MenuWorld.
     * 
     */
    public MenuWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        setBackground("images/menuPlaceholder.png");
        
        
        //load save file for data
        SaveFile.loadFile();
        
        Label playLabel = new Label("Play", 40);
        addObject(playLabel, 900, 200);
        Label settingsLabel = new Label("Settings", 40);
        addObject(settingsLabel, 900, 400);
        Label shopLabel = new Label("Shop", 40);
        addObject(shopLabel, 900, 600);
        
        WorldButton play = new WorldButton("button1.png", 1.1, (World)new TutorialWorld(), playLabel);
        addObject(play, 900, 200);
        WorldButton settings = new WorldButton("button1.png", 1.1, (World)new SettingWorld(this), settingsLabel);
        addObject(settings, 900, 400);
        //temporary
        WorldButton shop = new WorldButton("button1.png", 1.1, (World)new Shop(this), shopLabel);
        addObject(shop, 900, 600);
        
        setPaintOrder(Transition.class, Label.class, Button.class);
        
    }
    
    
}
