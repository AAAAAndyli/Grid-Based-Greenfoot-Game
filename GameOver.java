import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * It's the world when you lose
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class GameOver extends World
{
    private int actTimer = 0;
    private Transition transision = new Transition(true);
    String[] tips = new String[]{
        "Tip: A successful parry will give HP!",
        "Tip: Using a bomb or missile while \njumping provides extra height!",
        "Tip: Jumping again after slamming \ndown provides extra height!",
        "Tip: Bomb is a good AoE weapon!"
    };
    private Label tipLabel;
    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameOver()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        setBackground("GameOver.png");
        tipLabel = new Label(tips[Greenfoot.getRandomNumber(tips.length)], 40);
        addObject(tipLabel, getWidth() / 2, getHeight() / 2 + 150);
    }
    public void act()
    {
        actTimer++;
        if(actTimer == 300)
        {
            addObject(transision, 540, 360);
        }
        if(transision.fadedOnce())
        {
            SaveFile.setInfo("deaths", SaveFile.getInt("deaths") + 1);
            resetStats();
            Greenfoot.setWorld(new MenuWorld());
        }
    }
    public void resetStats()
    {
        SaveFile.setInfo("money", 0); // 0
        SaveFile.setInfo("health", 15);
        SaveFile.setInfo("maxHealth", 15);
        SaveFile.setInfo("level", 0);
        SaveFile.setInfo("damage", 1); // 1
        SaveFile.setInfo("hasBomb", 0); // 0
        SaveFile.setInfo("hasMissile", 0); // 0
        SaveFile.setInfo("hasSpread", 0); // 0
        SaveFile.setInfo("worldIndex", -1);
    }
}
