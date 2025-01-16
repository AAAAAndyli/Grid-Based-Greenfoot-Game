import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Shop here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shop extends World
{
    //source - https://www.greenfoot.org/topics/821
    private GreenfootImage[] shopAnimation = new GreenfootImage[35];
    
    private String s = "You feel refreshed";
    
    private int money;
    private int price;
    private int health;
    private int maxHealth;
    private int shieldLvl = 1;
    private int imageIndex = 0;
    private double speed = 0.4;
    
    SimpleTimer animationTimer = new SimpleTimer();
    
    private boolean clickedOne;
    private boolean clickedTwo;
    private boolean clickedThree;
    private boolean clickedFour;
    private boolean clickedFive;
    private boolean clickedSix;
    
    private boolean movingDown = true;
    
    private Button item1 = new Button("purchaseButton.png",.7);
    private Button item2 = new Button("purchaseButton.png",.7);
    private Button item3 = new Button("purchaseButton.png",.7);
    private Button item4 = new Button("purchaseButton.png",.7);
    private Button item5 = new Button("purchaseButton.png",.7);
    private Button item6 = new Button("purchaseButton.png",.7);
    
    private Button image1 = new Button("shopIcons/Shield.png",1.5, true,1);
    private Button image2 = new Button("shopIcons/fullHealth.png",1, true,2);
    private Button image3 = new Button("shopIcons/plusHP.png",.8, true,3);
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop(MenuWorld world)
    {    
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
        WorldButton back = new WorldButton("button1.png", 0.5, world, backLabel);
        addObject(back, 85, 50);
        
        money = 20000;
        health = 1;
        maxHealth = 10;
        
        setPaintOrder(Cursor.class, Label.class, Button.class, Shop.class);
    }
    
    public boolean isMaxShield()
    {
        if(shieldLvl == 11)
        {
            return true;
        }
        return false;
    }
    
    public void act()
    {
        purchase();
        animate();
        hover();
    }
    
    public void purchase()
    {
        clickedOne = item1.checkButton();
        clickedTwo = item2.checkButton();
        clickedThree = item3.checkButton();
        clickedFour = item4.checkButton();
        clickedFive = item5.checkButton();
        clickedSix = item6.checkButton();
        
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
                    addObject(new Button("shopIcons/bought.png",.7, true,10),250,300);
                }
                System.out.println("purchased, money left: " + money);
            }
            else
            {
                System.out.println("Cannot Purchase, money left: " + money);
            }
        }
        
        if(clickedTwo)
        {
            price = 25;
            if(money >= price)
            {
                money -= price;
                health = maxHealth;
                removeObject(item2);
                addObject(new Button("shopIcons/bought.png",.7, true,10),250,600);
                System.out.println("purchased, money left: " + money);
                System.out.println("health fully regain: " + health);
            }
            else
            {
                System.out.println("Cannot Purchase, money left: " + money);
            }
        }
    }
    
    public void animate()
    {
        if(animationTimer.millisElapsed() < 100){
            return;
        }
        animationTimer.mark();
        setBackground(shopAnimation[imageIndex]);
        imageIndex = (imageIndex + 1) % shopAnimation.length;
    }
    
    public void description()
    {
        if (Greenfoot.mouseMoved(image1)) {
            addObject(image1, 400, 150);
        }
    }
    
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
}

