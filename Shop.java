import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Shop here.
 * 
 * @author (Justin Ye) 
 * @version (a version number or a date)
 * Credits for a few shopIcons:
 * -https://www.vecteezy.com/vector-art/5450129-shotgun-pixel-art - Spreadshot
 * -https://www.vecteezy.com/free-vector/pixel-bomb - Bombshot
 * -https://www.vecteezy.com/vector-art/22276361-rocket-launcher-in-pixel-art-style - Rocketshot
 * -https://www.reddit.com/r/PixelArt/comments/t0klk1/i_made_a_machine_gun_i_like_my_machine_gun_do_you/?rdt=62969 - (default)Rapidfire

 */
public class Shop extends World
{
    //source - https://www.greenfoot.org/topics/821
    private GreenfootImage[] shopAnimation = new GreenfootImage[35];
    
    private int money;
    private int price;
    private int health;
    private int maxHealth;
    private int imageIndex = 0;
    private int maxHealthUpgrade = 2;
    private double speed = 0.4;
    
    //Handles Animation
    SimpleTimer animationTimer = new SimpleTimer();
    
    //Switches that help implement shop UI
    private boolean clickedOne;
    private boolean clickedTwo;
    private boolean clickedThree;
    private boolean clickedFour;
    private boolean clickedFive;
    private boolean clickedSix;
    
    private boolean isHoveringImage1 = false;
    private boolean isHoveringImage2 = false;
    private boolean isHoveringImage3 = false;
    private boolean isHoveringImage4 = false;
    private boolean isHoveringImage5 = false;
    private boolean isHoveringImage6 = false;
    
    private boolean hoverMovement = true;
    private boolean movingDown = true;
    private boolean purchased = false;
    
    //Buttons and images using a specific button constructor
    private Button item1 = new Button("Buttons/purchaseButton.png",.7);
    private Button item2 = new Button("Buttons/purchaseButton.png",.7);
    private Button item3 = new Button("Buttons/purchaseButton.png",.7);
    private Button item4 = new Button("Buttons/purchaseButton.png",.7);
    private Button item5 = new Button("Buttons/purchaseButton.png",.7);
    private Button item6 = new Button("Buttons/purchaseButton.png",.7);
    
    private Button image1 = new Button("shopIcons/plusDamage.png",1.5, true);
    private Button image2 = new Button("shopIcons/fullHealth.png",1, true);
    private Button image3 = new Button("shopIcons/plusHP.png",.8, true);
    private Button image4 = new Button("shopIcons/rocketShot.png",.8, true);
    private Button image5 = new Button("shopIcons/spreadShot.png",.8, true);
    private Button image6 = new Button("shopIcons/bombShot.png",.8, true);
    private Button image1Desc;
    private Button image2Desc;
    private Button image3Desc;
    private Button image4Desc;
    private Button image5Desc;
    private Button image6Desc;
    
    private Wallet wallet;
    
    GreenfootSound shopMusic;
    
    GreenfootSound[] musicList, effectList;
    
    int previousMusicVolume, previousEffectVolume;
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop(World world)
    {    
        //Adds all objects in the shop class, aand takes saved values from a file
        super(1080, 720, 1); 
        
        shopMusic = new GreenfootSound("Shop.mp3");
        shopMusic.setVolume(40);
        SaveFile.updateVolume(shopMusic, "musicVolume");
        
        money = SaveFile.getInt("money");
        health = SaveFile.getInt("health");
        maxHealth = SaveFile.getInt("maxHealth");
        
        for(int i = 0; i < shopAnimation.length; i++){
            shopAnimation[i] = new GreenfootImage("images/ShopBg/shop" + i  + ".png");
        }
        setBackground(shopAnimation[0]);
        
        wallet = new Wallet();
        addObject(wallet, 120, 620);
        
        addObject(item1, 250, 300);
        addObject(image1, 250, 150);
        
        addObject(item2, 250, 600);
        addObject(image2, 250, 450);
        
        addObject(item3, 600, 300);
        addObject(image3, 600, 150);
        
        addObject(item4, 600, 600);
        addObject(image4, 600, 450);
        
        addObject(item5, 950, 300);
        addObject(image5, 950, 150);
        
        addObject(item6, 950, 600);
        addObject(image6, 950, 450);
        
        WorldButton back = new WorldButton("Buttons/backButton.png", 0.5, world);
        addObject(back, 85, 50);
        
        purchased = false;
        
        //make sure to update the volume with values from savefile!
        previousMusicVolume = SaveFile.getInt("musicVolume");
        //make sure to update sound effects volume as shown above
        previousEffectVolume = SaveFile.getInt("effectVolume");
        
        setPaintOrder(Transition.class, Cursor.class, Label.class, Button.class, Shop.class);
    }
    
    public void act()
    {
        purchase();
        animate();
        checkMouseHover();
        hover();
        if(previousMusicVolume != SaveFile.getInt("musicVolume")){
            //update the list with each new music
            musicList = new GreenfootSound[]
            {
                shopMusic,
            };
            SaveFile.updateVolume(musicList, "musicVolume");
            previousMusicVolume = SaveFile.getInt("musicVolume");
        }
        if(previousEffectVolume != SaveFile.getInt("effectVolume")){
            //update the list with each new effect
            effectList = new GreenfootSound[]
            {
                
            };
            //UNCOMMENT WHEN EFFECTS ADDED
            //SaveFile.updateVolume(effectList, "effectVolume");
            //previousEffectVolume = SaveFile.getInt("musicVolume");
        }
        shopMusic.playLoop();
    }
    
    public void stopped(){
        shopMusic.pause();
    }
    public void started(){
        shopMusic.playLoop();
    }
    
    /**
     * Handles all UI related interactions around the purchasing of products in Shop class
     */
    public void purchase()
    {
        //checks all button statuses at all times
        clickedOne = item1.checkButton();
        clickedTwo = item2.checkButton();
        clickedThree = item3.checkButton();
        clickedFour = item4.checkButton();
        clickedFive = item5.checkButton();
        clickedSix = item6.checkButton();
        if(SaveFile.getInt("hasMissile") == 1)
        {
            removeObject(item4);
            addObject(new Button("shopIcons/bought.png",.7, true),600,600);
        }
        if(SaveFile.getInt("hasSpread") == 1)
        {
            removeObject(item5);
            addObject(new Button("shopIcons/bought.png",.7, true),950,300);
        }
        if(SaveFile.getInt("hasBomb") == 1)
        {
            removeObject(item6);
            addObject(new Button("shopIcons/bought.png",.7, true),950,600);
        }
        // If clicked first button it takes money in exchange to increase Shield level, which increases the amount of times you can parry
        if(clickedOne)
        {
            price = 100;
            if(money >= price)
            {
                wallet.changeAmount(-price);
                purchased = true;
                
                SaveFile.setInfo("damage", SaveFile.getInt("damage") + 1);
            }
            else
            {
                Button noByte = new Button("Buttons/noBytes.png", .25, true);
                addObject(noByte, 250, 150);
                noByte.fadeOut();
            }
        }
        
        // If clicked second button it takes money in exchange to replenish health to full
        else if(clickedTwo)
        {
            price = 50;
            if(money >= price)
            {
                wallet.changeAmount(-price);
                health = maxHealth;
                removeObject(item2);
                addObject(new Button("shopIcons/bought.png",.7, true),250,600);
                purchased = true;
                SaveFile.setInfo("health", health);
            }
            else
            {
                Button noByte = new Button("Buttons/noBytes.png", .25, true);
                addObject(noByte, 250, 450);
                noByte.fadeOut();
            }
        }
        else if(clickedThree)
        {
            if(maxHealthUpgrade > 0)
            {
                price = 50;
                if(money >= price)
                {
                    wallet.changeAmount(-price);
                    maxHealthUpgrade--;
                    maxHealth += 3;
                    health += 3;
                    purchased = true;
                    SaveFile.setInfo("health", health);
                    SaveFile.setInfo("maxHealth", maxHealth);
                }
                else
                {
                    Button noByte = new Button("Buttons/noBytes.png", .25, true);
                    addObject(noByte, 600, 150);
                    noByte.fadeOut();
                }
            }
            else 
            {
                removeObject(item3);
                addObject(new Button("shopIcons/bought.png",.7, true),600,300);
            }
        }
        else if(clickedFour)
        {
            price = 150;
            if(money >= price)
            {
                wallet.changeAmount(-price);
                purchased = true;
                SaveFile.setInfo("hasMissile", 1);
            }
            else
            {
                Button noByte = new Button("Buttons/noBytes.png", .25, true);
                addObject(noByte, 600, 450);
                noByte.fadeOut();
            }
        }
        else if(clickedFive)
        {
            price = 50;
            if(money >= price)
            {
                wallet.changeAmount(-price);
                purchased = true;
                SaveFile.setInfo("hasSpread", 1);
            }
            else
            {
                Button noByte = new Button("Buttons/noBytes.png", .25, true);
                addObject(noByte, 950, 150);
                noByte.fadeOut();
            }
        }
        else if(clickedSix)
        {
            price = 150;
            if(money >= price)
            {
                wallet.changeAmount(-price);
                purchased = true;
                SaveFile.setInfo("hasBomb", 1);
            }
            else
            {
                Button noByte = new Button("Buttons/noBytes.png", .25, true);
                addObject(noByte, 950, 450);
                noByte.fadeOut();
            }
        }
        
        
        if(purchased){
            purchased = false;
        }
    }
    
    /**
     * Animates the background
     */
    public void animate()
    {
        if(animationTimer.millisElapsed() < 100){
            return;
        }
        animationTimer.mark();
        setBackground(shopAnimation[imageIndex]);
        imageIndex = (imageIndex + 1) % shopAnimation.length;
    }
    
    /**
     * For decoration purposes, it Oscillates images back and forth
     */
    public void hover()
    { 
        if (movingDown)
        {
            image1.setLocation(image1.getExactX(), image1.getPreciseY() + speed);
            image2.setLocation(image2.getExactX(), image2.getPreciseY() + speed);
            image3.setLocation(image3.getExactX(), image3.getPreciseY() + speed);
            image4.setLocation(image4.getExactX(), image4.getPreciseY() + speed);
            image5.setLocation(image5.getExactX(), image5.getPreciseY() + speed);
            image6.setLocation(image6.getExactX(), image6.getPreciseY() + speed);
            if (image1.getY() >= 170) 
            {
                movingDown = false; 
            }
        }
        else
        {
            image1.setLocation(image1.getExactX(), image1.getPreciseY() - speed);
            image2.setLocation(image2.getExactX(), image2.getPreciseY() - speed);
            image3.setLocation(image3.getExactX(), image3.getPreciseY() - speed);
            image4.setLocation(image4.getExactX(), image4.getPreciseY() - speed);
            image5.setLocation(image5.getExactX(), image5.getPreciseY() - speed);
            image6.setLocation(image6.getExactX(), image6.getPreciseY() - speed);
            if (image1.getY() <= 150) 
            {
                movingDown = true;
            }
        }
    }
    
    /**
     * Gives a good description of what each product is by hovering it over the icon
     */
    public void checkMouseHover()
    {
        // Check if the mouse is over image1
        if (Greenfoot.mouseMoved(image1)) {
            hoverMovement = true;
            if (!isHoveringImage1) {
                image1Desc = new Button("shopIcons/text1.png", 1, true);
                addObject(image1Desc, image1.getX() + 150, image1.getY());
                isHoveringImage1 = true;
            }
        } else if (Greenfoot.mouseMoved(null) && isHoveringImage1) {
            removeObject(image1Desc);
            isHoveringImage1 = false;
        }
    
        // Check if the mouse is over image2
        if (Greenfoot.mouseMoved(image2)) {
            hoverMovement = true;
            if (!isHoveringImage2) {
                image2Desc = new Button("shopIcons/text2.png", 1, true);
                addObject(image2Desc, image2.getX() + 150, image2.getY());
                isHoveringImage2 = true;
            }
        } else if (Greenfoot.mouseMoved(null) && isHoveringImage2) {
            removeObject(image2Desc);
            isHoveringImage2 = false;
        }
        
        if (Greenfoot.mouseMoved(image3)) {
            hoverMovement = true;
            if (!isHoveringImage3) {
                image3Desc = new Button("shopIcons/text3.png", 1, true);
                addObject(image3Desc, image3.getX() + 150, image3.getY());
                isHoveringImage3 = true;
            }
        } else if (Greenfoot.mouseMoved(null) && isHoveringImage3) {
            removeObject(image3Desc);
            isHoveringImage3 = false;
        }
        
        if (Greenfoot.mouseMoved(image4)) {
            hoverMovement = true;
            if (!isHoveringImage4) {
                image4Desc = new Button("shopIcons/text4.png", 1, true);
                addObject(image4Desc, image4.getX() + 150, image4.getY());
                isHoveringImage4 = true;
            }
        } else if (Greenfoot.mouseMoved(null) && isHoveringImage4) {
            removeObject(image4Desc);
            isHoveringImage4 = false;
        }
        
        if (Greenfoot.mouseMoved(image5)) {
            hoverMovement = true;
            if (!isHoveringImage5) {
                image5Desc = new Button("shopIcons/text5.png", 1, true);
                addObject(image5Desc, image5.getX() - 150, image5.getY());
                isHoveringImage5 = true;
            }
        } else if (Greenfoot.mouseMoved(null) && isHoveringImage5) {
            removeObject(image5Desc);
            isHoveringImage5 = false;
        }
        
        if (Greenfoot.mouseMoved(image6)) {
            hoverMovement = true;
            if (!isHoveringImage6) {
                image6Desc = new Button("shopIcons/text6.png", 1, true);
                addObject(image6Desc, image6.getX() - 150, image6.getY());
                isHoveringImage6 = true;
            }
        } else if (Greenfoot.mouseMoved(null) && isHoveringImage6) {
            removeObject(image6Desc);
            isHoveringImage6 = false;
        }
    }
}

