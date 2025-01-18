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

    private String[] tutorialMessages = {
        "Welcome to the tutorial. Right arrow to continue",
        "Use WASD or set them in the Settings Menu to move. Right arrow to continue",
        "Reach as far as possible while not dying. Right arrow to continue",
        "You can trigger the monster spawns to practice. Right arrow to continue",
        "Press the right arrow key to start the levels and meet your fate..."
    };

    private int currentMessage = 0;
    private SuperTextBox label;
    private boolean hasDisplayedMessage = false; 
    
    public TutorialAvatar() {
        setImage("TempPlayer.png");
        setLocation(startX, startY);

        
        label = new SuperTextBox(tutorialMessages[currentMessage], new Font("Arial", true, false, 14), 550);
        
    }

    public void addedToWorld(World world) {
        
    }

    public void act() {
        moveToTarget();
        handleKey();

        
        if (currentFrame >= framesToMove && !hasDisplayedMessage) {
            hasDisplayedMessage = true; 
            getWorld().addObject(label, getX() - 250, getY() - 70); 
        }
    }

    /**
     * Handles displaying messages and switching to the next message when "right arrow" is pressed.
     */
    private void handleKey() {
        if (Greenfoot.isKeyDown("right") && currentMessage < tutorialMessages.length - 1) {
            currentMessage++;
            label.update(tutorialMessages[currentMessage]); 
            Greenfoot.delay(10); 
        }else if(Greenfoot.isKeyDown("right") && currentMessage >= tutorialMessages.length - 1){
            Greenfoot.setWorld(new LevelWorld());
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
