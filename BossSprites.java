import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.util.ArrayList;

/**
 * Write a description of class MovingGraphic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossSprites extends ScrollingActor
{
    protected Bosses boss;
    protected boolean flipped = false;
    
    protected int animationTimer = 0;
    
    protected int offsetX = 0;
    
    public BossSprites(Bosses boss)
    {
        this.boss = boss;
        //loadAnimationFrames("images/PlayerSprites");
    }
    
    /**
     * Act - do whatever the MovingGraphic wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(boss.getWorld() != null)
        {
            getPosition().setCoordinate(boss.getPosition().getX() + offsetX, boss.getPosition().getY());
        }
        animationTimer++;
        super.act();
    }
    
    protected void loadSingleAnimation(String path, ArrayList<GreenfootImage> animation, String action)
    {
        this.loadSingleAnimation(path, animation, action, false);
    }
    
    protected void loadSingleAnimation(String path, ArrayList<GreenfootImage> animation, String action, boolean isRotated)
    {
        for(int i = 0; i < new File(path + "/" + action).listFiles().length-1; i++)
        {
            animation.add(new GreenfootImage(path + "/" + action + "/" + i + ".png"));
            if(isRotated)
            {
                animation.get(i).mirrorHorizontally();
            }
        }
    }

    protected int animate(ArrayList<GreenfootImage> animation, int index)
    {
        if(animationTimer < 5){
            return index;
        }
        setImage(animation.get(index));
        index++;
        if(index > animation.size()-1)
        {
            index = 0;
        }
        animationTimer = 0;
        return index;
    }
}
