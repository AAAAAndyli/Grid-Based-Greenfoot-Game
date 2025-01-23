import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootSound;
import java.util.ArrayList;

/**
 * By: Darren T, Justin Y, Brennan L, Andy L, Braden C, Allan L
 * Written by: Darren T
 * 2025-01-21
 * 
 * A platformer rogue-like about a computer worm attempting to infect an AI running a mass surveillance system
 * 
 * Features:
 *      Custom Made Graphics: Nearly every monster and image was drawn/animated by 
 *                            Andy
 *                  
 *      Customizable Experience: Settings will allow you to change... 
 *                              - Movement keybinds
 *                              - Weapon swap keybinds
 *                              - Adjust music and sound effects
 *
 *      Upgradeable Character: Shoot and explode through tons of enemies to gain
 *                              bytes, which allow you to strengthen your character's
 *                              health and attack in the shop
 *
 *      Multiple Weapons: Buy and use 4 different weapons, suiting different scenarios 
 *                          such as single target, AoE, and Rocket Jumping (!)
 *                      
 *      Robust Movement: Clean mechanics such as increased height when slam jumping 
 *                          (hint: w, a, then w for extra height)
 *                          and invincibility frames from dashing allow you to feel like
 *                          a pro with ease!
 *                          
 *      Varied Bosses: Custom made bosses will shock and awe you before killing you!
 *      
 *      Hardcore Mode: Deaths will reset all progress, including money, weapons, and levels...
 *          Mr Cohen Mode: Feel free to change money and health to 999 in saveFile.csv if it's too hard, it's okay!
 *          
 *      Custom Music Producing: Nearly every music was original and produced by Justin and Andy
 *          
 * Sources:
 * 
 * Images:
 *      Padlock - https://stock.adobe.com/search/images?k=cartoon+padlock
 *      Spreadshot inventory and shop icon - https://www.vecteezy.com/vector-art/5450129-shotgun-pixel-art - Spreadshot
 *      Bombshot inventory and shop icon - https://www.vecteezy.com/free-vector/pixel-bomb - Bombshot
 *      Rocketshot inventory and shop icon - https://www.vecteezy.com/vector-art/22276361-rocket-launcher-in-pixel-art-style
 *      RapidFire inventory icon - https://www.reddit.com/r/PixelArt/comments/t0klk1/i_made_a_machine_gun_i_like_my_machine_gun_do_you/?rdt=62969
 *      All 
 *      Most other images made by Andy
 * 
 * Sounds:
 *      Purchase - https://www.myinstants.com/en/instant/pixel-gun-3d-purchase-sound-18/ 
 *      Parry - https://www.myinstants.com/en/instant/parry-ultrakill-37032/ 
 *      No Bytes - https://www.myinstants.com/en/instant/windows-error-remix-16451/ 
 *               - https://www.myinstants.com/en/instant/error-soundss-25534/ 
 *      getHurt - https://www.soundsnap.com/tags/damage
 *      jumpSound - https://pixabay.com/sound-effects/search/jump/
 *      dashSound - https://pixabay.com/sound-effects/search/teleport/
 *      pickUp - https://pixabay.com/sound-effects/search/key-pickup/
 *      Any Sounds not mentioned above are from Ultrakill
 *      
 *      Music:
 *      Most composed by Justin
 *      Obersavtion without operation by Andy
 *      Scorced Earth (Death of God's Will) By Hakita "Arsi" Patala for Ultrakill
 *      
 *      
 * Code:
 *      Code was used that was made by Mr. Cohen such as SuperStatBar class, 
 *      SuperSmoothMover class, Coordinate class
 *      A* PseudoCode: https://medium.com/@nicholas.w.swift/easy-a-star-pathfinding-7e6689c7f7b2
 *      Most of Cutscene world was AI generated due to it being 3 in the morning, though was retrofitted to work properly.
 *      
 */
public class MenuWorld extends World
{
    public GreenfootSound background;
    public GreenfootSound clickSound;

    private boolean movingDown = true;
    private double speed = 0.4;

    private GreenfootSound[] musicList, effectList;

    private int previousMusicVolume, previousEffectVolume;

    private Button title;
    private Button resetData;
    private WorldButton play, settings, stat;

    /**
     * Constructor for objects of class MenuWorld.
     * 
     */
    public MenuWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        background = new GreenfootSound("Opening.mp3");
        clickSound = new GreenfootSound("click.wav");

        setBackground("images/menu.png");

        title = new Button("title.png",.9,true);
        addObject(title,getWidth()/3, 150);

        resetData = new Button("Buttons/resetData.png",0.8);
        addObject(resetData, 150, 620);

        //load save file for data
        SaveFile.loadFile();

        WorldOrder.createArrayList();

        //update bgm volume
        previousMusicVolume = SaveFile.getInt("musicVolume");
        // ** IMPORTANT **
        //make sure to set a volume value for ALL sounds, otherwise it defaults to 0 even if sound plays
        background.setVolume(20); 
        //make sure to update the volume with values from savefile!
        SaveFile.updateVolume(background, "musicVolume");

        //make sure to update sound effects volume as shown above
        previousEffectVolume = SaveFile.getInt("effectVolume");

        //Label shopLabel = new Label("Shop", 30);
        //addObject(shopLabel, 900, 600);

        if (SaveFile.getInt("worldIndex") != -1 && WorldOrder.currentWorld() != null) {
            if(WorldOrder.currentWorld().equals("Arsys"))
            {
                play = new WorldButton("Buttons/playButton.png", 1.1, (World)new Shop(new CutsceneWorld(new ArSYSStartingWorld())));
                return;
            }
            if (WorldOrder.isArSYS()) {
                play = new WorldButton("Buttons/playButton.png", 1.1, (World)new ArsysWorld(WorldOrder.currentWorld()));
            } else {
                play = new WorldButton("Buttons/playButton.png", 1.1, (World)new Shop(new CutsceneWorld(new LevelWorld(WorldOrder.currentWorld()))));
            }
        } else {
            play = new WorldButton("Buttons/playButton.png", 1.1, (World)new Tutorial(this));
        }
        
        addObject(play, 900, 300);
        settings = new WorldButton("Buttons/settingsButton.png", 1.1, (World)new SettingWorld(this));
        addObject(settings, 900, 450);
        //temporary
        //WorldButton shop = new WorldButton("Buttons/button1.png", 1.1, (World)new Shop(this), shopLabel);
        //addObject(shop, 900, 600);

        stat = new WorldButton("Buttons/stats.png", 1.1, (World)new StatWorld(this));
        addObject(stat, 900, 600);

        setPaintOrder(Transition.class, Label.class, Button.class);
    }

    public void act(){
        if(resetData.checkButton())
        {
            resetStats();
            removeObject(play);
            play = new WorldButton("Buttons/playButton.png", 1.1, (World)new Tutorial(this));
            addObject(play, 900, 300);
        }
        if(previousMusicVolume != SaveFile.getInt("musicVolume")){
            //update the list with each new music
            musicList = new GreenfootSound[]
            {
                background,
            };
            SaveFile.updateVolume(musicList, "musicVolume");
            previousMusicVolume = SaveFile.getInt("musicVolume");
        }
        if(previousEffectVolume != SaveFile.getInt("effectVolume")){
            //update the list with each new effect
            effectList = new GreenfootSound[]
            {
                clickSound
            };
            SaveFile.updateVolume(effectList, "effectVolume");
            previousEffectVolume = SaveFile.getInt("musicVolume");
        }
        background.playLoop();
        hover();
    }

    public void stopped(){
        background.pause();
    }

    public void started(){
        background.playLoop();
    }

    public void hover()
    { 
        if (movingDown)
        {
            title.setLocation(title.getExactX(), title.getPreciseY() + speed);
            play.setLocation(play.getExactX(), play.getPreciseY() + speed);
            settings.setLocation(settings.getExactX(), settings.getPreciseY() + speed);
            stat.setLocation(stat.getExactX(), stat.getPreciseY() + speed);
            resetData.setLocation(resetData.getExactX(), resetData.getPreciseY() + speed);
            if (title.getY() >= 170) 
            {
                movingDown = false; 
            }
        }
        else
        {
            title.setLocation(title.getExactX(), title.getPreciseY() - speed);
            play.setLocation(play.getExactX(), play.getPreciseY() - speed);
            settings.setLocation(settings.getExactX(), settings.getPreciseY() - speed);
            stat.setLocation(stat.getExactX(), stat.getPreciseY() - speed);
            resetData.setLocation(resetData.getExactX(), resetData.getPreciseY() - speed);
            if (title.getY() <= 150) 
            {
                movingDown = true;
            }
        }
    }

    public void resetStats()
    {
        SaveFile.setInfo("money", 0); // 0
        SaveFile.setInfo("totalMoney", 0);
        SaveFile.setInfo("deaths", 0);
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
