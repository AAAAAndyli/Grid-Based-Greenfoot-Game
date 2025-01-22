import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Scorch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scorch extends Bosses
{
    private GreenfootSound swing1 = new GreenfootSound("swing1.wav");
    private GreenfootSound swing2 = new GreenfootSound("swing2.wav");
    private GreenfootSound falling = new GreenfootSound("ScorFall.wav");
    private GreenfootSound land = new GreenfootSound("Land.wav");
    
    private Attack slashFirst = new Attack(250, 200, 1, 0 , 100, 0, this, swing1);
    private Attack slashSecond = new Attack(250, 200, 1, 0 , 100, 0, this, swing2);
    private Attack diveAttack = new Attack(50, 200, 1, 0 , 0, 0, this, falling);
    
    private int xVelocity;
    
    private boolean firstJumpFrame = true;
    private int yVelocity;
    private int jumpStrength = -30;
    private int playerDistance = 0;
    private Player player;
    private Camera camera;
    private ScorchSprites scorchSprite = new ScorchSprites(this);
    
    private boolean playAttackAnimation;
    private boolean isFirstCombo = true;
    
    private Aura aura = new Aura(this);
    
    protected GreenfootImage hitBox;
    public Scorch()
    {
        currentAttackDone = true;
        totalAttackCooldown = 60;
        bytesOnDeath = 300;
        health = 800;
        bossbar.setMaxVal(800);
        createHitBox();
        setImage(hitBox);
    }
    
    /**
     * Act - do whatever the Scorch wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        bossActive = true;
        if(previousEffectVolume != SaveFile.getInt("effectVolume")){
            //update the list with each new effect
            effectList = new GreenfootSound[]
            {
                swing1, swing2, falling, land
            };
            SaveFile.updateVolume(effectList, "effectVolume");
        }
        if(bossActive)
        {
            if(player == null)
            {
                player = getWorldOfType(LevelWorld.class).getPlayer();
                camera = getWorld().getObjects(Camera.class).get(0);
                camera.activateBossMode();
                getWorld().addObject(bossbar, 520, 680);
                getWorldOfType(LevelWorld.class).setMusic("ScorchedEarth.mp3");
                getWorld().addObject(scorchSprite, getPosition().getX(), getPosition().getY());
            }
            else if(player.getWorld() != null)
            {
                if(currentAttackDone && attackCooldown < 0)
                {
                    attackCooldown = totalAttackCooldown;
                    state = Greenfoot.getRandomNumber(7);
                    if(state == 3)
                    {
                        state = 2;
                    }
                    currentAttackDone = false;
                    playerDistance = getDistance(player);
                    faceTowards(player.getPosition().getX());
                    return;
                }
                else if(currentAttackDone)
                {
                    faceTowards(player.getPosition().getX());
                    hoverAbove(0.01);
                    followPlayer(0.05, 200);
                    attackCooldown--;
                    state = -1;
                }
                else
                {
                    switch(state)
                    {
                        case 0: 
                            currentAttackDone = slash1();
                            break;
                        case 1:
                            currentAttackDone = slash2();
                            break;
                        case 2:
                            currentAttackDone = slash3();
                            break;
                        case 3:
                            currentAttackDone = slash3();
                            break;
                        case 4:
                            currentAttackDone = shootProjectiles();
                            break;
                        case 5:
                            currentAttackDone = shootHomeProjectiles();
                            break;
                        case 6:
                            currentAttackDone = dive();
                            break;
                    }
                }
            }
            if(health < 100)
            {
                totalAttackCooldown = 0;
            }
            else if(health < 300)
            {
                totalAttackCooldown = 10;
                getWorld().addObject(aura, getX(), getY());
            }
            else if(health < 400)
            {
                totalAttackCooldown = 30;
            }
        }
        super.act();
    }
    public void activateBoss()
    {
        bossActive = true;
    }
    public void faceTowards(int xPositionToFace)
    {
        if(xPositionToFace > getPosition().getX())
        {
            xDirection = 1;
        }
        else if(xPositionToFace < getPosition().getX())
        {
            xDirection = -1;
        }
    }
    public boolean slash1()
    {
        if(attackTimer == 0)
        {
            playAttackAnimation = false;
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown)
        {
            playAttackAnimation = true;
            attackTimer++;
            getClose(0.1);
            followPlayer(0.1, 100);
            if(attackTimer == attackCooldown + 35)
            {
                slashFirst.changeDirection(xDirection);
                slashFirst.performAttack();
            }
            if(scorchSprite.getAttackOver())
            {
                attackTimer = 0;
                return true;
            }
        }
        else
        {
            getClose(0.05);
            followPlayer(0.05, 100);
            faceTowards(player.getPosition().getX());
            attackTimer++;
        }
        return false;
    }
    public boolean slash2()
    {
        if(attackTimer == 0)
        {
            playAttackAnimation = false;
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown)
        {
            playAttackAnimation = true;
            attackTimer++;
            faceTowards(player.getPosition().getX());
            if(attackTimer == attackCooldown + 15)
            {
                slashSecond.changeDirection(xDirection);
                slashSecond.performAttack();
            }
            if(scorchSprite.getAttackOver())
            {
                attackTimer = 0;
                return true;
            }
        }
        else
        {
            getClose(0.05);
            followPlayer(0.05, 100);
            faceTowards(player.getPosition().getX());
            attackTimer++;
        }
        return false;
    }
    public boolean dive()
    {
        if(attackTimer == 0)
        {
            playAttackAnimation = false;
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown)
        {
            playAttackAnimation = true;
            attackTimer++;
            if(attackTimer > attackCooldown + 45)
            {
                getPosition().setCoordinate(getPosition().getX(), getPosition().getY() + 50);
                diveAttack.performAttack();
            }
            else
            {
                int playerPredictedX = (int)player.getXVelocity() * ((int)getDistance(player)/50);
                followPlayer(0.1, Math.abs(playerPredictedX));
            }
            if(getY() > player.getY() || attackTimer > attackCooldown + 270)
            {
                attackTimer = 0;
                land.play();
                return true;
            }
        }
        else
        {
            hoverFarAbove(0.5);
            faceTowards(player.getPosition().getX());
            followPlayer(0.05, 0);
            attackTimer++;
        }
        return false;
    }
    public boolean slash3()
    {
        if(isFirstCombo)
        {
            if(attackTimer == 0)
            {
                playAttackAnimation = false;
                getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
                attackTimer++;
            }
            else if(attackTimer > attackCooldown)
            {
                playAttackAnimation = true;
                attackTimer++;
                getClose(0.075);
                followPlayer(0.075, 50);
                if(attackTimer == attackCooldown + 35)
                {
                    slashFirst.changeDirection(xDirection);
                    slashFirst.performAttack();
                }
                if(scorchSprite.getAttackOver())
                {
                    isFirstCombo = false;
                    attackTimer = 0;
                    state = 3;
                }
            }
            else
            {
                getClose(0.05);
                followPlayer(0.05, 100);
                faceTowards(player.getPosition().getX());
                attackTimer++;
            }
        }
        else
        {
            if(attackTimer == 0)
            {
                playAttackAnimation = false;
                getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
                attackTimer++;
            }
            else
            {
                playAttackAnimation = true;
                attackTimer++;
                getClose(0.1);
                followPlayer(0.1, 50);
                faceTowards(player.getPosition().getX());
                if(attackTimer == 15)
                {
                    slashSecond.changeDirection(xDirection);
                    slashSecond.performAttack();
                }
                if(scorchSprite.getAttackOver())
                {
                    attackTimer = 0;
                    isFirstCombo = true;
                    return true;
                }
            }
        }
        return false;
    }
    public void followPlayer(double speed, int offset)
    {
        int playerX = player.getPosition().getX() - offset * xDirection;
        int bugX = this.getPosition().getX();
        int distance = playerX - bugX;
            
        getPosition().setCoordinate(getPosition().getX() + (int)(distance * speed), getPosition().getY());
    }
    public void hoverAbove(double speed)
    {
        int playerY = player.getPosition().getY() - 200;
        int bugY = this.getPosition().getY();
        int distance = playerY - bugY;
        
        getPosition().setCoordinate(getPosition().getX(), getPosition().getY() + (int)(distance * speed));
    }
    public void hoverFarAbove(double speed)
    {
        int playerY = player.getPosition().getY() - 400;
        int bugY = this.getPosition().getY();
        int distance = playerY - bugY;
        
        getPosition().setCoordinate(getPosition().getX(), getPosition().getY() + (int)(distance * speed));
    }
    public void getClose(double speed)
    {
        int playerY = player.getPosition().getY();
        int bugY = this.getPosition().getY();
        int distance = playerY - bugY;
        
        getPosition().setCoordinate(getPosition().getX(), getPosition().getY() + (int)(distance * speed));
    }
    public boolean shootProjectiles()
    {
        if(attackTimer == 0)
        {
            playAttackAnimation = false;
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown)
        {
            playAttackAnimation = true;
            if(scorchSprite.getAttackOver())
            {
                aiming(15);
                attackTimer++;
                useExplosiveProjectile(25, target);
                if(attackTimer > attackCooldown + 6)
                {
                    attackTimer = 0;
                    return true;
                }
            }
        }
        else
        {
            attackTimer++;
        }
        return false;
    }
    public boolean shootHomeProjectiles()
    {
        if(attackTimer == 0)
        {
            playAttackAnimation = false;
            getWorld().addObject(new AttackIndicator(scrollX, scrollY), getPosition().getX(), getPosition().getY() - getImage().getHeight()/2);
            attackTimer++;
        }
        else if(attackTimer > attackCooldown)
        {
            playAttackAnimation = true;
            if(scorchSprite.getAttackOver())
            {
                aiming(15);
                attackTimer++;
                useHomingProjectile(15, target);
                if(attackTimer > attackCooldown + 6)
                {
                    attackTimer = 0;
                    return true;
                }
            }
        }
        else
        {
            attackTimer++;
        }
        return false;
    }
    
    public Tile getOneTileAtOffset(int xOffset, int yOffset)
    {
        Tile tile = (Tile)getOneObjectAtOffset(xOffset, yOffset, Tile.class);
        if(tile  == null || tile.getButton() || tile.getCollidable() == false)
        {
            return null;
        }
        else
        {
            return tile;
        }
    }
    
    public int getState()
    {
        if(playAttackAnimation)
        {
            return state;
        }
        return -1;
    }
    
    public void hurt(int damage)
    {
        bossbar.update(health);
        super.hurt(damage);
    }
    
    public void createHitBox() 
    {
        hitBox = new GreenfootImage(50, 100);
        hitBox.setColor(new Color(0, 255, 0, 0));
        hitBox.fillRect(0, 0, 50, 100);
        setImage(hitBox);
    }
    
    public void die()
    {
        super.die();
    }
    
    public void loadAnimationFrames(String path)
    {
    }
}
