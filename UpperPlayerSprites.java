import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class UpperPlayerSprites here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UpperPlayerSprites extends PlayerSprites
{
    private int headDirection = 0;
    public UpperPlayerSprites(Player player)
    {
        super(player);
        loadAnimationFrames("images/PlayerSprites/Upper");
    }
    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null && mouse.getX() > getX()) //if mouse if to the right
        {
            flipped = false;
            //System.out.println("Up facing Right");
            player.setUpperSpriteDirection(1);
            headDirection = 1;
        }
        else
        {
            flipped = true;
            //System.out.println("Up facing Left");
            player.setUpperSpriteDirection(-1);
            headDirection = -1;
        }
        if(!player.matchingSpriteDirection())
        {
            offsetX = 5 * headDirection;
        }
        else
        {
            offsetX = 0;
        }
        super.act();
    }
}
