package g18.padi.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ImageReaderTest {

    private static final String VALID_PATH = "sample.png";
    private static final String INVALID_PATH = "nonExistentImage.png";

    @Test
    @DisplayName("Read image from valid path")
    void readImageSuccess() {
        BufferedImage result = ImageReader.readImage(VALID_PATH);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Read image from invalid path")
    void readImageInvalidPath() {
        BufferedImage result = ImageReader.readImage(INVALID_PATH);
        assertNull(result);
    }

}