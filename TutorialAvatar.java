import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)
import java.util.Queue;
import java.util.LinkedList;

/**
 * TutorialAvatar class.
 */
public class TutorialAvatar extends SuperSmoothMover {
    private int targetX = 1000;
    private int targetY = 650;
    private int startX = 1080;
    private int startY = 650;
    private int framesToMove = 80;
    private int currentFrame = 0;

    private Queue<String> messageQueue; 
    private StillLabel speechBubble;   
    private boolean spacePressed = false; 
    private StillLabel startLabel;     
    
    public TutorialAvatar() {
        setImage("TempPlayer.png");
        setLocation(startX, startY);

        
        messageQueue = new LinkedList<>();
        messageQueue.add("Use arrow keys/WASD to move.\n Press space to continue.");
        messageQueue.add("Defeat the enemies!\nPress space to start the level.");
    }

    public void addedToWorld(World world) {
        
        startLabel = new StillLabel("Welcome to the Tutorial World! \n Press space to continue.", 14, this);
        world.addObject(startLabel, 1000, 600); 
    }

    public void act() {
        moveToTarget(); 
        handleMessages(); 
    }

    /**
     * Handles displaying messages and switching to the next message when "space" is pressed.
     */
    private void handleMessages() {
        
        if (startLabel != null && Greenfoot.isKeyDown("space")) {
            getWorld().removeObject(startLabel);
            startLabel = null; 
        }

        
        if (Greenfoot.isKeyDown("space") && !spacePressed) {
            spacePressed = true; 
            nextMessage(); 
        }

        
        if (!Greenfoot.isKeyDown("space")) {
            spacePressed = false;
        }
    }

    /**
     * Displays the next message in the queue or removes the label when done.
     */
    private void nextMessage() {
        if (!messageQueue.isEmpty()) {
            String nextText = messageQueue.poll(); 

            
            if (speechBubble == null || speechBubble.getWorld() == null) {
                speechBubble = new StillLabel(nextText, 18, this); 
                getWorld().addObject(speechBubble, getX() - 30, getY() - 70); 
            } else {
                speechBubble.setValue(nextText); 
            }
        } else {
            
            if (speechBubble != null && speechBubble.getWorld() != null) {
                getWorld().removeObject(speechBubble);
            }

            
            Greenfoot.setWorld(new LevelWorld("test3.csv")); 
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
