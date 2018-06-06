/**
 * this data structure holds the coordinates
 * for a point in the quadtree
 * @author Margaret Stump marstump
 * @version 10/5/17
 *
 */
public class Point 
{
    private int x;
    private int y;
    private String name;
    
    /**
     * constructor - creates the point
     * @param name is the name of the point
     * @param x is the x coordinate of the point
     * @param y is the y coordinate of the point
     */
    public Point(String name, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    
    /**
     * returns the x coordinate of the point
     * @return x is the coordinate
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * returns the y coordinate of the point
     * @return y is the coordinate
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * erturns the name of the point
     * @return name is the name of the point
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * returns the point in the form of a string
     * @return s is the string
     */
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("(" + name + ", ");
        s.append(Integer.toString(x) + ", ");
        s.append(Integer.toString(y) + ")");
        
        return s.toString();
    }
}
