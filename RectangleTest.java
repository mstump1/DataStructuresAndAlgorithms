import student.TestCase;
/**
 * tests the methods in the Rectangle class
 * @author Margaret Stump
 * @version 9/7/17
 *
 */
public class RectangleTest extends TestCase
{
    private Rectangle r;
    
    /**
     * runs before every test
     */
    public void setUp()
    {
        r = new Rectangle("m", 1, 2, 3, 4);
        
    }
    
    /**
     * tests the toString method
     */
    public void testToString()
    {
        assertEquals("(m, 1, 2, 3, 4)", r.toString());
        
    }
    
    /**
     * tests the getName method
     */
    public void testGetName()
    {
        assertEquals("m", r.getName());
    }
    
    /**
     * tests the getNums method
     */
    public void testGetNums()
    {
        int[] n = r.getNums();
        assertEquals(1, n[0]);
        assertEquals(2, n[1]);
        assertEquals(3, n[2]);
        assertEquals(4, n[3]);
    }
}
