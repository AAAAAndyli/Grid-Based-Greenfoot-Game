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
    private int item;
    private int money;
    /**
     * Constructor for objects of class Shop.
     * 
     */
    public Shop()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        Cursor shopCursor = new Cursor();
        
        Button item1 = new Button();
        Button item2 = new Button();
        
        //addObject(shopCursor, getWidth()/2, getHeight()/2);
        
        addObject(item1, 200, 300);
        addObject(item2, 700, 300);
        
        setPaintOrder(Cursor.class, Button.class, Shop.class);
    }
}
