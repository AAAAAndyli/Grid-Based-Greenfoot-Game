import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collection;

/**
 * Write a description of class MapMakerWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MapMakerWorld extends World
{
    ArrayList<ArrayList<String>> world = new ArrayList<ArrayList<String>>();

    /**
     * Constructor for objects of class MapMakerWorld.
     */
    public MapMakerWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
    }
}
