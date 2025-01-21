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
    public Tutorial(MenuWorld menu)
    {
        this("tutorial.csv");
        super.setMainMenu(menu);
    }
    
    public Tutorial(String levelName)
    {
        super("Tutorial/" + levelName); 
        WorldOrder.createArSYSList();
        WorldOrder.setIndex(levelName);
        setBackground("black.png");
        TutorialAvatar tutorial = new TutorialAvatar();
        
        addObject(tutorial, 1080, 650);
    }
    
    public MenuWorld getMainMenu(){
        return super.getMainMenu();
    }
    
    public void act(){
        super.act();
    }
    
}
