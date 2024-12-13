import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends ScrollingActor
{
    private String type;
    private int rotations;
    private boolean isButton;
    
    private int x, y;
    
    private MapMaker mapMaker;
    
    public Tile(String type, int rotations, int xPosition, int yPosition)
    {
        this(type, rotations, false, null, xPosition, yPosition);
    }
    public Tile(String type, int rotations, boolean isButton, MapMaker mapMaker)
    {
        this(type, rotations, isButton, mapMaker, 0, 0);
    }
    public Tile(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition)
    {
        this.type = type;
        this.rotations = rotations;
        this.isButton = isButton;
        this.x = xPosition;
        this.y = yPosition;
        if(!isButton)
        {
            this.setImage("Tiles/"+type+".png");
            getImage().scale(50,50);
            getImage().rotate(rotations*90);
        }
        else
        {
            this.setImage("Tiles/"+type+".png");
            getImage().scale(50,50);
            getImage().rotate(rotations*90);
            this.mapMaker = mapMaker;
        }
    }
    public void addedToWorld()
    {
        //super.addedToWorld();
    }
    
    /**
     * Act - do whatever the Tile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(isButton)
        {
            setType();
        }
        else
        {
            super.act();
        }
    }
    public void setType()
    {
        if(Greenfoot.mouseClicked(this))
        {
            mapMaker.setType(type);
        }
    }
    public boolean getButton()
    {
        return isButton;
    }
    public String getString()
    {
        return(type + "," + rotations + ","  + x + "," + y);
    }
}
