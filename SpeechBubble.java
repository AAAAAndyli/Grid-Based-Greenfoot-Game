import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot, MouseInfo)
import java.util.List;

public class SpeechBubble extends Actor {
    private int duration; 
    private TutorialAvatar tutorialAvatar; 

    public SpeechBubble(String text, int duration, TutorialAvatar tutorialAvatar) {
        this.duration = duration;
        this.tutorialAvatar = tutorialAvatar;
        updateImage(text);
    }

    public void act() {
        
        if (duration > 0) {
            duration--;
        } else {
            getWorld().removeObject(this);
        }

        
        if (Greenfoot.mouseClicked(this)) {
            tutorialAvatar.nextMessage(); 
        }
    }

    public void updateText(String text, int duration) {
        this.duration = duration; 
        updateImage(text); 
    }

    private void updateImage(String text) {
        GreenfootImage bubble = new GreenfootImage(200, 100); 
        bubble.setColor(Color.WHITE); 
        bubble.fillRect(0, 0, bubble.getWidth(), bubble.getHeight()); 

        bubble.setColor(Color.BLACK); 
        bubble.drawRect(0, 0, bubble.getWidth() - 1, bubble.getHeight() - 1); 

        bubble.setColor(Color.BLACK); 
        bubble.setFont(new Font("Arial", 16)); 

        
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
