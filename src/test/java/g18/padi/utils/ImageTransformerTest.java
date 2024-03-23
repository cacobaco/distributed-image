package g18.padi.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageTransformerTest {

    private static final String IMAGE_PATH = "sample.png";
    private static final int VALID_N_ROWS = 4;
    private static final int VALID_N_COLS = 4;
    private static final int INVALID_N_ROWS = 5;
    private static final int INVALID_N_COLS = 5;
    private BufferedImage validImage;

    @BeforeEach
    void setUp() {
        validImage = ImageReader.readImage(IMAGE_PATH);
    }

    @Test
    @DisplayName("Split image successfully")
    void splitImageSuccess() {
        BufferedImage[][] result = ImageTransformer.splitImage(validImage, VALID_N_ROWS, VALID_N_COLS);
        assertEquals(VALID_N_ROWS, result.length);
        assertEquals(VALID_N_COLS, result[0].length);
    }

    @Test
    @DisplayName("Split image with invalid dimensions")
    void splitImageInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> ImageTransformer.splitImage(validImage, INVALID_N_ROWS, INVALID_N_COLS));
    }

    @Test
    @DisplayName("Remove reds successfully")
    void removeRedsSuccess() {
        BufferedImage result = ImageTransformer.removeReds(validImage);
        assertNotNull(result);

        int width = result.getWidth();
        int height = result.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = result.getRGB(i, j);
                int red = (rgb >> 16) & 0xFF;
                assertEquals(0, red);
            }
        }
    }

    @Test
    @DisplayName("Join images successfully")
    void joinImagesSuccess() {
        BufferedImage[][] imageSplits = ImageTransformer.splitImage(validImage, VALID_N_ROWS, VALID_N_COLS);

        for (int i = 0; i < VALID_N_ROWS; i++) {
            for (int j = 0; j < VALID_N_COLS; j++) {
                imageSplits[i][j] = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            }
        }
        BufferedImage result = ImageTransformer.joinImages(imageSplits, validImage.getWidth(), validImage.getHeight(), validImage.getType());
        assertNotNull(result);

        assertEquals(validImage.getWidth(), result.getWidth());
        assertEquals(validImage.getHeight(), result.getHeight());
    }

    @Test
    @DisplayName("Create image from bytes successfully")
    void createImageFromBytesSuccess() {
        byte[] bytes = ImageTransformer.createBytesFromImage(validImage);
        BufferedImage result = ImageTransformer.createImageFromBytes(bytes);
        assertNotNull(result);

        assertEquals(validImage.getWidth(), result.getWidth());
        assertEquals(validImage.getHeight(), result.getHeight());
    }

    @Test
    @DisplayName("Create bytes from image successfully")
    void createBytesFromImageSuccess() {
        byte[] bytes = ImageTransformer.createBytesFromImage(validImage);
        assertNotNull(bytes);

        BufferedImage result = ImageTransformer.createImageFromBytes(bytes);
        assertEquals(validImage.getWidth(), result.getWidth());
        assertEquals(validImage.getHeight(), result.getHeight());
    }

}