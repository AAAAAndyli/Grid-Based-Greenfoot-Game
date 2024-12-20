import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaserTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserTile extends Tile
{
    private Laser laser;
    public LaserTile(String type, int rotations, int xPosition, int yPosition)
    {
        this(type, rotations, false, null, xPosition, yPosition);
    }
    public LaserTile(String type, int rotations, boolean isButton, MapMaker mapMaker)
    {
        this(type, rotations, isButton, mapMaker, 0, 0);
    }
    public LaserTile(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        createLaser();
    }
    public void act()
    {
        super.act();
    }
    public void createLaser()
    {
        int length;
        for(length = 0; length < 10000; length++)   
        {
            Tile otherTile = (Tile)(getOneObjectAtOffset(
            rotations == 0 ? length:
            rotations == 2 ? -length:
            0,
            rotations == 1 ? length:
            rotations == 3 ? -length:
            0, Tile.class));
            if(otherTile!= null && otherTile != this)
            {
                break;
            }
        }
        int laserX = (rotations == 0) ? length : (rotations == 2) ? -length : 10;
        int laserY = (rotations == 1) ? length : (rotations == 3) ? -length : 10;
        laser = new Laser(Math.abs(laserX), Math.abs(laserY), 2);
        getWorld().addObject(laser, globalPosition.getX() + ((rotations == 0 || rotations == 2) ? laserX/2 : 0), globalPosition.getY() + ((rotations == 1 || rotations == 3) ? laserY/2 : 0));
    }
    public void removeLaser()
    {
        getWorld().removeObject(laser);
    }
}
