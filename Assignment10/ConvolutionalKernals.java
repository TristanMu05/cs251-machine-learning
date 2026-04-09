package Assignment10;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class ConvolutionalKernals {
    public static void main(String[] args){
        try {
            // prompt the user for their image

                        /*
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = fileChooser.showOpenDialog(null);
            File selectedFile = null;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                System.out.println("You selected: " + selectedFile.getAbsolutePath());
            }
                        */

            File selectedFile = new File("./building.jpg");
            // load the image
            BufferedImage imgIn = ImageIO.read(selectedFile);
            // apply the convoulution image filter
            BufferedImage edgeDetection = edgeDetection(imgIn);
            BufferedImage sharpening = sharpening(imgIn);
            BufferedImage sobelX = sobelx(imgIn);
            BufferedImage sobelY = sobely(imgIn);
            BufferedImage gaussianBlur = gaussianBlur(imgIn);
            // product output image files showign result of each kernal
            ImageIO.write(edgeDetection, "png", new File("./output/edgeDetection.png"));
            ImageIO.write(sharpening, "png", new File("./output/sharpening.png"));
            ImageIO.write(sobelX, "png", new File("./output/sobelX.png"));
            ImageIO.write(sobelY, "png", new File("./output/sobelY.png"));
            ImageIO.write(gaussianBlur, "png", new File("./output/gaussianBlur.png"));
            // compare results of multiple kernals
            /*
            Edge Detection: 
            Sharpening: 
            Sobel X: 
            Sobel Y: 
            Gaussian Blur: 
            */
            
        } catch (Exception e) {
            System.out.println(e + " error");
        }
    }

    public static int width = 0;
    public static int height = 0;
    // Edge detector kernal {-1,-1,-1,-1,8,-1,-1,-1,-1}
    public static BufferedImage edgeDetection(BufferedImage myImg){
        width = myImg.getWidth();
        height = myImg.getHeight();

        BufferedImage image = new BufferedImage(width - 2, height - 2, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width - 1; i ++){
            for (int j = 1; j < height - 1; j++){
                int[] kernal = {
                    -1,-1,-1,
                    -1,8,-1,
                    -1,-1,-1
                };
                int[] pixels = {
                    grayScale(myImg.getRGB(i-1, j-1)),
                    grayScale(myImg.getRGB(i, j-1)),
                    grayScale(myImg.getRGB(i+1, j-1)),
                    grayScale(myImg.getRGB(i-1, j)),
                    grayScale(myImg.getRGB(i, j)),
                    grayScale(myImg.getRGB(i+1, j)),
                    grayScale(myImg.getRGB(i-1, j+1)),
                    grayScale(myImg.getRGB(i, j+1)),
                    grayScale(myImg.getRGB(i+1, j+1))
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

    // Sharpening Kernal {0,-1,0,-1,5,-1,0,-1,0}
    public static BufferedImage sharpening(BufferedImage myImg){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width - 1; i++){
            for (int j = 1; j < height - 1; j++){
                int [] kernal = {
                    0,-1,0,
                    -1,5,-1,
                    0,-1,0
                };

                int[][] pixels = {
                    rgb(myImg.getRGB(i-1,j-1)),
                    rgb(myImg.getRGB(i,j-1)),
                    rgb(myImg.getRGB(i+1,j-1)),
                    rgb(myImg.getRGB(i-1,j)),
                    rgb(myImg.getRGB(i,j)),
                    rgb(myImg.getRGB(i+1,j)),
                    rgb(myImg.getRGB(i-1,j+1)),
                    rgb(myImg.getRGB(i,j+1)),
                    rgb(myImg.getRGB(i+1,j+1))
                };

                int[] sum = {0,0,0}; 
                for (int k = 0; k < 9; k++){
                    for (int l = 0; l < 3; l++){
                        sum[l] += pixels[k][l] * kernal[k];
                    }
                }

                for (int l = 0; l < 3; l++){
                    if (sum[l] < 0){
                        sum[l] = 0;
                    }
                    if (sum[l] > 255){
                        sum[l] = 255;
                    }
                }

                Color newColor = new Color(sum[0], sum[1], sum[2]);
                image.setRGB(i, j, newColor.getRGB());
            }
        }
        return image;
    }

    public static int[] rgb(int rgb){
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return new int[] {r,g,b};
    }

    // Sobel X Kernal {-1,0,1,-2,0,2,-1,0,1}
    public static BufferedImage sobelx(BufferedImage myImg){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width - 1; i++){
            for (int j = 1; j < height - 1; j++){
                int [] kernal = {
                    -1,0,1,
                    -2,0,2,
                    -1,0,1
                };

                int[] pixels = {
                    grayScale(myImg.getRGB(i-1,j-1)),
                    grayScale(myImg.getRGB(i,j-1)),
                    grayScale(myImg.getRGB(i+1,j-1)),
                    grayScale(myImg.getRGB(i-1,j)),
                    grayScale(myImg.getRGB(i,j)),
                    grayScale(myImg.getRGB(i+1,j)),
                    grayScale(myImg.getRGB(i-1,j+1)),
                    grayScale(myImg.getRGB(i,j+1)),
                    grayScale(myImg.getRGB(i+1,j+1))
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

                Color newColor = new Color(sum,sum,sum);
                image.setRGB(i, j, newColor.getRGB());
            }
        }
        return image;
    }

    // Sobel Y Kernal {-1,-2,-1,0,8,0,1,2,1}
    public static BufferedImage sobely(BufferedImage myImg){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width - 1; i++){
            for (int j = 1; j < height - 1; j++){
                int [] kernal = {
                    -1,-2,-1,
                    0,0,0,
                    1,2,1
                };

                int[] pixels = {
                    grayScale(myImg.getRGB(i-1,j-1)),
                    grayScale(myImg.getRGB(i,j-1)),
                    grayScale(myImg.getRGB(i+1,j-1)),
                    grayScale(myImg.getRGB(i-1,j)),
                    grayScale(myImg.getRGB(i,j)),
                    grayScale(myImg.getRGB(i+1,j)),
                    grayScale(myImg.getRGB(i-1,j+1)),
                    grayScale(myImg.getRGB(i,j+1)),
                    grayScale(myImg.getRGB(i+1,j+1))
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
                image.setRGB(i, j, newColor.getRGB());
            }
        }
        return image;
    }

    // Gaussian Blur {1,2,1,2,4,2,1,2,1}
    public static BufferedImage gaussianBlur(BufferedImage myImg){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < width - 1; i++){
            for (int j = 1; j < height - 1; j++){
                int [] kernal = {
                    1,2,1,
                    2,4,2,
                    1,2,1
                };

                int[][] pixels = {
                    rgb(myImg.getRGB(i-1,j-1)),
                    rgb(myImg.getRGB(i,j-1)),
                    rgb(myImg.getRGB(i+1,j-1)),
                    rgb(myImg.getRGB(i-1,j)),
                    rgb(myImg.getRGB(i,j)),
                    rgb(myImg.getRGB(i+1,j)),
                    rgb(myImg.getRGB(i-1,j+1)),
                    rgb(myImg.getRGB(i,j+1)),
                    rgb(myImg.getRGB(i+1,j+1))
                };

                int[] sum = {0,0,0}; 
                for (int k = 0; k < 9; k++){
                    for (int l = 0; l < 3; l++){
                        sum[l] += pixels[k][l] * kernal[k];
                    }
                }

                for (int l = 0; l < 3; l++){
                    if (sum[l] < 0){
                        sum[l] = 0;
                    }
                    if (sum[l] > 255){
                        sum[l] = 255;
                    }
                }

                Color newColor = new Color(sum[0], sum[1], sum[2]);
                image.setRGB(i, j, newColor.getRGB());
            }
        }
        return image;
    }
}
