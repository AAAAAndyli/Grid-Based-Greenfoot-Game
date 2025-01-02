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
    
    private MapMaker mapMaker = new MapMaker(this);
    private String loadedFile;
    //The primary frame
    private JFrame frame = new JFrame("Save/Load Map");
    
    /**
     * Constructor for objects of class MapMakerWorld.
     */
    public MapMakerWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1, false); 
        addObject(mapMaker, 540, 645);
        setPaintOrder(test.class, Tile.class, MapMaker.class, TileSelector.class);
        Greenfoot.setSpeed(51);
        addObject(new FPS(), 200, 10);
        
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
            showMainMenu();
            printWorld();
        }
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
        System.out.println(yTiles + " " + xTiles);
        Tile[][] map = new Tile[yTiles][xTiles];
        
        for(Tile tile : tileWorld)
        {
            System.out.println((tile.getGlobalY() + Math.abs(lowestY))/50 + " " + (tile.getGlobalX() + Math.abs(lowestX))/50);
            map[(tile.getGlobalY() - lowestY)/50][(tile.getGlobalX() - lowestX)/50] = tile;
        }
        return map;
    }
    
    public void printWorld()
    {
        sortWorld();
        System.out.println("The World:");
        for(Tile tile : tileWorld)
        {
            System.out.println(tile.getString());
        }
        System.out.println("The World in 2dArray:");
        Tile[][] tile2DArray = toGrid();
        for(int i = 0 ; i < tile2DArray.length ; i++)
        {
            System.out.println("Row #" + i);
            for(int j = 0 ; j < tile2DArray[i].length ; j++)
            {
                if(tile2DArray[i][j] != null)
                {
                    System.out.println(tile2DArray[i][j].getString());
                }
                else
                {
                    System.out.println("null");
                }
            }
        }
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
    
    public void saveFile()
    {
        sortWorld();
        writeFile(loadedFile,"", false, false);
        for(Tile tile : tileWorld)
        {
            writeFile(loadedFile, tile.getString(), true, true);
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
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
        catch (NullPointerException e)
        {
            System.out.println("Error: " + e);
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
                    placeTile(xMapPosition, yMapPosition, mapMaker.getType());
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
        Tile tile;
        if(type.equals("LasterTile"))
        {
            tile = new LaserTile(type, mapMaker.getRotations(), x, y);
        }
        else
        {
            tile = new Tile(type, mapMaker.getRotations(), x, y);
        }
        if(tileWorld.size() == 0)
        {
            tileWorld.add(tile);
            addObject(tile, x, y);
        }
        else
        {
            if((SAFE_MODE && getObjectsAt(x+scrollX, y+scrollY, Tile.class).size() == 0) || !SAFE_MODE)
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
            int sizeOfString = tokenizer.countTokens();
            try
            {
                String type = tokenizer.nextToken();
                int rotation = Integer.parseInt(tokenizer.nextToken());
                int xLocation = Integer.parseInt(tokenizer.nextToken());
                int yLocation = Integer.parseInt(tokenizer.nextToken());
                Tile newTile = new Tile(type, rotation, xLocation, yLocation);
                addObject(newTile, xLocation, yLocation);
                tileWorld.add(newTile);
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
