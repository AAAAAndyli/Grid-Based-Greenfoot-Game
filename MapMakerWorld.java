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
    ArrayList<String> world = new ArrayList<String>();
    private MapMaker mapMaker = new MapMaker(this);
    private String loadedFile;
    
    /**
     * Constructor for objects of class MapMakerWorld.
     */
    public MapMakerWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1, false); 
        addObject(mapMaker, 400, 550);
        setPaintOrder(Tile.class, MapMaker.class, TileSelector.class);
        Greenfoot.setSpeed(51);
        addObject(new FPS(), 200, 10);
    }
    public void act()
    {
        super.act();
        useArrowKeysToMove();
        editMap();
        if(Greenfoot.isKeyDown("enter"))
        {
            printWorld();
        }
    }
    public void printWorld()
    {
        System.out.println("The World:");
        for(String tile : world)
        {
            System.out.println(tile);
        }
        saveFileExplorer();
        saveFile();
    }
    
    public void saveFileExplorer()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv files", "csv");
        fileChooser.setFileFilter(filter);
        
        int r = fileChooser.showOpenDialog(null);
        // if the user selects a file
        
        if (r == JFileChooser.APPROVE_OPTION)
        {
            loadedFile = fileChooser.getSelectedFile().getAbsolutePath();
            if(!loadedFile.endsWith(".csv")) 
            {
                loadedFile += ".csv";
            }
        }
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
            if((Greenfoot.mouseClicked(this) || getObjects(TileSelector.class).size() != 0 && Greenfoot.mouseClicked(getObjects(TileSelector.class).get(0))) && mapMaker.getType() != null)
            {
                placeTile(xMapPosition, yMapPosition, mapMaker.getType());
            }
            if(mouse != null)
            {
                if (mouse.getButton() == 3)
                {
                    if(getObjectsAt(mouse.getX(), mouse.getY(), Tile.class).size() != 0)
                    {
                        removeObject(getObjectsAt(mouse.getX(), mouse.getY(), Tile.class).get(0));
                    }
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
        Tile tile = new Tile(type, mapMaker.getRotations(), x, y);
        world.add(tile.getString());
        addObject(tile, x, y);
    }
    public void replaceTile()
    {
        
    }
}
