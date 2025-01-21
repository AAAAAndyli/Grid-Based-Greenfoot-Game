import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyExplosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Aura extends Explosion
{
    private Bosses boss;
    public Aura(Bosses boss)
    {
        setImage("Aura.png");
        this.boss = boss;
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        setLocation(getPosition().getX() + getWorldOfType(ScrollingWorld.class).getScrollX(), getPosition().getY() + getWorldOfType(ScrollingWorld.class).getScrollY());
    }
    public void act()
    {
        if(boss.getWorld() == null)
        {
            getWorld().removeObject(this);
            return;
        }
        setRotation(Greenfoot.getRandomNumber(360));
        setLocation(boss.getX(), boss.getY());
    }
    public void hurtEntities(Entity entity)
    {
    }
}
