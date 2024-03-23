package g18.padi.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The ImageTransformer class implements a set of methods for performing transformations (split, join, and red removal)
 * in a specified image.
 */
public class ImageTransformer {

    /**
     * Splits an image into a grid of subimages.
     *
     * @param image    the BufferedImage containing the image
     * @param nRows    the number of rows in the grid
     * @param nColumns the number of columns in the grid
     * @return a BufferedImage array containing the subimages
     */
    public static BufferedImage[][] splitImage(BufferedImage image, int nRows, int nColumns) {
        int subimageWidth = image.getWidth() / nColumns;
        int subimageHeight = image.getHeight() / nRows;
        BufferedImage[][] subimages = new BufferedImage[nRows][nColumns];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                subimages[i][j] = new BufferedImage(subimageWidth, subimageHeight, image.getType());
                Graphics2D g = subimages[i][j].createGraphics();
                g.drawImage(image, 0, 0, subimageWidth, subimageHeight, subimageWidth * j, subimageHeight * i, subimageWidth * j + subimageWidth, subimageHeight * i + subimageHeight, null);
                g.dispose();
            }
        }
        return subimages;
    }

    /**
     * Removes the red component of a given image.
     *
     * @param image the BufferedImage containing the image
     * @return a BufferedImage without the red component
     */
    public static BufferedImage removeReds(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color c;
        BufferedImage resultingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                c = new Color(image.getRGB(i, j));
                int g = c.getGreen();
                int b = c.getBlue();
                resultingImage.setRGB(i, j, new Color(0, g, b).getRGB());
            }
        }
        return resultingImage;
    }

    /**
     * Joins a set of images into a single image.
     *
     * @param images the BufferedImage array containing the images to join
     * @param type   the type of the resulting image
     * @return a BufferedImage containing the joined images
     */
    public static BufferedImage joinImages(BufferedImage[][] images, int type) {
        int height = 0;
        int width = 0;
        for (BufferedImage[] image : images) {
            height += image[0].getHeight();
        }
        for (int i = 0; i < images[0].length; i++) {
            width += images[0][i].getWidth();
        }

        BufferedImage result = new BufferedImage(width, height, type);
        Graphics2D g = result.createGraphics();
        int y = 0;
        for (BufferedImage[] image : images) {
            int x = 0;
            for (BufferedImage bufferedImage : image) {
                g.drawImage(bufferedImage, x, y, null);
                x += bufferedImage.getWidth();
            }
            y += image[0].getHeight();
        }
        g.dispose();
        return result;
    }

    /**
     * Creates a Buffered image from a byte array
     *
     * @param imageData - Byte array of with the image
     * @return - Image as a BufferedItem object
     */
    public static BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serializes an image as a byte array so it can be sent through a socket. It is important to define the type of image
     *
     * @param image - image to be converted as a byte[]
     * @return - image as an array of bytes
     */
    public static byte[] createBytesFromImage(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

}