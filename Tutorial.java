import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tutorial here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tutorial extends LevelWorld
{
    private WorldButton pause;
    
    private SuperTextBox textbox;
    private SuperTextBox textbox1;
    /**
     * Constructor for objects of class Tutorial.
     * 
     */
    public Tutorial()
    {
        this("tutorial.csv");
    }
    
    public Tutorial(String levelName)
    {
        super("Tutorial/" + levelName); 
        WorldOrder.createArrayList();
        WorldOrder.setIndex(levelName);
        setBackground("black.png");
        TutorialAvatar tutorial = new TutorialAvatar();
        
        //pause = new WorldButton("Pause.png", 0.05, new SettingWorld(this, TutorialWorld.class));
        //addObject(pause, 1040, 40);
        
        addObject(tutorial, 1080, 650);
    }
    
    public void act(){
        super.act();
    }
    
}
