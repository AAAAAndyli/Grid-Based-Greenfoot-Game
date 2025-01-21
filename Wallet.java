import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Wallet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wallet extends Actor
{
    private int amount;
    private int changedAmount;

    private GreenfootImage image;
    private SuperTextBox text;
    private SimpleTimer timer = new SimpleTimer();
    private SaveFile saveFile;
    
    private boolean added, changed;

    public Wallet() {
        text = new SuperTextBox("" + changedAmount, Color.BLACK, Color.WHITE, new Font ("Arial", true, false, 24), false, 80, 0, Color.BLACK, 0);
        
        amount = SaveFile.getInt("money");
        changedAmount = amount;
        
        int randomImage = Greenfoot.getRandomNumber(2);
        if (randomImage == 0) { 
            image = new GreenfootImage("/wallet0.png");
        } else {
            image = new GreenfootImage("/wallet1.png");
        }
        setImage(image);
        getImage().scale(180,65);

        added = true;
    }

    public void act()
    {
        if (changedAmount != amount && !changed) {
            timer.mark();
            setLocation(getX(), getY() + 5);
            changed = true;
        }
        if (changed && timer.millisElapsed() > 50) {
            changed = false;
            setLocation(getX(), getY() - 5);
            changedAmount = amount;
        }
        if (added) {
            getWorld().addObject(text, 172, 178);
        }
        text.update("" + amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int x) {
        amount = x;
    }

    public void changeAmount(int x) {
        amount += x;
    }
}
