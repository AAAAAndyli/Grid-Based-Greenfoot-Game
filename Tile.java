import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends ScrollingActor
{
    protected String type;
    protected int rotations;
    protected boolean isButton;
    protected boolean collidable = true; //Can Collide by default
    protected int x, y;
    
    protected MapMaker mapMaker;
    
    public Tile(String type, int rotations, int xPosition, int yPosition, boolean isCollidable)
    {
        this(type, rotations, false, null, xPosition, yPosition, isCollidable);
    }
    public Tile(String type, int rotations, boolean isButton, MapMaker mapMaker)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, false);
    }
    public Tile(String type, int rotations, boolean isButton, MapMaker mapMaker, boolean isCollidable)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, isCollidable);
    }
    public Tile(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, boolean isCollidable)
    {
        this.type = type;
        this.rotations = rotations;
        this.isButton = isButton;
        this.x = xPosition;
        this.y = yPosition;
        this.collidable = isCollidable;
        if(!isButton)
        {
            this.setImage("Tiles/"+type+".png");
            if(type.contains("Background"))
            {
                collidable = false;
            }
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
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
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
            System.out.println(type);
        }
    }
    public String getType()
    {
        return type;
    }
    public boolean getCollidable()
    {
        return collidable;
    }
    public int getGlobalX()
    {
        return x;
    }
    public int getGlobalY()
    {
        return y;
    }
    public int getRotation()
    {
        return rotations;
    }
    public boolean isDiagonal()
    {
        return (type.equals("DiagonalTile"));
    }
    public boolean getButton()
    {
        return isButton;
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y);
    }
}
