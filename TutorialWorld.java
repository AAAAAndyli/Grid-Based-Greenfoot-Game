import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tutorial here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorialWorld extends LevelWorld
{
    private WorldButton pause;
    
    private SuperTextBox textbox;
    private SuperTextBox textbox1;
    
    GreenfootSound tutorialMusic;
    
    GreenfootSound[] musicList, effectList;
    
    int previousMusicVolume, previousEffectVolume;
    /**
     * Constructor for objects of class Tutorial.
     * 
     */
    public TutorialWorld()
    {
        this("tutorial.csv");
    }
    
    public TutorialWorld(String levelName)
    {
        super("Tutorial/" + levelName); 
        WorldOrder.createArSYSList();
        WorldOrder.setIndex(levelName);
        setBackground("black.png");
        TutorialAvatar tutorial = new TutorialAvatar();
        
        addObject(tutorial, 1080, 650);
        
        //make sure to update the volume with values from savefile!
        previousMusicVolume = SaveFile.getInt("musicVolume");
        //make sure to update sound effects volume as shown above
        previousEffectVolume = SaveFile.getInt("effectVolume");
    
        tutorialMusic = new GreenfootSound("goofyAh.mp3");
        tutorialMusic.setVolume(60);
        SaveFile.updateVolume(tutorialMusic, "musicVolume");
    }
    
    public void stopped(){
        tutorialMusic.pause();
    }
    
    public void started(){
        tutorialMusic.playLoop();
    }
    
    public void act(){
        if(previousMusicVolume != SaveFile.getInt("musicVolume")){
            //update the list with each new music
            musicList = new GreenfootSound[]
            {
                tutorialMusic,
            };
            SaveFile.updateVolume(musicList, "musicVolume");
            previousMusicVolume = SaveFile.getInt("musicVolume");
        }
        if(previousEffectVolume != SaveFile.getInt("effectVolume")){
            //update the list with each new effect
            effectList = new GreenfootSound[]
            {
                
            };
            //UNCOMMENT WHEN EFFECTS ADDED
            //SaveFile.updateVolume(effectList, "effectVolume");
            //previousEffectVolume = SaveFile.getInt("musicVolume");
        }
        tutorialMusic.playLoop();
    }
    
}
