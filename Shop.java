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
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        Cursor shopCursor = new Cursor();
        ShopButton button1 = new ShopButton();
        ShopButton button2 = new ShopButton();
        
        //addObject(shopCursor, getWidth()/2, getHeight()/2);
        
        addObject(button1, 200, 300);
        addObject(button2, 700, 300);
        
        setPaintOrder(Cursor.class, ShopButton.class, Shop.class);
    }
}
