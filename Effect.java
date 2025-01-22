import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effect here.
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class Effect extends ScrollingActor
{
    protected int timer = 0;
    protected int speed = 15;
    /**
     * Act - do whatever the Effect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        timer++;
        if(timer < 15)
        {
            getImage().scale(getImage().getWidth() + speed,getImage().getHeight() + speed);
        }
        else
        {
            if(getImage().getTransparency() - (timer - 15) * 10 > 0)
            {
                getImage().setTransparency(getImage().getTransparency() - (timer - 15) * 10);
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
    }}
