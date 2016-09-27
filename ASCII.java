/**********************************************
  * Name: Sophia Ciocca
  * 
  * Compilation: javac ASCII.java
  * Execution: java ASCII
  * 
  * Tests two functions, encode() and decode(). 
  * Turns a char into binary and then back into a char, according to ASCII library.
  * 
  * % java ASCII A
  * 1000001
  * A
  ***********************************************/

public class ASCII {
    
    public static void main(String[] args) {
        // convert command-line argument to bits
        boolean[] bits = encode(args[0]);
        printBooleanArray(bits);
        
        // convert the bits back to a string
        String s = decode(bits);
        System.out.println(s);
    }
    
    
//----------------------NON-MAIN FUNCTIONS----------------------//
    
    // Given Function for Testing: print an array of booleans as 1s and 0s
    private static void printBooleanArray(boolean[] bits) {
        for (int i = 0; i < bits.length; i++) {
            // print a 1 for true or 0 for false
            if (bits[i]) System.out.print("1");
            else         System.out.print("0");
        }
        
        System.out.println(); // add a newline
    }
    
    
    /* 
     * Name: encode     
     * What it does: Turns a string into an array of boolean values corresponding
     * to the chars' ASCII values in binary.
     * PreCondition: the user has entered a String (thingToBeEncoded)
     * PostCondition: no side-effects; an array of booleans is created
     */
    public static boolean[] encode(String msg) {
        
        //if String is null, return null
        if (msg == null) {
            return null;
        }
        
        //create array of booleans; create slots, 7 * # of characters in string
        boolean[] binaryCharacters = new boolean[7 * msg.length()]; 
        
        //go through each character and convert it into a binary number
        for (int i = 0; i < msg.length(); i++) {
            
            //turn each character into a number
            int c = (int) msg.charAt(i);
            
            //turning each number code for the char into bits
            //(converting decimal to binary)
            for (int j = 6; j >=0; j--) {
                binaryCharacters[7 * i + j] = (c % 2 == 1);
                c /= 2;  
            }
        }
        
        //return the message in binary ASCII numbers
        return binaryCharacters;        
    }
    
    
    /*  
     * Name: decode
     * What it does: Turns an array of boolean values into a string of 
     * characters that correspond to the binary booleans
     * PreCondition: there is an array of booleans
     * PostCondition: no side-effects; a char is created (and some values of 
     * array may be cut off if it is not divisible by 7)
     */
    public static String decode(boolean[] bits) {
        
        //if boolean array is null, return null
        if (bits == null) {
            return "null";
        }
        
        //create String & sum variables
        String backInCharacters = "";
        
        //create variable to be the # of original characters
        int numberOfCharacters = bits.length / 7;
        
        //go through the characters
        for (int i = 0; i < numberOfCharacters; i++) {
            
            int characterSum = 0;
            
            //go through each character's set of 7 numbers & convert to char
            for (int j = 6; j >= 0; j--) {
                
                //if it's a 1, add it to the sum
                if (bits[7 * i + (6 - j)]) {
                    
                    characterSum += Math.pow(2, j);
                }  
            } 
            
            //turn the sum back into 
            char charCharacterSum = (char) characterSum;
            backInCharacters += charCharacterSum;
        }  
        
        //return the original message in characters
        return backInCharacters; 
    }
       
}
