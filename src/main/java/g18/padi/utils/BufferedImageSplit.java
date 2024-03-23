package g18.padi.utils;

import java.awt.image.BufferedImage;

/**
 * The class BufferedImageSplit represents an image split and its corresponding position in the final image. The objects
 * of this class will be put in the buffer for the consuming threads and in the resulting buffer.
 */
public class BufferedImageSplit {
    private BufferedImage bufferedImage;
    private final int i;
    private final int j;

    /**
     * Constructs a BufferedImageSplit by specifying the image split and its position on the final image.
     *
     * @param bufferedImage the BufferedImage containing the image split
     * @param i             the row index of the image split in the final image
     * @param j             the column index of the image split in the final image
     */
    public BufferedImageSplit ( BufferedImage bufferedImage , int i , int j ) {
        this.bufferedImage = bufferedImage;
        this.i = i;
        this.j = j;
    }

    /**
     * Gets the BufferedImage containing the image split
     *
     * @return the BufferedImage containing the image split
     */
    public BufferedImage getBufferedImage ( ) {
        return bufferedImage;
    }
    //TODO java doc
    public void setBufferedImage (BufferedImage image) {
        this.bufferedImage = image;
    }
    /**
     * Get the row index of the image split in the final image
     *
     * @return the row index of the image split in the final image
     */
    public int getI ( ) {
        return i;
    }

    /**
     * Get the column index of the image split in the final image
     *
     * @return the column index of the image split in the final image
     */
    public int getJ ( ) {
        return j;
    }


}