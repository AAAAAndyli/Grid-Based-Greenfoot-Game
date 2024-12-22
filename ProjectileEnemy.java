import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProjectileEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProjectileEnemy extends Enemy
{
    private int attackTimer;
    public ProjectileEnemy()
    {
        attackTimer = 0;
    }
    /**
     * Act - do whatever the ProjectileEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(attackTimer > 120 && getObjectsInRange(500, Player.class).size() != 0)
        {
            attackTimer = 0;
            useProjectile(0, 15);
        }
        else
        {
            attackTimer++;
        }
        super.act();
    }
    public void useProjectile(int projectileType, int speed)
    {
        Player player = getWorld().getObjects(Player.class).get(0);
        Coordinate playerLocalPosition = new Coordinate(player.getX(), player.getY());
        getWorld().addObject(new EProjectile(playerLocalPosition, speed, this, "EnemyProjectile"), getX(), getY());
        System.out.println("PEWPEW");
    }
}
