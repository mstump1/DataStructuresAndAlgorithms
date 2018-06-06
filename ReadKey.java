/**
 * reads in the key pressed from the keyboard,
 * for the selection menu
 * @author Margaret Stump 
 * 
 *
 */
public class ReadKey 
{
    /**
     * prints the prompt string for input
     * @param s
     */
    public static void printPrompt(String s)
    {
        System.out.print(s + " ");
        System.out.flush();
    }
    
    /**
     * makes sure the input is valid
     */
    public static void input()
    {
        int a = 0;
        
        try {
            while(System.in.available() != 0)
            {
                a = System.in.read();
            }
        }
        catch (java.io.IOException e)
        {
            System.out.println("invalid input");
        }
    }
    
    /**
     * helper method for the ininteger method
     * @return
     */
    public static String inString() 
    {
        int aChar;
        String str = "";
        boolean finished = false;

        while (!finished) 
        {
          try {
            aChar = System.in.read();
            if (aChar < 0 || (char) aChar == '\n')
            {
                finished = true;
            }
              
            else if ((char) aChar != '\r')
            {
                 str = str + (char) aChar; // Enter into string
         
            }
         }

          catch (java.io.IOException e) 
          {
            System.out.println("Invalid input");
            finished = true;
          }
        }
        return str;
      }

    /**
     * takes in the integer from the console
     * @param prompt
     * @return
     */
    public static int inInteger(String prompt) 
    {
        while (true) {
          input();
          printPrompt(prompt);
          try {
            return Integer.valueOf(inString().trim()).intValue();
          }

          catch (NumberFormatException e) {
            System.out.println("Invalid input. Not an integer");
          }
        }
      }
    
    /**
     * takes in the string file name from the console
     * @param prompt
     * @return
     */
    public static String inFileName(String prompt) 
    {
        while (true) {
          input();
          printPrompt(prompt);
         
          return inString();
        }
      }
}
