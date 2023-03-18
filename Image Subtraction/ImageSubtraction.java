import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Scanner; 

public class ImageSubtraction {
    public static void main(String[] args) {
        try {
            // Query User for Image names to be subtracted
            Scanner myObj = new Scanner(System.in);
            System.out.println("*Step 1: Give me the path to your first image (with file extension): *");
            String image1Path= myObj.nextLine(); 
            System.out.print("You have entered: "+image1Path);
            BufferedImage image1 = ImageIO.read(new File(image1Path)); 
            System.out.print("\n");
            
            Scanner myObj2 = new Scanner(System.in);
            System.out.println("*Step 2: Give me the path to your second image (with file extension): *");
            String image2Path= myObj2.nextLine(); 
            System.out.print("You have entered: "+image2Path);
            BufferedImage image2 = ImageIO.read(new File(image2Path));
            System.out.print("\n"); 
            
            // asks user what they want the output file to be
            Scanner myObj3 = new Scanner(System.in);
            System.out.println("*Step 3: What do you want the output to be called? (Do Not add an extension at the end):  *");
            String imageResultName= myObj.nextLine(); 
            StringBuilder builder = new StringBuilder();
            builder.append(imageResultName);
            builder.append(".jpg");
            String resultNameBuilder = builder.toString();
            System.out.print("You have entered: "+resultNameBuilder);
            System.out.print("\n");
            
            // Get the dimensions of the images
            int width = image1.getWidth();
            int height = image1.getHeight();
            
            // Create a new image to hold the result
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            // Loop through each pixel in the images and subtract the pixel values
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // Get the RGB values of each pixel in both images
                    int rgb1 = image1.getRGB(x, y);
                    int rgb2 = image2.getRGB(x, y);
                    
                    // Extract the red, green, and blue components of each pixel
                    int r1 = (rgb1 >> 16) & 0xFF;
                    int g1 = (rgb1 >> 8) & 0xFF;
                    int b1 = rgb1 & 0xFF;
                    int r2 = (rgb2 >> 16) & 0xFF;
                    int g2 = (rgb2 >> 8) & 0xFF;
                    int b2 = rgb2 & 0xFF;
                    
                    // Subtract the pixel values
                    int rDiff = Math.abs(r1 - r2);
                    int gDiff = Math.abs(g1 - g2);
                    int bDiff = Math.abs(b1 - b2);
                    
                    // Combine the RGB components of the resulting pixel
                    int resultRGB = (rDiff << 16) | (gDiff << 8) | bDiff;
                    
                    // Set the pixel value in the result image
                    result.setRGB(x, y, resultRGB);
                }
            }
            
            // Save the result image
            // ImageIO.write(result, "jpg", new File("result.jpg"));
            ImageIO.write(result, "jpg", new File(resultNameBuilder));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
