import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effects here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DeathEffects extends ScrollingActor
{
    private GreenfootImage deathSprite1 = new GreenfootImage("DeathEffects/0.png");
    private GreenfootImage deathSprite2 = new GreenfootImage("DeathEffects/1.png");
    private int explodeTimer = 0;
    public DeathEffects()
    {
        if(Greenfoot.getRandomNumber(2) == 1)
        {
            setImage(deathSprite1);
        }
        else
        {
            setImage(deathSprite2);
        }
        getImage().scale(100,100);
        setRotation(270 + Greenfoot.getRandomNumber(90) - 45);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        setLocation(getPosition().getX() - scrollX, getPosition().getY() - scrollY);
    }
    /**
     * Act - do whatever the Effects wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        explodeTimer++;
        int speed = explodeTimer / 2 + 5;
        globalPosition.setCoordinate(globalPosition.getX()+(int)Math.round(speed * Math.cos(Math.toRadians(getRotation()))), globalPosition.getY()+(int)Math.round(speed * Math.sin(Math.toRadians(getRotation()))));
        if(explodeTimer < 60)
        {
            setRotation(getRotation()+Greenfoot.getRandomNumber(3)-1);
        }
        else
        {
            if(getImage().getTransparency() - (explodeTimer - 15) * 10 > 0)
            {
                getImage().setTransparency(getImage().getTransparency() - (explodeTimer - 15) * 10);
                if(getImage().getWidth() - 15 > 0)
                {
                    getImage().scale(getImage().getWidth() - 15,getImage().getHeight() - 15);
                }
            }
            else
            {
                getWorld().removeObject(this);
                return;
            }
        }
        super.act();
    }
}
