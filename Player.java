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
    
    private int knockbackX = 0, knockBackY = 0;
    
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
    
    public Player()
    {
        this(0,0);
    }
    
    public Player(int scrollX, int scrollY)
    {
        super(scrollX, scrollY);
        globalPosition = new Coordinate(0,0);
        health = 15;
        maxHealth = health;
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
            mouseX = mouse.getX();
            mouseY = mouse.getY();
        }
        if(invincibilityFrames == 2)
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
        movement();
        shoot();
        parry();
        
        if(Greenfoot.isKeyDown("f"))
        {
            aimIsActivated = !aimIsActivated;
        }
        //System.out.println("xVelocity: " + xVelocity + ", yVelocity: " + yVelocity);
        //System.out.println(globalPosition.getX() + ", " + globalPosition.getY());
        if(willDie)
        {
            die();
        }
    }
    
    
    
    public void dash()
    {
        if(Greenfoot.isKeyDown("shift") && dashTimer > 0)
        {
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
                getWorld().addObject(new PProjectile(mouseTarget, 15, this), getX(), getY());
            }
        }
        else
        {
            yGravity = normalFallingGravity;
            getWorld().removeObject(sight);
            getWorld().getObjects(Camera.class).get(0).setMultipleFollowing(false);
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
        collision();
        globalPosition.setCoordinate(globalPosition.getX() + (int)xVelocity, globalPosition.getY() + (int)yVelocity);
    }
    
    public void horizontalMovement()
    {
        if((Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))&&(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")))
        {
            xVelocity = 0;
        }
        else
        {
            if(Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
            {
                xVelocity = -xSpeed;
                xDirection = -1;
            }
            else if(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
            {
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
        /*
        if(midTouching)
        {
            moveOnDiagonal(mid);
        }
        if(leftTouching)
        {
            moveOnDiagonal(left);
        }
        if(rightTouching)
        {
            moveOnDiagonal(right);
        }
        */
        
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
        Tile predictedMidTile = getOneTileAtOffset((int)xVelocity, getImage().getHeight()/2+(int)yVelocity);
        Tile predictedLeftTile = getOneTileAtOffset(-getImage().getWidth()/2+(int)xVelocity + 20, getImage().getHeight()/2+(int)yVelocity);
        Tile predictedRightTile = getOneTileAtOffset(getImage().getWidth()/2+(int)xVelocity - 20, getImage().getHeight()/2+(int)yVelocity);
        
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
        if((Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up")) && coyoteTimer < 10 && !isJumpKeyDown)
        {
            yVelocity -= (jumpSpeed + storedJump);
            coyoteTimer = 100;
            storedJump = 0;
            isJumpKeyDown = true;
        }
        else if(!Greenfoot.isKeyDown("w") && !Greenfoot.isKeyDown("up"))
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
        Tile rightTouching1 = getOneTileAtOffset(getImage().getWidth()/2 + (int)xVelocity, 50);
        Tile rightTouching2 = getOneTileAtOffset(getImage().getWidth()/2 + (int)xVelocity, -50);
        
        boolean isRightTouching = rightTouching1 != null && rightTouching2 != null;
        Tile rightTouching = rightTouching1 != null ? rightTouching1 : rightTouching2;
        
        Tile leftTouching1 = getOneTileAtOffset(-getImage().getWidth()/2 + (int)xVelocity, 50);
        Tile leftTouching2 = getOneTileAtOffset(-getImage().getWidth()/2 + (int)xVelocity, -50);
        
        boolean isLeftTouching = leftTouching1 != null && leftTouching2 != null;
        Tile leftTouching = leftTouching1 != null ? leftTouching1 : leftTouching2;
        
        Tile upLeftTouching = getOneTileAtOffset(-getImage().getWidth()/2 + 5, -getImage().getHeight()/2);
        Tile upRightTouching = getOneTileAtOffset(getImage().getWidth()/2 - 5, -getImage().getHeight()/2);
        if(isRightTouching && (xDirection == 1||xVelocity > 0)) //&& !rightTouching.isDiagonal())
        {
            isCollidingRight = true;
            xVelocity = 0;
            globalPosition.setCoordinate(rightTouching.globalPosition.getX() - getImage().getWidth()/2 - rightTouching.getImage().getWidth()/2, globalPosition.getY());
        }
        //else if(rightTouching != null && (xDirection == 1||xVelocity > 0) && rightTouching.isDiagonal() && rightTouching.getRotation() == 0)
        //{
            //moveOnDiagonal(rightTouching);
        //}
        else
        {
            isCollidingRight = false;
        }
        if(isLeftTouching && (xDirection == -1||xVelocity < 0))// && !leftTouching.isDiagonal())
        {
            isCollidingLeft = true;
            xVelocity = 0;
            globalPosition.setCoordinate(leftTouching.globalPosition.getX() + getImage().getWidth()/2 + leftTouching.getImage().getWidth()/2, globalPosition.getY());
        }
        //else if(leftTouching != null && (xDirection == -1||xVelocity < 0) && leftTouching.isDiagonal() && leftTouching.getRotation() == 1)
        //{
            //moveOnDiagonal(leftTouching);
        //}
        else
        {
            isCollidingLeft = false;
        }
        if((upLeftTouching != null || upRightTouching != null) && yVelocity < 0)
        {
            isCollidingUp = true;
            yVelocity = 0;
            int ceilingTileBottom = upLeftTouching != null ? upLeftTouching.globalPosition.getY() + getImage().getHeight()/2 + upLeftTouching.getImage().getHeight()/2 : upRightTouching.globalPosition.getY() + getImage().getHeight()/2 + upRightTouching.getImage().getHeight()/2;
            globalPosition.setCoordinate(globalPosition.getX(), ceilingTileBottom);
        }
        else
        {
            isCollidingUp = false;
        }
    }
    
    
    public void parry()
    {
        if(cooldown > 0){
            cooldown--;
        }
        
        if (Greenfoot.isKeyDown("e") && cooldown == 0) {
        
            if (shield == null) {
                shield = new Shield();
                getWorld().addObject(shield, getX(), getY());
            }

            if (parryTimer > 0) {
                ArrayList<EProjectile> projectilesInRange = (ArrayList<EProjectile>) getObjectsInRange(100, EProjectile.class);
                for (EProjectile projectile : projectilesInRange) {
                    projectile.parried(mouseX, mouseY);
                    Greenfoot.delay(10);
                    getWorld().getObjects(Camera.class).get(0).screenShake(1, 10);
                    heal(3);
                }
                parryTimer--;
            }

        
            cooldown = 60;
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
    
    public void hurt(int damage)
    {
        if(canBeHurt)
        {
            super.hurt(damage);
            getWorld().getObjects(Camera.class).get(0).screenShake(3, 5);
            isHurt = true;
            invincibilityFrames = 1;
        }
        //Greenfoot.delay(10);
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
    public boolean getFacing()
    {
        //right = true
        return xDirection == 1;
    }
    public boolean getHurtable()
    {
        return canBeHurt;
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
    
    
    private class rangedWeapon
    {
        public rangedWeapon()
        {
            
        }
    }
}
