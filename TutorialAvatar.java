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

    private Queue<String> messageQueue; // Queue to store messages
    private StillLabel speechBubble;   // Label to display messages
    private boolean spacePressed = false; // Tracks if the space key is pressed

    public TutorialAvatar() {
        setImage("TempPlayer.png");
        setLocation(startX, startY);

        // Initialize the message queue with tutorial messages
        messageQueue = new LinkedList<>();
        messageQueue.add("Welcome to WORM-E\nPress space to continue.");
        messageQueue.add("Use arrow keys/WASD to move.\nPress space to continue.");
        messageQueue.add("Defeat the enemies!\nPress space to start the level.");
    }

    public void act() {
        moveToTarget(); // Handle smooth movement
        handleMessages(); // Handle message display and switching
    }

    /**
     * Handles displaying messages and switching to the next message when "space" is pressed.
     */
    private void handleMessages() {
        // Check if space is pressed and hasn't been processed yet
        if (Greenfoot.isKeyDown("space") && !spacePressed) {
            spacePressed = true; // Mark the space key as pressed
            nextMessage(); // Display the next message
        }

        // Reset the spacePressed flag when the key is released
        if (!Greenfoot.isKeyDown("space")) {
            spacePressed = false;
        }
    }

    /**
     * Displays the next message in the queue or removes the label when done.
     */
    private void nextMessage() {
        if (!messageQueue.isEmpty()) {
            String nextText = messageQueue.poll(); // Get the next message

            // Create a new label if none exists or update the existing one
            if (speechBubble == null || speechBubble.getWorld() == null) {
                speechBubble = new StillLabel(nextText, 18, this); // Create a StillLabel
                getWorld().addObject(speechBubble, getX(), getY() - 70); // Position above the avatar
            } else {
                speechBubble.setValue(nextText); // Update the text of the existing label
            }
        } else {
            // If all messages are displayed, remove the label and proceed
            if (speechBubble != null && speechBubble.getWorld() != null) {
                getWorld().removeObject(speechBubble);
            }

            // Perform any additional actions after the tutorial (e.g., start the level)
            Greenfoot.setWorld(new LevelWorld("test3.csv")); // Example transition to the next world
            getWorld().removeObject(this); // Remove the tutorial avatar
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
