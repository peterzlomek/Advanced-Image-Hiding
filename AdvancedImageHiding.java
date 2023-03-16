import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AdvancedImageHiding {

    public static void main(String[] args) {

        // Load the host and secret images
        BufferedImage hostImage = null;
        BufferedImage secretImage = null;
        // Prompt the user for the host image file name
        String hostFileName;
        do {
            System.out.print("Enter the name of the host image file (with Extension): ");
            hostFileName = System.console().readLine();
        } while (hostFileName.trim().isEmpty() || !new File(hostFileName).exists());
        
        // Prompt the user for the secret image file name
        String secretFileName;
        do {
            System.out.print("Enter the name of the secret image file (with Extension): ");
            secretFileName = System.console().readLine();
        } while (secretFileName.trim().isEmpty() || !new File(secretFileName).exists());
        
        try {
            // Load the host and secret images into BufferedImages
            hostImage = ImageIO.read(new File(hostFileName));
            secretImage = ImageIO.read(new File(secretFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Originally, I had them hard coded for inputs, but this made it harder to work with custom images without renaming
        // try {
        //     // hostImage = ImageIO.read(new File("host.png"));
        //     // secretImage = ImageIO.read(new File("secret.png"));
        //     hostImage = ImageIO.read(new File("host_image.jpg"));
        //     secretImage = ImageIO.read(new File("secret_image.jpg"));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // Determine whether to hide MSB or LSB of secret image in MSB or LSB of host image
        boolean hideMSBinMSB = false;
        boolean hideLSBinMSB = false;
        boolean hideLSBinLSB = false;
        boolean hideMSBinLSB = false;
        String hideIn;
        String hideWhere;
        do {
            System.out.print("Enter how you want to hide data in host image (MSB or LSB): ");
            hideIn = System.console().readLine().toLowerCase();
        } while (!hideIn.equals("msb") && !hideIn.equals("lsb"));
        do {
            System.out.print("Enter where to hide the secret image (MSB or LSB): ");
            hideWhere = System.console().readLine().toLowerCase();
        } while (!hideWhere.equals("msb") && !hideWhere.equals("lsb"));
        if (hideIn.equals("msb") && hideWhere.equals("msb")) {
            hideMSBinMSB = true;
        } else if (hideIn.equals("lsb") && hideWhere.equals("msb")) {
            hideLSBinMSB = true;
        } else if (hideIn.equals("lsb") && hideWhere.equals("lsb")) {
            hideLSBinLSB = true;
        } else if (hideIn.equals("msb") && hideWhere.equals("lsb")) {
            hideMSBinLSB = true;
        }

        // Loop through each pixel in the host image and hide the secret image in it
        for (int i = 0; i < hostImage.getWidth(); i++) {
            for (int j = 0; j < hostImage.getHeight(); j++) {
                // Get the RGB values of the host image pixel
                Color hostColor = new Color(hostImage.getRGB(i, j));
                int hostRed = hostColor.getRed();
                int hostGreen = hostColor.getGreen();
                int hostBlue = hostColor.getBlue();

                // Get the binary strings of the RGB values
                String hostRedBinary = padBinaryString(Integer.toBinaryString(hostRed));
                String hostGreenBinary = padBinaryString(Integer.toBinaryString(hostGreen));
                String hostBlueBinary = padBinaryString(Integer.toBinaryString(hostBlue));

                // Get the corresponding pixel in the secret image
                int secretX = i % secretImage.getWidth();
                int secretY = j % secretImage.getHeight();
                Color secretColor = new Color(secretImage.getRGB(secretX, secretY));
                int secretRed = secretColor.getRed();
                int secretGreen = secretColor.getGreen();
                int secretBlue = secretColor.getBlue();

                // Get the binary strings of the RGB values of the secret image
                String secretRedBinary = padBinaryString(Integer.toBinaryString(secretRed));
                String secretGreenBinary = padBinaryString(Integer.toBinaryString(secretGreen));
                String secretBlueBinary = padBinaryString(Integer.toBinaryString(secretBlue));

                // Hide the bits of the secret image in the host image
                if (hideMSBinMSB) {
                    hostRedBinary = hideBit(hostRedBinary, 7, secretRedBinary.charAt(7));
                    hostGreenBinary = hideBit(hostGreenBinary, 7, secretGreenBinary.charAt(7));
                    hostBlueBinary = hideBit(hostBlueBinary, 7, secretBlueBinary.charAt(7));
                } else if (hideLSBinMSB) {
                    hostRedBinary = hideBit(hostRedBinary, 7, secretRedBinary.charAt(0));
                    hostGreenBinary = hideBit(hostGreenBinary, 7, secretGreenBinary.charAt(0));
                    hostBlueBinary = hideBit(hostBlueBinary, 7, secretBlueBinary.charAt(0));
                } else if (hideLSBinLSB) {
                    hostRedBinary = hideBit(hostRedBinary, 0, secretRedBinary.charAt(0));
                    hostGreenBinary = hideBit(hostGreenBinary, 0, secretGreenBinary.charAt(0));
                    hostBlueBinary = hideBit(hostBlueBinary, 0, secretBlueBinary.charAt(0));
                } else if (hideMSBinLSB) {
                    hostRedBinary = hideBit(hostRedBinary, 0, secretRedBinary.charAt(7));
                    hostGreenBinary = hideBit(hostGreenBinary, 0, secretGreenBinary.charAt(7));
                    hostBlueBinary = hideBit(hostBlueBinary, 0, secretBlueBinary.charAt(7));
                }

                // Convert the binary strings back to integers and create a new Color object
                int newRed = Integer.parseInt(hostRedBinary, 2);
                int newGreen = Integer.parseInt(hostGreenBinary, 2);
                int newBlue = Integer.parseInt(hostBlueBinary, 2);
                Color newColor = new Color(newRed, newGreen, newBlue);

                // Set the new color for the host image pixel
                hostImage.setRGB(i, j, newColor.getRGB());
        }
    }

    // Save the modified host image to a file
    String outputName;
    do {
        System.out.print("Enter the output file name (must end in .png): ");
        outputName = System.console().readLine();
    } while (!outputName.endsWith(".png"));
    File outputFile = new File(outputName);
    try {
        ImageIO.write(hostImage, "png", outputFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/**
 * Pads a binary string with leading zeroes to make it 8 characters long.
 * 
 * @param binaryString the binary string to pad
 * @return the padded binary string
 */
public static String padBinaryString(String binaryString) {
    while (binaryString.length() < 8) {
        binaryString = "0" + binaryString;
    }
    return binaryString;
}

/**
 * Hides a bit of the secret image in a bit of the host image.
 * 
 * @param hostBit the bit of the host image to hide the secret image bit in
 * @param secretBit the bit of the secret image to hide
 * @return the new bit string for the host image pixel
 */
public static String hideBit(String hostBit, int bitIndex, char secretBit) {
    StringBuilder sb = new StringBuilder(hostBit);
    sb.setCharAt(bitIndex, secretBit);
    return sb.toString();
}
}