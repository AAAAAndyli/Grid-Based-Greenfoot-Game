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
            }
        }
        populateAirGrid();
        findLowestCoordinates(newGrid);
    }
    
    public static void populateAirGrid()
    {
        airGrid = new int[theGrid.length][theGrid[0].length];
        for(int i = 0 ; i < theGrid.length ; i++)
        {
            for(int j = 0 ; j < theGrid[i].length ; j++)
            {
                if(theGrid[i][j] != null)
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
    }
    
    public static ArrayList<Coordinate> findPathAir(Coordinate start, Coordinate end) 
    {
        int startXIndex = (start.getX() + lowestX) / 50;
        int startYIndex = (start.getY() + lowestY) / 50;
        int endXIndex = (end.getX() + lowestX) / 50;
        int endYIndex = (end.getY() + lowestY) / 50;
        
        int currentXIndex = startXIndex;
        int currentYIndex = startYIndex;
        ArrayList<Coordinate> tilePoints = new ArrayList<Coordinate>();
        /*
        while(currentXIndex != endXIndex && currentYIndex != endYIndex)
        {
            if(currentXIndex > endXIndex)
            {
                currentXIndex--;
            }
            else if(currentXIndex < endXIndex)
            {
                currentXIndex++;
            }
            if(currentYIndex > endYIndex)
            {
                currentYIndex--;
            }
            else if(currentYIndex < endYIndex)
            {
                currentYIndex++;
            }
            if(!checkOccupiedTile(currentYIndex, currentXIndex))
            {
                tilePoints.add(new Coordinate(currentXIndex * 50 - lowestX, currentYIndex * 50 - lowestY));
            }
            else
            {
                tilePoints.add(new Coordinate(currentXIndex * 50 - lowestX, currentYIndex * 50 - lowestY));
            }
        }
        */
        while (currentXIndex != endXIndex || currentYIndex != endYIndex) 
        {
            if (currentXIndex != endXIndex && currentYIndex != endYIndex) 
            {
                // Diagonal movement
                if (currentXIndex > endXIndex)
                {
                    currentXIndex--;
                } else 
                {
                    currentXIndex++;
                }
                if (currentYIndex > endYIndex)
                {
                    currentYIndex--;
                } 
                else 
                {
                    currentYIndex++;
                }
            } else if (currentXIndex != endXIndex) 
            {
                // Horizontal movement
                if (currentXIndex > endXIndex) 
                {
                    currentXIndex--;
                } 
                else 
                {
                    currentXIndex++;
                }
            } else if (currentYIndex != endYIndex) 
            {
                // Vertical movement
                if (currentYIndex > endYIndex) 
                {
                    currentYIndex--;
                } 
                else 
                {
                    currentYIndex++;
                }
            }
            if (!checkOccupiedTile(currentYIndex, currentXIndex)) 
            {
                tilePoints.add(new Coordinate(currentXIndex * 50 - lowestX, currentYIndex * 50 - lowestY));
            } else 
            {
                tilePoints.add(new Coordinate(currentXIndex * 50 - lowestX, currentYIndex * 50 - lowestY));
            }
            for(Coordinate coords : tilePoints)
            {
                System.out.println(coords.getString());
            }
        }
        
        return tilePoints;
    }
    public static boolean checkOccupiedTile(int x, int y)
    {
        //if occupied, true. else, false
        if((y + lowestY) / 50 > 0 && (x + lowestX) / 50 > 0 && (x + lowestX) / 50 < airGrid[0].length && (y + lowestY) / 50 < airGrid.length)
        {
            return airGrid[(y + lowestY) / 50][(x + lowestX) / 50] != 0;
        }
        return false;
    }
}
