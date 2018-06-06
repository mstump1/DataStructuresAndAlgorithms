/**
 * the leaf node class holds a linked list f points
 * @author Margaret Stump marstump
 * @version 10/15/17
 * @param <LinkedList> is the list of points in the leaf node
 *
 */
public class LeafNode<LinkedList> extends QuadNode 
{
    private LinkedList element;
    
    
    /**
     * each of the leaf nodes in the qTree will have a point
     *@param element is the point in the node
     */
    public LeafNode(LinkedList element)
    {
        super();
        this.element = element;
    }
    
    /**
     * returns the element in the leaf node
     * @return element is the elemnet in the node
     */
    public LinkedList getElement()
    {
        return element;
    }
    
    
}
