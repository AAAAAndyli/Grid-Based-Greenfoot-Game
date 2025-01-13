import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Wallet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wallet extends Actor
{
    private int amount = 0;

    private GreenfootImage image;
    private SuperTextBox text;

    private boolean added;

    public Wallet() {
        text = new SuperTextBox("" + amount, new Font ("Arial", true, false, 24), 100);

        image = new GreenfootImage("button1.png");
        setImage(image);
        getImage().scale(150,50);

        added = true;
    }

    public void act()
    {
        if (added) {
            getWorld().addObject(text, 120, 170);
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
