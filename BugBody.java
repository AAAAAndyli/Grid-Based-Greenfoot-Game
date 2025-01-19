import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BugBody here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BugBody extends BossSprites
{
    public BugBody(Bosses boss)
    {
        super(boss);
        setImage("images/Enemies/bug/body/body.png");
    }
    /**
     * Act - do whatever the BugBody wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(isFirstAct)
        {
            setLocation(globalPosition.getX() - tempScrollX, globalPosition.getY() - tempScrollY);
        }
        if(globalPosition != null)
        {
            setLocation(globalPosition.getX() + scrollX, globalPosition.getY() + scrollY);
        }
        followHead();
    }
    public void followHead()
    {
        int targetY = boss.getPosition().getY() + 300;
        int bugY = this.getPosition().getY();
        int distanceY = targetY - bugY;
        
        int targetX = boss.getPosition().getX();
        int bugX = this.getPosition().getX();
        int distanceX = targetX - bugX;
        
        getPosition().setCoordinate(getPosition().getX()+ (int)(distanceX * 0.05), getPosition().getY() + (int)(distanceY * 0.05));
    }
    public void loadAnimationFrames(String path)
    {}
}
