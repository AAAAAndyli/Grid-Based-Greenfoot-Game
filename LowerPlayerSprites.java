import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LowerPlayerSprites here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LowerPlayerSprites extends PlayerSprites
{
    private String previousState = "";
    public LowerPlayerSprites(Player player)
    {
        super(player);
        loadAnimationFrames("images/PlayerSprites/Lower");
    }
    public void act()
    {
        if(!player.getFacing()) //if player is facing right
        {
            flipped = true;            
            //System.out.println("Down facing Left");
            player.setLowerSpriteDirection(-1);
        }
        else
        {
            flipped = false;
            //System.out.println("Down facing Right");
            player.setLowerSpriteDirection(1);
        }
        super.act();
        String playerState = player.getState();
        if(!playerState.equals(previousState))
        {
            idleIndex = 0;
            walkIndex = 0;
            jumpIndex = 0;
            fallIndex = 0;
            parry = 0;
            slideIndex = 0;
            meleeAttackIndex = 0;
            dashIndex = 0;
        }
        switch(playerState)
        {
            case "idle":
                idleIndex = animate(!flipped ? idleAnimR : idleAnimL, idleIndex);
                break;
            case "running":
                walkIndex = animate(!flipped ? walkAnimR : walkAnimL, walkIndex);
                break;
            case "jumping":
                jumpIndex = animate(!flipped ? jumpAnimR : jumpAnimL, jumpIndex);
                break;
            case "falling":
            case "slamming":
                fallIndex = animate(!flipped ? fallAnimR : fallAnimL, fallIndex);
                break;
            case "parrying":
                parry = animate(!flipped ? parryAnimR : parryAnimL, parry);
                break;
            case "sliding":
                slideIndex = animate(!flipped ? slideAnimR : slideAnimL, slideIndex);
                break;
            case "meleeAttacking":
                meleeAttackIndex = animate(!flipped ? meleeAttackAnimR : meleeAttackAnimL, meleeAttackIndex);
                break;
            case "dashing":
                dashIndex = animate(!flipped ? dashAnimR : dashAnimL, dashIndex);
                break;
        }
        previousState = playerState;
        
    }
    /**
     * Loads in every frame for every animation
     * 
     * @param path - The file path for the unit
     */
    protected void loadAnimationFrames(String path)
    {
        loadSingleAnimation(path, idleAnimL, "idle", true);
        loadSingleAnimation(path, idleAnimR, "idle");
        loadSingleAnimation(path, walkAnimL, "run", true);
        loadSingleAnimation(path, walkAnimR, "run");
        loadSingleAnimation(path, jumpAnimL, "jump", true);
        loadSingleAnimation(path, jumpAnimR, "jump");
        loadSingleAnimation(path, fallAnimL, "fall", true);
        loadSingleAnimation(path, fallAnimR, "fall");
        loadSingleAnimation(path, meleeAttackAnimL, "meleeAttack", true);
        loadSingleAnimation(path, meleeAttackAnimR, "meleeAttack");
        loadSingleAnimation(path, slideAnimL, "slide", true);
        loadSingleAnimation(path, slideAnimR, "slide");
        loadSingleAnimation(path, parryAnimL, "parry", true);
        loadSingleAnimation(path, parryAnimR, "parry");
        loadSingleAnimation(path, dashAnimL, "dash", true);
        loadSingleAnimation(path, dashAnimR, "dash");
    }
}
