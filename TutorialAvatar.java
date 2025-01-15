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
            messageQueue.add("Welcome to WORM-E \n Press space to continue"); 
            messageQueue.add("Use arrow keys/WASD keys to move \n Press space to continue");
            messageQueue.add("Defeat the enemies! \n Press space to start the level");
            nextMessage(); 
        }
    }

    public void nextMessage() {
        
        if (!messageQueue.isEmpty()) {
            String nextText = messageQueue.poll(); 
            if (speechBubble == null || speechBubble.getWorld() == null) {
                
                speechBubble = new SpeechBubble(nextText, this);
                getWorld().addObject(speechBubble, getX() - 50, getY() - 70);
            } else {
                
                speechBubble.updateText(nextText);
            }
        }else{
            getWorld().removeObject(speechBubble);
            
            Greenfoot.setWorld(new LevelWorld("test3.csv"));
            getWorld().removeObject(this);
            
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
            
            speechBubble = new SpeechBubble(text, this);
            getWorld().addObject(speechBubble, getX(), getY() - 50); 
        } else {
            
            speechBubble.updateText(text);
        }
    }

}
