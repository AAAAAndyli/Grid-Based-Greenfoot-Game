import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WorldButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldButton extends Button
{
    private World destination;
    boolean pressed;
    
    public WorldButton(String file, double sizeMulti, World dest)
    {
        super(file, sizeMulti);
        destination = dest;
    }
    
    /**
     * Act - do whatever the WorldButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        pressed = checkButton();
        if(pressed){
            activateButton(destination);
        }
    }
    
    public void activateButton(World w){
        Greenfoot.setWorld(w);
    }
}
