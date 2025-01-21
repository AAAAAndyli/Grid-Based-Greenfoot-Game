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
 * Write a description of class ArsysWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ArsysWorld extends LevelWorld
{
    public ArsysWorld()
    {
        this("0.csv");
        currentMusic.stop();
    }

    /**
     * Constructor for objects of class ArsysWorld.
     */
    public ArsysWorld(String levelName)
    {
        super("ARSYS/" + levelName); 
        WorldOrder.createArSYSList();
        WorldOrder.setIndex(levelName);
        setBackground("black.png");
        currentMusic = new GreenfootSound("Arsys/" + WorldOrder.getIndex() + ".wav");
    }

    public void act()
    {
        if(enterWorld.fadedOnce())
        {
            removeObject(enterWorld);
        }
        if(player.getWorld() == null)
        {
            addObject(playerDeath, 540, 360);
            if(playerDeath.fadedOnce())
            {
                Greenfoot.setWorld(new GameOver());
            }
        }
        super.act();
    }

    public void loadParallax()
    {
    }
}
