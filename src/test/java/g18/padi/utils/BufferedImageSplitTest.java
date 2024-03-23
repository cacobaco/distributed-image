package g18.padi.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BufferedImageSplitTest {

    @Test
    @DisplayName("Test constructor")
    public void testCreateBufferedImageSplit() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        BufferedImageSplit split = new BufferedImageSplit(image, 1, 1);

        assertNotNull(split);
        assertEquals(image, split.getBufferedImage());
        assertEquals(1, split.getI());
        assertEquals(1, split.getJ());
    }

    @Test
    @DisplayName("Test setBufferedImage")
    public void testSetBufferedImage() {
        BufferedImage image1 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        BufferedImage image2 = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        BufferedImageSplit split = new BufferedImageSplit(image1, 1, 1);

        split.setBufferedImage(image2);

        assertEquals(image2, split.getBufferedImage());
    }

    @Test
    @DisplayName("Test getI")
    public void testGetI() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        BufferedImageSplit split = new BufferedImageSplit(image, 5, 1);

        assertEquals(5, split.getI());
    }

    @Test
    @DisplayName("Test getJ")
    public void testGetJ() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        BufferedImageSplit split = new BufferedImageSplit(image, 1, 5);

        assertEquals(5, split.getJ());
    }

}