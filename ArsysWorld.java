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
    }
    public void act()
    {
        if(previousMusicVolume != SaveFile.getInt("musicVolume")){
            //update the list with each new music
            musicList = new GreenfootSound[]
            {
                currentMusic,
            };
            SaveFile.updateVolume(musicList, "musicVolume");
            previousMusicVolume = SaveFile.getInt("musicVolume");
        }
        if(previousEffectVolume != SaveFile.getInt("effectVolume")){
            //update the list with each new effect
            effectList = new GreenfootSound[]
            {
                
            };
            //UNCOMMENT WHEN EFFECTS ADDED
            //SaveFile.updateVolume(effectList, "effectVolume");
            //previousEffectVolume = SaveFile.getInt("musicVolume");
        }
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
