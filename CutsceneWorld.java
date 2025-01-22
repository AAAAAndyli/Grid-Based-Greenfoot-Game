import greenfoot.*;
import java.util.ArrayList;

/**
 * majority of code here made with chat GPT. It's 4 am. I want to sleep.
 */

public class CutsceneWorld extends World {
    private ArrayList<GreenfootImage> cutscene = new ArrayList<GreenfootImage>();
    private int currentSceneIndex = 0; // Tracks the current cutscene image
    private boolean spaceKeyDown = false; // Tracks the space key press state
    private World previousWorld; // Reference to the world before the cutscene
    private boolean isNotCutScene = false;
    private Label help;
    
    /**
     * Constructor for objects of class CutsceneWorld.
     */
    public CutsceneWorld(World world) 
    {    
        super(1080, 720, 1); 
        previousWorld = world;
        setPaintOrder(Transition.class, Button.class, CutsceneWorld.class);
        
        help = new Label("Press Space to proceed", 30);
        addObject(help, getWidth() - 150, getHeight() - 30);
        
        if(world.getClass() == LevelWorld.class)
        {
            initializeCutsceneImages(((LevelWorld)world).getCutscene());
            if(cutscene.size() > 0)
            {
                setBackground(cutscene.get(currentSceneIndex)); // Set the initial background
            }
        }
        else if(world.getClass() == ArSYSStartingWorld.class)
        {
            initializeCutsceneImages(3);
            setBackground(cutscene.get(currentSceneIndex)); // Set the initial background
        }
        else if(world instanceof MenuWorld)
        {
            initializeCutsceneImages(4);
            setBackground(cutscene.get(currentSceneIndex)); // Set the initial background
        }
        else
        {
            isNotCutScene = true;
        }
    }

    public void act() 
    {
        if(isNotCutScene || cutscene.size() <= 0)
        {
            Greenfoot.setWorld(previousWorld);
        }
        handleInput();
    }

    /**
     * Initializes the cutscene images into the cutscene list.
     */
    private void initializeCutsceneImages(int cutsceneNumber) 
    {
        if(cutsceneNumber == 0)
        {
            for(int i = 0; i < 5 ; i++)
            {
                cutscene.add(new GreenfootImage("Cutscene/first/" + i + ".png"));
            }
        }
        else if(cutsceneNumber == 1)
        {
            for(int i = 0; i < 4 ; i++)
            {
                cutscene.add(new GreenfootImage("Cutscene/second/" + i + ".png"));
            }
        }
        else if(cutsceneNumber == 4)
        {
            for(int i = 0; i < 3 ; i++)
            {
                cutscene.add(new GreenfootImage("Cutscene/fourth/" + i + ".png"));
            }
        }
        else if(cutsceneNumber != -1)
        {
            cutscene.add(new GreenfootImage("Cutscene/third/0.png"));
        }
    }

    private void handleInput() 
    {
        if(Greenfoot.isKeyDown("space")) 
        {
            if(!spaceKeyDown)
            {
                spaceKeyDown = true; 
                advanceCutscene();
            }
        }
        else
        {
            spaceKeyDown = false; 
        }
    }

    /**
     * Advances the cutscene to the next image or exits the cutscene.
     */
    private void advanceCutscene() 
    {
        currentSceneIndex++;
        if (currentSceneIndex < cutscene.size()) 
        {
            setBackground(cutscene.get(currentSceneIndex));
        } else 
        {
            Greenfoot.setWorld(previousWorld);
        }
    }
}
