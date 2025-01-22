import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Subclass of button to bind a key to a specific
 * action in game
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */

public class BindButton extends Button {
    private SuperTextBox textBox;
    private Button bindBox;
    private Label keyLabel;
    private String text, buttonType;
    private String keyDummy, keyBinding;
    //when binding active is true, next key will be the new key for the specific action
    private boolean added, bindingActive = false;
    private int errorTimer = -1;
    private ArrayList<String> keybinds;
    
    //array to check for duplicate binds
    private String[] keybindNames = new String[]{
        "jump", "down", "left", "right", "dash",
        "parry", "rapid", "bomb", "missile", "spread"
    };

    public BindButton(String file, double sizeMulti, String txt, String type){
        super(file, sizeMulti);
        text = txt;
        buttonType = type;
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 180, 0);
        bindBox = new Button("Buttons/pressKeyButton.png", 0.55, this);
        //show what the current keybind for the associated BindButton is
        keyLabel = new Label(SaveFile.getString(type), 40);
    }

    public void act(){
        //add textBox when bindButton added to world
        if(!added){
            getWorld().addObject(textBox, getX() - 250, getY());
            getWorld().addObject(keyLabel, getX(), getY());
            added = true;
        }
        //if current label doesnt match up with savefile info, switch
        if(buttonType != SaveFile.getString(buttonType)){
            keyLabel.setValue(SaveFile.getString(buttonType));
        }
        super.checkClick();   

        //if the user does an invalid keybind
        //display message for 60 acts
        if(errorTimer != -1){
            errorTimer++;
        }
        if(errorTimer > 60){
            errorTimer = -1;
            textBox.update(text);
        }
        
        //play sound when clicking button or label attached to it
        if(Greenfoot.mouseClicked(this) || Greenfoot.mouseClicked(keyLabel)){
            super.clickSound.play();
        }    
        
        //if pressed and not currently looking for bind
        if((checkButton() || checkButton(keyLabel)) && !bindingActive){
            SettingWorld w = (SettingWorld)getWorld();
            w.removeBindBox();
            getWorld().addObject(bindBox, getWorld().getWidth() / 2 - 110, 650);
            //if user pressed key beforehand, this will take that 
            //pressed key -- prevents incorrect binding 
            keyDummy = Greenfoot.getKey(); 
            bindingActive = true;
        }

        //look for key input if bind is active
        if(bindingActive){
            keyBinding = Greenfoot.getKey(); //2nd key getting proper value 
            if(keyBinding != null){
                //invalid keys
                if(keyBinding.equals(",") || keyBinding.equals("right")){
                    textBox.update("Key not allowed!"); 
                    errorTimer++;
                    return;
                } //if a key is valid, update it 
                if(!checkDuplicate(keyBinding)){
                    keyLabel.setValue(keyBinding);
                    getWorld().removeObject(bindBox);
                    SaveFile.setInfo(buttonType, keyBinding);
                    bindingActive = false; //disable binding
                }
                else{ //invalid keys
                    textBox.update("No duplicates!"); 
                    errorTimer++;
                }
            }
        }
    }
    
    /**
     * Method to check if there are duplicate keybinds
     */
    public boolean checkDuplicate(String key){
        for(int i = 0; i<keybindNames.length; i++){
           if(SaveFile.getString(keybindNames[i]).equals(key)){
               return true;
           }
        }
        return false;
    }
    
    /**
     * Method to return button's type (eg, jump bind, dash bind, etc)
     */
    public String getButtonType(){
        return buttonType;
    }
    
    /**
     * Method to return the label
     */
    public Label getKeyLabel(){
        return keyLabel;
    }
    
    /**
     * Method to get the key that activates the button's action
     */
    public String getKeyBinding(){
        return keyBinding;
    }
    
    /**
     * Setter method to change if it is active or not
     */
    public void setBindingActive(boolean active){
        bindingActive = active;
    }
}
