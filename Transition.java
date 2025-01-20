import greenfoot.*;  

/**
 * Transition class for fade-in and fade-out effects.
 */
public class Transition extends Actor
{
    private int transparency = 0; 
    private int fadeSpeed = 5;    
    private boolean fadingIn = true; 
    private int fadeCount = 0;

    public Transition()
    {
        this(true);
    }
    
    public Transition(boolean fadingIn) {
        this.fadingIn = fadingIn;
        if(fadingIn)
        {
            transparency = 0;
        }
        else
        {
            transparency = 255;
        }
        GreenfootImage image = new GreenfootImage(1080, 720);
        image.setColor(Color.BLACK);
        image.fill();
        setImage(image);
        getImage().setTransparency(transparency);
    }

    public void act() {
        if (fadingIn) {
            
            if (transparency < 250) {
                transparency += fadeSpeed;
                getImage().setTransparency(transparency);
            } else {
                fadingIn = false; 
                if(getWorld() != null)
                {
                    //getWorld().removeObject(this);
                }
                fadeCount++;
            }
        }
        else {
            if(transparency > 0){
                transparency -= fadeSpeed;
                getImage().setTransparency(transparency);
            }else{
                fadingIn = true;
                if(getWorld() != null)
                {
                    //getWorld().removeObject(this);
                }
                fadeCount++;
            }
        }
    }
    
    public void resetFadeCount()
    {
        fadeCount = 0;
    }
    
    public int getFadeCount()
    {
        return fadeCount;
    }
    
    public boolean fadedOnce()
    {
        return fadeCount != 0;
    }
}
