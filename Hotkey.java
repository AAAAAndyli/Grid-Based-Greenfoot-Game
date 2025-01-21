import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hotkey here.
 * 
 * @author Darren T.
 * @version (a version number or a date)
 */
public class Hotkey extends Actor
{
    private String keybind, type, unlockString;
    private boolean unlocked, active, unlockedOnce, setImageOnce;
    private int myIndex;
    private Player player;
    private GreenfootImage icon;
    
    public Hotkey(String imagePath, String key, String unlockType, boolean unlockedYet, int weaponIndex, Player reference){
        type = imagePath;
        keybind = key;
        myIndex = weaponIndex;
        unlockString = unlockType;
        unlocked = unlockedYet;
        player = reference;
        
        if(unlockedYet){
            icon = new GreenfootImage("images/HotkeyIcons/" + imagePath + "Equipped.png");
            unlockedOnce = true;
        }
        else{
            System.out.println(type);
            icon = new GreenfootImage("images/HotkeyIcons/" + imagePath + "Locked.png");
            setImageOnce = true;
        }
            
        icon.scale(icon.getWidth() / 4, icon.getWidth() / 4);
        setImage(icon);

    }
    
    /**
     * Act - do whatever the Hotkey wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        if(!unlocked && SaveFile.getInt(unlockString) == 1){
            unlocked = true;
        }
        if(unlocked && !unlockedOnce){
            setNewImage("images/HotkeyIcons/" + type + ".png");
            unlockedOnce = true;
        }
        String pressed = Greenfoot.getKey();
        if(Greenfoot.isKeyDown(keybind) && unlocked){
            player.setWeaponIndex(myIndex);
            setNewImage("images/HotkeyIcons/" + type + "Equipped.png");
            setImageOnce = false;
        }
        if(player.getWeaponIndex() != myIndex && !setImageOnce){
            setNewImage("images/HotkeyIcons/" + type + ".png");
            setImageOnce = true;
        }
    }
    
    public void setNewImage(String path){
        icon = new GreenfootImage(path);
        icon.scale(icon.getWidth() / 4, icon.getWidth() / 4);
        setImage(icon);
    }
    
    public void setUnlocked(boolean u){
        unlocked = u;
    }
}
