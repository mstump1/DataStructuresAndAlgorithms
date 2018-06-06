import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;
import java.util.Scanner;


/**
 * HW5 directory with menu and content, 
 * delete, display file, encrypt decryppt
 * @author Margaret Stump
 *
 */
public class directory 
{
    private static int value;
    
    
    /**
     * runs the program
     * @throws IOException 
     * 
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("0 - Exit");
        System.out.println("1 - Select Directory");
        System.out.println("2 - List Directory Content (first level)");
        System.out.println("3 - List Directory Content (all levels)");
        System.out.println("4 - Delete File");
        System.out.println("5 - Display File (hexadecimal view)");
        System.out.println("6 - Encrypt file (XOR with password)");
        System.out.println("7 - Decrypt file (XOR with password)");
        //System.out.println("Select Option:");
        String directory = " ";
        while (true)
        {   
            
            value = ReadKey.inInteger("Select Option:");
            switch(value)
            {
                case 0:
                    System.out.println("Exit Selected");
                    System.exit(0);
                    break;
                case 1:
                    //System.out.println("Select Directory");
                    directory = ReadKey.inFileName("Enter directory: ");
                    File dFile = new File(directory);
                    if (dFile.isDirectory())
                    {
                        System.out.println(directory + " directory selected");
                    }
                    continue;
                    //break;
                case 2:
                    System.out.println("List directory content (first level) selected");
                    if (directory == " ")
                    {
                        System.out.println("Error: no directory selected");
                    }
                    else
                    {
                        listDirectoryFiles(directory);
                    }
                    continue;
                case 3:
                    System.out.println("List directory content (all levels) selected");
                    if (directory == " ")
                    {
                        System.out.println("Error: no directory selected");
                    }
                    else
                    {
                        listAllFiles(directory);
                    }
                    continue;
                case 4:
                    System.out.println("Delete file selected");
                    if (directory == " ")
                    {
                        System.out.println("Error: no directory selected");
                    }
                    else
                    {
                        String fileToDelete = ReadKey.inFileName("Enter file to delete");
                        deleteFile(directory, fileToDelete);
                    }
                    continue;
                case 5:
                    System.out.println("Display file (hexadecimal view)");
                    if (directory == " ")
                    {
                        System.out.println("Error: no directory selected");
                    }
                    else
                    {
                        String fileToDisplay = ReadKey.inFileName("Enter fileName: ");
                        displayFile(directory, fileToDisplay);
                    }
                    continue;   
                    
                case 6:
                    System.out.println("Encrypt file (XOR with password) Selected");
                    if (directory == " ")
                    {
                        System.out.println("Error: no directory selected");
                    }
                    else
                    {
                        //encrypt
                        //System.out.println("enter password");
                        String passwordEncrypt = ReadKey.inFileName("Enter password ");
                        //System.out.println("enter file to be encrypted");
                        String fileToEncrypt = ReadKey.inFileName("enter file to be encrypted");
                        
                        encryptFile(passwordEncrypt, fileToEncrypt, directory);
                    }
                    continue;
                case 7:
                    System.out.println("Decrypt file (XOR with password)");
                    if (directory == " ")
                    {
                        System.out.println("Error: no directory selected");
                    }
                    else
                    {//
                        //encrypt
                        //System.out.println("enter password");
                        String passwordDecrypt = ReadKey.inFileName("enter password: ");
                        //System.out.println("enter file to be decrypted");
                        String fileToDecrypt = ReadKey.inFileName("enter file to be decrypted: ");
                        
                        decryptFile(passwordDecrypt, fileToDecrypt, directory);
                    }
                    continue;
                default:
                    System.out.println("Invalid menu selection");
                    continue;
                
            }
        }
    }
    
    
    /**
     * lists the files in the directory
     * @param directory
     */
    public static void listAllFiles(String directory) 
    {
        File directoryFile = new File(directory);
        File[] contents = directoryFile.listFiles();
        for (int i = 0; i < contents.length; i++)
        {
            //lists the path of each file in the directory
            System.out.println(contents[i].getAbsolutePath());
        }
                
    }


    /**
     * lists the files in the given folder
     * @param folderName is the name of the folder
     */
    public static void listDirectoryFiles(String folderName)
    {
        File directory = new File(folderName);
        String[] contents  = directory.list();
        for (int i = 0; i < contents.length; i++)
        {
            //lists each file in the directory by name
            System.out.println(contents[i]);
        }
                
    }
    
    /**
     * deletes specified file if it exists
     * @param fileToDelete is the file that will be deleted
     * @param fileToDelete2 
     * @throws IOException 
     */
    public static void deleteFile(String directory, String fileToDelete) throws IOException
    {
        File delete = new File("C:\\Users\\Margaret\\eclipse-workspace\\HW5\\" + directory + "\\" + fileToDelete);
        
        boolean wasDeleted = delete.delete();
        if (wasDeleted)
        {
            System.out.println(fileToDelete + " was deleted");
        }
        else
        {
            System.out.println(fileToDelete + " was not found");
        }
    }
    
    /**
     * displays contents of the file in hexadecimal form
     * @param fileToDisplay is the file going to be displayed
     * @throws IOException 
     */
    public static void displayFile(String directory, String fileToDisplay) throws IOException
    {
        FileInputStream stream = null;
        File file = new File("C:\\Users\\Margaret\\eclipse-workspace\\HW5\\" + directory + "\\" + fileToDisplay);
        
        try
        {
            stream = new FileInputStream(file);
            int index = 0;
            int counter = 0;
            
            while ((index = stream.read()) != -1)
            {
                if (index != -1)
                {
                    System.out.printf("%02X ", index);
                    counter++;
                }
                if (counter == 16)
                {
                    System.out.println("");
                    counter = 0;
                }
            }
            System.out.println();
        }
        finally
        {
            if (stream == null)
            {
                System.out.println("File does not exist in directory");
            }
            else
            {
                stream.close();
            }
        }
        
            
    }
    
    /**
     * 
     * @param password
     * @param fileToEncrypt
     * @throws IOException 
     */
    public static void encryptFile(String password, String fileToEncrypt, String directory) throws IOException
    {
        File file = new File("C:\\Users\\Margaret\\eclipse-workspace\\HW5\\" + directory + "\\" + fileToEncrypt);
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(file);
        FileInputStream in = new FileInputStream("C:\\Users\\Margaret\\eclipse-workspace\\HW5\\" + directory + "\\" + fileToEncrypt);
        
        Properties props = new Properties();
        props.load(in);
        
        while(scan.hasNext())
        {
            String str = scan.nextLine();
            int[] output = new int[str.length()];
            for (int i = 0; i < str.length(); i++)
            {
                int out = (Integer.valueOf(str.charAt(i)) ^ Integer.valueOf(password.charAt(i % (password.length() - 1)))) + '0';
                output[i] = out;
            }
            props.setProperty(str, output.toString());
            System.out.println(output.toString());
        }
        in.close();
        
        
        
    }
   
    /**
     * 
     * @param password
     * @param fileToEncrypt
     * @throws IOException 
     */
    public static void decryptFile(String password, String fileToDecrypt, String directory) throws IOException
    {
        File file = new File("C:\\Users\\Margaret\\eclipse-workspace\\HW5\\" + directory + "\\" + fileToDecrypt);
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(file);
        
        FileInputStream in = new FileInputStream("C:\\Users\\Margaret\\eclipse-workspace\\HW5\\" + directory + "\\" + fileToDecrypt);
        
        Properties props = new Properties();
        props.load(in);
        
        while(scan.hasNext())
        {
            String str = scan.nextLine();
            char[] line = str.toCharArray();
            
            String output = "";
            int[] nums = new int[str.length()];
            for (int j = 0; j < str.length(); j++)
            {
                nums[j] = Integer.valueOf(line[j]);
            }
            for (int i = 0; i < nums.length; i++)
            {
                output += (char) ((nums[i] - 48) ^ (int) password.charAt(i % (password.length() - 1)));
            }
            props.setProperty(str, output);
            System.out.println(output);
        }
        in.close();
    }
    

}
