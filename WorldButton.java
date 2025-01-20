import greenfoot.*;  

/**
 * WorldButton class that creates a fade-in and fade-out transition when pressed.
 */
public class WorldButton extends Button
{
    private World destination;
    private TutorialWorld destinationTutorial;
    private LevelWorld destinationLevel;
    private Label label;
    private boolean pressed;
    private boolean transitioning = false; 
    private Transition transitionEffect;   
    private int transitionFrames = 240;    
    private int currentFrame = 0;    

    public WorldButton(String file, double sizeMulti, World dest) {
        super(file, sizeMulti);
        destination = dest;
    }
    
    public WorldButton(String file, double sizeMulti, World dest, Class worldClass) {
        this(file, sizeMulti, dest);
        
        if(worldClass == TutorialWorld.class){
            destinationTutorial = (TutorialWorld)dest;
        }
        else if(worldClass == LevelWorld.class){
            destinationLevel = (LevelWorld)dest;   
        }
    }

    public WorldButton(String file, double sizeMulti, World dest, Label labelReference) {
        this(file, sizeMulti, dest);
        label = labelReference;
    }

    /**
     * Act - do whatever the WorldButton wants to do.
     */
    public void act() {
        super.click();
        if (Greenfoot.mouseClicked(this)) {
            startTransition();
        }
        if (transitioning) {
            handleTransition();
        }
    }

    /**
     * Starts the transition effect when the button is pressed.
     */
    private void startTransition() {
        transitioning = true;
        transitionEffect = new Transition(); 
        getWorld().addObject(transitionEffect, getWorld().getWidth() / 2, getWorld().getHeight() / 2); 
    }

    /**
     * Handles the fade-in and fade-out transition logic.
     */
    private void handleTransition() {
        currentFrame++;
        World current = getWorld();
        if(Greenfoot.mouseClicked(transitionEffect)){
            currentFrame = 240;
            getWorld().removeObject(transitionEffect);
            
            if(current instanceof MenuWorld){
                ((MenuWorld)getWorld()).background.stop();
            }else if(current instanceof SettingWorld){
                //update binds/volume level
                SaveFile.loadFile();
                if(destinationLevel != null){
                    destinationLevel.getPlayer().setRunOnce(false);
                }
                if(destinationTutorial != null){
                    destinationTutorial.getPlayer().setRunOnce(false);
                }
                
                ((SettingWorld)getWorld()).background.stop();
            }else if(current instanceof Shop){
                ((Shop)getWorld()).shopMusic.stop();
            }

            Greenfoot.setWorld(destination);
            
        }
        
        else if (currentFrame == transitionFrames / 2) {
            getWorld().removeObject(transitionEffect);
            if(current instanceof MenuWorld){
                ((MenuWorld)getWorld()).background.stop();
            }else if(current instanceof SettingWorld){
                ((SettingWorld)getWorld()).background.stop();
            }else if(current instanceof Shop){
                ((Shop)getWorld()).shopMusic.stop();
            }


            Greenfoot.setWorld(destination); 
            
        }

        
        else if (currentFrame >= transitionFrames) {
            transitioning = false; 
            currentFrame = 0; 
            if (transitionEffect != null && transitionEffect.getWorld() != null) {
                getWorld().removeObject(transitionEffect);
                
            }
        }
    }
}
