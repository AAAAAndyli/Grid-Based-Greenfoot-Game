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
    private boolean followingMultipleActors = true;
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
            followSingleTarget();
        }
    }
    public void followSingleTarget()
    {
        int actorX = followingActor.getX();
        int actorY = followingActor.getY();
        //300 = global position - scroll x
        //
        if(actorX > 500)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - (actorX - 500));
        }
        else if(actorX < 300)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - (actorX - 300));
        }
        if(actorX > 800)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() - 800);
        }
        else if(actorX < 0)
        {
            getWorldOfType(ScrollingWorld.class).setScrollX(getWorldOfType(ScrollingWorld.class).getScrollX() + 0);
        }
        if(actorY > 400)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - (actorY - 400));
        }
        else if(actorY < 200)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - (actorY - 200));
        }
        if(actorY > 600)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() - 100);
        }
        else if(actorY < 0)
        {
            getWorldOfType(ScrollingWorld.class).setScrollY(getWorldOfType(ScrollingWorld.class).getScrollY() + 100);
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
