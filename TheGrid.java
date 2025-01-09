import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Comparator;
import java.util.LinkedList;

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
    
    /**
     * This is the pathfinding system using the algorithm A* <br>
     * Translated from pseudocode on the website https://medium.com/@nicholas.w.swift/easy-a-star-pathfinding-7e6689c7f7b2 <br>
     * Made more efficient from my poor attempt to translate it using Chat GPT. This will be the only time I (Andy) will ever use ChatGPT
    */
    public static ArrayList<Coordinate> aStarfindPath(Coordinate start, Coordinate end)
    {
        // Initialize both open and closed lists
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
    
        // Convert start and end coordinates to grid indices
        int startXIndex = (start.getX() + lowestX) / 50;
        int startYIndex = (start.getY() + lowestY) / 50;
        int endXIndex = (end.getX() + lowestX) / 50;
        int endYIndex = (end.getY() + lowestY) / 50;
    
        if (!isInBounds(startXIndex, startYIndex) || !isInBounds(endXIndex, endYIndex)) 
        {
            return new ArrayList<>(); 
        }
    
        // Add the start node
        Node startNode = new Node(startXIndex, startYIndex, 0, getHValue(startXIndex, startYIndex, endXIndex, endYIndex));
        Node endNode = new Node(endXIndex, endYIndex, 0, 0);
        openList.add(startNode);
    
        // Loop until you find the end
        while (!openList.isEmpty()) 
        {
            // get current node
            Node currentNode = getLowestCostNode(openList);
            openList.remove(currentNode);
            closedList.add(currentNode);
            
            if (currentNode.equals(endNode)) 
            {
                return createCoordinatePath(currentNode); //Found goal
            }
            // Search surrounding tiles
            for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}}) 
            {
                int neighborX = currentNode.getX() + direction[0];
                int neighborY = currentNode.getY() + direction[1];
                // Skip if out of bounds or blocked
                if (!isInBounds(neighborX, neighborY) || checkOccupiedTile(neighborX, neighborY)) 
                {
                    continue;
                }
                Node neighbor = new Node(neighborX, neighborY, currentNode.getGCost() + 1, getHValue(neighborX, neighborY, endXIndex, endYIndex));
                neighbor.setParent(currentNode);
                // Skip if already in the closed list
                if (closedList.contains(neighbor)) 
                {
                    continue;
                }
                // adds node to openList
                if (!openList.contains(neighbor) || neighbor.getFCost() < getExistingNodeFCost(openList, neighbor)) 
                {
                    openList.add(neighbor);
                }
            }
        }
        // Return no path if no path exists
        return new ArrayList<>();
    }
    
    private static Node getLowestCostNode(ArrayList<Node> nodeList) 
    {
        return Collections.min(nodeList, Comparator.comparingInt(Node::getFCost));
    }
    
    private static int getExistingNodeFCost(ArrayList<Node> nodeList, Node targetNode) 
    {
        for (Node node : nodeList) 
        {
            if (node.equals(targetNode)) 
            {
                return node.getFCost();
            }
        }
        return Integer.MAX_VALUE;
    }
    
    private static int getHValue(int x, int y, int targetX, int targetY) 
    {
        return Math.abs(x - targetX) + Math.abs(y - targetY); // distance
    }
    
    public static ArrayList<Coordinate> createCoordinatePath(Node endNode)
    {
        ArrayList<Coordinate> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) 
        {
            path.add(new Coordinate(current.getX() * 50 - lowestX, current.getY() * 50 - lowestY));
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
    
    private static class Node 
    {
        private int x, y, gCost, hCost;
        private Node parent;
        public Node(int x, int y, int gCost, int hCost) {
            this.x = x;
            this.y = y;
            this.gCost = gCost;
            this.hCost = hCost;
            this.parent = null;
        }
        public int getX() 
        {
            return x;
        }
        public int getY() 
        {
            return y;
        }
        public int getGCost() 
        {
            return gCost;
        }
        public int getFCost() 
        {
            return gCost + hCost;
        }
        public Node getParent() 
        {
            return parent;
        }
        public void setParent(Node parent) 
        {
            this.parent = parent;
        }
        @Override
        public boolean equals(Object obj) 
        {
            if (this == obj) 
            {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) 
            {
                return false;
            }
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }
        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
    

    /**
     * Method findPathAir
     *
     * @param start The starting tile
     * @param end The desired end location
     * @return The list of coordinates to follow
     * @deprecated
     */
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
