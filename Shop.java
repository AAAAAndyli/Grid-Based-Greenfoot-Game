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
    
    private String health = "You feel refreshed";
    
    private int money;
    private int price = 50;
    private int imageIndex = 0;
    
    SimpleTimer animationTimer = new SimpleTimer();
    
    private boolean clickedOne;
    
    private Button item1 = new Button("purchaseButton.png");
    private Button item2 = new Button("purchaseButton.png");
    private Button item3 = new Button("purchaseButton.png");
    private Button item4 = new Button("purchaseButton.png");
    private Button item5 = new Button("purchaseButton.png");
    private Button item6 = new Button("purchaseButton.png");
    
    private Button image1 = new Button("shopIcons/Shield.png",1.5, true);
    private Button image2 = new Button("shopIcons/fullHealth.png",1,true);
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop()
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
        addObject(item4, 600, 600);
        addObject(item5, 950, 300);
        addObject(item6, 950, 600);
        
        money = 200;
        
        setPaintOrder(Cursor.class, Button.class, Shop.class);
    }
    
    public void act()
    {
        purchase();
        animate();
    }
    
    public void purchase()
    {
        clickedOne = item1.checkButton();
        if(clickedOne)
        {
            if(money >= price)
            {
                money -= price;
                System.out.println(health);
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
}

