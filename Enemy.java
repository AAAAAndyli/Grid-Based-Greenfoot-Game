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
    //Facing variables
    protected int xDirection = 1;
    
    //Attacking Variables
    protected int attackTimer = 0;
    protected int attackCooldown = 120;
    protected int attackFrame;
    protected boolean isAttacking = false;
    protected int attackLength = 20;
    
    protected int attackXOffset, attackYOffset;    
    protected boolean attackAnimOver = true;
    
    
    //Ranged attacking Variables
    protected int attackRange = 1000;
    protected int projectileSpeed = 15;
    protected Coordinate target = new Coordinate(0,0);
    protected int totalVelocityOfTarget, averageVelocityOfTarget, time;
    
    protected int animationTimer = 0;
    protected int bytesOnDeath = 3;
    
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
    
    public void die()
    {
        for (int i = 0; i < bytesOnDeath; i++) 
        {
            getWorld().addObject(new Byte(getPosition().getX(), getPosition().getY()), getPosition().getX(), getPosition().getY());
        }
        
        getWorld().removeObject(this);
    }
    
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        animationTimer++;
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
    
    public void faceTowards(int xPositionToFace)
    {
        if(xPositionToFace > getPosition().getX())
        {
            xDirection = 1;
        }
        else if(xPositionToFace < getPosition().getX())
        {
            xDirection = -1;
        }
    }
    
    public void useProjectile(int projectileType, int projectileSpeed, Coordinate target)
    {
        getWorld().addObject(new HomingEProjectile(target, projectileSpeed, 1, this, "EnemyProjectile"), getX(), getY());
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
        for(int i = 0; i < new File(path + "/" + action).listFiles().length - 1; i++)
        {
            animation.add(new GreenfootImage(path + "/" + action + "/" + i + ".png"));
            if(isRotated)
            {
                animation.get(i).mirrorHorizontally();
            }
        }
        //debug code
        /*
        try
        {
            for(int i = 0; i < new File(path + "/" + action).listFiles().length - 1; i++)
            {
                animation.add(new GreenfootImage(path + "/" + action + "/" + i + ".png"));
                if(isRotated)
                {
                    animation.get(i).mirrorHorizontally();
                }
            }
            System.out.println("Loaded " + path + "/" + action);
        }
        catch(java.lang.NullPointerException e)
        {
            System.out.println("Cannot find " + path + "/" + action);
        }
        */
    }

    protected int animate(ArrayList<GreenfootImage> animation, int index)
    {
        if(animationTimer < 5){
            return index;
        }
        setImage(animation.get(index));
        //System.out.println("Index: " + index);
        index++;
        if(index > animation.size()-1)
        {
            index = 0;
        }
        animationTimer = 0;
        return index;
    }
    
    protected void createAfterImage()
    {
        getWorld().addObject(new AfterImage(new GreenfootImage(getImage()), scrollX, scrollY), getPosition().getX(), getPosition().getY());
    }
    
    protected class Attack
    {
        private int width, height;
        private int damage, direction;
        private int xOffset, yOffset;
        private EnemyHurtBox hurtBox;
        private boolean didHit;
        private int duration = 0;
        public Attack(int width, int height,int damage, int direction, int xOffset, int yOffset)
        {
            this.damage = damage;
            this.direction = direction;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            hurtBox = new EnemyHurtBox(width, height, damage);
        }
        public void performAttack()
        {
            hurtBox.createHurtBox();
            getWorld().addObject(hurtBox, getPosition().getX() + direction * xOffset, getPosition().getY() + yOffset);
            hurtBox.setLocation(hurtBox.getPosition().getX()+scrollX, hurtBox.getPosition().getY()+scrollY);
            didHit = hurtBox.collide();
            hurtBox.removeHurtBox();
        }
        public void changeDirection(int direction)
        {
            this.direction = direction;
        }
        public boolean attackHit()
        {
            return didHit;
        }
    }
}
