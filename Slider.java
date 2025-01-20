import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slider here.
 * 
 * @author Andy Li & Darren T.
 * @version (a version number or a date)
 */
public class Slider extends SuperStatBar
{
    private Label text;
    private int previous;
    private String sliderType;
    
    private int musicLevel = 90;
    private GreenfootSound slider;
    private int previousVolume, currentVolume;
    
    public Slider (int maxVal, int currVal, Actor owner, int width, int height, int offset, String type, String labelText)
    {
        super (maxVal, currVal, owner, width, height, offset, Color.GREEN, Color.RED, false, Color.BLACK, 2);
        text = new Label(labelText, 30);
        sliderType = type;
        
        slider = new GreenfootSound("sounds/slider.wav");
        slider.setVolume(musicLevel);
        SaveFile.updateVolume(slider, "effectVolume");
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
        checkSlider();
        mouseSet();
        getWorld().addObject(text, getX(), getY() + 30);
        if(getCurrent() != previous){
            previous = getCurrent() / 10;
            SaveFile.setInfo(sliderType, previous);
        }
    }
    
    public void mouseSet()
    {
        if(Greenfoot.getMouseInfo() != null && (Greenfoot.mouseDragged(this) || Greenfoot.mousePressed(this)) && (Greenfoot.getMouseInfo().getX() > getX()-width/2 && Greenfoot.getMouseInfo().getX() < getX()+width/2 && Greenfoot.getMouseInfo().getY() > getY()-height/2 && Greenfoot.getMouseInfo().getY() < getY()+height/2))
        {
            update(10 * (int)Math.ceil( ( (double)maxVals[0] / ( ( (double)(getX()+width/2) - (double)(getX()-width/2) ) ) * ( (double)Greenfoot.getMouseInfo().getX() - (double)(getX()-width/2) ) ) / 10 ) );
            slider.play();
            getWorld().addObject(text, getX(), getY() + 30);
        }
    }
    
    public void checkSlider(){    
        currentVolume = (int)(musicLevel * SaveFile.getInt("effectVolume") / 100.0);
        if(previousVolume != currentVolume){
            slider.setVolume(currentVolume);
            previousVolume = currentVolume;
        }
    }
    
    public int getCurrent(){
        return currVals[0];
    }
    
    public String getSliderType(){
        return sliderType;
    }
}
