import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slider here.
 * 
 * @author Andy Li
 * @version (a version number or a date)
 */
public class Slider extends SuperStatBar
{
    private Label hp = new Label("HP", 20);
    
    public Slider (int maxVal, int currVal, Actor owner, int width, int height, int offset)
    {
        super (maxVal, currVal, owner, width, height, offset, Color.GREEN, Color.RED);
    }
    
    public void addedToWorld()
    {
        getWorld().addObject(hp, getX(), getY());
    }
    
    /**
     * Act - do whatever the Slider wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        mouseSet();
        getWorld().addObject(hp, getX(), getY() + 30);
    }
    
    public void mouseSet()
    {
        if(Greenfoot.getMouseInfo() != null && (Greenfoot.mouseDragged(this) || Greenfoot.mousePressed(this)) && (Greenfoot.getMouseInfo().getX() > getX()-width/2 && Greenfoot.getMouseInfo().getX() < getX()+width/2 && Greenfoot.getMouseInfo().getY() > getY()-height/2 && Greenfoot.getMouseInfo().getY() < getY()+height/2))
        {
            System.out.println(111);
            update(10 * (int)Math.ceil( ( (double)maxVals[0] / ( ( (double)(getX()+width/2) - (double)(getX()-width/2) ) ) * ( (double)Greenfoot.getMouseInfo().getX() - (double)(getX()-width/2) ) ) / 10 ) );
            getWorld().addObject(hp, getX(), getY() + 30);
        }
    }
}
