import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;

/**
 * Write a description of class BindButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BindButton extends Button
{
    private SuperTextBox textBox, bindBox;
    private String text;
    private String key;
    private boolean added, pressed = false;
    
    public BindButton(String file, double sizeMulti, String txt)
    {
        super(file, sizeMulti);
        text = txt;
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 180);
        bindBox = new SuperTextBox("Press" + text, new Font("Arial", false, false, 20), 190);
    }
    
    /**
     * Act - do whatever the BindButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //add corresponding textBox once the button exists
        if(!added && textBox != null){
            getWorld().addObject(textBox, getX() - 250, getY());
            added = true;
        }
        pressed = checkButton();
        
        if(pressed){
            System.out.println(Arrays.toString(bindBox.getText()));
            getWorld().addObject(bindBox, getWorld().getWidth() / 2, getWorld().getHeight() - 50);
        }
        key = Greenfoot.getKey();
        if(Greenfoot.isKeyDown("a")){
            System.out.println(bindBox.getWorld());
        }
        if(bindBox.getWorld() != null && key != null){
            System.out.println(111);
            bindBox.removeSelf();
        }
    }
}
