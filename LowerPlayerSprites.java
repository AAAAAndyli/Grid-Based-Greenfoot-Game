import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LowerPlayerSprites here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LowerPlayerSprites extends PlayerSprites
{
    public LowerPlayerSprites(Player player)
    {
        super(player);
        loadAnimationFrames("images/PlayerSprites/Lower");
    }
    public void act()
    {
        if(!player.getFacing()) //if player is facing right
        {
            flipped = true;            
            System.out.println("Down facing Left");
            player.setLowerSpriteDirection(-1);
        }
        else
        {
            flipped = false;
            System.out.println("Down facing Right");
            player.setLowerSpriteDirection(1);
        }
        super.act();
    }
}
