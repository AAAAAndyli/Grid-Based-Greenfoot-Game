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
    private Player player;
    
    private int walkIndex;
    private ArrayList<GreenfootImage> walkAnim = new ArrayList<GreenfootImage>();
    private int attackIndex;
    private ArrayList<GreenfootImage> attackAnim = new ArrayList<GreenfootImage>();
    private int idleIndex;
    private ArrayList<GreenfootImage> idleAnim = new ArrayList<GreenfootImage>();
    private int deathIndex;
    private ArrayList<GreenfootImage> deathAnim = new ArrayList<GreenfootImage>();
    
    private int animationTimer = 0;
    
    public PlayerSprites(Player player)
    {
        this.player = player;
        loadAnimationFrames("images/PlayerSprites");
    }
    
    /**
     * Act - do whatever the MovingGraphic wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(player.getWorld() != null)
        {
            setLocation(player.getX(), player.getY());
        }
        animationTimer++;
        idleIndex = animate(idleAnim, idleIndex);
    }
    
     /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    protected void loadAnimationFrames(String path)
    {
        for(int i = 0; i < new File(path+"/idle").listFiles().length-1; i++)
        {
            idleAnim.add(new GreenfootImage(path + "/idle/" + i + ".png"));
        }
        idleAnim.add(new GreenfootImage(path + "/idle/" + 1 + ".png"));
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
