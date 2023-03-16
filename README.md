# Advanced-Image-Hiding
AdvancedImageHiding is a Java-based program for steganography, the practice of concealing a message within another message or medium. This program allows users to hide an image within another image by modifying the least or most significant bits of the host image's pixels. Users can select the number of bits to use for hiding the secret image, choose which bits to modify, and save the output image. The program is easy to use, and you can fork it for your own use as long as you cite this repo.

> To learn what steganography is, check out this video: https://www.youtube.com/watch?v=TWEXCYQKyDc

## To compile:
- 1) Make sure you have the Java SDK downloaded and installed (You can get it at: https://www.oracle.com/java/technologies/downloads/)
- 2) Clone this repo with: ```git clone https://github.com/peterzlomek/Advanced-Image-Hiding```
- 3) Open the folder in your terminal and compile the program with the command: ```javac AdvancedImageHiding.java```
- 4) Run the compiled .class file by using the command: ```java AdvancedImageHiding```
- 5) Enter the name of the host image file with its extension (If it is not in the same directory, make sure to include the absolute path to it).
- 6) Enter the name of the secret image file with its extension (Again, if it is not in the same directory, make sure to include the absolute path to it).
- 7) Choose if you want the host image to use MSB or LSB to store the secret image.
- 8) Choose if you want the secret image to be encoded with MSB or LSB.
- 9) Enter in a name for the output file (you have to put .png at the end regardless if the original files were .png or not).
- 10) Enjoy your image!
