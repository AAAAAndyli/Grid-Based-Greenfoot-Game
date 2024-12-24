import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ProjectileEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProjectileEnemy extends Enemy
{
    protected int attackTimer;
    protected int attackCooldown = 120;
    protected int speed = 15;
    protected Coordinate target = new Coordinate(0,0);
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
        super.act();
    }
    public void attack()
    {
        if(attackTimer > attackCooldown && getObjectsInRange(500, Player.class).size() != 0)
        {
            //System.out.println("attacking");
            attackTimer = 0;
            useProjectile(0, speed, target);
        }
        else if(attackTimer < attackCooldown - 5 && getObjectsInRange(500, Player.class).size() != 0)
        {
            aiming(speed);
        }
        if(attackTimer < attackCooldown + 5)
        {
            attackTimer++;
        }
        else
        {
            attackTimer = 0;
        }
    }
    public void useProjectile(int projectileType, int speed, Coordinate target)
    {
        getWorld().addObject(new EProjectile(target, speed, this, "EnemyProjectile"), getX(), getY());
    }
    public void aiming(int speed)
    {
        Player player = getWorld().getObjects(Player.class).get(0);
        int playerPredictedX, playerPredictedY;
        double distance = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));
        playerPredictedX = player.getPosition().getX() + (int)player.getXVelocity() * (10 + (int)distance/speed);
        playerPredictedY = player.getPosition().getY();
        
        target.setCoordinate(playerPredictedX, playerPredictedY);
        //getWorld().addObject(new test(), sightPlayerMidX, sightPlayerMidY);
        if(attackTimer == attackCooldown - 21)
        {
            getWorld().addObject(new test(), playerPredictedX, playerPredictedY);
        }
    }
}
