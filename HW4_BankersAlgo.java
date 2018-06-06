import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * HW4 - Banker's algorithm
 * @author Margaret Stump
 * @version 9/15/17
 */
public class HW4_BankersAlgo  
{
    private static int[][] max;
    private static int[][] allocation;
    private static int[][] need;
    private static int[] available;
    private static String fileName;
    
    private static int m;//resource types
    private static int n;//processes
    
    /**
     * RUNS THE PROGRAM.  AND PRINTS OUT RESULTS
     * @param args
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException //throws FileNotFoundException
    {
        //String[] args = {"HW4input"};
        
        fileName = args[0];
        read(fileName);
        calculateNeed();
        boolean alloc = false;
        boolean[] list = new boolean[n];
        int i = 0;
        
        while(i < n)
        {
            alloc = false;
            for (int j = 0; j < n; j++)
            {
                if (list[j] == false && check(j) == true)
                {
                    for (int k = 0; k < m; k++)
                    {
                        available[k] = available[k] - need[j][k] + max[j][k];
                        alloc = true;
                        list[j] = true;
                        
                        //System.out.println(Integer.toString(i));
                        
                    }
                    alloc = true;
                    list[j] = true;
                    //System.out.println(Integer.toString(i));
                    i++;
                    
                    if (alloc == false)
                    {
                        return;
                    }
                      
                }
                
                
            }
            if (i == n)
            {
                //allocation successful
                System.out.println("\nAll processes allocated");
                
                System.out.println("\nMax:");
                for (int x = 0; x < n; x++)
                {
                    System.out.print("P" + Integer.toString(x + 1) + "\t" );
                    for (int y = 0; y < m; y++)
                    {
                        System.out.print(Integer.toString(max[x][y]) + " ");
                    }
                    System.out.print("\n");
                }
                
                System.out.println("\nAllocation:");
                for (int x = 0; x < n; x++)
                {
                    System.out.print("P" + Integer.toString(x + 1) + "\t"); 
                    for (int y = 0; y < m; y++)
                    {
                        
                        System.out.print(Integer.toString(allocation[x][y]) + " ");
                    }
                    System.out.print("\n");
                }
                
                //print needed 
                System.out.println("\nNeeded:");
                for (int x = 0; x < n; x++)
                {
                    System.out.print("P" + Integer.toString(x + 1) + "\t" );
                    for (int y = 0; y < m; y++)
                    {
                        System.out.print(Integer.toString(need[x][y]) + " ");
                    }
                    System.out.print("\n");
                } 
                
                
            }
            else
            {    
                System.out.println("not all allocated safely");
            }
            System.out.println(" ");

            
        }
        
        
    }
    
    /**
     * reads the input file
     * @param fileName
     * @throws FileNotFoundException
     */
    public static void read(String fileName) throws FileNotFoundException
    {
        //String file = args[0];
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(new File(fileName));
        int count = 0;
        int countM = 0;
        int countA = 0;
        while(scan.hasNext())
        {
            String str = scan.nextLine();
            String[] line = str.trim().split("\\s+");
            
            if (count == 0)
            {
                n = Integer.parseInt(line[0]);
                System.out.print("processors: ");
                System.out.println(line[0]);
            }
            
            else if (count == 1)
            {
                m = Integer.parseInt(line[0]);
                System.out.print("resources: ");
                System.out.println(line[0]);
                
                max = new int[n][m];
                allocation = new int[n][m];
                need = new int[n][m];
                available = new int[m];
            }
            
            else if (line.length == 7)
            {
                
                //testing purposes, irrelevant
                //System.out.print(line[2]);
                //System.out.print(line[3]);
                //System.out.print(line[4]);
                //System.out.print(line[5]);
                //System.out.print(line[6]);
                
                //System.out.println(" ");
                
                if (line[0].equals("M"))
                {
                    //max matrix
                    for(int j = 0; j < m; j++)
                    {
                        max[countM][j] = Integer.parseInt(line[j + 2]);
                        //System.out.print(Integer.toString(max[i][j]));
                    }
                        //System.out.println(" ");
                    countM++;
                }
                
                if (line[0].equals("A"))
                {
                    //allocation matrix
                    for(int j = 0; j < m; j++)
                    {
                        allocation[countA][j] = Integer.parseInt(line[j + 2]);
                    }
                    countA++;
                    
                }
                
                
            }
            else if (line.length == 5)
            {
                //available array
                for(int j = 0; j < m; j++)
                {
                    available[j] = Integer.parseInt(line[j]);
                }
            }
            
            count++;
        }
        //System.out.println(Integer.toString(available[0]));
    }
    
    /**
     * calculates the need matrix and prints out the results
     * @return
     */
    public static int[][] calculateNeed()
    {
        
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
        return need;
    }
    
    /**
     * checks the contents of the available ARRAY 
     * @param i
     * @return
     */
    public static boolean check(int i)
    {
        for (int j = 0; j < m; j++)
        {
            if (available[j] < need[i][j])
            {
                return false;
            }
        }
        return true;
    }
    
    


    
    
    
}
