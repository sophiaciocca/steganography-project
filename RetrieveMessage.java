/**********************************************
  * Name: Sophia Ciocca
  * PennKey: sciocca
  * Recitation: 211
  * 
  * Compilation: javac RetrieveMessage.java
  * Execution: java RetrieveMessage
  * 
  * Retrieves, decrypts, and decodes a message that is embedded in an image.
  * 
  * % java RetrieveMessage stegosaur_encrypted.png 01101000010 8
  * Stegosaur: From Greek stegos, meaning roof, and sauros: lizard
  * Steganography: From Greek steganos, meaning covering, and graphia, 
  *                meaning writing
  ***********************************************/

public class RetrieveMessage {
    
    public static void main(String[] args) {
        
        //if # of command-line args isn't 1 or 3, print error message & exit
        if (args.length != 1 && args.length != 3) {
            System.out.println("Error: improper number of arguments entered.");
            return;
        }        

        //read in user arguments (image file, seed string of 0s/1s, & tap #)
        String imageFile = args[0];
        String seed = "";
        int tap = 0;
        if (args.length > 1) {
            seed = args[1];
            tap = Integer.parseInt(args[2]);
        }
        
        //if seed is not all 1s and 0s, print error message & exit
        for (int j = 0; j < seed.length(); j++) {
            if (seed.charAt(j) != '0' && seed.charAt(j) != '1') {
                System.out.println("Error: seed must be only 1s and 0s.");
                return;
           }
       }
        
        //if tap is not between 0 and length of seed, print error message & exit
        if (tap < 0 || tap > seed.length()) {
            System.out.println("Error: tap is not within bounds.");
            return;
        }  
        
        //load specified image as array of integers
        int[][] array2D = ImageData.load(imageFile);

        //declare 1D array that we'll fill with the last digits:
        boolean[] arrayOfValues = 
            new boolean[array2D.length * array2D[0].length];   
        
        //nested for loop to go through rows/columns and extract last digit
        int i = 0; //counter
        for (int row = 0; row < array2D.length; row++) {          
            for (int column = 0; column < array2D[row].length; column++) {
                
                if (array2D[row][column] % 2 == 0) {
                    arrayOfValues[i] = false;
                }
                else if (array2D[row][column] % 2 == 1) {
                    arrayOfValues[i] = true;
                }
                
                i++;    
            }          
        }
       
        //declare array of decrypted message
        boolean[] decryptedMessage = new boolean[arrayOfValues.length];
        
        //decrypt only if all three arguments are there
        if (args.length == 3) {
            decryptedMessage = Encrypt.encrypt(arrayOfValues, seed, tap);
        }
        else if (args.length == 1) {
            for (int k = 0; k < arrayOfValues.length; k++) {
                decryptedMessage[k] = arrayOfValues[k];
            }
        }    
       
        //decode it
        String decodedMessage = ASCII.decode(decryptedMessage);
        
        //if decoded message contains character whose numeric code is 0, 
            //only print characters up to BUT NOT INCLUDING THAT. "null".
        int wheresTheNull = decodedMessage.indexOf('\0');
        int n = wheresTheNull;
        decodedMessage = decodedMessage.substring(0, n);
        
        //print message using System.out.println()
        System.out.println(decodedMessage);
       
    }
}