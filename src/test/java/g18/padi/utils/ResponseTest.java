package g18.padi.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {

    private static final String IMAGE_PATH = "sample.png";
    private BufferedImage validImage;

    @BeforeEach
    void setup() {
        validImage = ImageReader.readImage(IMAGE_PATH);
    }

    @Test
    @DisplayName("Create Response instance")
    void createResponseInstance() {
        String validStatus = "Success";
        String validMessage = "Operation completed successfully";
        Response response = new Response(validStatus, validMessage, validImage);

        assertEquals(validStatus, response.getStatus());
        assertEquals(validMessage, response.getMessage());
        assertArrayEquals(ImageTransformer.createBytesFromImage(validImage), response.getImageSection());
    }

    @Test
    @DisplayName("Set status")
    void setStatus() {
        String validStatus = "Error";
        Response response = new Response("", "", validImage);

        response.setStatus(validStatus);

        assertEquals(validStatus, response.getStatus());
    }

    @Test
    @DisplayName("Set message")
    void setMessage() {
        String validMessage = "Operation failed";
        Response response = new Response("", "", validImage);

        response.setMessage(validMessage);

        assertEquals(validMessage, response.getMessage());
    }

    @Test
    @DisplayName("Set image section")
    void setImageSection() {
        Response response = new Response("", "", validImage);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        response.setImageSection(ImageTransformer.createBytesFromImage(image));

        assertArrayEquals(ImageTransformer.createBytesFromImage(image), response.getImageSection());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String validStatus = "Success";
        String validMessage = "Operation completed successfully";
        Response response = new Response(validStatus, validMessage, validImage);

        assertEquals("Response{status='Success', message='Operation completed successfully'}", response.toString());
    }

}