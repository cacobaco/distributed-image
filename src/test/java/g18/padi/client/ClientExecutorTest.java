package g18.padi.client;

import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.Request;
import g18.padi.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;




import java.awt.image.BufferedImage;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ClientExecutorTest {

    private ClientExecutor clientExecutor;
    private BufferedImage image;
    private Client client;

    @BeforeEach
    void setUp() {
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        client = Mockito.mock(Client.class);
        clientExecutor = new ClientExecutor(image, client);
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
    void testImageProcessing() throws IOException, InterruptedException {
        BufferedImageSplit imageSplit = new BufferedImageSplit(image, 0, 0);
        BufferedImage img = imageSplit.getBufferedImage();
        Response response = new Response("Success", "Request processed", img);
        when(client.sendRequestAndReceiveResponse(anyString(), anyInt(), any(Request.class))).thenReturn(response);

        clientExecutor.execute();

        verify(client, times(16)).sendRequestAndReceiveResponse(anyString(), anyInt(), any(Request.class));
    }
}
