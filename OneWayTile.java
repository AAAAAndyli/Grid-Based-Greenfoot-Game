import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class oneWayTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OneWayTile extends Tile
{
    private Player player;
    private int playerHeight, playerWidth;
    public OneWayTile(String type, int rotations, int xPosition, int yPosition)
    {
        this(type, rotations, false, null, xPosition, yPosition);
    }
    public OneWayTile(String type, int rotations, boolean isButton, MapMaker mapMaker)
    {
        this(type, rotations, isButton, mapMaker, 0, 0);
    }
    public OneWayTile(String type, int rotations, boolean isButton, MapMaker mapMaker, int xPosition, int yPosition)
    {
        super(type,rotations,isButton,mapMaker,xPosition,yPosition, false);
        oneWayCollision = false;
    }
    public void addedToWorld(World world)
    {
        super.addedToWorld(world);
    }
    public void act()
    {
        if(player == null)
        {
            if(getWorld().getObjects(Player.class).size() > 0)
            {
                player = getWorld().getObjects(Player.class).get(0);
                playerHeight = player.getImage().getHeight()/2;
                playerWidth = player.getImage().getWidth()/2;
            }
        }
        else
        {
            if(player.getWorld() != null && getDistance(player) < 100)
            {
                int displacement;
                if(collidable)
                {
                    displacement = -25;
                }
                else
                {
                    displacement = 25;
                }
                switch (getRotation())
                {
                    case 0: // push up
                        if (player.getY() + playerHeight > getY() - displacement)
                        {
                            collidable = false;
                        }
                        else
                        {
                            collidable = true;
                            if(intersects(player) && !player.getSlamming())
                            {
                                player.setYVelocity(-1);
                                player.setGrounded();
                                player.getPosition().setCoordinate(player.getPosition().getX(), getPosition().getY() - 25 - player.getImage().getHeight()/2);
                            }
                            else if(player.getSlamming())
                            {
                                collidable = false;
                            }
                        }
                        break;
                    case 1: // push right
                        if (player.getX() - playerWidth < getX() + displacement)
                        {
                            collidable = false;
                        }
                        else
                        {
                            collidable = true;
                            if(intersects(player))
                            {
                                player.getPosition().setCoordinate(getPosition().getX() + 26 + player.getImage().getWidth()/2, player.getPosition().getY());
                            }
                        }
                        break;
                    case 2: // push down
                        if (player.getY() - playerHeight < getY() + displacement)
                        {
                            collidable = false;
                        }
                        else
                        {
                            collidable = true;
                            if(intersects(player))
                            {
                                player.getPosition().setCoordinate(player.getPosition().getX(), player.getPosition().getY() + 5);
                            }
                        }
                        break;
                    case 3: // push left
                        if (player.getX() + playerWidth > getX() - displacement)
                        {
                            collidable = false;
                        }
                        else
                        {
                            collidable = true;
                            if(intersects(player))
                            {
                                player.getPosition().setCoordinate(getPosition().getX() - 26 - player.getImage().getWidth()/2, player.getPosition().getY());
                            }
                        }
                        break;
                }
            }
        }
        super.act();
    }
    public int getDistance(Actor actor)
    {
        int deltaX = actor.getX() - this.getX();
        int deltaY = actor.getY() - this.getY();
    
        return (int)Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
    }
}
