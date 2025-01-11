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
    private int width, height;
    /**
     * Act - do whatever the buttons wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Button()
    {
        this(1);
    }
    
    public Button(double sizeMulti)
    {
        setImage(image);
        width = (int)(getImage().getWidth() * sizeMulti);
        height = (int)(getImage().getHeight() * sizeMulti);
        getImage().scale(width, height);
    }
    
    public void act()
    {
        checkButton();
        
    }
    
    public void checkButton()
    {
        if(Greenfoot.mousePressed(this)) //when pressed
        {
            isPressed = true;

        } //if press and let go ON BUTTON (activate button)
        else if(Greenfoot.mouseClicked(this) && isPressed){
            isPressed = false;

        } //if press and let go OFF BUTTON (cancel button)
        else if(Greenfoot.mouseClicked(null) && isPressed){
            isPressed = false;

        }
        if(isPressed){
            getImage().scale((int)(0.85 * width), (int)(0.85 * height));
        }
        else{
            getImage().scale(width, height);
        }
    }
    
    public boolean getIsPressed()
    {
        return isPressed;
    }
}
