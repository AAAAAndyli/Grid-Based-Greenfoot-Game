import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
// for reading files
import java.util.StringTokenizer;
// for Files
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Write a description of class LevelWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelWorld extends ScrollingWorld
{
    private String levelName;
    ArrayList<String> world = new ArrayList<String>();
    ArrayList<Tile> tileWorld = new ArrayList<Tile>();
    private Crosshair crosshair = new Crosshair();
    private Camera camera = new Camera(crosshair);
    private ArrayList<ArrayList<Tile>> pathfindingTile = new ArrayList<ArrayList<Tile>>();
    private Player player;
    
    private ScrollingBackground layer1 = new ScrollingBackground(new GreenfootImage("Background/tower0.png"), 0.25, 0);
    private ScrollingBackground layer2 = new ScrollingBackground(new GreenfootImage("Background/tower1.png"), 0.5, 400);
    private ScrollingBackground layer3 = new ScrollingBackground(new GreenfootImage("Background/tower2.png"), 0.1, 800);
    private WorldButton pause;
    
    public LevelWorld()
    {
        this("level1.csv");
    }
    
    /**
     * Constructor for objects of class LevelWorld.
     */
    public LevelWorld(String levelName)
    {
        super(1080, 720, 1, false); 
        
        pause = new WorldButton("Pause.png", 0.05, new SettingWorld(this, LevelWorld.class));
        
        TriggerCollection.resetList();
        this.levelName = levelName;
        Greenfoot.setSpeed(51);
        loadLevel();
        saveLevel();
        /*
        for(int i = 0; i < toGrid().length; i++)
        {
            for(int j = 0; j < toGrid()[i].length; j++)
            {
                System.out.println(toGrid()[i][j].getString());
            }
        }
        */
        WorldOrder.createArrayList();
        WorldOrder.setIndex(levelName);
        addObject(new Shield(), 80, 650);
        addObject(pause, 40, 40);
        addObject(layer1, 0, 300);
        addObject(layer2, 400, 300);
        addObject(layer3, 800, 300);
        TheGrid.setGrid(toGrid());
        addObject(new FPS(), 200, 10);
        setPaintOrder(HealthBar.class, HealthBlob.class, HealthPod.class, PlayerSprites.class, Enemy.class, Actor.class, NextWorld.class, OneWayTile.class ,BossSprites.class, Tile.class, ScrollingBackground.class);
        setActOrder(PlayerSprites.class, Player.class, Tile.class, Enemy.class, Actor.class);
    }
    public void loadLevel()
    {
        Scanner scan = new Scanner (System.in);
        try
        {
            scan = new Scanner (new File("level/maps/" + levelName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found");
        }
        while (scan.hasNext()) // loop until end of file
        {
            world.add(scan.nextLine());
        }
        StringTokenizer tokenizer;
        String previousTile = "";
        for(String tile : world)
        {
            if(!tile.equals(previousTile))
            {
                tokenizer = new StringTokenizer(tile, ",");
                int sizeOfString = tokenizer.countTokens();
                try
                {
                    String type = tokenizer.nextToken();
                    int rotation = Integer.parseInt(tokenizer.nextToken());
                    int xLocation = Integer.parseInt(tokenizer.nextToken());
                    int yLocation = Integer.parseInt(tokenizer.nextToken());
                    int triggerNumber = -1;
                    int enemyNumber = -1;
                    String colour = "";
                    boolean isCollidable = true;
                    if(tokenizer.hasMoreTokens())
                    {
                        triggerNumber = Integer.parseInt(tokenizer.nextToken());
                    }
                    if(tokenizer.hasMoreTokens())
                    {
                        enemyNumber = Integer.parseInt(tokenizer.nextToken());
                    }
                    if(tokenizer.hasMoreTokens())
                    {
                        colour = tokenizer.nextToken();
                    }

                    switch(type)
                    {
                        case "PlayerSpawnPoint":
                            player = new Player();
                            addObject(crosshair, xLocation, yLocation);
                            addObject(player, xLocation, yLocation - player.getImage().getHeight()/4);
                            addObject(camera, 0, 0);
                            camera.addFollowing(player);
                            camera.addFollowing(player);
                            camera.addFollowing(player);
                            camera.addFollowing(player);
                            camera.addFollowing(player);
                            camera.setFollowing(player);
                            addObject(new HealthBar(player), 100, 100);
                            addObject(new Wallet(), 120, 170);
                            break;
                        case "LaserTile":
                            addObject(new LaserTile(type, rotation, xLocation, yLocation), xLocation, yLocation);
                            break;
                        case "EnemySpawnPoint":
                            Agast enemy = new Agast();
                            addObject(enemy, xLocation, yLocation);
                            break;
                        case "EnemySpawner":
                            EnemySpawner enemySpawner = new EnemySpawner(type, rotation, xLocation, yLocation, triggerNumber, EnemyID.getEnemy(enemyNumber));
                            addObject(enemySpawner, xLocation, yLocation);
                            break;
                        case "TriggerTile":
                            CollisionTrigger trigger = new CollisionTrigger(type, rotation, xLocation, yLocation, triggerNumber);
                            addObject(trigger, xLocation, yLocation);
                            break;
                        case "OneWayTile":
                            OneWayTile oneWayTile = new OneWayTile(type, rotation, xLocation, yLocation);
                            addObject(oneWayTile, xLocation, yLocation);
                            break;
                        case "Firewall":
                            Firewall firewall = new Firewall(type, rotation, xLocation, yLocation, triggerNumber, colour);
                            addObject(firewall, xLocation, yLocation);
                            break;
                        case "Key":
                            Key key = new Key(type, rotation, xLocation, yLocation, triggerNumber, colour);
                            addObject(key, xLocation, yLocation);
                            break;
                        case "BossSpawner":
                            BossSpawner BossSpawner = new BossSpawner(type, rotation, xLocation, yLocation, triggerNumber, enemyNumber);
                            addObject(BossSpawner, xLocation, yLocation);
                            break;
                        case "NextWorld":
                            NextWorld nextWorld = new NextWorld(type, rotation, xLocation, yLocation, triggerNumber);
                            addObject(nextWorld, xLocation, yLocation);
                            break;
                        default:
                            addObject(new Tile(type, rotation, xLocation, yLocation, true), xLocation, yLocation);
                            tileWorld.add(new Tile(type, rotation, xLocation, yLocation, true));
                            break;
                    }
                }
                catch(NumberFormatException e)
                {
                    System.out.println("Bad File >:(");
                }
            }
            previousTile = tile;
        }
        for(LaserTile laserTile : getObjects(LaserTile.class))
        {
            laserTile.removeLaser();
            laserTile.createLaser();
        }
    }
    
    public void saveLevel(){
        if(Greenfoot.isKeyDown("escape")){
            System.out.println("Would you like to save the level: ");
            
        }
    }
    
    public Tile[][] toGrid()
    {
        int lowestX = Integer.MAX_VALUE, lowestY = Integer.MAX_VALUE;
        int highestX = Integer.MIN_VALUE, highestY = Integer.MIN_VALUE;
        for(Tile tile : tileWorld)
        {
            if(lowestX > tile.getGlobalX())
            {
                lowestX = tile.getGlobalX();
            }
            if(lowestY > tile.getGlobalY())
            {
                lowestY = tile.getGlobalY();
            }
            if(highestX < tile.getGlobalX())
            {
                highestX = tile.getGlobalX();
            }
            if(highestY < tile.getGlobalY())
            {
                highestY = tile.getGlobalY();
            }
        }
        
        int xTiles = (highestX - lowestX)/50 + 1;
        int yTiles = (highestY - lowestY)/50 + 1;
        Tile[][] map = new Tile[yTiles][xTiles];
        
        for(Tile tile : tileWorld)
        {
            map[(tile.getGlobalY() - lowestY)/50][(tile.getGlobalX() - lowestX)/50] = tile;
        }
        return map;
    }
    
    public Player getPlayer()
    {
        return player;
    }
}
