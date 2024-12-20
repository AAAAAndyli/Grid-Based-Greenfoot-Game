import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class KillBorder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KillTile extends Tile
{
    public KillTile(String type, int rotations, int xPosition, int yPosition)
    {
        this(type, rotations, false, null, xPosition, yPosition);
    }
    public KillTile(String type, int rotations, boolean isButton, MapMaker mapMaker)
    {
        this(type, rotations, isButton, mapMaker, 0, 0);
    }
    public KillTile(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition);
    }
    public void act()
    {
        super.act();
    }
}
