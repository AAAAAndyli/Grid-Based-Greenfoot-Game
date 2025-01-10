import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyHurtBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyHurtBox extends HurtBox
{
    public EnemyHurtBox(int width, int height, int damage)
    {
        super(width, height, damage);
    }
    public void collide()
    {
        if(isTouching(Player.class))
        {
            Player player = (Player)getOneIntersectingObject(Player.class);
            if(player.getHurtable())
            {
                player.hurt(damage);
            }
        }
    }
    /**
     * Act - do whatever the EnemyHurtBox wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
