import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collection;

/**
 * Write a description of class MapMakerWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MapMakerWorld extends ScrollingWorld
{
    ArrayList<ArrayList<String>> world = new ArrayList<ArrayList<String>>();
    private MapMaker mapMaker = new MapMaker(this);
    
    /**
     * Constructor for objects of class MapMakerWorld.
     */
    public MapMakerWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1, false); 
        addObject(mapMaker, 400, 550);
        setPaintOrder(Tile.class, MapMaker.class, TileSelector.class);
    }
    public void act()
    {
        super.act();
        editMap();
    }
    public void setWorld(ArrayList<ArrayList<String>> world)
    {
        this.world = world;
    }
    public void editMap()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null)
        {
            int x = mouse.getX();
            int y = mouse.getY();
            int xMapPosition = (int)(50 * Math.round((double)(x) / 50) - 50 * Math.round((double)scrollX/50));
            int yMapPosition = (int)(50 * Math.round((double)(y) / 50) - 50 * Math.round((double)scrollY/50));
            x = (int)(50 * Math.round((double)(x) / 50));
            y = (int)(50 * Math.round((double)(y) / 50));
            if((Greenfoot.mouseClicked(this) || getObjects(TileSelector.class).size() != 0 && Greenfoot.mouseClicked(getObjects(TileSelector.class).get(0))) && mapMaker.getType() != null)
            {
                placeTile(xMapPosition, yMapPosition, mapMaker.getType());
            }
            if(mouse != null)
            {
                if (mouse.getButton() == 3)
                {
                    //for(
                }
            }
            if (getObjects(TileSelector.class).isEmpty()) 
            {
                addObject(new TileSelector(), x, y);
            } else 
            {
                removeObjects(getObjects(TileSelector.class));
                addObject(new TileSelector(), x, y);
            }
        }
    }

    public void placeTile(int x, int y, String type)
    {
        Tile tile = new Tile(type, mapMaker.getRotations());
        addObject(tile, x, y);
    }
    public void replaceTile()
    {
        
    }
}
