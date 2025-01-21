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
    
    private boolean transitionComplete = false;
    
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
        
        background = new GreenfootSound("ArSYS/-1.mp3");
        
        
        setBackground("images/menu.png");
        
        //load save file for data
        SaveFile.loadFile();
        
        previousMusicVolume = SaveFile.getInt("musicVolume");
        background.setVolume(20);
        SaveFile.updateVolume(background, "musicVolume");
        
        //make sure to update sound effects volume as shown above
        previousEffectVolume = SaveFile.getInt("effectVolume");
    }
    
    public void act()  
    {
        scrollX--;
        if (!transitionComplete) {
            background.playLoop();  // Only start music once
        }
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
                background.stop();
                transitionComplete = true;
                Greenfoot.setWorld(new ArsysWorld());
            }
        }
        super.act();
    }
    
    public void stopped(){
        background.pause();
    }
    
    public void started(){
        if (!transitionComplete) {
            background.playLoop();
        }
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
