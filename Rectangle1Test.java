import java.io.IOException;

import student.TestCase;

/**
 * tests the main method in the Rectangle1 class
 * @author Margaret Stump
 * @version 9/8/17
 *
 */
public class Rectangle1Test extends TestCase
{
    /**
     * runs before every test
     */
    public void setUp()
    {
        //empty on purpose
    }
    
    /**
     * tests the main method
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testMain() throws ClassNotFoundException, IOException 
    {
        String[] args = new String[1];
        args[0] = "SyntaxTest";
        
        System.out.println("Filename: SyntaxTest:\n");
        Rectangle1.main(args);
        


        String[] args2 = new String[1];
        args2[0] = "SimpleInsertionTest";
        
        System.out.println("\n\nFilename: SimpleInsertionTest:\n");
        Rectangle1.main(args2);
        
        String[] args3 = new String[1];
        args3[0] = "RegionSearchTest";
        
        System.out.println("\n\nFilename: RegionSearchTest:\n");
        Rectangle1.main(args3);
        
        String[] args4 = new String[1];
        args4[0] = "WebCatTests";
        
        System.out.println("\n\nFilename: WebCatTests:\n");
        Rectangle1.main(args4);
        
        String[] args5 = new String[1];
        args5[0] = "RemoveTest";
        
        System.out.println("\n\nFilename: RemoveTest:\n");
        Rectangle1.main(args5);
        
        String[] args6 = new String[1];
        args6[0] = "MoreCommands";
        
        System.out.println("\n\nFilename: MoreCommands:\n");
        Rectangle1.main(args6);
        
        assertEquals("RegionSearchTest", args3[0]);
    }
}
