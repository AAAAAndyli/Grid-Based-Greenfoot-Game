import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BurstTurret here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Reaper extends FlyingEnemy
{
    private int attackRange = 100;
    private Attack slice = new Attack(attackRange + 50, 70, 2, 0 , attackRange/2 + 10, 0);
    private Player player;
    public Reaper()
    {
        attackCooldown = 10;
        health = 24;
        speed = 7;
        attackFrame = 4;
        attackXOffset = 50;
        loadAnimationFrames("images/Enemies/reaper");
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
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
            if(path.size() > 0)
            {
                moveTowards(path.get(0));
            }   
            else
            {
                findPath();
            }
        }
        if(getOneObjectAtOffset(attackRange, 0, Player.class) != null || getOneObjectAtOffset(-attackRange, 0, Player.class) != null || isAttacking)
        {
            isAttacking = true;
            attack();
        }
        super.act();
    }
    public void attack()
    {
        if(attackTimer > attackCooldown + attackLength)
        {
            isAttacking = false;
            getPosition().setCoordinate(getPosition().getX() - attackXOffset * xDirection, getPosition().getY() - attackYOffset);
            attackTimer = 0;
        }
        else if(attackCooldown == attackTimer)
        {
            isAttacking = true;
            attackAnimOver = false;
            getPosition().setCoordinate(getPosition().getX() + attackXOffset * xDirection, getPosition().getY() + attackYOffset);
            attackIndex = animate(xDirection==1 ? attackAnimR : attackAnimL, attackIndex);
            attackIndex = 1;
            attackTimer++;
        }
        else if(attackCooldown + attackFrame == attackTimer)
        {
            slice.performAttack();
            attackTimer++;
        }
        else if(isAttacking && !attackAnimOver)
        {
            System.out.println("attackIndex" + attackIndex);
            int prevAttackIndex = attackIndex;
            attackIndex = animate(xDirection == 1 ? attackAnimR : attackAnimL, attackIndex);
            if(prevAttackIndex != attackIndex)
            {
                attackTimer++;
            }
            if(attackIndex == 0)
            {
                attackAnimOver = true;
            }
        }
        else
        {
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
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
        loadSingleAnimation(path, attackAnimL, "attack", true);
        loadSingleAnimation(path, attackAnimR, "attack");
    }
}
