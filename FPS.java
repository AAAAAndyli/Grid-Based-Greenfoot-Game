import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

import java.awt.Graphics;

/**
 * Displays the number of frames per second on 
 * a given scenario.
 * 
 * @author Michael Berry (mjrb4)
 * @version 06/07/08
 */
public class FPS extends Actor
{
    /**
     * The two possbile modes the calculation can take.
     * 
     * Average will display the number of feames that 
     * were displayed in the previous second, thereby
     * giving you an average FPS reading from the last
     * second.
     * 
     * Realtime will calculate the time difference 
     * between this frame and the last one and then
     * update the reading after each and every frame.
     * This is potentially more accurate if you want
     * to find accurateintermittent spikes in 
     * framerates, but less accurate for a simple reading.
     */
    public enum Mode {AVERAGE, REALTIME}
    /**
     * The mode that the FPS reading should take.
     * For most uses, leave this as Mode.AVERAGE.
     */
    public static final Mode mode = Mode.AVERAGE;
    
    private static final Color textColor = new Color(255, 0, 50);
    private static final String prefix = "FPS: ";
    private static final int updateFreq = 20;
    private long countAct;
    private long prevTime;
    private double fps;

    /**
     * Create a new FPS reading.
     */
    public FPS()
    {
        countAct = 0;
        setImage(new GreenfootImage(100, 16));
        GreenfootImage image = getImage();
        image.setColor(textColor);
        updateImage("-");
    }
    
    /**
     * Set the value of the frames per second.
     * @param val the value to set the FPS to.
     */
    private void setFPS(Long val)
    {
        fps = val;
        updateImage(val.toString());
    }
    
    /**
     * Set the value of the frames per second.
     * @param val the value to set the FPS to.
     */
    private void setFPS(Double val)
    {
        fps = val;
        updateImage(val.toString());
    }
    
    /**
     * Get the number of frames per second.
     * @return the number of frames per second.
     */
    public double getFPS()
    {
        return fps;
    }
   
    
    /**
     * Calculate the FPS based on the mode, and
     * update the reading.
     */
    public void act()
    {
        countAct++;
        
        if(mode == Mode.REALTIME) {
            long gap = System.currentTimeMillis() - prevTime;
            if(gap != 0 && countAct%updateFreq==0) {
                countAct=0;
                setFPS((1.0/gap)*1000.0);
            }
            prevTime = System.currentTimeMillis();
        }
        else if(mode == Mode.AVERAGE) {
            long curTime = System.currentTimeMillis();
            if(curTime>=prevTime+1000) {
                setFPS(countAct);
                prevTime = curTime;
                countAct = 0;
            }
        }
    }

    /**
     * Draw the image.
     * @param value the FPS value
     */
    private void updateImage(String value)
    {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(prefix + value, 1, 12);
    }
}