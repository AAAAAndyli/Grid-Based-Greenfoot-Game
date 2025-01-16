import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BurstTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Agast extends FlyingEnemy
{
    private int numberOfProjectiles = 0;
    private Player player;
    public Agast()
    {
        attackCooldown = 120;
        health = 20;
        speed = 10;
        loadAnimationFrames("images/Enemies/agast");
    }
    /**
     * Act - do whatever the BurstTurret wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        idleIndex = animate(idleAnimR, idleIndex);
        if(player == null)
        {
            player = getWorldOfType(LevelWorld.class).getPlayer();
        }
        attack();
        if(path.size() > 0)
        {
            moveTowards(path.get(0));
        }   
        else
        {
            findPath();
        }
        super.act();
    }
    public void attack()
    {
        if(((numberOfProjectiles > 0 && numberOfProjectiles < 10) && attackTimer > 5)||(numberOfProjectiles == 0 && attackTimer > attackCooldown) && getObjectsInRange(attackRange, Player.class).size() != 0)
        {
            //System.out.println("attacking");
            numberOfProjectiles ++ ;
            attackTimer = 0;
            useProjectile(projectileSpeed, target);
        }
        else if(attackTimer < attackCooldown - 5 && getObjectsInRange(attackRange, Player.class).size() != 0)
        {
            aiming(projectileSpeed);
        }
        else if(attackCooldown + attackFrame - 10 == attackTimer)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(numberOfProjectiles != 0)
        {
            numberOfProjectiles = 0;
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
    public void findPath()
    {
        if(player != null)
        {
            Coordinate hoverCoordinate = new Coordinate(player.getPosition().getX() + Greenfoot.getRandomNumber(300) - 150, player.getPosition().getY() - 200 + Greenfoot.getRandomNumber(50));
            for(Coordinate coords : TheGrid.aStarfindPath(getPosition(), hoverCoordinate))
            {
                path.add(coords);
            }
            if(path.size() == 0)
            {
                hoverCoordinate = new Coordinate(player.getPosition().getX() + Greenfoot.getRandomNumber(300) - 150, player.getPosition().getY() - 100 + Greenfoot.getRandomNumber(50));
                for(Coordinate coords : TheGrid.aStarfindPath(getPosition(), hoverCoordinate))
                {
                    path.add(coords);
                }
            }
        }
        //path = TheGrid.findPathAir(this.getPosition(), player.getPosition());
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        //System.out.println("Searching for images in: " + path + "/" + "hover");
        loadSingleAnimation(path, idleAnimR, "hover");
    }
}
