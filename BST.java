/**
 * representation of Points in plane
 * @author Margaret Stump
 * @version 9/6/17
 *
 */
public class BST 
{
    //number of intersections of tree
    private int index;
    
    //number of Points in the tree
    private int size;
    //private int depth;
    
    private Node<Point> root;
    
    /**
     * creates the BST with one node, the full plane
     */
    public BST()
    {
        root = null;
        index = 0;
        size = 0;
    }
    
    /**
     * inserts a Point with name name and origin x, y and dimensions w, h
     * @param point is the point being inserted
     * @return true if the Point is inserted
     */
    public boolean insert(Point point)
    {
        String name = point.getName();
        int x = point.getX();
        int y = point.getY();
        //must start with character
        if (!Character.isLetter(name.charAt(0)))
        {
            return false;
        }
        if (x < 0 || y < 0 || x > 1024 || y > 1024)
        {
            return false;
        }
        
        //insert Point
        if (size == 0)
        {
            root = new Node<Point>(point);
            //root.setDepth(count);
            //root.setElement(newPoint);
            size++;
        }
        else
        {
            helpInsert(point, root);
            updateDepth(root, 0);
            size++;
        }
        return true;
    }
    
    /**
     * helper method used to help the insert function
     * @param point is the Point being inserted
     * @param node is the root node
     * 
     * @return true when it is inserted
     */
    public Node<Point> helpInsert(Point point, Node<Point> node)
    {
        
        if (node == null)
        {
            Node<Point> newNode = new Node<Point>(point);
            //newNode.setDepth(level);
            return newNode;
        }
        
        String name = point.getName();
        String currName = node.getElement().getName();
        
        if (name.compareTo(currName) <= 0)
        {
            node.setLeft(helpInsert(point, node.getLeft()));
        }
        else 
        {
            node.setRight(helpInsert(point, node.getRight()));
        }
        
        return node;
        
    }
    
    /**
     * removes the Point with the name name if it is in the BST
     * @param name is the name of the Point being searched for 
     * @return found is the Point removed from the tree
     */
    public Point removeName(String name)
    {
        //find Point
        Point[] found = search(name);
        if (found == null)
        {
            return null;
        }
        
        //remove node from tree
        helperRemoveName(name, root);
        
        if (found[0] != null)
        {
            updateDepth(root, 0);
            size--;
        }
        
        return found[0];
    }
    
    /**
     * helper method for removeName
     * @param name is the name of the Point 
     * @param node is the current node, initially root
     */
    private Node<Point> helperRemoveName(String name, Node<Point> node)
    {
        Node<Point> result = node;
        
        if (node == null)
        {
            return null;
        }
        
        Point curr = node.getElement();
        String currName = curr.getName();
        
        if (name.compareTo(currName) < 0)
        {
            node.setLeft(helperRemoveName(name, node.getLeft()));
        }
        else if (name.compareTo(currName) > 0)
        {
            node.setRight(helperRemoveName(name, node.getRight()));
        }
        else
        {
            if ((node.getLeft() != null) && (node.getRight() != null) ||
                   node.getLeft() != null && node.getRight() == null )
            {
                Node<Point> first = findMax(node.getLeft());
                node.setElement(first.getElement());
                node.setLeft(helperRemoveName(first.getElement().getName(), 
                        node.getLeft()));
                result = node;
            }
            
            else if (node.getRight() != null && node.getLeft() == null)
            {
                if (node == root)
                {
                    Node<Point> first = findMin(node.getRight());
                    node.setElement(first.getElement());
                    node.setRight(helperRemoveName(
                            first.getElement().getName(), node.getRight()));
                    result = node;
                }
                result = node.getRight();
            }
            else
            {
                result = node.getLeft();
            }
               
        }
        
        return result;
    }
    
    /**
     * internal method used to find the first alphabetical name of
     * the Points in the tree with root node
     * @param node the node that roots the tree
     * @return node containing the first alphabetical item
     */
    private Node<Point> findMax(Node<Point> node) 
    {
        if (node == null)
        {
            return node;
        }
        else if (node.getRight() == null)
        {
            return node;
        }
        else
        {
            return findMax(node.getRight());
        }
    }
    

    /**
     * internal method used to find the first alphabetical name of
     * the Points in the tree with root node
     * @param node the node that roots the tree
     * @return node containing the first alphabetical item
     */
    private Node<Point> findMin(Node<Point> node) 
    {
        if (node == null)
        {
            return node;
        }
        else if (node.getLeft() == null)
        {
            return node;
        }
        else
        {
            return findMax(node.getLeft());
        }
    }

    /**
     * removes the Point at location x, y, with dimensions w, h
     * from the tree if it is in the BST
     * @param x is the x coordinate of  the Point
     * @param y is the y coordinate of the Point
     * 
     * @return found is the Point being removed from the tree
     */
    public Point removeXY(int x, int y)
    {
        
        //find in tree with traversal
        Node<Point> found = findPoint(root, x, y);
        
        if (found == null)
        {
            return null;
        }
        
        Point rect = found.getElement();
        helperRemoveName(rect.getName(), root);

        if (rect != null)
        {
            updateDepth(root, 0);
            size--;
        }
        
        //remove from tree and return
        return rect;
    }
    
    /**
     * helper method for removeXY method
     * @param node is the node, initially root
     * @param x is the x coordinate of the origin of the Point
     * @param y is the y coordinate of the origin of the Point
     * 
     * @return found is the Point
     */
    private Node<Point> findPoint(Node<Point> node,  
            int x, int y)
    {
        //boolean foundIt = false;
        if (size == 0 || node == null)
        {
            return null;
        }
        
        //in order traversal of x y w h
        Point current = node.getElement();
        int currX = current.getX();
        int currY = current.getY();
        if (currX == x && currY == y)
        {
            //remove 
            return node;
        }
        else
        {
            Node<Point> foundNode = findPoint(node.getLeft(),
                    x, y);
            if (foundNode == null)
            {
                foundNode = findPoint(node.getRight(), x, y);
            }
            return foundNode;
        }
    }
    
    /**
     * reports the number of Points in the plane that intersect with
     * the specified search area
     * @param x is the x coordinate of the origin of the search area
     * @param y is the y coordinate of the origin of the search area
     * @param w is the width of the search area
     * @param h is the height of the search area
     * @return s is the list of Points that intersect the search area
     */
    public String regionSearch(int x, int y, int w, int h)
    {
        StringBuilder s = new StringBuilder();
        
        //must be rejected if height or width is not greater than 0
        if (w <= 0 || h <= 0 )
        {
            s.append("Point rejected: ");
            s.append("(" + Integer.toString(x) + ", " + 
                    Integer.toString(y) + ", " + 
                    Integer.toString(w) + ", " + Integer.toString(h) + ")");
            return s.toString();
        }
        else
        {
            s.append("Points intersecting region ");
        }
        
        s.append("(" + Integer.toString(x) + ", " + 
                Integer.toString(y) + ", " + 
                Integer.toString(w) + ", " + Integer.toString(h) + "):");
        helperRegionSearch(s, root, x, y, w, h);
        return s.toString();
    }
    
    /**
     * helper method for regionsearch
     * @param s is the stringbuilder changing
     * @param node is the node, starts with root
     * @param findX is the x coordinate of the origin of the search area
     * @param findY is the y coordinate of the origin of the search area
     * @param findW is the width of the search area
     * @param findH is the height of the search area
     */
    public void helperRegionSearch(StringBuilder s, Node<Point> node, 
            int findX, int findY, int findW, int findH)
    {
        if (node == null)
        {
            return;
        }
        //do root
        Point curr = node.getElement();
        
        int x = curr.getX();
        int y = curr.getY();
        
        int searchW = findX + findW;
        int searchH = findY + findH;
        
        if (x > findX && x < searchW && y > findY && y < searchH)
        {
            s.append("\n" + curr.toString());
        }
        
        
        //do left an then right
        helperRegionSearch(s, node.getLeft(), findX, findY, findW, findH);
        helperRegionSearch(s, node.getRight(), findX, findY, findW, findH);
        
    }
    
    /**
     * reports the Points in the plane that intersect with each other
     * @return s is the list of intersecting Points
     */
    public String intersections()
    {
        Point[][] intersectPairs = new Point[size * 3][2];
        index = 0;
        
        StringBuilder s = new StringBuilder();
        
        helperIntersects1(root, intersectPairs);
        
        for (int i = 0; i < intersectPairs.length; i++)
        {

            for (int j = 0; j < intersectPairs.length; j++)
            {
                if (intersectPairs[i] != null && intersectPairs[j] != null)
                {
                    if (intersectPairs[i][0] == intersectPairs[j][1]
                        && intersectPairs[i][1] == intersectPairs[j][0])
                    {
                        intersectPairs[j] = null;
                    }
                    
                }
                
            }
            
            //print first Point in pair
            if (intersectPairs[i] != null)
            {
                
                s.append(intersectPairs[i][0].toString());
                s.append(" : ");
                
                //print second Point in pair
                s.append(intersectPairs[i][1].toString());
                s.append("\n");
            }
        }
        
 
        
        return s.toString();
    }
    
    /**
     * helper method for intersects method, uses recursion to find pairs 
     * of Points that intersect each other
     * @param node is the current node, starts with root
     * @param intersectPairs is the array of intersecting pairs
     */
    public void helperIntersects1(Node<Point> node, 
            Point[][] intersectPairs)
    {
        //starts with one Point, goes through every other Point to check
        //if they intersect each other, if the pair has already been printed
        //it does not print it a second time
        if (node == null)
        {
            return;
        }
        helperIntersects2(node, root, intersectPairs);
        
        helperIntersects1(node.getLeft(), intersectPairs);
        helperIntersects1(node.getRight(), intersectPairs);
        
    }
    
    /**
     * another helper method for the intersects command
     * @param node is the current node
     * @param region is the Point you are trying to find pairs for
     * @param intersectPairs is the array of Point pairs 
     */
    public void helperIntersects2(Node<Point> region, Node<Point> node, 
            Point[][] intersectPairs)
    {
        if (region == null || node == null || region == node)
        {
            return;
        }
        
        Point curr = node.getElement();
        
        int x = curr.getX();
        int y = curr.getY();
        
        Point point = region.getElement();
        int findX = point.getX();
        int findY = point.getY();
        
        if (x == findX && y == findY)
        {
            intersectPairs[index][0] = point;
            intersectPairs[index][1] = curr;
            
            index++;
        }
        
        
        
        helperIntersects2(region, node.getLeft(), intersectPairs);
        helperIntersects2(region, node.getRight(), intersectPairs);
        
    }
    
    /**
     * searches for a Point in the plane by the name and returns it
     * @param name is the name of the Point
     * @return Point is the Point found in the plane
     */
    public Point[] search(String name)
    {
        if (size == 0)
        {
            return null;
        }
        //Point[] duplicates = new Point[size];
        
        Point[] found = helperSearch(name, root);
        //Point Point = found.getElement();
        
        return found;
    }
    
    /**
     * helper method for search, finds all duplicates
     * @param name is the name of the Point
     * @param node is the node, initially the root
     * @return found the matching Point
     */
    private Point[] helperSearch(String name, Node<Point> node)
    {
        Point[] found = new Point[size];
        
        if (node == null)
        {
            return null;
        }
        
        String currName = node.getElement().getName();
        
        if (currName.equals(name))
        {
            found[0] = node.getElement();
            int i = 1;
            Node<Point> dup = node;
            
            while (dup.getLeft() != null)
            {
                if (dup.getLeft().getElement().getName()
                    .equals(dup.getElement().getName()))
                {
                    found[i] = dup.getLeft().getElement();
                    i++;
                    dup = dup.getLeft();
                }
                else
                {
                    break;
                }
                
            }
            
            
        }
        else if (name.compareTo(currName) < 0)
        {
            found = helperSearch(name, node.getLeft());
        }
        else 
        {
            found = helperSearch(name, node.getRight());
        }
        return found;
    }
    
    /**
     * creates a string filled with all of the contents of the 
     * plane
     * @return s is the list of all the Points in the plane
     */
    public String dump()
    {
        StringBuilder s = new StringBuilder();
        s.append("BST dump:\n");
        if (size == 0)
        {
            
            s.append("Node has depth 0, Value (null)\n");
        }
        else
        {
            //depth = 1;
            helperDump(root, s);
        }
        
        s.append("BST size is: " + Integer.toString(size));
        
        return s.toString();
    }
    
    /**
     * dumps the contents of nodes
     * @param node is the root node
     * @param s is the string builder
     */
    private void helperDump(Node<Point> node, StringBuilder s)
    {
        if (node == null)
        {
            //depth--;
            return;
        }
        
        
        else
        {
            //depth++;
            helperDump(node.getLeft(), s);
            
            s.append("Node has depth ");
            s.append(Integer.toString(node.getDepth()));
            s.append(", Value ");
            s.append(node.getElement().toString());
            s.append("\n");
            
                
            //depth++;
            helperDump(node.getRight(), s);
            
        }
        
        
    }
    
    /**
     * updates the depth of the nodes in the tree 
     * @param node is the current node 
     * @param depth is the depth of the current node
     */
    private void updateDepth(Node<Point> node, int depth)
    {
        if (node != null)
        {
            node.setDepth(depth);
            updateDepth(node.getLeft(), depth + 1);
            updateDepth(node.getRight(), depth + 1);
            
        }
    }
    
    
}
