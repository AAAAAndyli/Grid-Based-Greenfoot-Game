import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.util.ArrayList;

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemy extends Entity
{    
    //Attacking Variables
    protected int attackTimer;
    protected int attackCooldown = 120;
    
    
    //Ranged attacking Variables
    protected int attackRange = 1000;
    protected int projectileSpeed = 15;
    protected Coordinate target = new Coordinate(0,0);
    protected int totalVelocityOfTarget, averageVelocityOfTarget, time;
    
    protected int animationTimer = 0;
    
    
    public Enemy()
    {
        this(0,0);
    }
    
    public Enemy(int scrollX, int scrollY)
    {
        super(scrollX, scrollY);
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
    
     /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    protected abstract void loadAnimationFrames(String path);
    
    protected void loadSingleAnimation(String path, ArrayList<GreenfootImage> animation, String action)
    {
        this.loadSingleAnimation(path, animation, action, false);
    }
    
    protected void loadSingleAnimation(String path, ArrayList<GreenfootImage> animation, String action, boolean isRotated)
    {
        for(int i = 0; i < new File(path + "/" + action).listFiles().length-1; i++)
        {
            animation.add(new GreenfootImage(path + "/" + action + "/" + i + ".png"));
            if(isRotated)
            {
                animation.get(i).mirrorHorizontally();
            }
        }
    }

    protected int animate(ArrayList<GreenfootImage> animation, int index)
    {
        if(animationTimer < 10){
            return index;
        }
        setImage(animation.get(index));
        index++;
        if(index > animation.size()-1)
        {
            index = 0;
        }
        animationTimer = 0;
        return index;
    }
}
