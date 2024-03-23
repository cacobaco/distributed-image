package g18.padi.server;

import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.Request;
import g18.padi.utils.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTest {


    private static final String IMAGE_PATH = "sample.png";
    private BufferedImage validImage;
    private Server server;
    private Thread serverThread;
    private int port = 8888;
    private BufferedImageSplit imageSplit;


    @BeforeEach
    public void setUp() {
        server = new Server(port);
        serverThread = new Thread(server);
        try {
            validImage = ImageIO.read(new File(IMAGE_PATH)); // Load a valid image
            imageSplit = new BufferedImageSplit(validImage, 3, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverThread.start();
    }

    @AfterEach
    public void tearDown() {
        serverThread.interrupt();
    }

    @Test
    public void testServerConnection() throws IOException {


        // Connect to the server
        Socket clientSocket = new Socket("localhost", port);

        // Verify that the server is running
        assertTrue(serverThread.isAlive());

        // Send a test request
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            // Sending a test request
            out.writeObject(new Request("", "",imageSplit.getBufferedImage()));

            // Assuming server sends a response back
            Object response = in.readObject();

            assertTrue(response instanceof Response);
            Response serverResponse = (Response) response;
            assertEquals("OK", serverResponse.getStatus());
            assertEquals("Hello, Client!", serverResponse.getMessage());


            // Validate response if needed
            // assertTrue(response instanceof Response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            clientSocket.close();
        }
    }

}
