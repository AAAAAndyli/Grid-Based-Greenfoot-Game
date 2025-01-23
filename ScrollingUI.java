import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class ScrollingUI here.
 * 
 * @author Darren T
 * @version (a version number or a date)
 */
public class ScrollingUI extends UI
{
    private int pastY;
    private int currentY;
    private int yOffset;
    private boolean letGo, actorFollow, belowWorld;
    private MouseInfo mouse;
    private Class className;
    private ArrayList<Class> classList = new ArrayList<Class>();
    private SuperTextBox actor;
    
    public ScrollingUI(int x, int y, int width, int height, boolean follow, ArrayList<Class> classFollowList){
        GreenfootImage dimensions = new GreenfootImage(width, height);
        setImage(dimensions);
        
        actorFollow = follow;
        if(actorFollow){
            for(Class c : classFollowList){
                classList.add(c);
            }  
        }
        
        letGo = true;
    }
    
    public ScrollingUI(int x, int y, int width, int height, boolean follow, Color color, ArrayList<Class> classFollowList, SuperTextBox secondary){
        GreenfootImage dimensions = new GreenfootImage(width, height);
        dimensions.setColor(color);
        dimensions.fill();
        
        setImage(dimensions);
        
        actor = secondary;
        actorFollow = follow;
        if(actorFollow){
            for(Class c : classFollowList){
                classList.add(c);
            }  
        }
        
        letGo = true;
    }
    
    /**
     * Method to allow scrolling based on user movement
     */
    public void scroll(Actor actor){
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null){
            //if player clicks mouse, get original y position as reference point
            if(Greenfoot.mousePressed(actor) && letGo){
                pastY = mouse.getY();
                letGo = false;
            } //release = stop scrolling
            if(Greenfoot.mouseClicked(null) || Greenfoot.mouseClicked(actor)){
                letGo = true;
            } // move up or down based on reference point
            if(!letGo){
                setLocation(getX(), getY() + mouse.getY() - pastY);
                //only if ScrollingUI specified to move an actor
                if(actorFollow){
                    for(Class c : classList){
                        moveActors(mouse.getY() - pastY, c);  
                    }
                }
                //update reference point for relative scrolling 
                //(moves down 10 units if mouse moves 10 units), wont 
                //move to just the mouse.getY() because that sucks
                //and cant allow infinite scrolling
                pastY = mouse.getY();
                
            } 
        }
           
    }
    
    /**
     * Method to move any actors supposed to be
     * part of the UI as it scrolls
     * 
     * @param y = The relative y distance mouse has moved
     * @param actor - The class to move with scrolling
     */
    public void moveActors(int y, Class actor){
        //get all buttons
        ArrayList<Actor> objects = (ArrayList<Actor>)(ArrayList<?>)getIntersectingObjects(actor);
        for(Actor o : objects){
            o.setLocation(o.getX(), o.getY() + y);
        }
    }
    
    /**
     * Act - do whatever the ScrollingUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(getY() >= 0 && getY() <= getWorld().getHeight()){
            scroll(this);
            if(actor != null){
                scroll(actor);
            }
            belowWorld = getY() >= getWorld().getHeight() ? true : false;
        }
        else{
            yOffset = belowWorld ? -50 : 50;
            setLocation(getX(), getY() + yOffset);
            for(Class c : classList){
                moveActors(yOffset, c);    
            }
        }
    }
}
