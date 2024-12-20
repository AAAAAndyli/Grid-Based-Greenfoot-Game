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
            followMultipleTargets();
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
        int actorX = followingActor.getX();
        int actorY = followingActor.getY();
        //300 = global position - scroll x
        //
        if(actorX > 425)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - (actorX - 425));
        }
        else if(actorX < 375)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - (actorX - 375));
        }
        if(actorY < 275)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - (actorY - 275));
        }
        else if(actorY > 325)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - (actorY - 325));
        }
    }
    
    public void followMultipleTargets()
    {
        int totalX = 0;
        int totalY = 0;
        for(Actor targets: followingActors) 
        {
            totalX += targets.getX();
            totalY += targets.getY();
        }
        totalX /= followingActors.size();
        totalY /= followingActors.size();
        if(totalX > 400)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - (totalX - 400));
        }
        else if(totalX < 400)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - (totalX - 400));
        }
        if(totalY > 300)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - (totalY - 300));
        }
        else if(totalY < 300)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - (totalY - 300));
        }
    }
    
    public void setMultipleFollowing()
    {
        followingMultipleActors = true;
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
