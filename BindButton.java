import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BindButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BindButton extends Button
{
    private SuperTextBox textBox;
    private String text;
    private boolean added = false;
    
    public BindButton(String file, double sizeMulti, String txt)
    {
        super(file, sizeMulti);
        text = txt;
        textBox = new SuperTextBox(text, new Font("Arial", false, false, 20), 180);
    }
    
    /**
     * Act - do whatever the BindButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!added && textBox != null){
            getWorld().addObject(textBox, getX() - 250, getY());
            added = true;
        }
        super.act();
    }
}
