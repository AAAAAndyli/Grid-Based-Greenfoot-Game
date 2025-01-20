import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ArSYSStartingWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArSYSStartingWorld extends ScrollingWorld
{
    protected static final GreenfootImage BACKGROUND = new GreenfootImage("Background/ARSYSBackground.png");
    private int trueXBackground = 0;
    protected ScrollingBackground layer1 = new ScrollingBackground(BACKGROUND, 1, trueXBackground);
    protected ScrollingBackground layer2 = new ScrollingBackground(BACKGROUND, 1, trueXBackground + 1080);
    protected int projectilesFired = 0;
    private Transition transition1 = new Transition(false);
    private Transition transition2 = new Transition(true);
    
    GreenfootSound background;
    GreenfootSound clickSound;
    GreenfootSound[] musicList, effectList;
    
    int previousMusicVolume, previousEffectVolume;
    
    /**
     * Constructor for objects of class ArSYSStartingWorld.
     * 
     */
    public ArSYSStartingWorld()
    {    
        super(1080, 720, 1, false); 
        setBackground("Background/ARSYSBackground.png");

        addObject(layer1, trueXBackground, 360);
        addObject(layer2, trueXBackground + 1080, 360);
        addObject(new Mail(), 100, 360);
        addObject(transition1, 540,360);
        
        background = new GreenfootSound("ObWiOp0.mp3");
        
        
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
    }
    
    public void act()
    {
        scrollX--;
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
        if(projectilesFired < 200)
        {
            if(transition1.fadedOnce())
            {
                removeObject(transition1);
            }
            if(Greenfoot.getRandomNumber(10) == 0)
            {
                projectilesFired++;
                int yPos = Greenfoot.getRandomNumber(720);
                addObject(new EProjectile(new Coordinate(0, yPos), 20, 1, "ExplosiveEnemyProjectile"), 1080 - scrollX, yPos);
            }
        }
        else
        {
            addObject(transition2, 540,360);
            if(transition2.fadedOnce())
            {
                Greenfoot.setWorld(new ArsysWorld());
                background.stop();
            }
        }
        super.act();
    }
    
    public void stopped(){
        background.pause();
    }
    
    public void started(){
        background.playLoop();
    }
    
    public void dying()
    {
        addObject(transition2, 540,360);
        if(transition2.fadedOnce())
        {
            Greenfoot.setWorld(new GameOver());
            background.stop();
        }
    }
}
