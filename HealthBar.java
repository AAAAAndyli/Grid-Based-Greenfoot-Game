import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collections;

/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{
    private Player player;
    private ArrayList<HealthPod> healthPods = new ArrayList<HealthPod>();
    private int healthBlobCount, health;
    private int currentHealthBarHealth;
    public HealthBar(Player player)
    {
        this.player = player;
        healthBlobCount = player.getHealthBarHP();
        health = player.getHP();
        setImage("HealthBar/h0.png");
    }
    public void addedToWorld(World world)
    {
        for(int i = 0 ; i < healthBlobCount; i++)
        {
            HealthPod healthPod = new HealthPod();
            healthPods.add(healthPod);
            getWorld().addObject(healthPod, getX() + getImage().getWidth()/2 + healthPod.getImage().getWidth() * (i), getY());
        }
    }
    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        health = player.getHP();
        for (int i = 0; i < healthPods.size(); i++) 
        {
            int podHealth = Math.min(3, health - (i * 3));
            podHealth = Math.max(0, podHealth);
            healthPods.get(i).setHealth(podHealth);
        }
    }
}
