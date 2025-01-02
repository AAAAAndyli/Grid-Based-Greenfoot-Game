import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.util.ArrayList;

/**
 * Write a description of class MovingGraphic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerSprites extends SuperSmoothMover
{
    protected Player player;
    protected boolean flipped = false;
    
    protected int walkIndex;
    protected ArrayList<GreenfootImage> walkAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> walkAnimL = new ArrayList<GreenfootImage>();
    protected int attackIndex;
    protected ArrayList<GreenfootImage> attackAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> attackAnimL = new ArrayList<GreenfootImage>();
    protected int idleIndex;
    protected ArrayList<GreenfootImage> idleAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> idleAnimL = new ArrayList<GreenfootImage>();
    protected int deathIndex;
    protected ArrayList<GreenfootImage> deathAnimR = new ArrayList<GreenfootImage>();    
    protected ArrayList<GreenfootImage> deathAnimL = new ArrayList<GreenfootImage>();

    protected int animationTimer = 0;
    
    protected int offsetX = 0;
    
    public PlayerSprites(Player player)
    {
        this.player = player;
        //loadAnimationFrames("images/PlayerSprites");
    }
    
    /**
     * Act - do whatever the MovingGraphic wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(player.getWorld() != null)
        {
            setLocation(player.getX() + offsetX, player.getY());
        }
        animationTimer++;
        idleIndex = animate(!flipped ? idleAnimR : idleAnimL, idleIndex);
    }
    
     /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    protected void loadAnimationFrames(String path)
    {
        loadSingleAnimation(path, idleAnimL, "idle", true);
        loadSingleAnimation(path, idleAnimR, "idle");
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
        if(animationTimer < 10){
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
