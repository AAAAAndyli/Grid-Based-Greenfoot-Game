import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class BindButton here.
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
    private boolean added, bindingActive = false;
    private int errorTimer = -1;
    private ArrayList<String> keybinds;
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
        keyLabel = new Label(SaveFile.getString(type), 40);
    }

    public void act(){
        //add textBox when bindButton added to world
        if(!added){
            getWorld().addObject(textBox, getX() - 250, getY());
            getWorld().addObject(keyLabel, getX(), getY());
            added = true;
        }
        if(buttonType != SaveFile.getString(buttonType)){
            keyLabel.setValue(SaveFile.getString(buttonType));
        }
        super.checkClick();   

        if(errorTimer != -1){
            errorTimer++;
        }
        if(errorTimer > 60){
            errorTimer = -1;
            textBox.update(text);
        }
        
        if(Greenfoot.mouseClicked(this) || Greenfoot.mouseClicked(keyLabel)){
            super.clickSound.play();
        }    
        
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
                if(keyBinding.equals(",") || keyBinding.equals("right")){
                    textBox.update("Key not allowed!"); 
                    errorTimer++;
                    return;
                }
                if(!checkDuplicate(keyBinding)){
                    keyLabel.setValue(keyBinding);
                    getWorld().removeObject(bindBox);
                    SaveFile.setInfo(buttonType, keyBinding);
                    bindingActive = false; //disable binding
                }
                else{
                    textBox.update("No duplicates!"); 
                    errorTimer++;
                }
            }
        }
    }
    
    public boolean checkDuplicate(String key){
        for(int i = 0; i<keybindNames.length; i++){
           if(SaveFile.getString(keybindNames[i]).equals(key)){
               return true;
           }
        }
        return false;
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
