import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slider here.
 * 
 * @author Andy Li
 * @version (a version number or a date)
 */
public class Slider extends SuperStatBar
{
    private Label text = new Label("Volume", 30);
    
    public Slider (int maxVal, int currVal, Actor owner, int width, int height, int offset)
    {
        super (maxVal, currVal, owner, width, height, offset, Color.GREEN, Color.RED, false, Color.BLACK, 2);
    }
    
    public void addedToWorld()
    {
        getWorld().addObject(text, getX(), getY());
    }
    
    /**
     * Act - do whatever the Slider wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        mouseSet();
        getWorld().addObject(text, getX(), getY() + 30);
    }
    
    public void mouseSet()
    {
        if(Greenfoot.getMouseInfo() != null && (Greenfoot.mouseDragged(this) || Greenfoot.mousePressed(this)) && (Greenfoot.getMouseInfo().getX() > getX()-width/2 && Greenfoot.getMouseInfo().getX() < getX()+width/2 && Greenfoot.getMouseInfo().getY() > getY()-height/2 && Greenfoot.getMouseInfo().getY() < getY()+height/2))
        {
            update(10 * (int)Math.ceil( ( (double)maxVals[0] / ( ( (double)(getX()+width/2) - (double)(getX()-width/2) ) ) * ( (double)Greenfoot.getMouseInfo().getX() - (double)(getX()-width/2) ) ) / 10 ) );
            getWorld().addObject(text, getX(), getY() + 30);
        }
    }
}
