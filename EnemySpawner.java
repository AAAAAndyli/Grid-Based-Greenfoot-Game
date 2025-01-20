import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemySpawner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemySpawner extends TriggerTile
{
    private Enemy enemy;
    private int timer = 0;
    private Label triggerNumberDisplay;
    private boolean activated = false;
    private int spawnDelay, spawnDelayTimer = 0;
    
    //private Label enemyNumberDisplay;
    private StillLabel buttonEnemyNumberDisplay;
    
    public EnemySpawner(String type, int rotations, int xPosition, int yPosition, int triggerNumber, Enemy enemy)
    {
        this(type, rotations, false, null, xPosition, yPosition, triggerNumber, enemy);
    }
    public EnemySpawner(String type, int rotations, boolean isButton, MapMaker mapMaker, int triggerNumber, Enemy enemy)
    {
        this(type, rotations, isButton, mapMaker, 0, 0, triggerNumber, enemy);
    }
    public EnemySpawner(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition, int triggerNumber, Enemy enemy)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, triggerNumber);
        this.triggerNumber = triggerNumber;
        this.enemy = enemy;
        collidable = false;
        //enemyNumberDisplay = new Label(EnemyID.getID(enemy), 25, this);
        buttonEnemyNumberDisplay = new StillLabel(EnemyID.getID(enemy), 25, this);
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
        trigger = new Trigger(triggerNumber);
        if(TriggerCollection.searchTrigger(trigger))
        {
            trigger = TriggerCollection.returnTrigger(trigger);
            trigger.addSpawner(this);
        }
        else
        {
            TriggerCollection.addTrigger(trigger);
            trigger.addSpawner(this);
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
        getWorld().addObject(enemy, getPosition().getX(), getPosition().getY() - enemy.getImage().getHeight()/2);
        enemy.setLocation(enemy.getPosition().getX() + scrollX, getPosition().getY() - enemy.getImage().getHeight()/2 + scrollY);
        activated = false;
    }
    public String toString()
    {
        return(type + "," + rotations + ","  + x + "," + y + "," + triggerNumber + "," + EnemyID.getID(enemy));
    }
    /**
     * Act - do whatever the EnemySpawner wants to do. This method is called whenever
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
                System.out.println(spawnDelayTimer);
                spawnEnemies();
                getWorld().removeObject(this);
                return;
            }
        }
        super.act();
    }
}
