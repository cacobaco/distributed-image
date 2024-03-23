package g18.padi.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    private static final String IMAGE_PATH = "sample.png";
    private BufferedImage validImage;

    @BeforeEach
    void setup() {
        validImage = ImageReader.readImage(IMAGE_PATH);
    }

    @Test
    @DisplayName("Create Request instance")
    void createRequestInstance() {
        String validMessageType = "type";
        String validMessageContent = "content";
        BufferedImage validImageSection = validImage;
        Request request = new Request(validMessageType, validMessageContent, validImageSection);

        assertEquals(validMessageType, request.getMessageType());
        assertEquals(validMessageContent, request.getMessageContent());
        assertArrayEquals(ImageTransformer.createBytesFromImage(validImageSection), request.getImageSection());
    }

    @Test
    @DisplayName("Set message type")
    void setMessageType() {
        String validMessageType = "type";
        Request request = new Request("", "", validImage);

        request.setMessageType(validMessageType);

        assertEquals(validMessageType, request.getMessageType());
    }

    @Test
    @DisplayName("Set message content")
    void setMessageContent() {
        String validMessageContent = "content";
        Request request = new Request("", "", validImage);

        request.setMessageContent(validMessageContent);

        assertEquals(validMessageContent, request.getMessageContent());
    }

    @Test
    @DisplayName("Set image section")
    void setImageSection() {
        Request request = new Request("", "", validImage);

        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        request.setImageSection(ImageTransformer.createBytesFromImage(image));

        assertArrayEquals(ImageTransformer.createBytesFromImage(image), request.getImageSection());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String validMessageType = "type";
        String validMessageContent = "content";
        Request request = new Request(validMessageType, validMessageContent, validImage);

        assertEquals("Request{messageType='type', messageContent='content'}", request.toString());
    }

}
