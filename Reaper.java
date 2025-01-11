import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BurstTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Reaper extends FlyingEnemy
{
    private int attackRange = 200;
    private Attack slice = new Attack(attackRange + 50, 70, 2, 0 , attackRange/2 + 10, 0);
    private Player player;
    public Reaper()
    {
        attackCooldown = 60;
        health = 24;
        speed = 7;
    }
    /**
     * Act - do whatever the BurstTurret wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(player == null)
        {
            player = getWorldOfType(LevelWorld.class).getPlayer();
        }
        else
        {
            faceTowards(player.getPosition().getX());
            slice.changeDirection(xDirection);
        }
        if(!isAttacking)
        {
            if(path.size() > 0)
            {
                moveTowards(path.get(0));
            }   
            else
            {
                findPath();
            }
        }
        if(getObjectsInRange(attackRange + 50, Player.class).size() != 0)
        {
            attack();
        }
        super.act();
    }
    public void attack()
    {
        if(attackTimer > attackCooldown + attackLength)
        {
            isAttacking = false;
            attackTimer = 0;
        }
        else if(attackCooldown == attackTimer)
        {
            isAttacking = true;
            slice.performAttack();
            attackTimer++;
        }
        else
        {
            attackTimer++;
        }
    }
    public void findPath()
    {
        if(player != null)
        {
            Coordinate playerOffset = new Coordinate(player.getPosition().getX() - xDirection * attackRange, player.getPosition().getY());
            for(Coordinate coords : TheGrid.aStarfindPath(getPosition(),playerOffset))
            {
                path.add(coords);
                getWorld().addObject(new test(true), coords.getX(), coords.getY());
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
        loadSingleAnimation(path, idleAnimL, "hover", true);
        loadSingleAnimation(path, idleAnimR, "hover");
    }
}
