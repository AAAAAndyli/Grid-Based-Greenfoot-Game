import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Wall SPrites
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class WallSprites extends BossSprites
{
    private int previousState;
    protected int walkIndex;
    protected ArrayList<GreenfootImage> walkAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> walkAnimL = new ArrayList<GreenfootImage>();
    protected int jumpIndex;
    protected ArrayList<GreenfootImage> jumpAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> jumpAnimL = new ArrayList<GreenfootImage>();
    protected int idleIndex;
    protected ArrayList<GreenfootImage> idleAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> idleAnimL = new ArrayList<GreenfootImage>();
    protected int shootIndex;
    protected ArrayList<GreenfootImage> shootAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> shootAnimL = new ArrayList<GreenfootImage>();
    public WallSprites(Bosses boss)
    {
        super(boss);
        loadAnimationFrames("images/Enemies/wall");
    }
    public void act()
    {
        super.act();
        if(boss.getWorld() == null)
        {
            getWorld().removeObject(this);
            return;
        }
        int xDirection = boss.getFacing();
        int state = boss.getState();
        switch(state)
        {
            case -1:
                idleIndex = animate(xDirection==1 ? idleAnimR : idleAnimL, idleIndex);
            case 0:
                if(jumpIndex < 3)
                {
                    jumpIndex = animate(xDirection==1 ? jumpAnimR : jumpAnimL, jumpIndex);
                }
                break;
            case 1:
                walkIndex = animate(xDirection==1 ? walkAnimR : walkAnimL, walkIndex);
                break;
            case 2:
                shootIndex = animate(xDirection==1 ? shootAnimR : shootAnimL, shootIndex);
                break;
        }
        previousState = state;
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        loadSingleAnimation(path, idleAnimL, "idle", true);
        loadSingleAnimation(path, idleAnimR, "idle");
        loadSingleAnimation(path, jumpAnimL, "jump", true);
        loadSingleAnimation(path, jumpAnimR, "jump");
        loadSingleAnimation(path, shootAnimL, "shoot", true);
        loadSingleAnimation(path, shootAnimR, "shoot");
        loadSingleAnimation(path, walkAnimL, "move", true);
        loadSingleAnimation(path, walkAnimR, "move");
    }
}
