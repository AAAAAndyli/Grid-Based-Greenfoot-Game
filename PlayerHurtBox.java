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
    public boolean collide()
    {
        if(isTouching(Enemy.class))
        {
            Enemy enemyTarget = (Enemy)getOneIntersectingObject(Enemy.class);
            enemyTarget.hurt(damage);
            return true;
        }
        return false;
    }

}
