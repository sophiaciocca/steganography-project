# steganography-project
Retrieves, decrypts, and decodes a message that is embedded in an image.

Image steganography is the science of hiding secret messages inside of images. The idea is just to minutely adjust some pixels' color in a way that isn’t perceivable to the human eye, but that the computer can interpret. The human eye is least sensitive to blue wavelengths, so that’s the wavelength I adjusted.

An image is a 2D array of pixels; the color of each pixel is an integer between 0 and 16.8 million, with 8 bits dedicated each to red, green, and blue respectively.

By flipping the ones bit of this number (subtracting 1 from an odd number or adding 1 to an even number, we change the amount of blue in the color by a tiny amount.

----

ASCII

The ASCII library includes an “encode” function to turn a string of ASCII characters into an array of Booleans/binary, and a “decode” function to turn the binary back into a string of ASCII characters.

----

HIDING A MESSAGE

To HIDE a message, we’ll optionally encrypt it as a sequence of bits (according to the ASCII character set), then hide that sequence of bits in the image by setting the least significant bit of each successive pixel to 0 or 1 to match the values of each bit in our message. So the blue value of each pixel will change by at most one step, which won’t be noticeable, but now each pixel carries one bit of our image. 

Note: each ASCII character requires 7 bits, so each character will be spread across 7 pixels in the image.

--
RETRIEVING A MESSAGE

To RETRIEVE the information, we then have to read the ones bit of each pixel. Every seven bits that we retrieve represents a single character. 

---

Because there are several moving pieces to this project, it is broken up into separate library classes, each implementing a different aspect: ASCII conversation, the LFSR (given), and encryption/decryption. To use this, an ImageData library is also necessary (not listed in this repository).
