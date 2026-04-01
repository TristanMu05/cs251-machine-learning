public class MazeCell {
    // DATA MEMBERS OF A MAZE CELL
    public int x;
    public int y;
    public int id;
    public boolean visited;
    public boolean north;
    public boolean south;
    public boolean east;
    public boolean west;

    // EXPLICIT CONSTRUCTOR
    public MazeCell(int xPos, int yPos, int cellID) {
        x = xPos;
        y = yPos;
        id = cellID;
        visited = false;
        north = true;
        south = true;
        east = true;
        west = true;
    }
}