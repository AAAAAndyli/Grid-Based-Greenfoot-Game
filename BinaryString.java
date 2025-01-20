import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BinaryString here.
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class BinaryString extends UI
{
    //List of binary strings to cycle from, no meaning whatsoever...
    private String[] secret = new String[]
    {
        "0\n1\n0\n0\n1\n0\n0\n0", "0\n1\n1\n0\n1\n0\n0\n1", "0\n1\n0\n0\n1\n1\n0\n1", "0\n1\n1\n1\n0\n0\n1\n0", 
        "0\n1\n0\n0\n0\n0\n1\n1", "0\n1\n1\n0\n1\n1\n1\n1", "0\n1\n1\n0\n1\n0\n0\n0", "0\n1\n1\n0\n0\n1\n0\n1", "0\n1\n1\n0\n1\n1\n1\n0"   
    };
    //1 = down, -1 = up
    private int direction;
    
    public BinaryString(){
        //pick a random text to write
        String text = secret[Greenfoot.getRandomNumber(9)];
        GreenfootImage image = new GreenfootImage(text, 25, Color.GREEN, null);
        setImage(image);
        
        //randomly choose direction
        direction = Greenfoot.getRandomNumber(2) == 1 ? 1 : -1;
    }
    
    /**
     * Act - do whatever the BinaryString wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //move the binary string up or down  until it is out of screen
        setLocation(getX(), getY() + direction);
        
        if((direction == -1 && getY() < -100) || (direction == 1 && getY() > getWorld().getHeight() + 100)){
            BinaryString binary = new BinaryString();
            int ySpawn = binary.getDirection() == 1 ? -100 - Greenfoot.getRandomNumber(100) : Greenfoot.getRandomNumber(100) + 100 + getWorld().getHeight();
            //create a new binary string then remove itself once out of world
            getWorld().addObject(binary, Greenfoot.getRandomNumber(800) + 100, ySpawn);
            getWorld().removeObject(this);
        }
    }
    
    public int getDirection(){
        return direction;
    }
}
