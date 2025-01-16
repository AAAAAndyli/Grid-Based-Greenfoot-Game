import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyExplosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyExplosion extends Explosion
{
    public EnemyExplosion()
    {
        setImage("enemyExplosion.png");
        getImage().scale(150,150);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        setLocation(getPosition().getX() + getWorldOfType(ScrollingWorld.class).getScrollX(), getPosition().getY() + getWorldOfType(ScrollingWorld.class).getScrollY());
    }
    public void hurtEntities(Entity entity)
    {
        if(entity.getClass() == Player.class)
        {
            Player player = (Player)entity;
            player.hurt(1);
            player.launch(5);
        }
    }
}
