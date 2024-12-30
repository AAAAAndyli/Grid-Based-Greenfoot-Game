import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Entity
{
    
    
    //Attacking Variables
    protected int attackTimer;
    protected int attackCooldown = 120;
    protected int attackRange = 1000;
    
    
    //Ranged attacking Variables
    protected int projectileSpeed = 15;
    protected Coordinate target = new Coordinate(0,0);
    protected int totalVelocityOfTarget, averageVelocityOfTarget, time;
    
    public Enemy()
    {
        
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        globalPosition.setCoordinate(getX(), getY());
    }
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        barrier();
        if(willDie)
        {
            die();
        }
    }
    
    public void attack()
    {
        if(attackTimer > attackCooldown && getObjectsInRange(attackRange, Player.class).size() != 0)
        {
            //System.out.println("attacking");
            attackTimer = 0;
            useProjectile(0, projectileSpeed, target);
        }
        else if(attackTimer < attackCooldown && getObjectsInRange(attackRange, Player.class).size() != 0)
        {
            aiming(projectileSpeed);
        }
        if(attackTimer < attackCooldown + 5)
        {
            attackTimer++;
        }
        else
        {
            attackTimer = 0;
            time = 0;
            totalVelocityOfTarget = 0;
            averageVelocityOfTarget = 0;
        }
    }
    
    public void useProjectile(int projectileType, int projectileSpeed, Coordinate target)
    {
        getWorld().addObject(new EProjectile(target, projectileSpeed, 1, this, "EnemyProjectile"), getX(), getY());
    }
    
    public void aiming(int projectileSpeed)
    {
        Player player = getWorld().getObjects(Player.class).get(0);
        int playerPredictedX, playerPredictedY;
        double distance = Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));
        
        
        if(attackTimer < attackCooldown && distance < 250)
        {
            totalVelocityOfTarget += player.getXVelocity();
            time++;
            averageVelocityOfTarget = totalVelocityOfTarget/time;
        }
        else
        {
            averageVelocityOfTarget = (int)player.getXVelocity();
        }
        
        playerPredictedX = player.getPosition().getX() + scrollX + averageVelocityOfTarget * ((int)distance/(projectileSpeed != 0 ? projectileSpeed : 15));
        playerPredictedY = player.getPosition().getY() + scrollY;
        
        target.setCoordinate(playerPredictedX, playerPredictedY);
    }
    
    
    
    public void findPath()
    {
        
    }
}
