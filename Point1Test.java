import java.io.IOException;

import student.TestCase;

/**
 * tests the main method in the Rectangle1 class
 * @author Margaret Stump
 * @version 9/8/17
 *
 */
public class Point1Test extends TestCase
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
        args[0] = "p2Syntax1";
        
        System.out.println("Filename: p2Syntax1:\n");
        Point1.main(args);
        


        String[] args2 = new String[1];
        args2[0] = "p2Syntax2";
        
        System.out.println("\n\nFilename: p2Syntax2:\n");
        Point1.main(args2);
    
        
        String[] args3 = new String[1];
        args3[0] = "MoreCommands";
        
        System.out.println("\n\nFilename: MoreCommands:\n");
        Point1.main(args3);
        
        String[] args4 = new String[1];
        args4[0] = "RegionSearchTest";
        
        System.out.println("\n\nFilename: RegionSearchTest:\n");
        Point1.main(args4);
        
        String[] args5 = new String[1];
        args5[0] = "SimpleInsertionTest";
        
        System.out.println("\n\nFilename: SimpleInsertionTest:\n");
        Point1.main(args5);
        
        String[] args6 = new String[1];
        args6[0] = "RemoveTest";
        
        System.out.println("\n\nFilename: RemoveTest:\n");
        Point1.main(args6);
        
        String[] args7 = new String[1];
        args7[0] = "DuplicatesTest";
        
        System.out.println("\n\nFilename: DuplicatesTest:\n");
        Point1.main(args7);
        
        String[] args8 = new String[1];
        args8[0] = "ExtraTests";
        
        System.out.println("\n\nFilename: ExtraTests:\n");
        Point1.main(args8);
        
        String[] args9 = new String[1];
        args9[0] = "WebCatTests";
        
        System.out.println("\n\nFilename: WebCatTests:\n");
        Point1.main(args9);
        
        Node<String> node = new Node<String>("test");
        
        assertEquals("test", node.toString());
    }
}