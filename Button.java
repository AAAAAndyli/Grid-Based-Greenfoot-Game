import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Button superclass which performs an action when clicked
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
    
    protected GreenfootSound clickSound;
    private int musicLevel = 90;
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
        
        //click sfx for button
        clickSound = new GreenfootSound("click.wav");
        clickSound.setVolume(musicLevel);
        SaveFile.updateVolume(clickSound, "effectVolume");
    }
    
    public Button(String file, double sizeMulti, Label labelReference)
    {
        this(file, sizeMulti);
        //if a button has a label attached to it
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
        //play sfx when clicked
        if (Greenfoot.mouseClicked(this)) {
            clickSound.play();
        }
        checkClick();
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
    
    /**
     * Method to check if the current volume is aligned with savefile volume
     */
    public void checkClick(){    
        currentVolume = (int)(musicLevel * SaveFile.getInt("effectVolume") / 100.0);
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
        if(isPressed || isPressedActor){
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
            System.out.println(11);
        } //if press and let go ON BUTTON (activate button)
        else if(Greenfoot.mouseClicked(actor) && isPressedActor){
            isPressedActor = false;
            System.out.println(12);
            //has been let go and activated
            return true;
        } //if press and let go OFF BUTTON (cancel button)
        else if(Greenfoot.mouseClicked(null) && isPressedActor){
            isPressedActor = false;
            System.out.println(13);
            //cancelled button
            return false;
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