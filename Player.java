import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collection;


/**
 * Write a description of class Player here.
 * 
 * @author Andy
 * @version (a version number or a date)
 */
public class Player extends Entity
{
    private String state;
    
    private boolean isOneWaying = false;
    
    private double xVelocity = 0;
    private int xDirection = 0;
    private final int xSpeed = 10;
    
    private final int slideSpeed = 25;
    private boolean isSliding;
    
    private boolean isSlamming = false;
    private boolean isCollidingLeft, isCollidingRight, isCollidingUp;
    
    private double yVelocity = 0;
    private double storedJump = 0;
    private double yGravity = 1;
    private final double normalFallingGravity = 1;
    private final double aimingFallingGravity = 0.2;
    private final double maximumYVelocity = 40;
    private final double jumpSpeed = 20;
    private int coyoteTimer = 0;
    private boolean isJumpKeyDown = false;
    
    private boolean touchingFloor;
    private Laser sight = new Laser(1,5,3,false);
    
    private Coordinate mouseTarget = new Coordinate(0, 0);
    
    private int parryTimer = 10;
    private int dashTimer = 10;
    private final int dashCooldown = -200;
    private boolean canBeHurt = true;
    
    private boolean isHeal = false;
    private boolean isHurt = false;
    private int invincibilityFrames = 0;
    private final int totalInvincibilityFrames = 30;
    
    private Crosshair crosshair;
    private boolean isAiming;
    private boolean aimIsActivated = false;
    
    private static int mouseX, mouseY;
    
    private Shield shield = null;
    private int cooldown = 0;
    
    private ArrayList<RangedWeapon> weaponList = new ArrayList<RangedWeapon>();
    private RangedWeapon currentWeapon;
    private boolean shooting;
    private int weaponIndex = 0;
    private RangedWeapon missile = new RangedWeapon(60, 2, this, 1);
    private RangedWeapon bomb = new RangedWeapon(30, 1, this, 1);
    private RangedWeapon spread = new RangedWeapon(30, 3, this, 1);
    private RangedWeapon rapid = new RangedWeapon(10, 0, this, 1);
    
    //keybind related info
    private String jump = SaveFile.getKey("jump");
    private String parry = SaveFile.getKey("parry");
    private String dash = SaveFile.getKey("dash");
    
    public Player()
    {
        this(0,0);
    }
    
    public Player(int scrollX, int scrollY)
    {
        super(scrollX, scrollY);
        setImage("Hitbox.png");
        state = "idle";
        globalPosition = new Coordinate(0,0);
        health = 15;
        maxHealth = health;
        //weaponList.add(missile);
        weaponList.add(spread);
        weaponList.add(rapid);
        weaponList.add(bomb);
        currentWeapon = weaponList.get(weaponIndex);
    }
    
    public void addedToWorld(World world)
    {
        getWorld().addObject(new LowerPlayerSprites(this), getX(), getY()); 
        getWorld().addObject(new UpperPlayerSprites(this), getX(), getY()); 
        globalPosition.setCoordinate(getX(), getY());
        crosshair = getWorld().getObjects(Crosshair.class).get(0);
    }
    
    public void act()
    {     
        super.act();
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        if(mouse != null)
        {
            if (shooting && (Greenfoot.mouseDragEnded(null) || Greenfoot.mouseClicked(null))) 
            {
                shooting = false;
            }
            if (!shooting && Greenfoot.mousePressed(null)) 
            {
                shooting = true;
            }
            mouseX = mouse.getX();
            mouseY = mouse.getY();
        }
        if(invincibilityFrames == 1)
        {
            invincibilityFrames++;
            isHurt = false;
            canBeHurt = false;
        }
        else if(invincibilityFrames > totalInvincibilityFrames)
        {
            invincibilityFrames = 0;
            canBeHurt = true;
        }
        else if(invincibilityFrames != 0)
        {
            invincibilityFrames++;
        }
        state = "idle";
        movement();
        shoot();
        parry();
        currentWeapon.incrementTimer();
        if(Greenfoot.isKeyDown("1"))
        {
            weaponIndex = 0;
            currentWeapon = weaponList.get(weaponIndex);
        }
        else if(Greenfoot.isKeyDown("2"))
        {
            weaponIndex = 1;
            currentWeapon = weaponList.get(weaponIndex);
        }
        else if(Greenfoot.isKeyDown("3"))
        {
            weaponIndex = 2;
            currentWeapon = weaponList.get(weaponIndex);
        }
        if(willDie)
        {
            die();
        }
    }
    
    public void dash()
    {
        if(Greenfoot.isKeyDown(dash) && dashTimer > 0)
        {
            state = "dashing";
            dashTimer --;
            xVelocity = 40 * xDirection;
            yVelocity = 0;
            canBeHurt = false;
        }
        else if(dashTimer == -100)
        {
            dashTimer = 10;
            canBeHurt = true;
        }
        else if(dashTimer != 10)
        {
            invincibilityFrames = 1;
            dashTimer --;
            canBeHurt = true;
        }
    }
    
    public void shoot()
    {
        if(Greenfoot.getMouseInfo() != null && (Greenfoot.getMouseInfo().getButton() == 1)||isAiming)
        {   
            mouseTarget = new Coordinate(mouseX, mouseY);
            if(aimIsActivated)
            {
                if(yVelocity > 0)
                {
                    yGravity = aimingFallingGravity;
                }
                else
                {
                    yGravity = normalFallingGravity;
                }
                
                getWorld().getObjects(Camera.class).get(0).setMultipleFollowing(true);
                isAiming = true;
                getWorld().addObject(sight, getX(), getY());
                
                int deltaX = mouseX - getX();
                int deltaY = mouseY - getY();
                double scale = 500 / Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
                int offsetX = (int) (deltaX * scale);
                int offsetY = (int) (deltaY * scale);
                int sightPlayerMidX = getX() + offsetX;
                int sightPlayerMidY = getY() + offsetY;
                
                sight.setLocation(sightPlayerMidX, sightPlayerMidY);
                sight.turnTowards(mouseX, mouseY);
                sight.setWidth(1000);
            }
            if(Greenfoot.mouseClicked(null))
            {
                isAiming = false;
                currentWeapon.shoot();
            }
        }
        else
        {
            yGravity = normalFallingGravity;
            getWorld().removeObject(sight);
            getWorld().getObjects(Camera.class).get(0).setMultipleFollowing(false);
        }
        if(currentWeapon == rapid && shooting)
        {
            isAiming = false;
            currentWeapon.shoot();
        }
    }
    
    public void movement()
    {
        if(dashTimer == 10 || dashTimer < 0)
        {
            slide();
            predictFloor();
            checkFloor();
            applyGravity();
            jump();
        }
        if(!isSliding)
        {
            horizontalMovement();
            slam();
        }
        dash();
        globalPosition.setCoordinate(globalPosition.getX() + (int)xVelocity, globalPosition.getY() + (int)yVelocity);
        collision();
    }
    
    public void horizontalMovement()
    {
        if((Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))&&(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")))
        {
            state = "idle";
            xVelocity = 0;
        }
        else
        {
            if(Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
            {
                state = "running";
                xVelocity = -xSpeed;
                xDirection = -1;
            }
            else if(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
            {
                state = "running";
                xVelocity = xSpeed;
                xDirection = 1;
            }
            else
            {
                xVelocity = 0;
            }
        }
    }
    
    public boolean checkFloor()
    {
        Tile mid = getOneTileAtOffset(0, getImage().getHeight()/2);
        Tile left = getOneTileAtOffset(-getImage().getWidth()/2 + 15, getImage().getHeight()/2);
        Tile right = getOneTileAtOffset(getImage().getWidth()/2 - 15, getImage().getHeight()/2);
        boolean midTouching = mid != null;
        boolean leftTouching = left != null;
        boolean rightTouching = right != null;
        touchingFloor = midTouching || leftTouching || rightTouching;
        
        if((midTouching && mid.getOneWayCollidable()) || (leftTouching && left.getOneWayCollidable()) || rightTouching && right.getOneWayCollidable())
        {
            yVelocity = 0;
            touchingFloor = true;
        }
        
        if(!touchingFloor)
        {
            coyoteTimer++;
        }
        else
        {
            coyoteTimer = 0;
        }
        return touchingFloor;
    }
    
    public void predictFloor()
    {
        for(int i = 1; i < 10 ; i++)
        {
            Tile predictedMidTile = getOneTileAtOffset((int)xVelocity, getImage().getHeight()/2+(int)yVelocity/i);
            Tile predictedLeftTile = getOneTileAtOffset(-getImage().getWidth()/2+(int)xVelocity + 20, getImage().getHeight()/2+(int)yVelocity/i);
            Tile predictedRightTile = getOneTileAtOffset(getImage().getWidth()/2+(int)xVelocity - 20, getImage().getHeight()/2+(int)yVelocity/i);
            
            boolean midWillTouch = predictedMidTile != null;
            boolean leftWillTouch = predictedLeftTile != null;
            boolean rightWillTouch = predictedRightTile != null;
    
            if((midWillTouch || leftWillTouch || rightWillTouch))
            {
                yVelocity = -1;
                touchingFloor = true;
                if(midWillTouch)
                {
                    globalPosition.setCoordinate(globalPosition.getX(), predictedMidTile.globalPosition.getY() - getImage().getHeight()/2 - predictedMidTile.getImage().getHeight()/2);
                }
                else if(leftWillTouch)
                {
                    globalPosition.setCoordinate(globalPosition.getX(), predictedLeftTile.globalPosition.getY() - getImage().getHeight()/2 - predictedLeftTile.getImage().getHeight()/2);
                }
                else if(rightWillTouch)
                {
                    globalPosition.setCoordinate(globalPosition.getX(), predictedRightTile.globalPosition.getY() - getImage().getHeight()/2 - predictedRightTile.getImage().getHeight()/2);
                }
            }
        }
    }
    
    public Tile getOneTileAtOffset(int xOffset, int yOffset)
    {
        for(Tile tile : getObjectsAtOffset(xOffset, yOffset, Tile.class))
        {
            if(tile != null && tile.getCollidable())
            {
                return tile;
            }
        }
        return null;
    }
    
    public void moveOnDiagonal(Tile diagonalTile)
    {
        if(diagonalTile.isDiagonal())
        {
            int tileTop = diagonalTile.getY()-diagonalTile.getImage().getHeight()/2-getImage().getHeight()/2;
            if(diagonalTile.getRotation() == 0)
            {
                touchingFloor = true;
                double heightOnTile = getX() - diagonalTile.getX();
                globalPosition.setY(tileTop - diagonalTile.getScrollY() - (int)heightOnTile);
            }
            else if(diagonalTile.getRotation() == 1)
            {
                touchingFloor = true;
                double heightOnTile = diagonalTile.getX() - getX();
                globalPosition.setY(tileTop - diagonalTile.getScrollY() - (int)heightOnTile);
            }
        }
    }
    
    public void applyGravity()
    {
        if(touchingFloor == false)
        {
            yVelocity += yGravity;
        }
        else
        {
            yVelocity = 0;
        }
        if(yVelocity > maximumYVelocity)
        {
            yVelocity = maximumYVelocity;
        }
    }
    
    public void jump()
    {
        if(Greenfoot.isKeyDown(jump) && coyoteTimer < 10 && !isJumpKeyDown)
        {
            state = "jumping";
            yVelocity -= (jumpSpeed + storedJump);
            coyoteTimer = 100;
            storedJump = 0;
            isJumpKeyDown = true;
        }
        else if(!Greenfoot.isKeyDown(jump))
        {
            isJumpKeyDown = false;
        }
    }
    
    public void slam()
    {
        if(((touchingFloor || getOneTileAtOffset(0, getImage().getHeight()/2+10) != null|| getOneTileAtOffset(getImage().getWidth()/2, getImage().getHeight()/2+10) != null) || getOneTileAtOffset(-getImage().getWidth()/2, getImage().getHeight()/2+10) != null) && !(Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")))
        {
            isSlamming = false;
        }
        else if((Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down") || isSlamming))
        {
            state = "slamming";
            storedJump = 5;
            xVelocity = 0;
            isSlamming = true;
            yVelocity = 35;
            predictFloor();
            checkFloor();
        }
    }
    
    public void slide()
    {
        if((Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")) && (touchingFloor||isSliding) && !isSlamming)
        {
            state = "sliding";
            switch(xDirection)
            {
                case -1:
                    if(!isSliding)
                    {
                        xVelocity = -slideSpeed;
                    }
                    if(xVelocity < 0)
                    {
                        xVelocity += 0.2;
                    }
                    else
                    {
                        xVelocity = 0;
                    }
                    break;
                case 1:
                    if(!isSliding)
                    {
                        xVelocity = slideSpeed;
                    }
                    if(xVelocity > 0)
                    {
                        xVelocity -= 0.2;
                    }
                    else
                    {
                        xVelocity = 0;
                    }
                    break;
            }
            isSliding = true;
        }
        else
        {
            isSliding = false;
        }
    }
    
    public void collision()
    {
        // Right touching tiles
        int detectionOffset = getImage().getWidth() / 2;
        Tile sideTouching1 = getOneTileAtOffset(xDirection * detectionOffset + (int) xVelocity, 20);
        Tile sideTouching2 = getOneTileAtOffset(xDirection * detectionOffset + (int) xVelocity, -20);
        Tile sideTouching3 = getOneTileAtOffset(xDirection * detectionOffset + (int) xVelocity, 0);
        Tile sideTouching4 = getOneTileAtOffset(xDirection * (detectionOffset + 5), 0);
    
        boolean isSideTouching = sideTouching1 != null || sideTouching2 != null || sideTouching3 != null || sideTouching4 != null;
        Tile sideTouching = sideTouching1 != null ? sideTouching1 : sideTouching2 != null ? sideTouching2 : sideTouching3 != null ? sideTouching3 : sideTouching4;
        
        Tile upLeftTouching = getOneTileAtOffset(-getImage().getWidth()/2 + 5, -getImage().getHeight()/2);
        Tile upRightTouching = getOneTileAtOffset(getImage().getWidth()/2 - 5, -getImage().getHeight()/2);
        
        boolean isUpTouching = upLeftTouching != null || upRightTouching != null;
        Tile upTouching = upLeftTouching != null ? upLeftTouching : upRightTouching;
        // System.out.println("direction: " + xDirection + ", velocity: " + xVelocity);
        // System.out.println(sideTouching1 != null);
        // System.out.println(sideTouching2 != null);
        // System.out.println(sideTouching3 != null);
        // System.out.println(sideTouching4 != null);
        if(isSideTouching)
        {
            xVelocity = 0;
            globalPosition.setCoordinate(sideTouching.globalPosition.getX() + -xDirection * (getImage().getWidth()/2 + sideTouching.getImage().getWidth()/2), globalPosition.getY());
        }
        else
        {
            // getWorld().addObject(new test(false), globalPosition.getX() + xDirection * detectionOffset + (int) xVelocity, globalPosition.getY() + 20);
            // getWorld().addObject(new test(false), globalPosition.getX() + xDirection * detectionOffset + (int) xVelocity, globalPosition.getY() - 20);
            // getWorld().addObject(new test(false), globalPosition.getX() + xDirection * detectionOffset + (int) xVelocity, globalPosition.getY());
            // getWorld().addObject(new test(false), globalPosition.getX() + xDirection * (detectionOffset + 5), globalPosition.getY());
        }
        
        if(isUpTouching && yVelocity < 0)
        {
            yVelocity = 0;
            int ceilingTileBottom = upLeftTouching != null ? upLeftTouching.globalPosition.getY() + getImage().getHeight()/2 + upLeftTouching.getImage().getHeight()/2 : upRightTouching.globalPosition.getY() + getImage().getHeight()/2 + upRightTouching.getImage().getHeight()/2;
            globalPosition.setCoordinate(globalPosition.getX(), ceilingTileBottom);
        }
    }
    
    
    public void parry()
    {
        if(cooldown > 0){
            cooldown--;
        }
        
        if (Greenfoot.isKeyDown(parry) && cooldown == 0) {
        
            if (shield == null) {
                shield = new Shield();
                getWorld().addObject(shield, getX(), getY());
            }else{
                shield.setLocation(getX(), getY());
            }

            if (parryTimer > 0) {
                state = "parrying";
                ArrayList<EProjectile> projectilesInRange = (ArrayList<EProjectile>) getObjectsInRange(100, EProjectile.class);
                for (EProjectile projectile : projectilesInRange) {
                    projectile.parried(mouseX, mouseY);
                    Greenfoot.delay(10);
                    getWorld().getObjects(Camera.class).get(0).screenShake(1, 10);
                    heal(3);
                }
                parryTimer--;
            }

        
            cooldown = 0;
        } else if (!Greenfoot.isKeyDown("e")) {
            
            if (shield != null) {
                getWorld().removeObject(shield);
                shield = null;
            }
    
            
            parryTimer = 10;
        }
    }
    
    public void heal(int regen)
    {
        health += regen;
        if(health > maxHealth)
        {
            health = maxHealth;
        }
        isHeal = true;
        getWorld().getObjects(HealthBar.class).get(0).raise();
    }
    public void launch(int vertStrength)
    {
        touchingFloor = false;
        yVelocity -= vertStrength;
        globalPosition.setCoordinate(globalPosition.getX(), globalPosition.getY() + (int)yVelocity);
    }
    
    public void hurt(int damage)
    {
        if(canBeHurt)
        {
            super.hurt(damage);
            getWorld().getObjects(Camera.class).get(0).screenShake(3, 5);
            isHurt = true;
            canBeHurt = false;
            invincibilityFrames = 1;
        }
    }
    
    private int upperSpriteDirection;
    private int lowerSpriteDirection;
    //animation methods
    public boolean matchingSpriteDirection()
    {
        return upperSpriteDirection == lowerSpriteDirection;
    }
    public void setUpperSpriteDirection(int newDir)
    {
        upperSpriteDirection = newDir;
    }
    public void setLowerSpriteDirection(int newDir)
    {
        lowerSpriteDirection = newDir;
    }
    public boolean getHurt()
    {
        return isHurt;
    }
    public boolean getHeal()
    {
        return isHeal;
    }
    public int getHealthBarHP()
    {
        return health/3;
    }
    public boolean getSlamming()
    {
        System.out.println(isSlamming);
        return isSlamming;
    }
    public void setGrounded()
    {
        coyoteTimer = 0;
        isSlamming = false;
        touchingFloor = true;
    }
    public boolean getFacing()
    {
        //right = true
        return xDirection == 1;
    }
    public boolean getHurtable()
    {
        return canBeHurt;
    }
    public void setXVelocity(double xVelocity)
    {
        this.xVelocity = xVelocity;
    }
    public void setYVelocity(double yVelocity)
    {
        this.yVelocity = yVelocity;
    }
    public double getXVelocity()
    {
        return xVelocity;
    }
    public double getYVelocity()
    {
        return yVelocity;
    }
    public double getYGravity()
    {
        return yGravity;
    }
    public String getState()
    {
        return state;
    }
    
    
    private class RangedWeapon
    {
        private int cooldown, type, damage;
        private int cooldownTimer = 0;
        private Player owner;
        public RangedWeapon(int cooldown, int type, Player owner, int damage)
        {
            this.cooldown = cooldown;
            this.type = type;
            this.owner = owner;
            this.damage = damage;
        }
        public void shoot()
        {
            if(cooldown > cooldownTimer)
            {
                return;
            }
            cooldownTimer = 0;
            switch(type)
            {
                case 0:
                    getWorld().addObject(new PProjectile(mouseTarget, 15, owner, damage), getX(), getY());
                    break;
                case 1:
                    getWorld().addObject(new BombPProjectile(mouseTarget, 25, owner), getX(), getY());
                    break;
                case 2:
                    getWorld().addObject(new MissilePProjectile(mouseTarget, 0, owner), getX(), getY());
                    break;
                case 3:
                    Coordinate coord1 = new Coordinate(mouseTarget.getX() + 50, mouseTarget.getY() + 50);
                    Coordinate coord2 = new Coordinate(mouseTarget.getX() - 50, mouseTarget.getY() - 50);
                    Coordinate coord3 = new Coordinate(mouseTarget.getX() - 100, mouseTarget.getY() + 100);
                    Coordinate coord4 = new Coordinate(mouseTarget.getX() + 100, mouseTarget.getY() - 100);
                    getWorld().addObject(new PProjectile(mouseTarget, 15, owner, damage), getX(), getY());
                    getWorld().addObject(new PProjectile(coord1, 15, owner, damage), getX(), getY());
                    getWorld().addObject(new PProjectile(coord2, 15, owner, damage), getX(), getY());
                    getWorld().addObject(new PProjectile(coord3, 15, owner, damage), getX(), getY());
                    getWorld().addObject(new PProjectile(coord4, 15, owner, damage), getX(), getY());
                    break;
            }
        }
        public void incrementTimer()
        {
            cooldownTimer++;
        }
    }
}
