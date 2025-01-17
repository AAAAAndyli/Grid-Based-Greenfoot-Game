import greenfoot.*;  

/**
 * Transition class for fade-in and fade-out effects.
 */
public class Transition extends Actor
{
    private int transparency = 0; 
    private int fadeSpeed = 5;    
    private boolean fadingIn = true; 

    public Transition() {
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
            }
        }
        else {
            if(transparency > 250){
                transparency -= fadeSpeed;
                getImage().setTransparency(transparency);
            }else{
                fadingIn = true;
            }
        }
    }
}
