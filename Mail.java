import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mail here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mail extends Player
{
    public Mail()
    {
        if(SaveFile.getString("jump") == null){
            SaveFile.loadFile("saveFile/defaultSaveFile.csv");
        }
        setImage("Mail.png");
        health = 15;
    }
    public void addedToWorld(World world)
    {
    }
    /**
     * Act - do whatever the Mail wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!runOnce){
            //if accessing levelworld through debug, load default binds
            jump = SaveFile.getString("jump");
            parry = SaveFile.getString("parry");
            dash = SaveFile.getString("dash");
            left = SaveFile.getString("left");
            right = SaveFile.getString("right");
            down = SaveFile.getString("down");
            runOnce = true;
        }
        int yPosition = getPosition().getY();
        int rotation = getRotation(); 
        if(health > 10)
        {
           setImage("Mail.png");
        }
        else if(health > 5)
        {
           setImage("YellowMail.png");
        }
        else if (health > 0)
        {
            setImage("RedMail.png");
        }
        else if(health <= 0)
        {
            setLocation(getX(), getY() + (15 - health));
        }
        if(getY() > 1000)
        {
            getWorldOfType(ArSYSStartingWorld.class).dying();
        }
        if(Greenfoot.isKeyDown(jump) && getY() > 0)
        {
            setLocation(getX(), getY() - 15);
            rotation = Math.max(rotation - 5, 350);
        }
        else if(Greenfoot.isKeyDown(down) && getY() < 720)
        {
            setLocation(getX(), getY() + 15);
            rotation = Math.min(rotation + 5, 10);
        }
        else
        {
            if (rotation < 180) 
            {
                rotation -= 1;
            } 
            else if (rotation > 0) 
            {
                rotation += 1;
            }
        }
        setRotation(rotation);
    }
    public void hurt(int damage)
    {
        health -= damage;
    }
}
