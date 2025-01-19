import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BugLeg here.
 * 
 * @author Andy
 * @version 2, I had to rewrite all of this after I overwrote it.
 */
public class BugLeg extends Enemy
{
    private int attackTimer = 0;
    private int x, y;
    private int direction;
    private static Camera camera;
    private boolean isVertical;
    private static GreenfootImage vertLeg = new GreenfootImage("images/Enemies/bug/legs/attackLeg.png");
    private static GreenfootImage leftLeg = new GreenfootImage("images/Enemies/bug/legs/rattackLeg.png");
    private static GreenfootImage rightLeg = new GreenfootImage("images/Enemies/bug/legs/lattackLeg.png");
    private Attack vertStab = new Attack(30, 700, 1, 0 , 0, 0, this);
    private Attack horStab = new Attack(1080, 30, 1, 0 , 0, 0, this);
    public BugLeg(int x, int y, boolean isVertical, Camera camera)
    {
        this.camera = camera;
        this.x = x;
        this.y = y;
        health = 1000;
        this.isVertical = isVertical;
        if(isVertical)
        {
            setImage(vertLeg);
        }
        else if(Greenfoot.getRandomNumber(2) == 0)
        {
            direction = -1;
            setImage(rightLeg);
        }
        else
        {
            direction = 1;
            setImage(leftLeg);
        }
    }
    public void die()
    {
        getWorld().removeObject(this);
    }
    /**
     * Act - do whatever the BugLeg wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(isVertical)
        {
            verticalStab();
        }
        else
        {
            horizontalStab();
        }
        super.act();
    }
    public void verticalStab()
    {
        if(attackTimer == 0)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), x, y);
            attackTimer++;
        }
        else if(attackTimer > 30)
        {
            vertStab.performAttack();
            getPosition().setCoordinate(x, y + (int)Math.pow(attackTimer - 50, 2));
            attackTimer++;
        }
        else
        {
            attackTimer++;
        }
        if(attackTimer > 360)
        {
            willDie = true;
        }
    }
    public void horizontalStab()
    {
        if(attackTimer == 0)
        {
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), x + direction * 100, y - 50);
            attackTimer++;
        }
        else if(attackTimer > 30)
        {
            horStab.performAttack();
            getPosition().setCoordinate(x + direction * (int)Math.pow(attackTimer - 60, 2), y);
            attackTimer++;
        }
        else
        {
            attackTimer++;
        }
        if(attackTimer > 360)
        {
            willDie = true;
        }
    }
    public void loadAnimationFrames(String path)
    {}
}
