import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Camera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Camera extends SuperSmoothMover
{
    private Actor followingActor;
    private ArrayList<Actor> followingActors = new ArrayList<Actor>();
    private boolean followingMultipleActors = false;
    private int numberOfFollowingActors = 2;
    
    public Camera(Actor followingActor)
    {
        this.followingActor = followingActor;
    }
    /**
     * Act - do whatever the Camera wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(followingMultipleActors)
        {
            try
            {
                followMultipleTargets();
            }
            catch(IllegalStateException e)
            {
                for(Actor actor: followingActors)
                {
                    if(actor.getWorld() == null)
                    {
                        removeFollowing(actor);
                        followingMultipleActors = false;
                        break;
                    }
                }
            }
        }
        else
        {
            if(followingActor.getWorld() != null)
            {
                followSingleTarget();
            }
        }
    }
    public void followSingleTarget()
    {
        ScrollingWorld world = getWorldOfType(ScrollingWorld.class);
        
        int targetScrollX = world.getScrollX() - (followingActor.getX() - world.getWidth()/2);
        int targetScrollY = world.getScrollY() - (followingActor.getY() - world.getHeight()/2);
        
        int newScrollX = (int) (world.getScrollX() + (targetScrollX - world.getScrollX()) * 0.5);
        int newScrollY = (int) (world.getScrollY() + (targetScrollY - world.getScrollY()) * 0.5);
        
        // Set the new scroll positions
        world.setScrollX(newScrollX);
        world.setScrollY(newScrollY); 
    }
    
    public void followMultipleTargets()
    {
        ScrollingWorld world = getWorldOfType(ScrollingWorld.class);
        int totalX = 0;
        int totalY = 0;
        for (Actor targets : followingActors) 
        {
            totalX += targets.getX();
            totalY += targets.getY();
        }
        totalX /= followingActors.size();
        totalY /= followingActors.size();
        
        int targetScrollX = world.getScrollX() - (totalX - world.getWidth()/2);
        int targetScrollY = world.getScrollY() - (totalY - world.getHeight()/2);
        
        int newScrollX = (int) (world.getScrollX() + (targetScrollX - world.getScrollX()) * 0.5);
        int newScrollY = (int) (world.getScrollY() + (targetScrollY - world.getScrollY()) * 0.5);
        
        // Set the new scroll positions
        world.setScrollX(newScrollX);
        world.setScrollY(newScrollY);    
    }
    
    public void setMultipleFollowing(boolean multipleActors)
    {
        followingMultipleActors = multipleActors;
    }
    public void setFollowing(Actor newFollowingActor)
    {
        followingActor = newFollowingActor;
    }
    public void addFollowing(Actor newFollowingActor)
    {
        followingActors.add(newFollowingActor);
    }
    public void removeFollowing(Actor newFollowingActor)
    {
        followingActors.remove(followingActors.indexOf(newFollowingActor));
    }
}
