import student.TestCase;
/**
 * tests all of the methods in the node class
 * @author Margaret Stump
 * @version 9/7/17
 *
 */
public class NodeTest extends TestCase
{
    private Node<Rectangle> node;
    private Rectangle r;
    
    /**
     * runs before every test
     */
    public void setUp()
    {
        r = new Rectangle("marge", 1, 2, 3, 4);
        node = new Node<Rectangle>(r);
        
    }
    
    /**
     * tests the getElement method
     */
    public void testGetElement()
    {
        assertEquals(r, node.getElement());
    }
    
    /**
     * tests the setelement method
     */
    public void testSetElement()
    {
        Rectangle r2 = new Rectangle("katelyn", 4, 4, 5, 6);
        node.setElement(r2);
        assertEquals(r2, node.getElement());
    }
    
    /**
     * tests the getLeft method
     */
    public void testLeftRightString()
    {
        assertNull(node.getLeft());
        
        Rectangle r2 = new Rectangle("katelyn", 4, 4, 5, 6);
        Rectangle r3 = new Rectangle("b", 4, 2, 6, 1);
        
        Node<Rectangle> node2 = new Node<Rectangle>(r2);
        Node<Rectangle> node3 = new Node<Rectangle>(r3);
        node.setLeft(node2);
        node.setRight(node3);
        
        assertEquals(node2, node.getLeft());
        assertEquals(node3, node.getRight());
        assertEquals("(katelyn, 4, 4, 5, 6), \r\n" + 
                "(marge, 1, 2, 3, 4), \r\n" + 
                "(b, 4, 2, 6, 1)", node.toString());
    }
    
    
    
    
}
