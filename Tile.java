import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends Actor
{
    private String type;
    public Tile(String type)
    {
        this.type = type;
        switch(type)
        {
            case "Stair":
                this.setImage("tile
        }
    }
    
    /**
     * Act - do whatever the Tile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        
    }
}
