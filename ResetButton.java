import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class ResetButton here.
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class ResetButton extends Button
{
    private boolean isPressed = false;
    private Label resetLabel;
    
    public ResetButton(String file, double sizeMulti){
        super(file, sizeMulti);
    }
    
    /**
     * Act - do whatever the ResetButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        super.checkClick();
        if(Greenfoot.mouseClicked(this)){
            super.clickSound.play();
        }
        isPressed = checkButton();
        
        if(isPressed){
            resetSettings();
        }
    }
    
    public void resetSettings(){
        SettingWorld w = (SettingWorld)getWorld();
        ArrayList<BindButton> binds = (ArrayList<BindButton>)w.getObjects(BindButton.class);
        SaveFile.loadFile("saveFile/defaultSaveFile.csv");
        for(BindButton b : binds){
            b.getKeyLabel().setValue(SaveFile.getString(b.getButtonType()));
        }

    }
}
