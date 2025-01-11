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
    private GreenfootImage image = new GreenfootImage("bg.png");
    private String sword = "sword";
    private int money;
    private int price = 50;
    
    private boolean clicked;
    
    private Button item1 = new Button();
    private Button item2 = new Button();
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop()
    {    
        super(1080, 720, 1); 
        Cursor shopCursor = new Cursor();
        
        //addObject(shopCursor, getWidth()/2, getHeight()/2);
        
        addObject(item1, 200, 300);
        addObject(item2, 700, 300);
        money = 200;
        
        setPaintOrder(Cursor.class, Button.class, Shop.class);
    }
    
    public void act()
    {
        purchase();
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
            System.out.println(money);
        }
    }
}

