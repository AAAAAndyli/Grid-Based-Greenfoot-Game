import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * A StillLabel class that allows you to display a textual value on screen.
 * 
 * The StillLabel is an actor, so you will need to create it, and then add it to the world
 * in Greenfoot.  If you keep a reference to the StillLabel then you can change the text it
 * displays.  
 *
 * @author Amjad Altadmri 
 * @version 1.1
 */
public class StillLabel extends Actor
{
    private String value;
    private Actor creator;
    private int fontSize;
    private Color lineColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    
    private static final Color transparent = new Color(0,0,0,0);

    
    /**
     * Create a new label, initialise it with the int value to be shown and the font size 
     */
    public StillLabel(int value, int fontSize, Actor actor)
    {
        this(Integer.toString(value), fontSize, actor);
    }
    
    /**
     * Create a new label, initialise it with the needed text and the font size 
     */
    public StillLabel(String value, int fontSize, Actor actor)
    {
        this.value = value;
        this.fontSize = fontSize;
        creator = actor;
        updateImage();
    }
    
    public void act()
    {
        if(creator.getWorld() == null)
        {
            getWorld().removeObject(this);
        }
    }

    /**
     * Sets the value  as text
     * 
     * @param value the text to be show
     */
    public void setValue(String value)
    {
        this.value = value;
        updateImage();
    }
    
    /**
     * Sets the value as integer
     * 
     * @param value the value to be show
     */
    public void setValue(int value)
    {
        this.value = Integer.toString(value);
        updateImage();
    }
    
    /**
     * Sets the line color of the text
     * 
     * @param lineColor the line color of the text
     */
    public void setLineColor(Color lineColor)
    {
        this.lineColor = lineColor;
        updateImage();
    }
    
    /**
     * Sets the fill color of the text
     * 
     * @param fillColor the fill color of the text
     */
    public void setFillColor(Color fillColor)
    {
        this.fillColor = fillColor;
        updateImage();
    }
    

    /**
     * Update the image on screen to show the current value.
     */
    private void updateImage()
    {
        setImage(new GreenfootImage(value, fontSize, fillColor, transparent, lineColor));
    }
}
