import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.StringTokenizer;

//Window Creation
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import javax.swing.filechooser.*;
import java.io.*;
import java.lang.*;

/**
 * Write a description of class MapMakerWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MapMakerWorld extends ScrollingWorld
{
    //Safe mode prevents overlapping tiles
    private final static boolean SAFE_MODE = true;
    
    ArrayList<String> world = new ArrayList<String>();
    ArrayList<Tile> tileWorld = new ArrayList<Tile>();
    private String[][] grid2D;
    private String value;
    private int index;
    private int enemyIndex;
    private String colour = "red";
    private int colourChangeTimer = 0;
    
    private MapMaker mapMaker = new MapMaker(this);
    private String loadedFile;
    //The primary frame
    private JFrame frame = new JFrame("Save/Load Map");
    private Tile previousTile;
    
    private int lowestX = Integer.MAX_VALUE, lowestY = Integer.MAX_VALUE;
    
    private StillLabel currentTriggerID = new StillLabel("Trigger ID: ", 40, mapMaker);
    private StillLabel currentEnemyID = new StillLabel("Enemy ID: ", 40, mapMaker);
    private StillLabel currentColour = new StillLabel("Firewall Colour: ", 40, mapMaker);
    
    /**
     * Constructor for objects of class MapMakerWorld.
     */
    public MapMakerWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1, false); 
        addObject(mapMaker, 540, 645);
        setPaintOrder(StillLabel.class, Label.class, Tile.class, MapMaker.class, TileSelector.class);
        Greenfoot.setSpeed(51);
        addObject(new FPS(), 200, 10);
        addObject(currentTriggerID, 900, 40);
        addObject(currentEnemyID, 900, 80);
        addObject(currentColour, 850, 120);
        
        
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setVisible(false);
    }
    
    public void act()
    {
        super.act();
        useArrowKeysToMove();
        editMap();
        if(Greenfoot.isKeyDown("enter"))
        {
            centerWorldOnLowestPoint();
            showMainMenu();
            printWorld();
        }
        if(Greenfoot.isKeyDown("shift") && colourChangeTimer > 30)
        {
            colourChangeTimer = 0;
            switch(colour)
            {
                case "red":
                    colour = "blue";
                    break;
                case "blue":
                    colour = "pink";
                    break;
                case "pink":
                    colour = "green";
                    break;
                case "green":
                    colour = "red";
                    break;
            }
        }
        colourChangeTimer++;
        currentTriggerID.setValue("Trigger ID: " + mapMaker.getTriggerID());
        currentEnemyID.setValue("Enemy ID: " + mapMaker.getEnemyID());
        currentColour.setValue("Firewall Colour: " + colour);
    }
    
    /*
    public void toGrid(int x, int y)
    {
        grid2D = new String[x][y];
        index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                value = world.get(index);
                grid2D[i][j] = value;
                index++;
            }
        }
    }
    */
    public ArrayList<Tile> sortWorld()
    {
        Collections.sort(tileWorld, new TileComparator());
        return tileWorld;
    }
    public Tile[][] toGrid()
    {
        sortWorld();
        int highestX = Integer.MIN_VALUE, highestY = Integer.MIN_VALUE;
        for(Tile tile : tileWorld)
        {
            getLowestCoordinates();
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
        System.out.println(yTiles + " " + xTiles);
        Tile[][] map = new Tile[yTiles][xTiles];
        
        for(Tile tile : tileWorld)
        {
            System.out.println((tile.getGlobalY() + Math.abs(lowestY))/50 + " " + (tile.getGlobalX() + Math.abs(lowestX))/50);
            //tile.getPosition().setCoordinate(tile.getPosition().getX() - lowestX, tile.getPosition().getY() - lowestY);
            map[(tile.getGlobalY() - lowestY)/50][(tile.getGlobalX() - lowestX)/50] = tile;
        }
        return map;
    }
    private void getLowestCoordinates()
    {
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
        }
    }
    public void printWorld()
    {
        sortWorld();
        System.out.println("The World:");
        for(Tile tile : tileWorld)
        {
            System.out.println(tile.toString());
        }
        System.out.println("The World in 2dArray:");
        Tile[][] tile2DArray = toGrid();
        /*
        for(int i = 0 ; i < tile2DArray.length ; i++)
        {
            System.out.println("Row #" + i);
            for(int j = 0 ; j < tile2DArray[i].length ; j++)
            {
                if(tile2DArray[i][j] != null)
                {
                    System.out.println(tile2DArray[i][j].toString());
                }
                else
                {
                    System.out.println("null");
                }
            }
        }
        */
    }
    
    public void showMainMenu()
    {
        frame.setVisible(true);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();
        JButton loadButton = new JButton("Load Map");
        JButton createButton = new JButton("Save Map");
        
        //Create new map file
        createButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv files", "csv");
                fileChooser.setFileFilter(filter);
                
                int r = fileChooser.showSaveDialog(null);
                // if the user selects a file
                if (r == JFileChooser.APPROVE_OPTION)
                {
                     // set the label to the path of the selected file
                    loadedFile = fileChooser.getSelectedFile().getAbsolutePath();
                    if(!loadedFile.endsWith(".csv")) 
                    {
                        loadedFile += ".csv";
                    }
                    saveFile();
                }
            }
        });
        //Load map file
        loadButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv files", "csv");
                fileChooser.setFileFilter(filter);
                
                int r = fileChooser.showOpenDialog(null);
                // if the user selects a file
                if (r == JFileChooser.APPROVE_OPTION)
                {
                    // set the label to the path of the selected file
                    loadedFile = fileChooser.getSelectedFile().getAbsolutePath();
                    loadLevel(loadedFile);
                }
            }
        });
        buttonPanel.add(createButton);
        buttonPanel.add(loadButton);
        
        
        mainPanel.add(buttonPanel);
        mainPanel.add(textPanel);
        
        frame.add(mainPanel);
    }
    
    public void updateFrame()
    {
        frame.invalidate();
        frame.validate();
        frame.repaint();
        frame.show();
    }
    
    public void centerWorldOnLowestPoint()
    {
        getLowestCoordinates();
        for(Tile tile : getObjects(Tile.class))
        {
            if(!tile.isButton)
            {
                removeObject(tile);
            }
        }
        ArrayList<Tile> newTileList = new ArrayList<Tile>();
        for(Tile tile : tileWorld)
        {
            addObject(tile, tile.getGlobalX() - lowestX, tile.getGlobalY() - lowestY);
            tile.getPosition().setCoordinate(tile.getGlobalX() - lowestX, tile.getGlobalY() - lowestY);
            tile.setGlobalX(tile.getGlobalX() - lowestX);
            tile.setGlobalY(tile.getGlobalY() - lowestY);
            newTileList.add(tile);
        }
        tileWorld.clear();
        tileWorld = newTileList;
    }
    
    public void saveFile()
    {
        sortWorld();
        writeFile(loadedFile,"", false, false);
        for(Tile tile : tileWorld)
        {
            //tile.getPosition().setCoordinate(tile.getPosition().getX() - lowestX, tile.getPosition().getY() - lowestY);
            writeFile(loadedFile, tile.toString(), true, true);
            previousTile = tile;
        }
    }
    
    public void writeFile(String path, String line, boolean append, boolean newLine)
    {
        try
        {
            FileWriter out = new FileWriter(path, append);
            PrintWriter output = new PrintWriter(out);
            if(newLine)
            {
                output.println(line);
            }
            else
            {
                output.print(line);
            }
            output.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
    
    public void setWorld(ArrayList<Tile> world)
    {
        this.tileWorld = world;
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
            if(mouse != null)
            {
                if (mouse.getButton() == 3)
                {
                    ArrayList<Tile> tilesAtMouse = (ArrayList<Tile>)getObjectsAt(mouse.getX(), mouse.getY(), Tile.class);
                    if(tilesAtMouse.size() != 0 && !tilesAtMouse.get(0).getButton())
                    {
                        tileWorld.remove(tileWorld.indexOf(tilesAtMouse.get(0)));
                        removeObject(tilesAtMouse.get(0));
                    }
                }
                else if((mouse.getButton() == 1) && mapMaker.getType() != null)
                {
                    placeTile(xMapPosition, yMapPosition, mapMaker.getType(), mapMaker.getTriggerID(), EnemyID.getEnemy(mapMaker.getEnemyID()), mapMaker.getRotation(), colour, mapMaker.getEnemyID());
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

    public void placeTile(int x, int y, String type, int triggerID, Enemy enemy, int rotation, String colour, int bossID)
    {
        Tile tile;
        if(type.equals("LasterTile"))
        {
            tile = new LaserTile(type, rotation, x, y);
        }
        else if(type.equals("EnemySpawner"))
        {
            tile = new EnemySpawner(type, rotation, x, y, triggerID, enemy);
        }
        else if(type.equals("TriggerTile"))
        {
            tile = new CollisionTrigger(type, rotation, x, y, triggerID);
        }
        else if(type.equals("NextWorld"))
        {
            tile = new NextWorld(type, rotation, x, y, triggerID);
        }
        else if(type.equals("OneWayTile"))
        {
            tile = new OneWayTile(type, rotation, x, y);
        }
        else if(type.equals("Firewall"))
        {
            tile = new Firewall(type, rotation, x, y, triggerID, colour);
        }
        else if(type.equals("Key"))
        {
            tile = new Key(type, rotation, x, y, triggerID, colour);
        }
        else if(type.equals("BossSpawner"))
        {
            tile = new BossSpawner(type, rotation, x, y, triggerID, bossID);
        }
        else
        {
            tile = new Tile(type, rotation, x, y, true);
        }
        if(tileWorld.size() == 0)
        {
            tileWorld.add(tile);
            addObject(tile, x, y);
        }
        else
        {
            Tile tileInSpace = null;
            if(getObjectsAt(x+scrollX, y+scrollY, Tile.class).size() != 0)
            {
                tileInSpace = (Tile)(getObjectsAt(x+scrollX, y+scrollY, Tile.class).get(0));
            }
            if(SAFE_MODE && (tileInSpace == null || (!tileInSpace.getType().equals(tile.getType()) || tileInSpace.getButton())) || !SAFE_MODE)
            {
                tileWorld.add(tile);
                addObject(tile, x, y);
            }
        }
    }
    
    public void loadLevel(String path)
    {
        Scanner scan = new Scanner (System.in);
        try
        {
            scan = new Scanner (new File(path));
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
        for(String tile : world)
        {
            tokenizer = new StringTokenizer(tile, ",");
            try
            {
                String type = tokenizer.nextToken();
                int rotation = Integer.parseInt(tokenizer.nextToken());
                int xLocation = Integer.parseInt(tokenizer.nextToken());
                int yLocation = Integer.parseInt(tokenizer.nextToken());
                int triggerNumber = -1;
                int enemyNumber = -1;
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
                placeTile(xLocation, yLocation, type, triggerNumber, EnemyID.getEnemy(enemyNumber), rotation, colour, enemyNumber);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Bad File >:(");
            }
        }
    }
    
}

class TileComparator implements Comparator<Tile> {
    public int compare(Tile t1, Tile t2) {
        // descending order (ascending order would be:
        // t1.getJerseyNumber()-t2.getJerseyNumber())
        int xCompare = t1.getGlobalX()-t2.getGlobalX();
        int yCompare = t1.getGlobalY()-t2.getGlobalY();
        
        return (yCompare == 0) ? xCompare : yCompare;
    }
    
}
