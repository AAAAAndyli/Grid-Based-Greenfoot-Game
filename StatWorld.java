import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A world which houses a few statistics 
 * of your gameplay
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class StatWorld extends World
{    
    private int numWeapons = 1 + SaveFile.getInt("hasBomb") + SaveFile.getInt("hasSpread") + SaveFile.getInt("hasMissile");
    private SuperTextBox text;
    private ArrayList<Class> classList = new ArrayList<Class>();
    //list of things to keep track of
    private String[] stats = new String[]{
        "Bytes earned: " + SaveFile.getString("totalMoney"), "Deaths: " + SaveFile.getString("deaths"), 
        "Current Health: " + SaveFile.getInt("health"), "Max Health: " + SaveFile.getInt("maxHealth"),
        "Current Damage: " + SaveFile.getInt("damage"), "Unlocked Weapon: " + numWeapons
    };
    private int actCounter = 0;
    /**
     * Constructor for objects of class StatWorld.
     * 
     */
    public StatWorld(World world)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1, false); 
        setBackground("images/Background/StatsBg.png");

        WorldButton back = new WorldButton("Buttons/backButton.png", 0.5, world);
        addObject(back, 85, 50);
        
        classList.add(SuperTextBox.class);
        
        text = new SuperTextBox(stats, new Color(0,0,0,0), Color.RED, new Font("Arial", true, false, 18), false, 400, 0, new Color(0, 0, 0, 0), 40);
        addObject(text, getWidth() / 2 + 20, 350);
        
        ScrollingUI scroll = new ScrollingUI(540, 350, 400, 1500, true, new Color(0, 106, 78, 100), classList, text);
        addObject(scroll, getWidth() / 2, 350);
        
        //create binary strings as decoration
        for(int i = 0; i < 14; i++){   
            BinaryString binary = new BinaryString();
            int ySpawn = binary.getDirection() == 1 ? -100 - Greenfoot.getRandomNumber(100) : Greenfoot.getRandomNumber(100) + 100 + getHeight();
            addObject(binary, Greenfoot.getRandomNumber(800) + 100, ySpawn);
        }
        
        setPaintOrder(SuperTextBox.class, ScrollingUI.class, BinaryString.class);
    }
    
    public void act(){
        actCounter++;
        //update info every 60 acts
        if(actCounter > 60){
            actCounter = 0;
            SaveFile.loadFile();
            //remove old textbox
            removeObject(text);
            //update info
            stats = new String[]{
                "Bytes earned: " + SaveFile.getString("totalMoney"), "Deaths: " + SaveFile.getString("deaths"), 
                "Current Health: " + SaveFile.getInt("health"), "Max Health: " + SaveFile.getInt("maxHealth"),
                "Current Damage: " + SaveFile.getInt("damage"), "Unlocked Weapon: " + numWeapons
            };
            //create new textbox to prevent multiple textboxes
            text = new SuperTextBox(stats, new Color(0,0,0,0), Color.RED, new Font("Arial", true, false, 18), false, 400, 0, new Color(0, 0, 0, 0), 40);
            addObject(text, getWidth() / 2 + 20, 350);
            text.update(stats);
        }
    }
}
