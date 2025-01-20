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
    private boolean isFading = false;
    private int fadeAmount = 2; 
    
    private GreenfootImage image;
    private int count = 0;
    private int width, height;
    private Label selfLabel;
    
    private GreenfootSound clickSound;
    private int musicLevel = 80;
    private int previousVolume, currentVolume;
    private BindButton creator;
    
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
        
        clickSound = new GreenfootSound("click.wav");
        clickSound.setVolume(musicLevel);
        SaveFile.updateVolume(clickSound, "effectVolume");
    }
    
    public Button(String file, double sizeMulti, Label labelReference)
    {
        this(file, sizeMulti);
        selfLabel = labelReference;
    }
    
    public Button(String file, double sizeMulti, boolean isShopItem)
    {
        this(file, sizeMulti);
        isShopIcon = isShopItem;
    }
    
    public Button(String file, double sizeMulti, BindButton actor)
    {
        this(file, sizeMulti);
        creator = actor;
    }
    
    public void act()
    {
        click();
        if (isFading) {
            performFadeOut();
        }
        else if(!isShopIcon)
        {
            checkButton();
            if(selfLabel != null){
                checkButton(selfLabel);    
            }
        }
    }
    
    public void click(){
        if (Greenfoot.mouseClicked(this)) {
            clickSound.play();
        }
        
        currentVolume = (int)(musicLevel * SaveFile.getInt("musicVolume") / 100.0);
        if(previousVolume != currentVolume){
            clickSound.setVolume(currentVolume);
            previousVolume = currentVolume;
        }
    }
    
    /**
     * Method to check when a button has been pressed
     * 
     * @return boolean, button was activated (pressed and let go within button hitbox)
     */
    public boolean checkButton()
    {
        return checkButton(this);
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
    
    public void fadeOut() {
        isFading = true;
    }
    
    public void performFadeOut() {
        int transparency = getImage().getTransparency(); 
        if (transparency > 0) {
            transparency -= fadeAmount; 
            if (transparency < 0)
            {
               transparency = 0; 
            }
            // Updates transparency
            getImage().setTransparency(transparency); 
        } else {
            // When fully transparent, remove the button from the world
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
        }
    }
    
    public BindButton getCreator(){
        return creator;
    }
}