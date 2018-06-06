/**
 * each rectangle is a node in the bst
 * 
 * @author Margaret Stump
 * @version 9/6/17
 *
 */
public class Rectangle 
{
    private String name;
    private int x;
    private int y;
    private int w;
    private int h;
    //private boolean isPair;
    
    
    /**
     * creates the rectangle to be inserted in the tree
     * @param name is the name of the rectangle
     * @param x is the x coordinate of upper left corner
     * @param y is the width of the rectangle
     * @param w is the y coordinate of upper left corner
     * @param h is the height of the rectangle
     */
    public Rectangle(String name, int x, int y, int w, int h)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        //isPair = false;
    }
    
    /**
     * returns the rectangle in the form of a string
     * @return s is the string of the rectangle
     */
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("(");
        s.append(name + ", ");
        s.append(Integer.toString(x) + ", ");
        s.append(Integer.toString(y) + ", ");
        s.append(Integer.toString(w) + ", ");
        s.append(Integer.toString(h) + ")");
        
        return s.toString();
        
    }
    
    /**
     * returns the name of the rectangle 
     * @return name is the name key of the rectangle
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * returns an array of the x,y,w,h of the rectangle
     * @return nums is the array of x, y, w, h
     */
    public int[] getNums()
    {
        int[] nums = {x, y, w, h};
        return nums;
    }
    
    
    
    
    
    

}
