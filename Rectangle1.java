import java.io.File;
import java.io.IOException;
import java.util.Scanner;
//On my honor:
//
//- I have not used source code obtained from another student,
//or any other unauthorized source, either modified or
//unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.

/**
 * reads the input file and acts accordingly
 * @author Margaret Stump
 * @version 9/7/17
 *
 */
public class Rectangle1 
{
    private static BST tree;
    
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
        tree = new BST();
        read(name);
    }
    
    /**
     * reads the input file and keeps track
     * of the BST
     * @param file is the name of the file
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws  
     * line example = insert virtual_rec0 1 1 0 0
     */
    public static void read(String file) throws 
        IOException, ClassNotFoundException
    {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(new File(file));
        
        while (scan.hasNext())
        {
            String str = scan.nextLine();
            String[] line = str.trim().split("\\s+");
            
            
            //possible commands
            String command = line[0];
            
            if (command.equals("insert"))
            {
                String name = line[1];
                int x = Integer.valueOf(line[2].trim());
                int y = Integer.valueOf(line[3].trim());
                int w = Integer.valueOf(line[4].trim());
                int h = Integer.valueOf(line[5].trim());
                
                //create new rectangle
                Rectangle newRectangle = new Rectangle(name, x, y, w, h);
                
                //insert new rectangle
                boolean result = tree.insert(name, x, y, w, h);
                
                StringBuilder s = new StringBuilder(); 
                if (result)
                {
                    s.append("Rectangle accepted: ");
                    s.append(newRectangle.toString());
                    
                }
                else
                {
                    s.append("Rectangle rejected: ");
                    
                    s.append(newRectangle.toString());
                
                }
                System.out.println(s.toString());
            }
            
            else if (command.equals("regionsearch"))
            {
                int findX1 = Integer.valueOf(line[1].trim());
                int findY1 = Integer.valueOf(line[2].trim());
                int findX2 = Integer.valueOf(line[3].trim());
                int findY2 = Integer.valueOf(line[4].trim());
                
                //regionsearch
                String s = tree.regionSearch(findX1, findY1, findX2, findY2);
                
                
                     
                System.out.println(s);
            }
            
            else if (command.equals("intersections"))
            {
                //report all intersecting rectangles in the BST
                String s = tree.intersections();
                System.out.println("Intersections pairs:");
                System.out.println(s);
            }
            
            else if (command.equals("search"))
            {
                StringBuilder b = new StringBuilder();
                
                //search for name command in BST
                String name = line[1];
                Rectangle[] found = tree.search(name);
                
                if (found == null)
                {
                    b.append("Rectangle not found: ");
                    b.append(name);
                    b.append("\n");
                }
                else
                {
                    //b.append("Rectangle found: ");
                    //int index = 0;
                    
                    for (int i = 0; i < found.length; i++)
                    {
                        if (found[i] != null)
                        {
                            b.append("Rectangle found: ");
                            b.append(found[i].toString() + "\n");
                        }
                    }
                }
                
                System.out.print(b.toString());
            }
            
            else if (command.equals("dump"))
            {
                //dump contents of BST
                String s = tree.dump();
                System.out.println(s);
            }
            
            else if (command.equals("remove"))
            {
                //check name
                if (line.length == 2)
                {
                    //remove by name
                    String name = line[1];
                    Rectangle r = tree.removeName(name);
                    if (r == null)
                    {
                        System.out.println("Rectangle rejected: " + name);
                    }
                }
                else if (line.length == 5)
                {
                    //remove by coordinates
                    int findX1 = Integer.valueOf(line[1].trim());
                    int findY1 = Integer.valueOf(line[2].trim());
                    int findX2 = Integer.valueOf(line[3].trim());
                    int findY2 = Integer.valueOf(line[4].trim());
                    
                    Rectangle r = tree.removeXY(findX1, findY1, findX2, findY2);
                    
                    if (r == null)
                    {
                        System.out.println("Rectangle rejected: " + "(" +
                                line[1] + ", " + 
                                line[2] + ", " + 
                                line[3] + ", " + 
                                line[4] + ")");
                    }
                    
                }
                
            }
                   
        }
        
    }
}
