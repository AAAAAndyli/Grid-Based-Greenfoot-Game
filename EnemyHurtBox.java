import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyHurtBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyHurtBox extends HurtBox
{
    private Enemy origin;
    private boolean hitstop;
    public EnemyHurtBox(int width, int height, int damage, Enemy origin)
    {
        this(width, height, damage, origin, true);
    }
    public EnemyHurtBox(int width, int height, int damage, Enemy origin, boolean hitStop)
    {
        super(width, height, damage);
        this.hitstop = hitstop;
        this.origin = origin;
    }
    public boolean collide()
    {
        if(isTouching(Player.class))
        {
            Player player = (Player)getOneIntersectingObject(Player.class);
            if(player.getHurtable() && !(player.getState().equals("parrying") && player.getParryTimer() < 20))
            {
                player.hurt(damage);
                getWorld().removeObject(this);
                return true;
            }
            else if(player.getState().equals("parrying") && player.getParryTimer() < 20)
            {
                parried();
                if(hitstop)
                {
                    Greenfoot.delay(5);
                }
                getWorld().getObjects(Camera.class).get(0).screenShake(1, 10);
                getWorld().removeObject(this);
                return false;
            }
        }
        return false;
    }
    public void parried()
    {
        origin.hurt(damage);
    }
}
