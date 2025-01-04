
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collection;

/**
 * Write a description of class MapMakerAssets here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MapMaker extends SuperSmoothMover
{
    private MapMakerWorld world;
    private ArrayList<Tile> tileListOptions = new ArrayList<Tile>();
    private String type;
    private int rotation = 0;
    private int page = 0;
    private boolean isFirstAct = true;
    private boolean hide = false;
    
    private int enemyID = 0; 
    private int triggerID = 0;
    
    private int actTimer = 60;
    public MapMaker(MapMakerWorld origin)
    {
        world = origin;
        getImage().scale(1080, 75);
    }
    /**
     * Act - do whatever the MapMakerAssets wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(isFirstAct)
        {
            refreshOptions(rotation);
            isFirstAct = false;
        }
        if(Greenfoot.isKeyDown("="))
        {
            if(actTimer != 0)
            {
                triggerID++;
            }
        }
        else if(Greenfoot.isKeyDown("="))
        {
            if(actTimer != 0)
            {
                if(triggerID > 0)
                {
                    triggerID--;
                }
            }
        }
        if(Greenfoot.isKeyDown("r"))
        {
            if(actTimer != 0)
            {
                actTimer = 0;
                rotation++;
                if(rotation > 3)
                {
                    rotation = 0;
                }
                refreshOptions(rotation);
            }
        }
        else if(Greenfoot.isKeyDown("h"))
        {
            if(actTimer != 0)
            {
                actTimer = 0;
                if(hide == false)
                {
                    hide = true;
                }
                else
                {
                    hide = false;
                }
            }
        }
        else
        {
            actTimer++;
        }
        if(hide)
        {
            hide();
        }
        else
        {
            displayOptions();
        }
        String key = Greenfoot.getKey();
        if(key != null && key.matches("\\d+"))
        {
            page = Integer.parseInt(key) - 1;
        }
    }
    public void hide()
    {
        ArrayList<Tile> allTiles = new ArrayList<Tile>(getWorld().getObjects(Tile.class));
        for(Tile tile: allTiles)
        {
            if(tile.getButton())
            {
                getWorld().removeObject(tile);
            }
        }
    }
    public void refreshOptions(int rotations)
    {
        ArrayList<Tile> allTiles = new ArrayList<Tile>(getWorld().getObjects(Tile.class));
        for(Tile tile: allTiles)
        {
            if(tile.getButton())
            {
                getWorld().removeObject(tile);
            }
        }
        tileListOptions.clear();
        pickOptions(page, rotations);
        //getWorld().getWidth()/2;
        displayOptions();
    }
    public void pickOptions(int page, int rotations)
    {
        switch(page)
        {
            case 0:
                tileListOptions.add(new Tile("FullTile", rotations, true, this));
                tileListOptions.add(new Tile("DiagonalTile", rotations, true, this));
                tileListOptions.add(new Tile("Stair", rotations, true, this));
                tileListOptions.add(new Tile("PlayerSpawnPoint", 0, true, this));
                tileListOptions.add(new LaserTile("LaserTile", rotations, true, this));
                tileListOptions.add(new Tile("EnemySpawnPoint", 0, true, this));
                tileListOptions.add(new TriggerTile("TriggerTile", 0, true, this, triggerID));
                tileListOptions.add(new EnemySpawner("EnemySpawner", 0, true, this, triggerID, EnemyID.getEnemy(enemyID)));
                break;
        } 
    }
    public void displayOptions()
    {
        double tileNum = - (double)tileListOptions.size()/2;
        int gapSize = getWorld().getWidth() / tileListOptions.size();
        for(Tile tile: tileListOptions)
        {
            getWorld().addObject(tile, (int)(getWorld().getWidth() / 2 + tileNum * gapSize + gapSize / 2), getY());
            tileNum ++;
        }
    }
    public int getEnemyID()
    {
        return enemyID;
    }
    public int getTriggerID()
    {
        return triggerID;
    }
    public void setType(String newType)
    {
        type = newType;
    }
    public String getType()
    {
        return type;
    }
    public int getRotations()
    {
        return rotation;
    }
}
