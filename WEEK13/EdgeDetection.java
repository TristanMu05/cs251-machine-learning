import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

//BASIC IMAGE FILTER
public class EdgeDetection {

	static public void main(String args[]) {

		try {

			// TASK 1: SELECT AND OPEN A PNG FILE TO READ AS THE INPUT FILE
			String filePathname = "./building.jpg";
			File inputFile = new File(filePathname);

			// TASK 2: Store the input file in a BufferedImage.
			// ImageReader performs the input (read) by wrapping the file in an
			// ImageInputStream.
			BufferedImage imgIn = ImageIO.read(inputFile);

			// TASK 3: call a method to filter the image and return the new buffered image.
			imgIn = edgeDetection(imgIn);

			// TASK 4: Specify the path for blackwhite.png output file
			File outputFile = new File("./output/edgedetection.png");

			// TASK 5: Use an ImageWriter to write the filtered buffered image to an output
			// file.
			ImageIO.write(imgIn, "png", outputFile);
			System.out.println("Filter is Completed and stored in edgedetection.png...");

		} catch (Exception e) {
			System.out.println(e + " file not found");
		}
	}

    public static BufferedImage edgeDetection(BufferedImage myImage){
        int width = myImage.getWidth();
        int height = myImage.getHeight();

        BufferedImage image = new BufferedImage(width-2, height-2, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width - 1; i++){
            for (int j = 1; j < height - 1; j++){
                int[] kernal = {
                    -1, -1, -1,
                    -1, 8, -1,
                    -1, -1, -1
                };

                int[] pixels = {
                    grayScale(myImage.getRGB(i-1, j-1)),
                    grayScale(myImage.getRGB(i, j-1)),
                    grayScale(myImage.getRGB(i+1, j-1)),
                    grayScale(myImage.getRGB(i-1, j)),
                    grayScale(myImage.getRGB(i, j)),
                    grayScale(myImage.getRGB(i+1, j)),
                    grayScale(myImage.getRGB(i-1, j+1)),
                    grayScale(myImage.getRGB(i, j+1)),
                    grayScale(myImage.getRGB(i+1, j+1))
                };

                int sum = 0;
                for (int k = 0; k < 9; k++){
                    sum += pixels[k] * kernal[k];
                }

                if (sum < 0){
                    sum = 0;
                }
                if (sum > 255){
                    sum = 255;
                }

                Color newColor = new Color(sum, sum, sum);
                image.setRGB(i-1, j-1, newColor.getRGB());
            }
        }
        return image;
    }

    public static int grayScale(int rgb){
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        int avg = (r + g + b) / 3;

        return avg;
    }
}
