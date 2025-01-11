import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, and MouseInfo)
import java.util.Queue;
import java.util.LinkedList;

/**
 * TutorialAvatar class.
 */
public class TutorialAvatar extends SuperSmoothMover {
    private int targetX = 1020; 
    private int targetY = 650; 
    private int startX = 1080; 
    private int startY = 650; 
    private int framesToMove = 60; 
    private int currentFrame = 0; 
    private SpeechBubble speechBubble;
    private Queue<String> messageQueue;
    public TutorialAvatar() {
        setImage("TempPlayer.png");
        setLocation(startX, startY); 
        messageQueue = new LinkedList<>();
    }

    public void act() {
        moveToTarget(); 
        if (Greenfoot.isKeyDown("space") && speechBubble == null) {
            messageQueue.clear(); 
            messageQueue.add("1"); 
            messageQueue.add("2");
            messageQueue.add("3");
            nextMessage(); 
        }
    }

    public void nextMessage() {
        
        if (!messageQueue.isEmpty()) {
            String nextText = messageQueue.poll(); 
            if (speechBubble == null || speechBubble.getWorld() == null) {
                
                speechBubble = new SpeechBubble(nextText, 300, this);
                getWorld().addObject(speechBubble, getX(), getY() - 50);
            } else {
                
                speechBubble.updateText(nextText, 300);
            }
        } else {
            
            if (speechBubble != null && speechBubble.getWorld() != null) {
                getWorld().removeObject(speechBubble);
                speechBubble = null;
            }
        }
    }
    
    private void moveToTarget() {
        
        if (currentFrame < framesToMove) {
            
            double stepX = (targetX - startX) / (double) framesToMove;
            double stepY = (targetY - startY) / (double) framesToMove;

            
            setLocation(getX() + stepX, getY() + stepY);

            
            currentFrame++;
        }
    }
    
    public void speak(String text, int duration) {
        if (speechBubble == null || speechBubble.getWorld() == null) {
            
            speechBubble = new SpeechBubble(text, duration, this);
            getWorld().addObject(speechBubble, getX(), getY() - 50); 
        } else {
            
            speechBubble.updateText(text, duration);
        }
    }

}
