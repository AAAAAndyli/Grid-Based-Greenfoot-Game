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

    public void Wallet() {
        
    }

    public void act()
    {
        
    }

    public void changeAmount(int x) {
        amount += x;
    }
}
