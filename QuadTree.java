import java.util.LinkedList;

/**
 * the quadtree
 * @author Margaret Stump marstump
 * @author Julie Tran
 * @version 10/5/17
 */
public class QuadTree 
{
    private QuadNode root;
    private int originX;
    private int originY;
    private int fullSize;
    private int numPoints;
    private int numNodes;
    private LeafNode<LinkedList<Point>> flyweight;

    /**
     * creates quadtree - constructor
     * @param originX is the x coordinate of the origin
     * @param originY is the y coordinate of the origin
     * @param fullSize is the size of the world
     */
    public QuadTree(int originX, int originY, int fullSize)
    {
        this.originX = originX;
        this.originY = originY;
        this.fullSize = fullSize;

        numPoints = 0;
        numNodes = 1;
        flyweight = new LeafNode<LinkedList<Point>>(null);
        root = flyweight;
    }

    /**
     * inserts a point into the tree
     * @param name is the name of the point
     * @param x is the x coordinate of the point
     * @param y is the y coordinate of the point
     * @return result is the node that the point was inserted into
     */
    public QuadNode insert(String name, int x, int y)
    {
        QuadNode result = null;
        Point point = new Point(name, x, y);
        if (numPoints == 0)
        {
            LinkedList<Point> list = new LinkedList<Point>();

            list.add(point);
            LeafNode<LinkedList> lNode = new LeafNode<>(list);
            root = lNode;
            result = lNode;
        }
        else
        {
            result = insertHelp(point, root, originX, originY, fullSize);
        }
        numPoints++;
        return result;
    }

    /**
     * /**
     * helper function for insert
     * @param point is the point being inserted
     * @param node is the node that is being checked
     *
     * @param x is the x coordinate of the current node
     * @param y is the y coordinate of the current node
     * @param size is the size of the current node
     * @return true when the point is inserted
     */
    public QuadNode insertHelp(Point point, QuadNode node, 
            int x, int y, int size)
    {
        //if the node is an empty leaf node, insert
        if (node == flyweight)
        {
            LinkedList<Point> list = new LinkedList<Point>();
            list.add(point);
            LeafNode<LinkedList> lNode = new LeafNode<>(list);
            node = lNode;
            return lNode;
        }
        else if (node.getClass() == LeafNode.class)
        {

            //check if the point in the leaf node is equal to x and y 
            LeafNode<LinkedList> current = (LeafNode<LinkedList>)node;
            LinkedList<Point> currList = current.getElement();

            if (size == 1)
            {
                currList.add(point);
                return node;
            }

            //adding a point to this list will split the leaf node 
            //into more nodes
            if (currList.size() >= 3)
            {
                boolean isDupeList = true;

                for (int i = 0; i < currList.size(); i++)
                {
                    Point currPoint = currList.get(i);

                    if (currPoint.getX() != point.getX() || 
                            currPoint.getY() != point.getY())
                    {
                        isDupeList = false;
                        break;
                    }
                }

                //if the list is only all the same point, can be more than three
                if (isDupeList)
                {
                    currList.add(point);
                    return node;
                }
                else
                {
                    InternalNode newInternalNode = new InternalNode(flyweight);
                    numNodes += 4;
                    if (root == node)
                    {
                        root = newInternalNode;
                    }
                    node = newInternalNode;


                    for (int i = 0; i < currList.size(); i++)
                    {
                        insertHelp(currList.get(i), node, x, y, size);
                    }

                    insertHelp(point, node, x, y, size);

                }

            }
            //smaller than three points
            else
            {
                currList.add(point);
                return node;
            }

        }
        else if (node.getClass() == InternalNode.class)
        {
            InternalNode current = (InternalNode)node;

            int pointX = point.getX();
            int pointY = point.getY();
            int half = size / 2;

            if ((pointX > x && pointX < x + half && pointY > y 
                    && pointY < y + half) ||
                        (pointX == x + half && pointY < y + half))
            {
                //for NW
                current.setNW(insertHelp(point, current.getNW(), x, y, 
                        half));
            }
            else if ((pointX > (x + half) && pointX <= (x + size) 
                    && pointY >= y && pointY <= (y + half)) || 
                        (pointX >= x + half && pointY == y + half)) 
            {
                //for NE
                current.setNE(insertHelp(point, current.getNE(), 
                        x + half, y, half));
            }
            //TO:do finish
            else if ((pointX >= (x + half)  && pointX <= (x + size) && 
                    pointY > (y + half) && pointY <= (y + size)) ||
                        (pointX == x + half && pointY > y + half))
                    
            {
                //for SE
                current.setSE(insertHelp(point, current.getSE(), x + size / 2,
                        y + size / 2, size / 2));
            }
            else if (pointX >= x  && pointX < (x + size / 2) && 
                    pointY >= (y + size / 2) && pointY <= (y + size) ||
                        (pointX < x + half && pointY == y + half))
            {
                //for SW
                current.setSW(insertHelp(point, current.getSW(), 
                        x, y + size / 2, size / 2));
            }
        }
        return node;
    }


    /**
     * removes a point from the tree with 
     * the specified name
     * @param name is the name of the point
     * @return the point when it is removed
     */
    public boolean removeName(String name) 
    {
        //use helper function
        //int level = 0;
        boolean removed = false;
        if (numPoints == 0)
        {
            return false;
        }

        boolean found = removeNameHelper(name, root, removed);
        if (found)
        { 
            updateQuadTree();
            //numPoints--;
            removed = true;
        }
        //System.out.println(Integer.toString(numPoints));
        return removed;
    }

    /**
     * helper method for removeName method
     * @param name is the name of the point to be removed
     * @param node is the current node in the tree, originally root
     * @param removed is true when the point with name name is removed
     * @return the point that was removed from the tree
     */
    public boolean removeNameHelper(String name, QuadNode node,
            boolean removed)
    {
        if (!removed)
        {
            if (node != flyweight)
            {
                if (node.getClass() == InternalNode.class)
                {
                    //InternalNode current = (InternalNode) node;
                    //level++;
                    removed = (removeNameHelper(name, 
                            ((InternalNode)node).getNE(), removed));
                    removed = (removeNameHelper(name, 
                            ((InternalNode)node).getNW(), removed));
                    removed = (removeNameHelper(name, 
                            ((InternalNode)node).getSE(), removed));
                    removed = (removeNameHelper(name, 
                            ((InternalNode)node).getSW(), removed));

                }
                else if (node.getClass() == LeafNode.class)
                {
                    LinkedList<?> list = (LinkedList<?>) 
                            ((LeafNode<?>) node).getElement();

                    if (list.size() == 1)
                    {
                        //LeafNode<?> curr = (LeafNode<?>) node;
                        Point point = (Point) list.get(0);
                        if (point.getName().equals(name))
                        {
                            removed = true;
                            node = flyweight;
                            numPoints--;
                            return true;
                        }
                    }
                    else
                    {
                        for (int i = 0; i < list.size(); i++)
                        {
                            Point point = (Point) list.get(i);
                            if (point.getName().equals(name))
                            {

                                list.remove(i);
                                numPoints--;
                                return true;
                            }
                        }
                    }

                }
            }

        }
        return removed;
    }

    /**
     * removes the point with the specified x, y
     * coordinates
     * if the point is not in the tree, returns null
     * @param x is the point's x coordinate
     * @param y is the point's y coordinate
     * @return found is the removed point
     */
    public boolean removeXY(int x, int y) 
    {
        //int level = 0;
        boolean found = false;
        if (numPoints == 0)
        {
            return false;
        }
        found = removeXYHelp(root, x, y, 
                originX, originY, fullSize, found);



        if (found)
        {
            updateQuadTree();
            //numPoints--;
        }
        //System.out.println(Integer.toString(numPoints));

        return found;
    }

    /**
     * helper method for removeXY
     * @param node is the current node, root initially
     * @param cX is the originX of the current node
     * @param cY is the originY of the current node
     * @param x is the x coord of the point being removed
     * @param y is the y cood of the point being removed
     * @param size is the size of the current ndoe
     * @param found is true when the point x, y is removed
     * @return is true when point x, y is removed
     */
    private boolean removeXYHelp(QuadNode node, int x, int y, int cX, int cY, 
            int size, boolean found)
    {
        if (node != flyweight)
        {

            if (node.getClass() == LeafNode.class)
            {
                LinkedList<?> current = (LinkedList<?>) 
                        ((LeafNode<?>)node).getElement();
                if (current.size() == 1) 
                {
                    //LeafNode<?> curr = (LeafNode<?>) node;
                    node = flyweight;

                    numPoints--;
                    numNodes -= 4;
                    return true;
                    //return node;
                }
                else {
                    //check to see if there is more than one point 
                    //in this location
                    //and only remove one
                    for (int i = 0; i < current.size(); i++) 
                    {
                        Point currentPoint = (Point) current.get(i);
                        if (currentPoint.getX() == x && 
                                currentPoint.getY() == y)
                        {
                            current.remove(i);
                            numPoints--;
                            return true;
                            //return node;
                        }
                    }
                }

            }

            //if the current node is an internal node, 
            //check the x and y and go to the 
            //corresponding child node
            else if (node.getClass() == InternalNode.class)
            {
                InternalNode curr = (InternalNode) node;
                int half = size / 2;

                if ((x > cX && x < cX + half &&
                        y > cY && y < cY + half) ||
                            ((x == cX + half && y < cY + half)))
                {
                    return (removeXYHelp(curr.getNW(), x, y, cX, 
                            cY, half, found));
                }
                else if ((x > cX + half && x < cX + size &&
                        y > cY && y < cY + half) ||
                            (x >= cX + half && y == cY + half) ||
                                (x == half && y == half))
                {
                    return (removeXYHelp(curr.getNE(), x, y, cX + half, 
                            y, half, found));
                }
                else if ((x > cX + half && x < cX + size &&
                        y > cY + size / 2 && y < cY + size) ||
                            (x == cX + half && y > cY + half))
                {
                    return (removeXYHelp(curr.getSE(), x, y, cX + half, 
                            cY + half, half, found));
                }
                else if ((x > cX  && x < (cX + half) && 
                    y > (cY + half) && y < (y + size)) ||
                        (x < cX + half && y == cY + half))
                {
                    return (removeXYHelp(curr.getSW(), x, y, cX, 
                            cY + half, half, found));
                }
            }
        }

        return false;
    }
    /**
     * returns the points that are within the 
     * specified region
     * @param findX is the x coordinate of the origin of the region
     * @param findY is the y coordinate of the origin of the region
     * @param findW is the width of the region 
     * @param findH is the height of the region
     * @return s is the string list of points in the region
     */
    public String regionSearch(int findX, int findY, int findW, int findH) 
    {
        StringBuilder s = new StringBuilder();
        if (findW <= 0 || findH <= 0)
        {
            s.append("Invalid Region: " + "(" + Integer.toString(findX) + ", "
                    + Integer.toString(findY) + ", " + Integer.toString(findW)
                    + ", " + Integer.toString(findH) + ")");
        }
        else
        {
            s.append("Points Intersecting Region: " + "(" + 
                    Integer.toString(findX) + ", " + Integer.toString(findY)
                    + ", " + Integer.toString(findW) + ", " + 
                    Integer.toString(findH) + ")");
            int visited = 0;
            if (root == flyweight) {
                visited++;
                s.append("");
            }
            else 
            {
                if (root.getClass() == LeafNode.class)
                {
                    visited = 1;
                    visited = helperRegionSearch(s, root, findX, findY, findW,
                            findH, originX, originY, fullSize);
                    s.append("\n" + Integer.toString(visited) + 
                            " QuadTree Nodes Visited");

                }
                else
                {
                    visited = 0;
                    visited = helperRegionSearch(s, root, findX, findY, findW, 
                            findH, originX, originY, fullSize);
                    s.append("\n" + Integer.toString(visited) + 
                            " QuadTree Nodes Visited");

                }
            }

        }
        return s.toString();
    }

    /**
     * /**
     * returns the points that are within the 
     * specified region
     * @param findX is the x coordinate of the origin of the region
     * @param findY is the y coordinate of the origin of the region
     * @param findW is the width of the region 
     * @param findH is the height of the region
     *
     *
     * @param s is the stringbuilder being edited
     * @param node sis the current node, initially root
     * @param x is the origin of the node
     * @param y is the origin of the node
     * @param size is the size of the node
     * @return visited is the number of nodes visited
     */
    public int helperRegionSearch(StringBuilder s, QuadNode node, 
            int findX, int findY, int findW, int findH, int x, 
            int y, int size) 
    {
        int visited = 1; 
        if (node != flyweight)
        {
            if (node.getClass() == InternalNode.class)
            {     
                int searchW = findX + findW;
                int searchH = findY + findH;
                InternalNode curr = (InternalNode)node;
                if (intersects(curr.getNW(), findX, findY, searchW, searchH, 
                        x, y, size))
                {
                    visited += helperRegionSearch(s, curr.getNW(), findX, 
                            findY, findW, findH, x, y, size / 2);
                    
                }
                if (intersects(curr.getNE(), findX, findY, searchW, searchH, 
                        x, y, size))
                {
                    visited += helperRegionSearch(s, curr.getNE(), findX, 
                            findY, findW, findH, x + size / 2, y, size / 2);
                }
                if (intersects(curr.getSW(), findX, findY, searchW, searchH, 
                        x, y, size))
                {
                    visited += helperRegionSearch(s, curr.getSW(), findX, 
                            findY, findW, findH, x, y + size / 2, size / 2);
                }
                if (intersects(curr.getSE(), findX, findY, searchW, searchH, 
                        x, y, size))
                {
                    visited += helperRegionSearch(s, curr.getSE(), findX, 
                            findY, findW, findH, x + size / 2, y + size / 2, 
                            size / 2);
                }

            }
            else if (node.getClass() == LeafNode.class)
            {
                LinkedList<?> list = (LinkedList<?>) 
                        ((LeafNode<?>)node).getElement();

                for (int i = 0; i < list.size(); i++)
                {
                    Point point = (Point) list.get(i);

                    int currX = point.getX();
                    int currY = point.getY();

                    int searchW = findX + findW;
                    int searchH = findY + findH;

                    if (currX >= findX && currX <= searchW && currY >= findY &&
                            currY <= searchH)
                    {
                        s.append("\n" + point.toString());
                    }
                }

            }
        }

        return visited;
    }

    /**
     * checks to see if the current quadnode intersects with the 
     * search region 
     * @param node is the node being checked
     * @param findX is the x being looked for
     * @param findY is the y being looked for
     * @param searchW is the width being looked for
     * @param searchH is the height being looked for
     * @return b whether intersects or not
     */
    private boolean intersects(QuadNode node, int findX, int findY, 
            int searchW, int searchH, int x, int y, int size) 
    {
        boolean b = false; 

        int rectangleW = x + size;
        int rectangleH = y + size;

        if (!(rectangleW < findX || searchW < x || 
                rectangleH < findY || searchH < y))
        {
            b = true;
        }
        return b;
    }

    /**
     * returns the points in the qTree that are duplicates of each
     * other
     * @return the duplicate points in the tree
     */
    public String duplicates() 
    {
        Point[] dupes = new Point[numPoints];
        //int numDupes = 0;
        int index = 0;
        //when there are more than one point, add to dupes
        duplicatesHelp(dupes, root, index);
        //and print out at the end
        StringBuilder s = new StringBuilder();
        s.append("Duplicate Points:");
        for (int i = 0; i < numPoints; i++)
        {
            if (dupes[i] != null)
            {
                s.append("\n" + "(" + dupes[i].getX() + ", ");
                s.append(dupes[i].getY() + ")");

            }

        }
        return s.toString();
    }

    /**
     * goes through the quadtree and checks leaf nodes to see if there are 
     * duplicate points
     * and adds them to the existing list of duplicates
     * @param dupes is the list of duplicate points
     * @param node is the current node in the tree
     * @param index is the index  of the point list
     * @return index is the current index in ht dupes array
     */
    public int duplicatesHelp(Point[] dupes, QuadNode node, int index)
    {
        if (node != flyweight)
        {
            if (node.getClass() == InternalNode.class)
            {
                //level++;
                index = duplicatesHelp(dupes, 
                        ((InternalNode)node).getNW(), index);
                index = duplicatesHelp(dupes, 
                        ((InternalNode)node).getNE(), index);
                index = duplicatesHelp(dupes, 
                        ((InternalNode)node).getSE(), index);
                index = duplicatesHelp(dupes, 
                        ((InternalNode)node).getSW(), index);

            }
            else if (node.getClass() == LeafNode.class)
            {
                LinkedList<?> list = (LinkedList<?>) 
                        ((LeafNode<?>)node).getElement();

                if (list.size() > 3)
                {
                    //if point has more than one entry in the node, add 
                    //all to the list
                    dupes[index] = (Point) list.get(0);
                    
                    index++;
                }
                else if (list.size() == 2)
                {
                    Point p1 = (Point) list.get(0);
                    Point p2 = (Point) list.get(1);
                    if (p1.getX() == p2.getX() && 
                            p1.getY() == p2.getY())
                    {
                        dupes[index] = p1;
                        index++;
                    }
                }
                else if (list.size() == 3)
                {
                    Point p1 = (Point) list.get(0);
                    Point p2 = (Point) list.get(1);
                    Point p3 = (Point) list.get(2);

                    //all three are the same
                    if (p1.getX() == p2.getX() && 
                            p1.getY() == p2.getY() &&
                            p1.getX() == p3.getX() &&
                            p1.getY() == p3.getY())
                    {
                        dupes[index] = p1;
                        index++;
                    }

                    //just 2 are same
                    else if (((p1.getX() == p2.getX() && 
                            p1.getY() == p2.getY())) ||
                            (p1.getX() == p3.getX() &&
                            p1.getY() == p3.getY()))
                    {
                        dupes[index] = p1;
                        index++;
                    }
                    else if ((p2.getX() == p3.getX() &&
                            p2.getY() == p3.getY()))
                    {
                        dupes[index] = p2;
                        index++;
                    }

                }
            }
        }
        return index;
    }

    /**
     * finds all the points in the tree with the name name
     * @param name is the name of the point
     * @return found are the points with the name name
     */
    public Point[] search(String name) 
    {
        if (numPoints == 0)
        {
            return null;
        }

        Point[] found = new Point[numPoints];
        int index = 0;
        found = searchHelper(found, name, root, index);
        return found;
    }

    /**
     * 
     * helper method for the search function, traverses the Quadtree
     * and adds the nodes with the name name to a list of points and returns 
     * them
     * @param name is the name being searched for
     * @param node is the current node in the tree, starts with the root
     *
     * @param found is the list of points with the name name
     *
     * @param index is the index tracker for the array
     * @return found is a list of points with name name
     */
    public Point[] searchHelper(Point[] found, String name, QuadNode node, 
            int index)
    {
        if (node != flyweight)
        {
            if (node.getClass() == InternalNode.class)
            {
                //level++;
                found = searchHelper(found, name, 
                        ((InternalNode)node).getNW(), index);
                found = searchHelper(found, name, 
                        ((InternalNode)node).getNE(), index);
                found = searchHelper(found, name, 
                        ((InternalNode)node).getSE(), index);
                found = searchHelper(found, name, 
                        ((InternalNode)node).getSW(), index);

            }
            else if (node.getClass() == LeafNode.class)
            {
                LinkedList<?> list = (LinkedList<?>) 
                        ((LeafNode<?>)node).getElement();

                for (int i = 0; i < list.size(); i++)
                {
                    Point currPoint = (Point) list.get(i);

                    if (currPoint.getName().equals(name))
                    {
                        found[index] = currPoint;
                        index++;
                    }
                }
            }
        }
        return found;

    }

    /**
     * iterates through the whole tree and finds what empty nodes should be 
     * merged together 
     * 
     * also makes non leaf nodes and non internal nodes equal to the flyweight
     * 
     */
    private void updateQuadTree()
    {
        if (numPoints == 0)
        {
            root = flyweight;
            numNodes = 1;
        }

        LinkedList<Point> descendants = new LinkedList<Point>();
        int count = 0;
        int numChildren = 0;
        updateHelper(root, descendants, count, numChildren);
    }


    /**
     * helper method for the updateQuadTree method
     * @param node is the current node in the tree, initially root
     * @param descendants is the list of descendant points
     * @param count is the number of leaf nodes visited
     */
    private QuadNode updateHelper(QuadNode node, LinkedList<Point> descendants,
            int count, int numChildren)
    {
        if (node.getClass() == LeafNode.class)
        {
            if (node != flyweight)
            {
                LinkedList<?> list = (LinkedList<?>) 
                    ((LeafNode<?>)node).getElement();
                if (list.size() == 0)
                {
                    node = flyweight;
                    return node;
                }
                else
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        descendants.add((Point) list.get(i));
                        numChildren++;
                    }                   
                }  
            }
                
        }
        else if (node.getClass() == InternalNode.class)
        {
            InternalNode curr = (InternalNode) node;
            curr.setNW(updateHelper(curr.getNW(), descendants, 
                    count, numChildren));
            curr.setNE(updateHelper(curr.getNE(), descendants, 
                    count, numChildren));
            curr.setSW(updateHelper(curr.getSW(), descendants, 
                    count, numChildren));
            curr.setSE(updateHelper(curr.getSE(), descendants, 
                    count, numChildren));
            if (numChildren <= 3)
            {
                //descendants
                //LinkedList<Point> list = new LinkedList<Point>
                if (numPoints <= 3)
                {
                    root = new LeafNode<LinkedList>(descendants);
                }
                node = flyweight;
                for (int i = 0; i < numChildren; i++)
                {
                    insertHelp(descendants.get(i), root, originX, 
                            originY, fullSize);
                }
                numNodes -= count;
            }
        }
        count++;
        return node;
    }

    /**
     * prints out the contents of the qTree
     * @return string representation of the nodes and points in the tree
     */
    public String dump() 
    {
        StringBuilder s = new StringBuilder();
        s.append("QuadTree Dump:\n");
        int nodes = 0;

        nodes = preOrderTraversal(root, 0, s, originX, originY, 
                fullSize, nodes);

        s.append("QuadTree Size: " + Integer.toString(nodes) + 
                " QuadTree Nodes Printed.");

        return s.toString();
    }

    /**
     * goes through the quadtree and prints out the nodes 
     * and locations of the nodes
     * @param node is the current node in the tree, starts with rot
     * @param height is the height tracker of the tree
     * @param s is the stringbuilder being changed
     * @param x is the current x 
     * @param y is the current y
     * @param size is the current size
     * @param nodes 
     */
    private int preOrderTraversal(QuadNode node, int height, StringBuilder s,
            int x, int y, int size, int nodes)
    {
        for (int i = 0; i < height; i++)
        {
            s.append("  ");
        }

        if (node == flyweight)
        {
            s.append("Node at ");
            s.append(Integer.toString(x) + ", ");
            s.append(Integer.toString(y) + ", ");
            s.append(Integer.toString(size) + ": ");
            s.append("Empty");
            s.append("\n"); 
            nodes++;
        }
        else if (node.getClass() == InternalNode.class)
        {
            s.append("Node at ");
            InternalNode current = (InternalNode)node;
            //int []nums = current.getNums();


            s.append(Integer.toString(x));
            s.append(", ");
            s.append(Integer.toString(y));
            s.append(", ");
            s.append(Integer.toString(size));
            s.append(": ");
            s.append("Internal");

            s.append("\n"); 
            nodes++;

            height++;
            //for NW
            nodes = preOrderTraversal(current.getNW(), height, s, x, y, 
                    size / 2, nodes);
            //for NE
            nodes = preOrderTraversal(current.getNE(), height,  s, 
                    x + (size / 2), y, size / 2, nodes);
            //for SW
            nodes = preOrderTraversal(current.getSW(), height, s, x, 
                    y + (size / 2), size / 2, nodes);
            //for SE
            nodes = preOrderTraversal(current.getSE(), height, s, 
                    x + (size / 2), y + (size / 2), size / 2, nodes);
        }


        else if (node.getClass() == LeafNode.class)
        {
            s.append("Node at " + Integer.toString(x) + ", " + 
                    Integer.toString(y) + ", " + 
                    Integer.toString(size) + ":\n");
            LinkedList<?> list = (LinkedList<?>) 
                    ((LeafNode<?>) node).getElement();

            for (int i = 0; i < list.size(); i++) 
            {
                for (int j = 0; j < height; j++)
                {
                    s.append("  ");
                }
                Point point = (Point) list.get(i);
                s.append(point.toString());
                s.append("\n");
            }
            nodes++;
        }   
        return nodes;
    }

}
