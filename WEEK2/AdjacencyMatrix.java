import java.util.ArrayList;
import java.util.Stack;

public class AdjacencyMatrix {

    public static void main(String[] args) {

        // TASK 1: CREATE AN ADJACENCY MATRIX TO STORE TRAIN STATION CONNECTIONS
        int[][] adjacencyMatrix = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        // TASK 2: IDENTIFY THE SOURCE STATION AND THE DESTINATION STATION
        int start = 0;
        int end = 9;

        PathFinderDFS pathFinder = new PathFinderDFS(adjacencyMatrix, start, end);
        pathFinder.findPathDFS();

        System.out.println(pathFinder);
    }
}


class PathFinderDFS {

    // DATA MEMBERS
    private int[][] adjacencyMatrix;
    private int start;
    private int end;
    private ArrayList<Integer> path;
    private boolean pathFound;

    public PathFinderDFS(int[][] adjacencyMatrix, int s, int d) {
        this.adjacencyMatrix = adjacencyMatrix;
        start = s;
        end = d;
    }

    public boolean findPathDFS() {

        // TASK 1: CONSTRUCT THE STACK TO STORE STATIONS
        Stack<Integer> stack = new Stack<>();
        path = new ArrayList<>();

        // TASK 2: KEEP TRACK OF STATIONS THAT HAVE BEEN VISITED
        boolean[] visited = new boolean[adjacencyMatrix.length];

        // TASK 3: PLACE THE START STATION ON THE STACK AND MARK AS VISITED
        stack.push(start);
        visited[start] = true;

        System.out.print("Start: ");

        while (!stack.isEmpty()) {

            // STEP 1: POP THE STACK AND EXPLORE THIS STATION
            int current = stack.pop();
            path.add(current);

            System.out.println("Travel to Station " + current);

            // STEP 2: CHECK IF DESTINATION IS REACHED
            if (current == end) {
                pathFound = true;
                return true;
            }

            // STEP 3: EXPLORE ADJACENT STATIONS
            int howManyWaysToGo = 0;

            for (int i = 0; i < adjacencyMatrix[current].length; i++) {
                if (adjacencyMatrix[current][i] == 1 && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                    howManyWaysToGo++;
                }
            }

            // STEP 4: DEAD END CHECK
            if (howManyWaysToGo == 0) {
                System.out.println("   DEAD END AT " + current);
                System.out.println("   BACKTRACKING to " + path.get(path.size() - 2));
                path.remove(path.size() - 1);
            }
        }

        pathFound = false;
        return false;
    }

    @Override
    public String toString() {
        String out = "\n\n";

        if (pathFound) {
            out += "Path from station " + start + " to station " + end + ": ";

            for (int i = 0; i < path.size(); i++) {
                out += path.get(i);
                if (i != path.size() - 1) {
                    out += " -> ";
                }
            }
        } else {
            out += "There is no path from station " + start + " to station " + end;
        }

        return out;
    }
}
