import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthBlob here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBlob extends Actor
{
    private static GreenfootImage green = new GreenfootImage("HealthBar/3.png");
    private static GreenfootImage yellow = new GreenfootImage("HealthBar/2.png");
    private static GreenfootImage red = new GreenfootImage("HealthBar/1.png");
    private static GreenfootImage nothing = new GreenfootImage("HealthBar/0.png");
    public HealthBlob()
    {
        setImage(green);
    }
    public void lower()
    {
        if(getImage() == green)
        {
            setImage(yellow);
        }
        else if(getImage() == yellow)
        {
            setImage(red);
        }
        else
        {
            setImage(nothing);
        }
    }
    public void set(int health)
    {
        switch(health)
        {
            case 0:
                setImage(nothing);
                break;
            case 1:
                setImage(red);
                break;
            case 2:
                setImage(yellow);
                break;
            case 3:
                setImage(green);
                break;
        }
    }
    public void raise()
    {
        if(getImage() == nothing)
        {
            setImage(red);
        }
        else if(getImage() == red)
        {
            setImage(yellow);
        }
        else
        {
            setImage(green);
        }
    }
}
