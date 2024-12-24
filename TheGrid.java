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

    /**
     * Constructor for objects of class TheGrid
     */
    public TheGrid(Tile[][] theGrid)
    {
        this.theGrid = theGrid;
    }
    public void populateAirGrid()
    {
        for(int i = 0 ; i < theGrid.length ; i++)
        {
            for(int j = 0 ; j < theGrid[i].length ; i++)
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
    
    public ArrayList<Coordinate> findPathAirGreedy(Coordinate start, Coordinate end) 
    {
        int startXIndex = start.getX() / 50;
        int startYIndex = start.getY() / 50;
        int endXIndex = end.getX() / 50;
        int endYIndex = end.getY() / 50;
    
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.hCost));
        HashSet<String> closedSet = new HashSet<>();
    
        openSet.add(new Node(startXIndex, startYIndex, getHeuristic(startXIndex, startYIndex, endXIndex, endYIndex), null));
    
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Left, Right, Up, Down
    
        while(!openSet.isEmpty()) {
            Node current = openSet.poll();
            if(current.x == endXIndex && current.y == endYIndex) {
                return reconstructPath(current);
            }
            String currentKey = current.x + "," + current.y;
            closedSet.add(currentKey);
    
            for(int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                if (newX >= 0 && newY >= 0 && airGrid[newY][newX] == 0 && !closedSet.contains(newX + "," + newY)) {
                    openSet.add(new Node(newX, newY, getHeuristic(newX, newY, endXIndex, endYIndex), current));
                }
            }
        }
        return new ArrayList<>(); // Return empty path if no path is found
    }

    private int getHeuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
    private ArrayList<Coordinate> reconstructPath(Node node) {
        ArrayList<Coordinate> path = new ArrayList<>();
        while (node != null) {
            path.add(0, new Coordinate(node.x * 50, node.y * 50));
            node = node.parent;
        }
        return path;
    }

    private static class Node 
    {
        int x, y, hCost;
        Node parent;
        Node(int x, int y, int hCost, Node parent) 
        {
            this.x = x;
            this.y = y;
            this.hCost = hCost;
            this.parent = parent;
        }
    }
}
