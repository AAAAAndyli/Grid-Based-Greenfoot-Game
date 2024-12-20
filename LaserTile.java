import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaserTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserTile extends Tile
{
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
            System.out.println("otherTile");
            if(otherTile!= null && otherTile != this)
            {
                System.out.println("Thing Found!!");
                break;
            }
        }
        Laser laser = new Laser(rotations == 0 ? length:
            rotations == 2 ? -length:
            20,
            rotations == 1 ? length:
            rotations == 3 ? -length:
            20, 2);
        System.out.println(laser);
        getWorld().addObject(laser, globalPosition.getX(), globalPosition.getY());
    }
    public void act()
    {
        super.act();
    }
}
