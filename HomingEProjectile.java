import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HomingEProjectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HomingEProjectile extends EProjectile
{
    private Player target;
    public HomingEProjectile(Coordinate target, double speed, int damage, ScrollingActor spawner, String sprite)
    {
        
        super(target, speed, damage, spawner, sprite);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    public void act()
    {
        if(target == null && getWorld().getObjects(Player.class).size() != 0)
        {
            target = getWorld().getObjects(Player.class).get(0);
        }
        else
        {
            if(target != null)
            {
                double targetAngle = Math.toDegrees(Math.atan2(target.globalPosition.getY() - globalPosition.getY(), target.globalPosition.getX() - globalPosition.getX()));
                targetAngle = (targetAngle + 360) % 360;
                int currentAngle = (getRotation() + 360) % 360;
                double angleDifference = ((targetAngle - currentAngle + 540) % 360) - 180;
                turn(angleDifference > 0 ? 5 : -5);
            }
        }
        super.act();
    }
    public void collide()
    {
        super.collide();
    }
    public void parried(int mouseX, int mouseY)
    {
        super.parried(mouseX, mouseY);
    }
}
