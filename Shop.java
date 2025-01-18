import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Shop here.
 * 
 * @author (Justin Ye) 
 * @version (a version number or a date)
 */
public class Shop extends World
{
    //source - https://www.greenfoot.org/topics/821
    private GreenfootImage[] shopAnimation = new GreenfootImage[35];
    
    private int money;
    private int price;
    private int health;
    private int maxHealth;
    private int shieldLvl = 1;
    private int imageIndex = 0;
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
    
    private Button image1 = new Button("shopIcons/Shield.png",1.5, true);
    private Button image2 = new Button("shopIcons/fullHealth.png",1, true);
    private Button image3 = new Button("shopIcons/plusHP.png",.8, true);
    private Button image1Desc;
    private Button image2Desc;
    
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop(MenuWorld world)
    {    
        //Adds all objects in the shop class, aand takes saved values from a file
        super(1080, 720, 1); 
        
        Cursor shopCursor = new Cursor();
        for(int i = 0; i < shopAnimation.length; i++){
            shopAnimation[i] = new GreenfootImage("images/ShopBg/shop" + i  + ".png");
        }
        setBackground(shopAnimation[0]);
        //addObject(shopCursor, getWidth()/2, getHeight()/2);
        
        addObject(item1, 250, 300);
        addObject(image1, 250, 150);
        
        addObject(item2, 250, 600);
        addObject(image2, 250, 450);
        
        addObject(item3, 600, 300);
        addObject(image3, 600, 150);
        
        addObject(item4, 600, 600);
        
        addObject(item5, 950, 300);
        addObject(item6, 950, 600);
        
        Label backLabel = new Label("Back", 40);
        addObject(backLabel, 85, 50);
        WorldButton back = new WorldButton("Buttons/button1.png", 0.5, world, backLabel);
        addObject(back, 85, 50);
        
        money = Integer.valueOf(SaveFile.get("money"));
        health = 1;
        maxHealth = 15;
        
        purchased = false;
        
        setPaintOrder(Transition.class, Cursor.class, Label.class, Button.class, Shop.class);
    }
    
    public void act()
    {
        purchase();
        animate();
        checkMouseHover();
        hover();
    }
    
    /**
     * checks if shield level is max so the program can determine when to stop 
     * allowing purchase
     */
    public boolean isMaxShield()
    {
        if(shieldLvl == 11)
        {
            return true;
        }
        return false;
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
        
        // If clicked first button it takes money in exchange to increase Shield level, which increases the amount of times you can parry
        if(clickedOne)
        {
            price = 100;
            if(money >= price)
            {
                money -= price;
                shieldLvl++;
                if(isMaxShield())
                {
                    removeObject(item1);
                    addObject(new Button("shopIcons/bought.png",.7, true),250,300);
                }
                System.out.println("purchased, money left: " + money);
                purchased = true;
            }
            else
            {
                System.out.println("Cannot Purchase, money left: " + money);
            }
        }
        
        // If clicked second button it takes money in exchange to replenish health to full
        if(clickedTwo)
        {
            price = 25;
            if(money >= price)
            {
                money -= price;
                health = maxHealth;
                removeObject(item2);
                addObject(new Button("shopIcons/bought.png",.7, true),250,600);
                System.out.println("purchased, money left: " + money);
                System.out.println("health fully regain: " + health);
                //remember to add this to each clicked___ condition
                purchased = true;
                SaveFile.setInfo("health", String.valueOf(health));
            }
            else
            {
                System.out.println("Cannot Purchase, money left: " + money);
            }
        }
        
        if(purchased){
            purchased = false;
            SaveFile.setInfo("money", String.valueOf(money));
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
    }
}

