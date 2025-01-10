import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerHurtBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerHurtBox extends HurtBox
{
    public PlayerHurtBox(int width, int height, int damage)
    {
        super(width, height, damage);
    }
    public void collide()
    {
        if(isTouching(Enemy.class))
        {
            Enemy enemyTarget = (Enemy)getOneIntersectingObject(Enemy.class);
            enemyTarget.hurt(damage);
        }
    }
    /**
     * Act - do whatever the PlayerHurtBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
