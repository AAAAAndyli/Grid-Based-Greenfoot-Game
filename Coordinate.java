/**
 * Write a description of class Coordinate here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coordinate  
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setCoordinate(int x, int y)
    {
        //System.out.println(this);
        this.x = x;
        this.y = y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
    public String getString()
    {
        return "(" + x + ", " + y + ")";
    }
    
}
