import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class buttons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends UI
{
    private boolean isPressed = false;
    private GreenfootImage image = new GreenfootImage("button1.png");
    private int count = 0;
    /**
     * Act - do whatever the buttons wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Button()
    {
        setImage(image);
    }
    
    public void act()
    {
        checkButton();
    }
    
    public void checkButton()
    {
        MouseInfo m = Greenfoot.getMouseInfo();
        if(Greenfoot.mousePressed(this))
        {
            isPressed = true;
            count++;
            System.out.println(count);
        }
    }
    
    public int buyProduct()
    {
        if(isPressed)
        {
            return 0;
        }
        return 0;
    }
}
