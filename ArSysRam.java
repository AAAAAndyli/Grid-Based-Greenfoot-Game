import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ArSysRam extends Bosses
{
    //attack variables
    private boolean isRight;
    private Player player;
    public ArSysRam(boolean isRight)
    {
        super();
        health = 200;
        bytesOnDeath = 25;
        this.isRight = isRight;
        totalAttackCooldown = 60;
        if(isRight)
        {
            setImage("Enemies/ArSysRam/idle.png");
            getImage().mirrorHorizontally();
        }
        else
        {
            setImage("Enemies/ArSysRam/idle.png");
        }
    }
    /**
     * Act - do whatever the WalMare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(player == null)
        {
            player = getWorldOfType(LevelWorld.class).getPlayer();
            //getWorld().addObject(bossbar, 520, 680);
            //getWorld().addObject(new BugLegSprite(this), getPosition().getX(), getPosition().getY());
            //getWorld().addObject(new BugBody(this), getPosition().getX(), getPosition().getY());
        }
        else if(player.getWorld() != null)
        {
            if(currentAttackDone && attackCooldown < 0)
            {
                attackCooldown = totalAttackCooldown;
                currentAttackDone = false;
            }
            else if(currentAttackDone)
            {
                attackCooldown--;
            }
            else
            {
                currentAttackDone = shootProjectiles();
            }
        }
        super.act();
    }
    public void hurt(int damage)
    {
        super.hurt(damage);
    }
    public boolean isRight()
    {
        return isRight;
    }
    public void die()
    {
        if(getWorld().getObjects(ArSysRam.class).size() < 2)
        {
            super.die();
        }
        else
        {
            getWorld().removeObject(this);
        }
    }
    public boolean shootProjectiles()
    {
        if(attackTimer == 0)
        {
            setImage("images/Enemies/ArSysRam/prepare.png");
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > 30)
        {
            attackTimer = 0;
            setImage("images/Enemies/ArSysRam/idle.png");
            aiming(15);
            useHomingProjectile(15, new Coordinate(player.getPosition().getX(), player.getPosition().getY()));
            useHomingProjectile(10, new Coordinate(player.getPosition().getX(), player.getPosition().getY()));
            useHomingProjectile(5, new Coordinate(player.getPosition().getX(), player.getPosition().getY()));
            return true;
        }
        else
        {
            attackTimer++;
        }
        return false;
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
    }
}
