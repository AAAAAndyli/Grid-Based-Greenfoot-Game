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
    private String text;
    private String keyDummy, keyBinding;
    private boolean added, pressed, bindingActive = false;

    public BindButton(String file, double sizeMulti, String txt){
        super(file, sizeMulti);
        text = txt;
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 180);
        bindBox = new SuperTextBox("Press a key for " + text, new Font("Arial", false, false, 20), 190, this);
        keyLabel = new Label("BROOO", 40, this);
    }

    public void act(){
        //add textBox when bindButton added to world
        if(!added){
            getWorld().addObject(textBox, getX() - 250, getY());
            getWorld().addObject(keyLabel, getX(), getY());
            added = true;
        }

        //check if button is pressed
        if(Greenfoot.isKeyDown("a")){
            System.out.println(Greenfoot.mouseClicked(keyLabel));
        }
        pressed = (checkButton() || checkButton(keyLabel));
        
        //if button clicked and not currently active
        if(pressed && !bindingActive){
            SettingWorld w = (SettingWorld)getWorld();
            w.removeBindBox();
            getWorld().addObject(bindBox, getWorld().getWidth() / 2, getWorld().getHeight() - 50);
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
                bindingActive = false; //disable binding
            }
        }
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
