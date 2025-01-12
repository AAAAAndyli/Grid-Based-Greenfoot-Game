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
                if(theGrid[i][j] != null && (theGrid[i][j].getCollidable() == true && !theGrid[i][j].getType().contains("Background")))
                {
                    //System.out.print(1);
                }
                else
                {
                    //System.out.print(0);
                }
            }
            //System.out.println();
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
                if(theGrid[i][j] != null && (theGrid[i][j].getCollidable() == true && !theGrid[i][j].getType().contains("Background")))
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
        lowestX = Math.abs(lowestX);
        lowestY = Math.abs(lowestY);
    }
    
    /**
     * This is the pathfinding system using the algorithm A* <br>
     * Translated from pseudocode on the website https://medium.com/@nicholas.w.swift/easy-a-star-pathfinding-7e6689c7f7b2 <br>
     * Made more efficient from my poor attempt to translate it using Chat GPT. This will be the only time I (Andy) will ever use ChatGPT
    */
    public static ArrayList<Coordinate> aStarfindPath(Coordinate start, Coordinate end)
    {
        System.out.println("A* Pathfinding Start: " + start + ", End: " + end);
    
        ArrayList<Coordinate> path = new ArrayList<>();
        // Initialize both open and closed lists
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
    
        
        int startXIndex = (start.getX()) / 50;
        int endXIndex = (end.getX()) / 50;
        int startYIndex = (start.getY()) / 50;
        int endYIndex = (end.getY()) / 50;

        
        System.out.println("Start Index: (" + startXIndex + ", " + startYIndex + ")");
        System.out.println("End Index: (" + endXIndex + ", " + endYIndex + ")");
        
        System.out.println("Start Global Coordinates: (" + start.getX() + ", " + start.getY() + ")");
        System.out.println("End Global Coordinates: (" + end.getX() + ", " + end.getY() + ")");
        System.out.println("xSize: " + airGrid[0].length);
        System.out.println("ySize: " + airGrid.length);
        //System.out.println("LowestX: " + lowestX);
        //System.out.println("LowestY: " + lowestY);
        System.out.println("startXIndex: " + startXIndex);
        System.out.println("startYIndex: " + startYIndex);
        System.out.println("endXIndex: " + endXIndex);
        System.out.println("endYIndex: " + endYIndex);
        
        
    
        if (!isInBounds(startXIndex, startYIndex) || !isInBounds(endXIndex, endYIndex)) 
        {
            System.out.println("Path Unobtainable");
            return new ArrayList<Coordinate>(); 
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
                System.out.println("Found Goal");
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
        System.out.println("Path Blocked");
        return path;
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
            int xCoord = current.getX() * 50;
            int yCoord = current.getY() * 50;
            path.add(new Coordinate(xCoord, yCoord));
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
    
    private static boolean isInBounds(int x, int y) 
    {
        return y >= 0 && y < airGrid.length && x >= 0 && x < airGrid[0].length;
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
