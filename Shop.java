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
    
    private String sword = "sword";
    
    private int money;
    private int price = 50;
    private int imageIndex = 0;
    
    SimpleTimer animationTimer = new SimpleTimer();
    
    private boolean clicked;
    
    private Button item1 = new Button("purchaseButton.png");
    private Button item2 = new Button("purchaseButton.png");
    private Button item3 = new Button("purchaseButton.png");
    private Button item4 = new Button("purchaseButton.png");
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
        
        addObject(item1, 550, 150);
        addObject(item2, 550, 450);
        addObject(item3, 900, 150);
        addObject(item4, 900, 450);
        
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
        clicked = item1.checkButton();
        if(clicked)
        {
            if(money >= price)
            {
                money -= price;
                System.out.println(sword);
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

