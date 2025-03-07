import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Player Projectile (Abbreviated PProjectile
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PProjectile extends Projectile
{
    /**
     * Act - do whatever the PProjectile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public PProjectile(Coordinate target, double speed, ScrollingActor spawner, int damage)
    {
        super(target, speed, damage, spawner, "PlayerSmall");
    }
    
    public PProjectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
    {
        super(target, speed, damage, spawner, sprite);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    public void act()
    {
        super.act();
    }
    public void collide()
    {
        if(isTouching(Enemy.class))
        {
            Enemy enemyTarget = (Enemy)getOneIntersectingObject(Enemy.class);
            if(enemyTarget.getHurtable())
            {
                enemyTarget.hurt(damage);
                markedForDeletion = true;
            }
        }
    }
}
