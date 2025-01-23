import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerExplosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerExplosion extends Explosion
{
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        setLocation(getPosition().getX() + getWorldOfType(ScrollingWorld.class).getScrollX(), getPosition().getY() + getWorldOfType(ScrollingWorld.class).getScrollY());
    }
    public void hurtEntities(Entity entity)
    {
        if(entity.getClass() != Player.class)
        {
            entity.hurt(1, true);
        }
        else
        {
            Player player = (Player)entity;
            player.launch(5);
        }
    }
}
