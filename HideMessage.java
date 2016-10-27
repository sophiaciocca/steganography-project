/**********************************************
  * Name: Sophia Ciocca
  * PennKey: sciocca
  * Recitation: 211
  * 
  * Compilation: javac HideMessage.java
  * Execution: java HideMessage
  * 
  * Encodes, encrypts, and hides a message in an image.
  * 
  * % java HideMessage img1encryptedseed10010tap4.png message1.txt 10010 4
  * [image pops up]
  ***********************************************/

public class HideMessage {
    
    public static void main(String[] args) {
        
        //read in user arguments (image file, message, seed string, & tap #)
        String imageFile = args[0];
        String messageFile = args[1];
        String seed = args[2];
        int tap = Integer.parseInt(args[3]);
        
        
        //EXTRA STIPULATIONS:
        //if only two arguments, then just embed message w/o encrypting
        
        //if seed is null, print error message & exit
        if (seed == null) {
            System.out.println("Error: seed is null.");
            return;
        }
        
        //if seed is not all 1s and 0s, print error message & exit
        for (int j = 0; j < seed.length(); j++) {
            if (seed.charAt(j) != '0' && seed.charAt(j) != '1') {
                System.out.println("Error: seed must be only 1s and 0s.");
                return;
           }
       }
        
        //if tap is not between 0 and length of seed, print error message & exit
        if (tap < 0 || tap > seed.length() - 1) {
            System.out.println("Error: tap is not within bounds.");
            return;
        }
        
        //if any other number of arguments than 2 or 4, error message & quit program
        if (args.length != 2 && args.length != 4) {
            System.out.println("Error: impromper number of arguments.");
            return;   
        }
        
        //load specified image & message file
        //(use ImageData library to load and display images)
        int[][] imageArray = ImageData.load(imageFile);
        
        
        //read message file using In's readAll() method (create readMessageFile);
        In inStream = new In(messageFile); 
        String readMessageFile = inStream.readAll();
        
        int numberOfPixels = imageArray.length * imageArray[0].length;
        
        //embed encrypted message in image
        //if message is too long to fit in image, truncate it to only the portion that will fit
        //(recall that each character needs 7 pixels, so if #pixels/7 < message length, it needs to be truncated 
        if (readMessageFile.length() > numberOfPixels / 7) {
            
            //find number of pixels and divide by 7
            int numberOfChars = numberOfPixels / 7;
            
            //redefine string as substring with that length
            readMessageFile = readMessageFile.substring(0, numberOfChars - 1);
            
        }
        
        //add NULL character to end of message
        readMessageFile = readMessageFile + '\0';

        //encode message into binary
        boolean[] encodedMessage = ASCII.encode(readMessageFile);
        
        //encrypt message using specified seed/tap position
        boolean[] encryptedMessage = Encrypt.encrypt(encodedMessage, seed, tap);
        
        //nested for loop to go through rows/columns and change last digit
        int i = 0; //counter
        for (int row = 0; row < imageArray.length; row++) {          
            for (int column = 0; column < imageArray[row].length; column++) {
                
                int lastBit = imageArray[row][column] % 2;
                
                if (i < encryptedMessage.length) {
                    if (encryptedMessage[i] == true) {
                        if (lastBit == 0) {
                            imageArray[row][column] = imageArray[row][column] + 1;
                        }
                    }
                    else if (encryptedMessage[i] == false) {
                        if (lastBit == 1) {
                            imageArray[row][column] = imageArray[row][column] - 1;
                        }
                    }
                }
                
                i++;    
            }          
        }
        
        //display the result
        ImageData.show(imageArray);
           
    }
    
}