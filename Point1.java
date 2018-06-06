import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//On my honor: 
// 
// - I have not used source code obtained from another student, 
// or any other unauthorized source, either modified or 
// unmodified. 
// 
// - All source code and documentation used in my program is 
// either my original work, or was derived by me from the 
// source code published in the textbook for this course. 
// 
// - I have not discussed coding details about this project with 
// anyone other than my partner (in the case of a joint 
// submission), instructor, ACM/UPE tutors or the TAs assigned 
// to this course. I understand that I may discuss the concepts 
// of this program with other students, and that another student 
// may help me debug my program so long as neither of us writes 
// anything during the discussion or modifies any computer file 
// during the discussion. I have violated neither the spirit nor 
// letter of this restriction. 

/**
 * creates the BST and QuadTree and modifies
 * according to the commands in the input file
 * 
 * @author Margaret Stump marstump
 * @author Julie Tran
 * @version 10/6/2017
 *
 */
public class Point1 
{
    private static BST bTree;
    private static QuadTree qTree;
    
    /**
     * runs the program by reading from the command line
     * @param args is the filename
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws 
        ClassNotFoundException, IOException
    {
        String name = args[0];
        bTree = new BST();
        qTree = new QuadTree(0, 0, 1024);
        read(name);
    }

    /**
     * reads the file from the command line line by line
     * and acts accordingly 
     * @param fileName is the name of the file from the 
     * command line
     * @throws FileNotFoundException 
     */
    private static void read(String fileName) throws FileNotFoundException 
    {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(new File(fileName));
        
        while (scan.hasNext())
        {
            String str = scan.nextLine();
            String[] line = str.trim().split("\\s+");
            
            //possible commands:
            String command = line[0];
            
            //insert(name, x, y)
            if (command.equals("insert"))
            {
                String name = line[1];
                int x = Integer.valueOf(line[2].trim());
                int y = Integer.valueOf(line[3].trim());
                
                Point point = new Point(name, x, y);
                boolean result = bTree.insert(point);
                    
                if (result)
                {
                    qTree.insert(name, x, y);
                    System.out.println("Point Inserted: " + 
                            point.toString());                    
                }
                else
                {
                    System.out.println("Point Rejected: " + 
                            point.toString());                    
                    
                }
                
            }
            //remove
            else if (command.equals("remove"))
            {
                //remove(name)
                if (line.length == 2)
                {
                    String name = line[1];
                    qTree.removeName(name);
                    Point removed = bTree.removeName(name);
                    if (removed == null)
                    {
                        System.out.println("Point Rejected: " + name);
                    }
                    else
                    {
                        qTree.removeName(name);
                    }
                            
                }
                //remove(x, y)
                else if (line.length == 3)
                {
                    int x = Integer.parseInt(line[1]);
                    int y = Integer.parseInt(line[2]);
                    
                    
                    Point removed = bTree.removeXY(x, y);
                    if (removed == null)
                    {
                        System.out.println("Point Rejected: " + "(" + 
                                line[1] + ", " + line[2] + ")");
                    }
                    else
                    {
                        qTree.removeXY(x, y);
                    }
                    
                }
            }
            //regionsearch(x, y, w, h)
            else if (command.equals("regionsearch"))
            {
                int findX1 = Integer.valueOf(line[1].trim());
                int findY1 = Integer.valueOf(line[2].trim());
                int findX2 = Integer.valueOf(line[3].trim());
                int findY2 = Integer.valueOf(line[4].trim());
                
                //regionsearch
                
                String s = qTree.regionSearch(findX1, findY1, findX2, findY2);
               
                System.out.println(s);
                bTree.regionSearch(findX1, findY1, findY2, findY2);
                        
            }
            //duplicates()
            else if (command.equals("duplicates"))
            {
                String s = qTree.duplicates();
                bTree.intersections();
                
                System.out.println(s);
            }
            //search(name)
            else if (command.equals("search"))
            {
                String name = line[1];
                Point[] s = qTree.search(name);
                if (s == null || s.length == 0 || s[0] == null)
                {
                    System.out.println("Point not found: " + name);
                }
                else
                {
                    for (int i = 0; i < s.length; i++)
                    {
                        if (s[i] != null)
                        {
                            System.out.println("Point Found: " + 
                                    s[i].toString());
                        }
                    }
                }
                
                bTree.search(name);
            }
            //dump
            else if (command.equals("dump"))
            {
                String bTreeString = bTree.dump();
                String qTreeString = qTree.dump();
              
                System.out.println(bTreeString);
                System.out.println(qTreeString);
                
            }
        }
    }
}
