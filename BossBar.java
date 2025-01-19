import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BossBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossBar extends SuperStatBar
{
    /**
     * Main constructor - A basic constructor that sets default values. Easy to use, not very flexible.
     */
    public BossBar()
    {
        this(100, 100, null, 48, 6, 36);
    }

    /**
     * A simple constructor - specify a single value (which will be treated as both current and 
     * max for the stat) as well as an owner to follow. If you do not want this to follow an Actor, 
     * use null for the second parameter.
     * 
     * @param maxVal    The maximum value for this stat, which will also be the starting value for this stat
     * @param owner     The Actor to follow around. If you do not want to associate this with an Actor, provide
     *                  null instead.
     */
    public BossBar (int maxVal, Actor owner){
        this(maxVal, maxVal, owner, 48, 4, 36);
    }

    /**
     *  A simple constructor for a somewhat customized Green and Red stat bar. If owner is null, just position this object where you want it and it wont move.
     *  If owner is not null, this object will follow the owner.
     *  
     *  @param  maxVal  the maximum value for this stat
     *  @param currVal  the starting value for this stat
     *  @param  owner   the Actor that this stat bar will follow (null for DONT FOLLOW). Can be changed to just an Actor if needed
     *  @param  width   the width of the stat bar
     *  @param height   the height of the stat bar
     *  @param offset   the y-offset for positioning this bar in relation to it's owner
     */
    public BossBar (int maxVal, int currVal, Actor owner, int width, int height, int offset){
        this (maxVal, currVal, owner, width, height, offset, Color.GREEN, Color.RED);
    }

    /**
     *  Similar to above, but with the ability to customize colors
     *  
     *  @param  maxVal  the maximum value for this stat
     *  @param currVal  the starting value for this stat
     *  @param  owner   the Actor that this stat bar will follow (null for DONT FOLLOW). Can be changed to just an Actor if needed
     *  @param  width   the width of the stat bar
     *  @param height   the height of the stat bar
     *  @param offset   the y-offset for positioning this bar in relation to it's owner
     *  @param filledColor  the color to be used to represent the current value
     *  @param missingColor the color to be used to represent the missing value
     */
    public BossBar (int maxVal,  int currVal, Actor owner, int width, int height, int offset, Color filledColor, Color missingColor){
        this (maxVal, currVal, owner, width, height, offset, filledColor, missingColor, HIDE_AT_MAX_DEFAULT);
    }

    /**
     *  Similar to above, but with the ability to have the bar hide when full - for example if you don't want full health bars shown.
     *  
     *  @param  maxVal  the maximum value for this stat
     *  @param currVal  the starting value for this stat
     *  @param  owner   the Actor that this stat bar will follow (null for DONT FOLLOW). Can be changed to just an Actor if needed
     *  @param  width   the width of the stat bar
     *  @param height   the height of the stat bar
     *  @param offset   the y-offset for positioning this bar in relation to it's owner
     *  @param filledColor  the color to be used to represent the current value
     *  @param missingColor the color to be used to represent the missing value
     *  @param  hideAtMax   set to true to have this statBar hide itself when currVal == maxVal
     */
    public BossBar (int maxVal,  int currVal, Actor owner, int width, int height, int offset, Color filledColor, Color missingColor, boolean hideAtMax){
        this (maxVal, currVal, owner, width, height, offset, filledColor, missingColor, hideAtMax, null, 0);
    }

    /**
     *  The most detailed constructor! Can specify a border including thickness and color.
     *  
     *  @param  maxVal  the maximum value for this stat
     *  @param currVal  the starting value for this stat
     *  @param  owner   the Actor that this stat bar will follow (null for DONT FOLLOW). Can be changed to just an Actor if needed
     *  @param  width   the width of the stat bar
     *  @param height   the height of the stat bar
     *  @param offset   the y-offset for positioning this bar in relation to it's owner
     *  @param filledColor  the color to be used to represent the current value
     *  @param missingColor the color to be used to represent the missing value
     *  @param  hideAtMax   set to true to have this statBar hide itself when currVal == maxVal
     *  @param borderColor  the Color of the border
     *  @param borderThickness  the thickness of the border. This value should be at least 1.
     */
    public BossBar (int maxVal,  int currVal, Actor owner, int width, int height, int offset, Color filledColor, Color missingColor, boolean hideAtMax, Color borderColor, int borderThickness){
        this (new int[]{maxVal}, new int[]{currVal}, owner, width, height, offset, new Color[] {filledColor}, new Color[] {missingColor}, hideAtMax, borderColor, borderThickness);
    }

    /**
     *  The king of all StatBar constuctors!
     * 
     *  Takes details for an array of bars, otherwise the same as above. Note that all arrays must be the same length.
     * 
     *  @param  maxVal[]  the maximum values for each stat
     *  @param currVal[]  the starting values for each stat
     *  @param  owner   the Actor that this stat bar will follow (null for DONT FOLLOW). Can be changed to just an Actor if needed
     *  @param  width   the width of the stat bar
     *  @param height   the height of the stat bar
     *  @param offset   the y-offset for positioning this bar in relation to it's owner
     *  @param filledColor[]  the colors to be used to represent the current values
     *  @param missingColor[] the colors to be used to represent the missing values
     *  @param  hideAtMax   set to true to have this statBar hide itself when currVal == maxVal
     *  @param borderColor  the Color of the border
     *  @param borderThickness  the thickness of the border. This value should be at least 1.
     */
    public BossBar (int maxVals[],  int currVals[], Actor owner, int width, int height, int offset, Color filledColor[], Color missingColor[], boolean hideAtMax, Color borderColor, int borderThickness){
        super(maxVals, currVals, owner, width, height, offset, filledColor, missingColor, hideAtMax, borderColor, borderThickness);
    }
    /**
     *   The Actual drawing method that draws the bars onto the image for this Actor
     *      
     *      This method is private because we don't want another method to 
     *      waste time calling this if no changes have been made to the 
     */
    private void redraw() 
    {
        if (hasBorder)
        {
            bar.setColor(borderColor);
            for (int i = 0; i < borderThickness; i++) 
            {
                bar.drawRect(i, i, width - 1 - (i * 2), height - 1 - (i * 2));
            }
        }

        for (int i = 0; i < barCount; i++) 
        {
            currPercentVal = (double) currVals[i] / maxVals[i];
            int filledBarHeight = (int) (currPercentVal * (height - (borderThickness * 2))); // Height of the filled part
            int missingBarHeight = height - (borderThickness * 2) - filledBarHeight; // Remaining height
    
            // Draw the filled portion (grows upwards)
            bar.setColor(filledColor[i]);
            bar.fillRect(borderThickness + (i * barHeight), borderThickness + missingBarHeight, width - (2 * borderThickness), filledBarHeight);
    
            // Draw the missing portion (stays at the top)
            bar.setColor(missingColor[i]);
            bar.fillRect(borderThickness + (i * barHeight), borderThickness, width - (2 * borderThickness), missingBarHeight);
        }
    }
}
