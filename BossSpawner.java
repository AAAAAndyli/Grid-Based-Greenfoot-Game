import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BossSpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossSpawner extends TriggerTile
{
    private Bosses boss;
    private int bossID;
    private int timer = 0;
    private Label triggerNumberDisplay;
    private boolean activated = false;
    private int spawnDelay, spawnDelayTimer = 0;
    
    private Label enemyNumberDisplay;
    private StillLabel buttonEnemyNumberDisplay;
    
    public BossSpawner(String type, int rotations, int xPosition, int yPosition, int triggerNumber, int enemyNumber)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber, enemyNumber);
    }
    public BossSpawner(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber, int enemyNumber)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber, enemyNumber);
    }
    public BossSpawner(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber, int enemyNumber)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
        this.triggerNumber = triggerNumber;
        bossID = enemyNumber;
        collidable = false;
        enemyNumberDisplay = new Label(enemyNumber, 25, this);
        buttonEnemyNumberDisplay = new StillLabel(enemyNumber, 25, this);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        trigger = new Trigger(triggerNumber);
        if(TriggerCollection.searchTrigger(trigger))
        {
            trigger = TriggerCollection.returnTrigger(trigger);
        }
        else
        {
            TriggerCollection.addTrigger(trigger);
        }
        if(!isButton)
        {
            //getWorld().addObject(enemyNumberDisplay, getPosition().getX()+15, getPosition().getY()+15);
        }
        else
        {
            getWorld().addObject(buttonEnemyNumberDisplay, getPosition().getX()+15, getPosition().getY()+15);
        }
    }
    public void whenTriggered()
    {
        activated = true;
        if(timer > 0)
        {
            trigger.permanentlyDeactivateTrigger();
        }
        else
        {
            timer++;
        }
    }
    public void setSpawnDelay(int spawnDelay)
    {
        this.spawnDelay = spawnDelay;
    }
    public void spawnEnemies()
    {
        boss = getBoss();
        getWorld().addObject(boss, getPosition().getX(), getPosition().getY() - boss.getImage().getHeight()/2);
        boss.setLocation(boss.getPosition().getX() + scrollX, getPosition().getY() - boss.getImage().getHeight()/2 + scrollY);
        activated = false;
    }
    public int getID(Bosses boss)
    {
        if(boss.getClass() == Wall.class)
        {
            return 0;
        }
        else if(boss.getClass() == Bug.class)
        {
            return 1;
        }
        else if(boss.getClass() == Scorch.class)
        {
            return 2;
        }
        else if(boss.getClass() == ArSysRam.class)
        {
            if(((ArSysRam)boss).isRight())
            {
                return 3;
            }
            else
            {
                return 4;
            }
        }
        return -1;
    }
    public Bosses getBoss()
    {
        if(bossID == 0)
        {
            return new Wall();
        }
        else if(bossID == 1)
        {
            return new Bug();
        }
        else if(bossID == 2)
        {
            return new Scorch();
        }
        else if(bossID == 3)
        {
            return new ArSysRam(true);
        }
        else if(bossID == 4)
        {
            return new ArSysRam(false);
        }

        return null;
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber + "," + bossID);
    }
    /**
     * Act - do whatever the BossSpawner wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(trigger.getTrigger())
        {
            whenTriggered();
        }
        if(activated)
        {
            spawnDelayTimer++;
            if(spawnDelay < spawnDelayTimer)
            {
                spawnEnemies();
                getWorld().removeObject(this);
                return;
            }
        }
        super.act();
    }
}
