import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;

/**
 * Write a description of class TheGrid here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TheGrid  
{
    // instance variables - replace the example below with your own
    private static Tile[][] theGrid;
    private static int[][] airGrid; // 1 - air, 0 - tile
    private static int lowestX = Integer.MAX_VALUE, lowestY = Integer.MAX_VALUE;


    /**
     * Constructor for objects of class TheGrid
     */
    public static void setGrid(Tile[][] newGrid)
    {
        theGrid = new Tile[newGrid.length][newGrid[0].length];
        for(int i = 0; i < theGrid.length; i++)
        {
            for(int j = 0; j < theGrid[i].length; j++)
            {
                theGrid[i][j] = newGrid[i][j];
                if(theGrid[i][j] != null)
                {
                    System.out.print(1);
                }
                else
                {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
        populateAirGrid();
        findLowestCoordinates(theGrid);
    }
    
    public static void populateAirGrid()
    {
        airGrid = new int[theGrid.length][theGrid[0].length];
        for(int i = 0 ; i < theGrid.length ; i++)
        {
            for(int j = 0 ; j < theGrid[i].length ; j++)
            {
                if(theGrid[i][j] != null && theGrid[i][j].getCollidable() == true)
                {
                    airGrid[i][j] = 1;
                }
                else
                {
                    airGrid[i][j] = 0;
                }
            }
        }
    }
    
    public static void findLowestCoordinates(Tile[][] grid)
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if(grid[i][j] != null)
                {
                    if(lowestX > grid[i][j].getGlobalX())
                    {
                        lowestX = grid[i][j].getGlobalX();
                    }
                    if(lowestY > grid[i][j].getGlobalY())
                    {
                        lowestY = grid[i][j].getGlobalY();
                    }
                }
            }
        }
        lowestX = Math.abs(lowestX) - 100;
        lowestY = Math.abs(lowestY);
    }
    
    public static ArrayList<Coordinate> findPathAir(Coordinate start, Coordinate end) 
    {
        ArrayList<Coordinate> path = new ArrayList<>();
    
        int startXIndex = (start.getX() + lowestX) / 50;
        int startYIndex = (start.getY() + lowestY) / 50;
        int endXIndex = (end.getX() + lowestX) / 50;
        int endYIndex = (end.getY() + lowestY) / 50;
    
        System.out.println("xSize: " + airGrid[0].length);
        System.out.println("ySize: " + airGrid.length);
        System.out.println("LowestX: " + lowestX);
        System.out.println("LowestY: " + lowestY);
        System.out.println("startXIndex: " + startXIndex);
        System.out.println("startYIndex: " + startYIndex);
        System.out.println("endXIndex: " + endXIndex);
        System.out.println("endYIndex: " + endYIndex);
        
        if (!isInBounds(startXIndex, startYIndex) || !isInBounds(endXIndex, endYIndex)) 
        {   
            //System.out.println("Start or end point is out of bounds.");
            return path;
        }
    
        int currentX = startXIndex;
        int currentY = startYIndex;
    
        while (currentX != endXIndex || currentY != endYIndex) 
        {
            if (currentX < endXIndex && !checkOccupiedTile(currentX + 1, currentY)) 
            {
                currentX++;
            } 
            else if (currentX > endXIndex && !checkOccupiedTile(currentX - 1, currentY)) 
            {
                currentX--;
            } 
            else if (currentY < endYIndex && !checkOccupiedTile(currentX, currentY + 1)) 
            {
                currentY++;
            } 
            else if (currentY > endYIndex && !checkOccupiedTile(currentX, currentY - 1)) 
            {
                currentY--;
            } 
            else 
            {
                //System.out.println("Path blocked at (" + currentX + ", " + currentY + ").");
                break;
            }
            path.add(new Coordinate(currentX * 50 - lowestX, currentY * 50 - lowestY));
        }
        return path;
    }
    
    private static boolean isInBounds(int x, int y) 
    {
        return x >= 0 && y >= 0 && x < airGrid[0].length && y < airGrid.length;
    }
    
    private static boolean checkOccupiedTile(int x, int y) 
    {
        if (!isInBounds(x, y)) 
        {
            return true;
        }
        return airGrid[y][x] == 1;
    }
    

    public static Coordinate checkSurroundingTiles(int x, int y)
    {
        if(!checkOccupiedTile(y, x - 1)) 
        {
            return(new Coordinate((x-1) * 50 - lowestX, y * 50 - lowestY));
        } 
        else 
        {
            if(!checkOccupiedTile(y, x + 1)) 
            {
                return(new Coordinate((x+1) * 50 - lowestX, y * 50 - lowestY));
            } 
            else 
            {
                if(!checkOccupiedTile(y + 1, x)) 
                {
                    return(new Coordinate(x * 50 - lowestX, (y+1) * 50 - lowestY));
                } 
                else 
                {
                    if(!checkOccupiedTile(y - 1, x)) 
                    {
                        return(new Coordinate(x * 50 - lowestX, (y-1) * 50 - lowestY));
                    } 
                    else 
                    {
                        return null;
                    }
                }
            }
        }
    }
}
