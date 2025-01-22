import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Manager for HealthPods and HealthBlob
 * 
 * @author Andy
 * @version 1
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
        health = SaveFile.getInt("health");
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
     * Act - sets every healthPod to health
     */
    public void act()
    {
        health = SaveFile.getInt("health");
        for (int i = 0; i < healthPods.size(); i++) 
        {
            int podHealth = Math.min(3, health - (i * 3));
            podHealth = Math.max(0, podHealth);
            healthPods.get(i).setHealth(podHealth);
        }
    }
}
