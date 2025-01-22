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
    
    
    
    private String[] tutorialMessages = {
        "Welcome to the tutorial. \n Right arrow to continue",
        "Use WASD or set them in the Settings Menu \n to move. Right arrow to continue",
        "Use E to parry, changeable in Settings. \n Right arrow to continue",
        "Defeat monsters to collect cash for \n upgrades. Right arrow to continue",
        "To switch weapons, press 1/2/3/4. Right \n arrow to continue",
        "Hotkey 1 is an automatic, 2 is \n a projectile, 3 is a rocket launcher \n and 4 is a shotgun Right arrow to continue",
        "Reach as far as possible while not \n dying. Right arrow to continue",
        "You can trigger the monster spawns to \n practice. Right arrow to continue",
        "Some places have firewalls. Find keys \n to access past it Right arrow to continue",
        "Find the portal and press space to move \n into the next level.. Good luck"
    };
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
        WorldOrder.createArrayList();
        WorldOrder.setIndex(levelName);
        setBackground("black.png");
        TutorialAvatar tutorial = new TutorialAvatar(tutorialMessages);
        
        addObject(tutorial, 1080, 650);
    }
    
    public MenuWorld getMainMenu(){
        return super.getMainMenu();
    }
    
    public void act(){
        super.act();
    }
    
}
