import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootSound;
import java.util.ArrayList;

/**
 * Write a description of class MenuWorld here.
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class MenuWorld extends World
{
    GreenfootSound background;
    GreenfootSound clickSound;
    
    GreenfootSound[] musicList, effectList;
    
    int previousMusicVolume, previousEffectVolume;
    /**
     * Constructor for objects of class MenuWorld.
     * 
     */
    public MenuWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        background = new GreenfootSound("Opening.mp3");
        clickSound = new GreenfootSound("click.wav");
        
        
        setBackground("images/menu.png");
        
        //load save file for data
        SaveFile.loadFile();
        
        //update bgm volume
        previousMusicVolume = SaveFile.getInt("musicVolume");
        // ** IMPORTANT **
        //make sure to set a volume value for ALL sounds, otherwise it defaults to 0 even if sound plays
        background.setVolume(20); 
        //make sure to update the volume with values from savefile!
        SaveFile.updateVolume(background, "musicVolume");
        
        //make sure to update sound effects volume as shown above
        previousEffectVolume = SaveFile.getInt("effectVolume");
        
        Label shopLabel = new Label("Shop", 30);
        addObject(shopLabel, 900, 600);
        
        WorldButton play = new WorldButton("Buttons/playButton.png", 1.1, (World)new TutorialWorld());
        addObject(play, 900, 300);
        WorldButton settings = new WorldButton("Buttons/settingsButton.png", 1.1, (World)new SettingWorld(this));
        addObject(settings, 900, 450);
        //temporary
        WorldButton shop = new WorldButton("Buttons/button1.png", 1.1, (World)new Shop(this), shopLabel);
        addObject(shop, 900, 600);
        
        setPaintOrder(Transition.class, Label.class, Button.class);
        
    }
    
    public void act(){
        if(previousMusicVolume != SaveFile.getInt("musicVolume")){
            //update the list with each new music
            musicList = new GreenfootSound[]
            {
                background
            };
            SaveFile.updateVolume(musicList, "musicVolume");
            previousMusicVolume = SaveFile.getInt("musicVolume");
        }
        if(previousEffectVolume != SaveFile.getInt("effectVolume")){
            //update the list with each new effect
            effectList = new GreenfootSound[]
            {
                clickSound
            };
            //UNCOMMENT WHEN EFFECTS ADDED
            //SaveFile.updateVolume(effectList, "effectVolume");
            //previousEffectVolume = SaveFile.getInt("musicVolume");
        }
        background.playLoop();
    }
    
    public void stopped(){
        background.pause();
    }
    
    public void started(){
        background.playLoop();
    }
}
