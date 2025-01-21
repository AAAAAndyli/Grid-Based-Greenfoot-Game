import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)
import java.util.Queue;
import java.util.LinkedList;

/**
 * Write a description of class Pause here.
 * 
 * @author Allan L.
 * @version (a version number or a date)
 */
public class TutorialAvatar extends SuperSmoothMover {
    private int targetX = 1000;
    private int targetY = 650;
    private int startX = 1080;
    private int startY = 650;
    private int framesToMove = 80;
    private int currentFrame = 0;

    private String[] tutorialMessages = {
        "Welcome to the tutorial. \n Right arrow to continue",
        "Use WASD or set them in the Settings Menu \n to move. Right arrow to continue",
        "Use E to parry, changeable in Settings. \n Right arrow to continue",
        "Defeat monsters to collect cash for \n upgrades. Right arrow to continue",
        "To switch weapons, press 1/2/3/4. Right \n arrow to continue",
        "Hotkey 1 is an automatic, 2 is \n a projectile, 3 is a rocket launcher \n and 4 is a shotgun Right arrow to continue",
        "Reach as far as possible while not \n dying. Right arrow to continue",
        "You can trigger the monster spawns to \n practice. Right arrow to continue",
        "Some places have firewalls. Find keys \n to access past it Right arrow to continue",
        "Find the portal and press space to move \n into the next level.. Good luck"
    };
    
    
    private int currentMessage = 0;
    private SuperTextBox label;
    private boolean hasDisplayedMessage = false; 
    private int actCounter = 10;
    public TutorialAvatar() {
        setImage("Tera.png");
        setLocation(startX, startY);

        
        label = new SuperTextBox(tutorialMessages[currentMessage], new Font("Arial", true, false, 18), 400, 40);
        
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
            if(actCounter == 10){
                currentMessage++;
                label.update(tutorialMessages[currentMessage]);
                actCounter = 0;
            }
            actCounter++;
        }else if(Greenfoot.isKeyDown("right") && currentMessage >= tutorialMessages.length - 1){
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
