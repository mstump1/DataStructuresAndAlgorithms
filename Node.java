/**
 * makes up the BST, each node contains a
 * rectangle
 * @author Margaret Stump
 * @version 9/7/17
 * @param <T> is the type of object within the node
 *
 */
public class Node<T>
{
    private T element;
    private Node<T> left;
    private Node<T> right;
    private int depth;
    
    
    /**
     * constructor creates the binary tree node
     * @param element is the element in the node
     */
    Node(T element)
    {
        this.element = element;
        left = null;
        right = null;
        depth = 0;
    }
    
    /**
     * returns the element in the node
     * @return element is the element
     */
    public T getElement()
    {
        return element;
    }
    
    /**
     * sets the node element to a new element
     * @param newElement is the new element
     */
    public void setElement(T newElement)
    {
        element = newElement;
    }
    
    /**
     * returns the left element of the node
     * @return left is the node to the left
     */
    public Node<T> getLeft()
    {
        return left;
    }
    
    /**
     * sets the left node to be a new node
     * @param newLeft is the new node
     */
    public void setLeft(Node<T> newLeft)
    {
        left = newLeft;
    }
    
    /**
     * returns the node to the right
     * @return right is the right child
     */
    public Node<T> getRight()
    {
        return right;
    }
    
    /**
     * sets the right child to be a new node
     * @param newRight is the new right node
     */
    public void setRight(Node<T> newRight)
    {
        right = newRight;
    }
    
    /**
     * sets the depth of the node to be newDepth
     * @param newDepth is the new depth of the node
     */
    public void setDepth(int newDepth)
    {
        depth = newDepth;
    }
    
    /**
     * returns the depth of the node
     * @return depth is the depth of the node in the tree
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Provides an in-order representation of the node
     * @return a string representation of the node
     */      
    @Override
    public String toString() 
    { 
        StringBuilder builder = new StringBuilder();   
        if (left != null)
        {   
            builder.append(left.toString() + ", \n");
        } 
        
        builder.append(element.toString());
        
        if (right != null)
        {
            builder.append(", \n" + right.toString());
        }
        return builder.toString();    
    }
}
