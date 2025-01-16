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
    int pastY;
    int currentY;
    int yOffset;
    boolean letGo, actorFollow, belowWorld;
    MouseInfo mouse;
    Class className;
    ArrayList<Class> classList = new ArrayList<Class>();
    
    public ScrollingUI(int x, int y, int width, int height, boolean follow, ArrayList<Class> classFollowList){
        //DEBUG get rid of this later, rectangle placeholder
        GreenfootImage dimensions = new GreenfootImage(width, height);
        setImage(dimensions);
        dimensions.drawRect(x, y, width - 1, height - 1);
        //dimensions.fillRect(x, y, width - 1, height - 1);
        
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
    public void scroll(){
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null){
            //if player clicks mouse, get original y position as reference point
            if(Greenfoot.mousePressed(this) && letGo){
                pastY = mouse.getY();
                letGo = false;
            } //release = stop scrolling
            if(Greenfoot.mouseClicked(null) || Greenfoot.mouseClicked(this)){
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
            scroll();
            belowWorld = getY() >= getWorld().getHeight() ? true : false;
        }
        else{
            yOffset = belowWorld ? -10 : 10;
            setLocation(getX(), getY() + yOffset);
            for(Class c : classList){
                moveActors(yOffset, c);    
            }
            
        }
    }
}
