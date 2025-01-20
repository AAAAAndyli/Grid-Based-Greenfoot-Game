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
    private boolean added, pressedButton, pressedLabel, bindingActive = false;

    public BindButton(String file, double sizeMulti, String txt, String type){
        super(file, sizeMulti);
        text = txt;
        buttonType = type;
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 180);
        bindBox = new Button("Buttons/pressKeyButton.png", 0.55, this);
        
        keyLabel = new Label(SaveFile.getString(type), 40);
    }
    
    /**
    public BindButton(String file, double sizeMulti, String txt, String type, Label labelReference){
        this(file, sizeMulti, txt, type);
        keyLabel = labelReference;
    }
    */

    public void act(){
        super.checkClick();   
        if(Greenfoot.mouseClicked(this) || Greenfoot.mouseClicked(keyLabel)){
            super.clickSound.play();
        }    
        
        //add textBox when bindButton added to world
        if(!added){
            getWorld().addObject(textBox, getX() - 250, getY());
            getWorld().addObject(keyLabel, getX(), getY());
            added = true;
        }
        
        boolean test = checkButton();
        boolean test2 = checkButton(keyLabel);
        
        if(Greenfoot.isKeyDown("a")){
            System.out.println("test: " + test + ", test2: " + test2);
            System.out.println("bindingActive: " + bindingActive);
        }
        
        if((test || test2) && !bindingActive){
            SettingWorld w = (SettingWorld)getWorld();
            w.removeBindBox();
            System.out.println(123);
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
                keyLabel.setValue(keyBinding);
                getWorld().removeObject(bindBox);
                SaveFile.setInfo(buttonType, keyBinding);
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
