import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class buttons here.
 * 
 * @author Darren T and Justin Y
 * @version (a version number or a date)
 */
public class Button extends UI
{
    private boolean isPressed = false;
    private boolean isPressedActor = false;
    private boolean isShopIcon, isReset = false;
    private GreenfootImage image;
    private int count = 0;
    private int width, height;
    private Label selfLabel;
    private ArrayList<Label> bindLabels;
    
    /**
     * Act - do whatever the buttons wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Button(String file)
    {
        this(file, 1);
    }
    
    public Button(String file, double sizeMulti)
    {
        image = new GreenfootImage("images/" + file);
        setImage(image);
        width = (int)(getImage().getWidth() * sizeMulti);
        height = (int)(getImage().getHeight() * sizeMulti);
        getImage().scale(width, height);
    }
    
    public Button(String file, double sizeMulti, boolean isShopItem, int num)
    {
        this(file, sizeMulti);
        isShopIcon = isShopItem;
    }
    
    public void act()
    {
        if(!isShopIcon)
        {
            checkButton();
        }
        if(isReset){
            if(checkButton()){
                SaveFile.loadFile("saveFile/defaultSaveFile.csv");
                
            }
        }
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
    
    /**
     * Method to check when a button has been pressed
     * 
     * @param actor, the actor to check for instead of button (usually Label)
     * @return boolean, button was activated (pressed and let go within button hitbox)
     */
    public boolean checkButton(Actor actor)
    {
        if(Greenfoot.mousePressed(actor)) //when pressed
        {
            isPressedActor = true;
        } //if press and let go ON BUTTON (activate button)
        else if(Greenfoot.mouseClicked(actor) && isPressedActor){
            isPressedActor = false;
            //has been let go and activated
            return true;
        } //if press and let go OFF BUTTON (cancel button)
        else if(Greenfoot.mouseClicked(null) && isPressedActor){
            isPressedActor = false;
            //cancelled button
            return false;
        }
        //visual feedback of button being pressed
        if(isPressedActor){
            getImage().scale((int)(0.85 * width), (int)(0.85 * height));
        }
        else{
            getImage().scale(width, height);
        }
        //any unusual edge cases like issues with mouse
        return false;
    }
}
