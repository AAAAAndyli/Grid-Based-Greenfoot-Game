import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Cursor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cursor extends SuperSmoothMover
{
    private GreenfootImage image = new GreenfootImage("placeHolderCursor.png");
    /**
     * Act - do whatever the Cursor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Cursor()
    {
        setImage(image);
    }
    
    public void act()
    {
        trackMouse();
    }
    
    public void trackMouse()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null)
        {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            setLocation(mouseX, mouseY);
        }
    }
}
