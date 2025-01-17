import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class BindButton here.
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */

public class BindButton extends Button {
    private SuperTextBox textBox, bindBox;
    private Label keyLabel;
    private String text, buttonType;
    private String keyDummy, keyBinding;
    private boolean added, pressed, bindingActive = false;

    public BindButton(String file, double sizeMulti, String txt, String type){
        super(file, sizeMulti);
        text = txt;
        buttonType = type;
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 180);
        bindBox = new SuperTextBox("Press a key", new Font("Arial", false, false, 20), 190, this);
        
        keyLabel = new Label(SaveFile.getKey(type), 40);
    }
    
    /**
    public BindButton(String file, double sizeMulti, String txt, String type, Label labelReference){
        this(file, sizeMulti, txt, type);
        keyLabel = labelReference;
    }
    */

    public void act(){
        //add textBox when bindButton added to world
        if(!added){
            getWorld().addObject(textBox, getX() - 250, getY());
            getWorld().addObject(keyLabel, getX(), getY());
            added = true;
        }

        //check if button is pressed
        pressed = (checkButton() || checkButton(keyLabel));
        
        //if button clicked and not currently active
        if(pressed && !bindingActive){
            SettingWorld w = (SettingWorld)getWorld();
            w.removeBindBox();
            getWorld().addObject(bindBox, getWorld().getWidth() / 2 - 50, getWorld().getHeight() - 50);
            //if user pressed key beforehand, this will take that 
            //pressed key -- prevents incorrect binding 
            keyDummy = Greenfoot.getKey(); 
            bindingActive = true;
        }

        //look for key input if bind is active
        if(bindingActive){
            keyBinding = Greenfoot.getKey(); //2nd key getting proper value 
            if(keyBinding != null){
                keyLabel.setValue(keyBinding);
                getWorld().removeObject(bindBox);
                SaveFile.setKeyBind(buttonType, keyBinding);
                bindingActive = false; //disable binding
            }
        }
    }
    
    public String getButtonType(){
        return buttonType;
    }
    
    public Label getKeyLabel(){
        return keyLabel;
    }
    
    public String getKeyBinding(){
        return keyBinding;
    }
    
    public void setBindingActive(boolean active){
        bindingActive = active;
    }
}
