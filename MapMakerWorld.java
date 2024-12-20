import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collection;

import java.util.Scanner;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.StringTokenizer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

//Window Creation
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

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
        super(800, 600, 1, false); 
        addObject(mapMaker, 400, 550);
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
        }
    }
    public void printWorld()
    {
        System.out.println("The World:");
        for(String tile : world)
        {
            System.out.println(tile);
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
        JButton createButton = new JButton("Create New Map");
        
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
        writeFile(loadedFile,"", false, false);
        for(String tile : world)
        {
            writeFile(loadedFile, tile, true, true);
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
    public void setWorld(ArrayList<String> world)
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
            if(mouse != null)
            {
                if (mouse.getButton() == 3)
                {
                    ArrayList<Tile> tilesAtMouse = (ArrayList<Tile>)getObjectsAt(mouse.getX(), mouse.getY(), Tile.class);
                    if(tilesAtMouse.size() != 0 && !tilesAtMouse.get(0).getButton())
                    {
                        world.remove(world.indexOf(tilesAtMouse.get(0).getString()));
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
        if(!type.equals("LaserTile"))
        {
            Tile tile = new Tile(type, mapMaker.getRotations(), x, y);
            if(world.size() == 0)
            {
                world.add(tile.getString());
                addObject(tile, x, y);
            }
            else
            {
                ArrayList<Tile> tileAtMouse = (ArrayList<Tile>)getObjectsAt(x+scrollX, y+scrollY, Tile.class);
                if((SAFE_MODE && tileAtMouse.size() == 0) || !SAFE_MODE)
                {
                    world.add(tile.getString());
                    addObject(tile, x, y);
                }
            }
        }
        switch(type)
        {
            case "LaserTile":
                LaserTile tile = new LaserTile(type, mapMaker.getRotations(), x, y);
                if(world.size() == 0)
                {
                    world.add(tile.getString());
                    addObject(tile, x, y);
                }
                else
                {
                    ArrayList<Tile> tileAtMouse = (ArrayList<Tile>)getObjectsAt(x+scrollX, y+scrollY, Tile.class);
                    if((SAFE_MODE && tileAtMouse.size() == 0) || !SAFE_MODE)
                    {
                        world.add(tile.getString());
                        addObject(tile, x, y);
                    }
                }
                break;
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
                addObject(new Tile(type, rotation, xLocation, yLocation), xLocation, yLocation);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Bad File >:(");
            }
        }
    }
}
