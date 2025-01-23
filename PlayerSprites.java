import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.util.ArrayList;

/**
 * Write a description of class MovingGraphic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerSprites extends ScrollingActor
{
    protected Player player;
    protected boolean flipped = false;
    
    protected int walkIndex;
    protected ArrayList<GreenfootImage> walkAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> walkAnimL = new ArrayList<GreenfootImage>();
    protected int dashIndex;
    protected ArrayList<GreenfootImage> dashAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> dashAnimL = new ArrayList<GreenfootImage>();
    protected int jumpIndex;
    protected ArrayList<GreenfootImage> jumpAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> jumpAnimL = new ArrayList<GreenfootImage>();
    protected int fallIndex;
    protected ArrayList<GreenfootImage> fallAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> fallAnimL = new ArrayList<GreenfootImage>();
    protected int rangedAttackIndex;
    protected ArrayList<GreenfootImage> rangedAttackAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> rangedAttackAnimL = new ArrayList<GreenfootImage>();
    protected int meleeAttackIndex;
    protected ArrayList<GreenfootImage> meleeAttackAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> meleeAttackAnimL = new ArrayList<GreenfootImage>();
    protected int slideIndex;
    protected ArrayList<GreenfootImage> slideAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> slideAnimL = new ArrayList<GreenfootImage>();
    protected int idleIndex;
    protected ArrayList<GreenfootImage> idleAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> idleAnimL = new ArrayList<GreenfootImage>();
    protected int parry;
    protected ArrayList<GreenfootImage> parryAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> parryAnimL = new ArrayList<GreenfootImage>();
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
            getPosition().setCoordinate(player.getPosition().getX() + offsetX, player.getPosition().getY());
        }
        else
        {
            for(int i = 0; i < 3; i++)
            {
                getWorld().addObject(new DeathEffects(), getPosition().getX(), getPosition().getY());
            }
            getWorld().removeObject(this);
        }
        animationTimer++;
        //afterImage creation
        switch(player.getState())
        {
            case "slamming":
            case "dashing":
                getWorld().addObject(new AfterImage(new GreenfootImage(getImage()), scrollX, scrollY), player.getPosition().getX(), player.getPosition().getY());
                break;
        }
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
