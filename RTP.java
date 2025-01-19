import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RTP here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RTP extends GroundedEnemy
{
    //attack variables
    private int attackRange = 500;
    private int attackCooldown = 60;
    private Attack pierce = new Attack(400, 100, 1, 0 , xDirection, 0, this);
    private int attackFrame = 6; 
    private int attackXOffset = 100;
    private Player player;    
    
    public RTP()
    {
        super();
        bytesOnDeath = 10;
        isImmuneToExplosions = true;
        xSpeed = 5;
        health = 30;
        loadAnimationFrames("images/Enemies/rtp");
    }
    /**
     * Act - do whatever the WalMare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!isAttacking)
        {
            attackIndex = 0;
            if(checkForPlayer() || player != null)
            {
                if(player == null)
                {
                    player = (Player)getOneObjectAtOffset(playerDistance, 0, Player.class);
                }
                //System.out.println("PlayerPos: " + player.getPosition().getX() + ", EnemyPosition: " +  getPosition().getX() + ", AttackRange: " + attackRange + ", DifferenceInRange: " +  Math.abs(player.getPosition().getX() - getPosition().getX()));
                if(player.getWorld() != null)
                {
                    if(Math.abs(player.getPosition().getX() - getPosition().getX()) > attackRange && getOneTileAtOffset(getImage().getWidth()/2 * xDirection, 0) == null)
                    {
                        walkIndex = animate(xDirection==1 ? walkAnimR : walkAnimL, walkIndex);
                        moveTo(player.getPosition().getX() - xDirection * attackRange);
                        faceTowards(player.getPosition().getX());
                        followTimer = 0;
                    }
                    else if(player != null && getDistance(player) < 600)
                    {
                        faceTowards(player.getPosition().getX());
                        xVelocity = 0;
                        pierce.changeDirection(xDirection);
                        attack();
                    }
                    else
                    {
                        xVelocity = 0;
                    }
                }
            }
            else
            {
                xVelocity = 0;
                if(checkTimer > 60)
                {
                    xDirection *= -1;
                    checkTimer = 0;
                }
                else
                {
                    checkTimer++;
                }
            }
            idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
        }
        else
        {
            attack();
        }
        super.act();
    }
    public void hurt(int damage)
    {
        super.hurt(damage);
        //Player player = getWorld().getObjects(Player.class).get(0);
        //faceTowards(player.getPosition().getX());
    }
    public void attack()
    {
        if((player != null && getDistance(player) < 300) || isAttacking)
        {
            if(attackTimer > attackCooldown + attackLength)
            {
                isAttacking = false;
                getPosition().setCoordinate(getPosition().getX() - attackXOffset * xDirection, getPosition().getY() - attackYOffset);
                idleIndex = animate(xDirection == 1 ? idleAnimR : idleAnimL, idleIndex);
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
                pierce.performAttack();
                attackTimer++;
            }
            else if(attackCooldown + attackFrame - 20 == attackTimer)
            {
                getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
                attackTimer++;
            }
            else if(isAttacking && !attackAnimOver)
            {
                int prevAttackIndex = attackIndex;
                attackIndex = animate(xDirection==1 ? attackAnimR : attackAnimL, attackIndex);
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
                attackTimer++;
            }
        }
        else if(player != null)
        {
            if(attackCooldown - 10 == attackTimer)
            {
                getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
                attackTimer++;
            }
            else if(attackTimer > attackCooldown)
            {
                attackTimer = 0;
                useExplosiveProjectile(5, player.getPosition());
                useExplosiveProjectile(10, player.getPosition());
                useExplosiveProjectile(15, player.getPosition());
            }
            else
            {
                attackTimer++;
            }
        }
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        //System.out.println("Searching for images in: " + path + "/" + "");
        loadSingleAnimation(path, idleAnimL, "idle", true);
        loadSingleAnimation(path, idleAnimR, "idle");
        loadSingleAnimation(path, attackAnimL, "attack", true);
        loadSingleAnimation(path, attackAnimR, "attack");
        loadSingleAnimation(path, walkAnimL, "move", true);
        loadSingleAnimation(path, walkAnimR, "move");
    }
}
