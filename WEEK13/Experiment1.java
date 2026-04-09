import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

//BASIC IMAGE FILTER
public class Experiment1 {

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
			imgIn = createBlackWhiteImage(imgIn);

			// TASK 4: Specify the path for blackwhite.png output file
			File outputFile = new File("./output/blackandwhite.png");

			// TASK 5: Use an ImageWriter to write the filtered buffered image to an output
			// file.
			ImageIO.write(imgIn, "png", outputFile);
			System.out.println("Filter is Completed and stored in blackwhite.png...");

			imgIn = createInvertedImage(imgIn);

			File outputFile2 = new File("./output/inverted.png");
			ImageIO.write(imgIn, "png", outputFile2);
			System.out.println("Filter is Completed and stored in inverted.png...");

		} catch (Exception e) {
			System.out.println(" file not found");
		}
	}

	public static BufferedImage createBlackWhiteImage(BufferedImage myImage) {
		// CONSTRUCT THE BLACK AND WHITE IMAGE PIXEL BY PIXEL
		int width = myImage.getWidth();
		int height = myImage.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				int rgb = myImage.getRGB(i, j);

				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = rgb & 0xFF;

				int avg = (r + g + b) / 3;

				Color newColor = new Color(avg, avg, avg);
				image.setRGB(i, j, newColor.getRGB());
			}
		}
		
		return image;

	}

	public static BufferedImage createInvertedImage(BufferedImage myImage) {
		int width = myImage.getWidth();
		int height = myImage.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++){
				int rgb = myImage.getRGB(i,j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = rgb & 0xFF;

				int newR = 255 - r;
				int newG = 255 - g;
				int newB = 255 - b;

				Color newColor = new Color(newR, newG, newB);
				image.setRGB(i, j, newColor.getRGB());
			}
		}
		return image;
	}
		
}
