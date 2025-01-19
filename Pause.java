import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pause here.
 * 
 * @author Allan L.
 * @version (a version number or a date)
 */
public class Pause extends SuperSmoothMover
{
    /**
     * Act - do whatever the Pause wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    GreenfootImage image = new GreenfootImage("images/Pause.png");
    
    public Pause(){
        image.scale(50, 50);
        setImage(image);
    }
    
    public void act()
    {
        //Switches to the MenuWorld if clicked
        if(Greenfoot.mouseClicked(this)){
            Greenfoot.setWorld(new SettingWorld(new MenuWorld()));
        }
    }
}
