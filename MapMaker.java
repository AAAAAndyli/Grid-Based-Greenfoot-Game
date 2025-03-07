
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collection;

/**
 * A way to select tiles to use in mapMakerWorld
 * 
 * @author Andy
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
    
    private int changeIDDelay;
    private final int delayLength = 15;
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
        //Changes triggerID
        if(Greenfoot.isKeyDown("="))
        {
            if(changeIDDelay > delayLength)
            {
                changeIDDelay = 0;
                triggerID++;
            }
        }
        else if(Greenfoot.isKeyDown("-"))
        {
            if(changeIDDelay > delayLength)
            {
                changeIDDelay = 0;
                if(triggerID > 0)
                {
                    triggerID--;
                }
            }
        }
        //changes enemy ID
        if(Greenfoot.isKeyDown("]"))
        {
            if(changeIDDelay > delayLength)
            {
                changeIDDelay = 0;
                enemyID++;
            }
        }
        else if(Greenfoot.isKeyDown("["))
        {
            if(changeIDDelay > delayLength)
            {
                changeIDDelay = 0;
                if(enemyID > 0)
                {
                    enemyID--;
                }
            }
        }
        changeIDDelay++;
        //rotates the tile
        if(Greenfoot.isKeyDown("r"))
        {
            if(actTimer != 0)
            {
                actTimer = 0;
                rotation = (rotation + 1) % 4; // Cycles through 0 to 3
                System.out.println("Updated Rotation: " + rotation);
                refreshOptions(rotation);
            }
        }
        //hides tiles
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
            refreshOptions(rotation);
            //System.out.println("Page: " + page);
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
    //uses different pages
    public void pickOptions(int page, int rotations)
    {
        switch(page)
        {
            case 0:
                tileListOptions.add(new Tile("FullTile", rotations, true, this));
                tileListOptions.add(new OneWayTile("OneWayTile", rotations, true, this));
                tileListOptions.add(new Tile("PlayerSpawnPoint", 0, true, this));
                tileListOptions.add(new LaserTile("LaserTile", rotations, true, this));
                break;
            case 1:
                tileListOptions.add(new Tile("BackgroundFullTile", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundFullTile2", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundFullTile3", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundFullTile4", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundFullTile5", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundFullTile6", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundFullTile7", rotations, true, this, false));
                tileListOptions.add(new Tile("BackgroundDiagonalTile", rotations, true, this, false));
                tileListOptions.add(new Tile("Stair", rotations, true, this, false));
                break;
            case 2:
                tileListOptions.add(new CollisionTrigger("TriggerTile", 0, true, this, triggerID));
                tileListOptions.add(new EnemySpawner("EnemySpawner", 0, true, this, triggerID, EnemyID.getEnemy(enemyID)));
                tileListOptions.add(new BossSpawner("BossSpawner", 0, true, this, triggerID, enemyID));
                tileListOptions.add(new Firewall("Firewall", rotations, true, this, triggerID, "red"));
                tileListOptions.add(new Key("Key", rotations, true, this, triggerID, "red"));
                tileListOptions.add(new NextWorld("NextWorld", 0, true, this, triggerID));
                break;
            case 3:
                tileListOptions.add(new Tile("OWOFlat", rotations, true, this));
                tileListOptions.add(new Tile("OWOCorner", rotations, true, this));
                tileListOptions.add(new Tile("OWOEdge", rotations, true, this));
                tileListOptions.add(new Tile("OWOTile", rotations, true, this));
                tileListOptions.add(new Tile("OWOStrip", rotations, true, this));
                tileListOptions.add(new Tile("OWOEdgeCorner", rotations, true, this));
                tileListOptions.add(new Tile("OWOBlank", rotations, true, this));
                tileListOptions.add(new Tile("Blank", rotations, true, this));
                break;
        } 
    }
    /**
     * Method displayOptions - shows all the available tiles of the page
     *
     */
    public void displayOptions()
    {
        double tileNum = - (double)tileListOptions.size()/2;
        int gapSize = tileListOptions.size() != 0 ? getWorld().getWidth() / tileListOptions.size() : 1;
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
    public int getRotation()
    {
        System.out.println("getRotations() returning: " + rotation);    
        return rotation;
    }
}
