import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends World
{
    private int actTimer = 0;
    private Transition transision = new Transition(true);
    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameOver()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        setBackground("GameOver.png");
    }
    public void act()
    {
        actTimer++;
        if(actTimer == 300)
        {
            addObject(transision, 540, 360);
        }
        if(transision.fadedOnce())
        {
            SaveFile.setInfo("deaths", SaveFile.getInt("deaths") + 1);
            Greenfoot.setWorld(new MenuWorld());
        }
    }
}
