import greenfoot.*; 
import java.util.List;

/**
 * Write a description of class ScrollingWorld here.
 * 
 * @author Allan L.
 * @version (a version number or a date)
 */
public class SpeechBubble extends Actor {
    
    private TutorialAvatar tutorialAvatar; 
    private boolean spacePressed = false; 

    public SpeechBubble(String text, TutorialAvatar tutorialAvatar) {
        this.tutorialAvatar = tutorialAvatar;
        updateImage(text);
    }

    public void act() {
        
        if (Greenfoot.isKeyDown("space") && !spacePressed) {
            spacePressed = true; 
            tutorialAvatar.nextMessage(); 
        }

        
        if (!Greenfoot.isKeyDown("space")) {
            spacePressed = false; 
        }
    }

    public void updateText(String text) {
        updateImage(text); 
    }

    private void updateImage(String text) {
        GreenfootImage bubble = new GreenfootImage(200, 100); 
        bubble.setColor(Color.WHITE); 
        bubble.fillRect(0, 0, bubble.getWidth(), bubble.getHeight()); 

        bubble.setColor(Color.BLACK); 
        bubble.drawRect(0, 0, bubble.getWidth() - 1, bubble.getHeight() - 1); 

        bubble.setColor(Color.BLACK); 
        bubble.setFont(new Font("Arial", 14)); 

        
        int textWidth = bubble.getWidth() - 20; 
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int y = 30;
        for (String word : words) {
            if (bubble.getAwtImage().getGraphics().getFontMetrics().stringWidth(line + word) > textWidth) {
                bubble.drawString(line.toString(), 10, y);
                line = new StringBuilder(word + " ");
                y += 20;
            } else {
                line.append(word).append(" ");
            }
        }
        bubble.drawString(line.toString(), 10, y);

        setImage(bubble); 
    }
}
