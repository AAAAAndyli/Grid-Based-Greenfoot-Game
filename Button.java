import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class buttons here.
 * 
 * @author Darren T and Justin Y
 * @version (a version number or a date)
 */
public class Button extends UI
{
    private boolean isPressed = false;
    private GreenfootImage image = new GreenfootImage("button1.png");
    private int count = 0;
    private int width, height;
    private boolean added = false;
    
    private World destination;
    private SuperTextBox textBox;
    
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
    
    public Button(double sizeMulti, World dest)
    {
        this(sizeMulti);
        destination = dest;
    }
    
    public Button(double sizeMulti, String text)
    {
        this(sizeMulti);
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 100);
        
    }
    
    public void act()
    {
        if(added == false && textBox != null){
            getWorld().addObject(textBox, getX(), getY());
            added = true;
        }
        checkButton();
        
    }
    
    /**
     * Method to check when a button has been pressed
     * 
     * @return boolean, button was activated (pressed and let go within button hitbox)
     */
    public boolean checkButton()
    {
        if(Greenfoot.mousePressed(this)) //when pressed
        {
            isPressed = true;
        } //if press and let go ON BUTTON (activate button)
        else if(Greenfoot.mouseClicked(this) && isPressed){
            isPressed = false;
            //for world changing buttons
            if(destination != null){
                activateButton(destination);
            }
            //has been let go and activated
            return true;
        } //if press and let go OFF BUTTON (cancel button)
        else if(Greenfoot.mouseClicked(null) && isPressed){
            isPressed = false;
            //cancelled button
            return false;
        }
        //visual feedback of button being pressed
        if(isPressed){
            getImage().scale((int)(0.85 * width), (int)(0.85 * height));
        }
        else{
            getImage().scale(width, height);
        }
        //any unusual edge cases like issues with mouse
        return false;
    }
    
    public void activateButton(World w){
        Greenfoot.setWorld(w);
    }
}
