package g18.padi.client;

import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.Request;
import g18.padi.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientExecutorTest {

    private ClientExecutor clientExecutor;
    private BufferedImage image;
    private Client client;

    @BeforeEach
    void setUp() {
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        client = new Client("TestClient");
        clientExecutor = new ClientExecutor(image, client, "red");
    }

    @Test
    @DisplayName("Test ClientExecutor construction")
    void testConstructor() {
        assertNotNull(clientExecutor);
    }

    @Test
    @DisplayName("Test image getter")
    void testGetImage() {
        assertEquals(image, clientExecutor.getImage());
    }

    @Test
    @DisplayName("Test image processing")
    void testImageProcessing() throws IOException {

        try {
            clientExecutor.execute();
        } catch (Exception e) {
            assertTrue(false, "Exception occurred during image processing: " + e.getMessage());
        }
    }
}
