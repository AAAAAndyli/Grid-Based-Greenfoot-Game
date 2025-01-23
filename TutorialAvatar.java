import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)
import java.util.Queue;
import java.util.LinkedList;

/**
 * Write a description of class TutorialAvatar here.
 * 
 * @author Allan L.
 * @version (a version number or a date)
 */
public class TutorialAvatar extends SuperSmoothMover {
    
    //Placement variables
    private int targetX = 1000;
    private int targetY = 650;
    private int startX = 1080;
    private int startY = 650;
    private int framesToMove = 80;
    private int currentFrame = 0;
    
    //Message variables
    private String[] messages; 
    private int currentMessage = 0;
    private SuperTextBox label;
    private boolean hasDisplayedMessage = false;
    private int actCounter = 10;

    public TutorialAvatar(String[] str) {
        setImage("Tera.png");
        setLocation(startX, startY);

        
        this.messages = str;

        
        label = new SuperTextBox(messages[currentMessage], new Font("Arial", true, false, 18), 400, 40);
    }

    public void act() {
        moveToTarget();
        handleKey();
        //Add the object 
        if (currentFrame >= framesToMove && !hasDisplayedMessage) {
            hasDisplayedMessage = true;
            getWorld().addObject(label, getX() - 250, getY() - 70);
        }
    }

    /**
     * Handles displaying messages and switching to the next message when "right arrow" is pressed.
     */
    
    private void handleKey() {
        
        if (Greenfoot.isKeyDown("right") && currentMessage < messages.length - 1) {
            //While there are more messages, if the counter is 10, the text is updated
            if (actCounter == 10) {
                currentMessage++;
                label.update(messages[currentMessage]);
                actCounter = 0;
            }
            actCounter++;
        } else if (Greenfoot.isKeyDown("right") && currentMessage >= messages.length - 1) {
            //Remove the textbox and the avatar
            getWorld().stopped();
            getWorld().removeObject(label);
            getWorld().removeObject(this);
        }
    }

    /**
     * Smoothly moves the avatar to the target location.
     */
    private void moveToTarget() {
        if (currentFrame < framesToMove) {
            double stepX = (targetX - startX) / (double) framesToMove;
            double stepY = (targetY - startY) / (double) framesToMove;

            setLocation(getX() + stepX, getY() + stepY);
            currentFrame++;
        }
    }
}
