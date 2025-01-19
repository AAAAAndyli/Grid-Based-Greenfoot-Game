import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Wal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScorchSprites extends BossSprites
{
    private int previousState;
    private boolean attackIsOver;
    protected int hoverIndex;
    protected ArrayList<GreenfootImage> hoverAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> hoverAnimL = new ArrayList<GreenfootImage>();
    protected int slash1Index;
    protected ArrayList<GreenfootImage> slash1AnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> slash1AnimL = new ArrayList<GreenfootImage>();
    protected int slash2Index;
    protected ArrayList<GreenfootImage> slash2AnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> slash2AnimL = new ArrayList<GreenfootImage>();
    protected int shootIndex;
    protected ArrayList<GreenfootImage> shootEAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> shootEAnimL = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> shootHAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> shootHAnimL = new ArrayList<GreenfootImage>();
    protected int diveIndex;
    protected ArrayList<GreenfootImage> diveAnimR = new ArrayList<GreenfootImage>();
    protected ArrayList<GreenfootImage> diveAnimL = new ArrayList<GreenfootImage>();
    
    private int xDirection;
    public ScorchSprites(Bosses boss)
    {
        super(boss);
        loadAnimationFrames("images/Enemies/scorch");
    }
    public void act()
    {
        super.act();
        if(boss.getWorld() == null)
        {
            getWorld().removeObject(this);
            return;
        }
        xDirection = boss.getFacing();
        int state = boss.getState();
        switch(state)
        {
            case -1:
                hoverIndex = animate(xDirection==1 ? hoverAnimR : hoverAnimL, hoverIndex);
                break;
            case 0:
                slash1Index = animateAndEnd(slash1AnimR, slash1AnimL, slash1Index);
                createAfterImage();
                break;
            case 1:
                slash2Index = animateAndEnd(slash2AnimR, slash2AnimL, slash2Index);
                createAfterImage();
                break;
            case 2:
                slash1Index = animateAndEnd(slash1AnimR, slash1AnimL, slash1Index);
                createAfterImage();
                break;
            case 3:
                slash2Index = animateAndEnd(slash2AnimR, slash2AnimL, slash2Index);
                createAfterImage();
                break;
            case 4:
                shootIndex = animateAndEnd(shootEAnimR, shootEAnimL, shootIndex);
                break;
            case 5:
                shootIndex = animateAndEnd(shootHAnimR, shootHAnimL, shootIndex);
                break;
            case 6:
                if(previousState != state)
                {
                    diveIndex = 0;
                }
                else if(diveIndex == 9)
                {
                    diveIndex = 6;
                }
                diveIndex = animate(xDirection==1 ? diveAnimR : diveAnimL, diveIndex);
                createAfterImage();
                break;
        }
        previousState = state;
    }
    public boolean getAttackOver()
    {
        return attackIsOver;
    }
    public int animateAndEnd(ArrayList<GreenfootImage> rightAnim, ArrayList<GreenfootImage> leftAnim, int index)
    {
        if(index == rightAnim.size() - 1)
        {
            attackIsOver = true;
            index++;
            return 0;
        }
        else
        {
            attackIsOver = false;
        }
        return animate(xDirection==1 ? rightAnim : leftAnim, index);
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    public void loadAnimationFrames(String path)
    {
        loadSingleAnimation(path, hoverAnimL, "hover", true);
        loadSingleAnimation(path, hoverAnimR, "hover");
        loadSingleAnimation(path, slash1AnimL, "slash1", true);
        loadSingleAnimation(path, slash1AnimR, "slash1");
        loadSingleAnimation(path, slash2AnimL, "slash2", true);
        loadSingleAnimation(path, slash2AnimR, "slash2");
        loadSingleAnimation(path, shootEAnimL, "shootExplosive", true);
        loadSingleAnimation(path, shootEAnimR, "shootExplosive");
        loadSingleAnimation(path, shootHAnimL, "shootHoming", true);
        loadSingleAnimation(path, shootHAnimR, "shootHoming");
        loadSingleAnimation(path, diveAnimL, "dive", true);
        loadSingleAnimation(path, diveAnimR, "dive");
    }
}
