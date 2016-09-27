/**********************************************
  * Name: Sophia Ciocca
  * 
  * Compilation: javac Encrypt.java
  * Execution: java Encrypt
  * 
  * Takes an array of booleans and uses LFSR to encrypt it into
  * a new array of encrypted booleans
  * 
  * % java Encrypt 11101110010 4
  * test encrypted: 10000
  * test decrypted: 11111
  ***********************************************/

public class Encrypt {
    
    // print an array of booleans as 1s and 0s
    private static void printBooleanArray(boolean[] bits) {
        for (int i = 0; i < bits.length; i++) {
            // print a 1 for true or 0 for false
            if (bits[i]) System.out.print("1");
            else         System.out.print("0");
        }
        
        System.out.println(); // add a newline
    }
    
    public static void main(String[] args) {
        // sample array for testing encrypt()
        int[] test = { 1, 1, 1, 0, 0, 0 };
        
        // convert int array to booleans
        boolean[] boolTest = new boolean[test.length];
        for (int k = 0; k < test.length; k++) {
            if (test[k] == 1) {
                boolTest[k] = true;
            }
            else if (test[k] == 0) {
                boolTest[k] = false;
            }
        }
        
        // sample seed and tap position
        String seed = args[0];
        int    tap  = Integer.parseInt(args[1]);
       
        boolean[] encrypted = encrypt(boolTest, seed, tap);
        System.out.print("test encrypted: ");
        printBooleanArray(encrypted);
        boolean[] decrypted = encrypt(encrypted, seed, tap);
        System.out.print("test decrypted: ");
        printBooleanArray(decrypted);
    }
    
    //----------------------NON-MAIN FUNCTIONS----------------------//
    
    /* 
     * Name: encrypt     
     * What it does: Takes an array of bits and uses a seed to encrypt it into 
     * another array of bits
     * PreCondition: there is an array of bits, a seed, and a tap given
     * PostCondition: no side-effects; a new array of booleans created
     */
    public static boolean[] encrypt(boolean[] bits, String seed, int tap) {
        
        //if boolean array is null, return null
        if (bits == null || seed == null) {
            return null;
        }
        
        //if seed is not all 1s and 0s, return null
        for (int j = 0; j < seed.length(); j++) {
            if (seed.charAt(j) != '0' && seed.charAt(j) != '1') {
                return null;
           }
       }
        
        //if tap is not between 0 and length of seed, return null
        if (tap < 0 || tap > seed.length()) {
            return null;
        }
        
        //initialize LFSR function
        LFSR.init(seed, tap);
        
        //define encryptedBits[i] array
        boolean[] encryptedBits = new boolean[bits.length];
        
        //go through bits and encrypt them one by one
        for (int i = 0; i < encryptedBits.length; i++) {
            //use step function on each bit in bits[] to make it encrypted
            encryptedBits[i] = bits[i]^LFSR.step(); 
        }
        
        //return the boolean array of encryptedBits
        return encryptedBits;
    }
}
