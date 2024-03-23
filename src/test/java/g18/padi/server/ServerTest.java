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
    private int port = 8001;
    private BufferedImageSplit imageSplit;

    @BeforeEach
    public void setUp() {

        try {
            validImage = ImageIO.read(new File(IMAGE_PATH)); // Load a valid image
            imageSplit = new BufferedImageSplit(validImage, 3, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server = new Server(port);
        serverThread = new Thread(server);
        serverThread.start();
        try {
            Thread.sleep(1000); // Adjust delay as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        serverThread.interrupt();
        // Add a short delay to allow the server to stop
        try {
            Thread.sleep(1000); // Adjust delay as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServerConnection() throws IOException {
        try (Socket clientSocket = new Socket("localhost", port);
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            // Verify that the server is running
            assertTrue(serverThread.isAlive());

            // Sending a test request
            out.writeObject(new Request("", "", imageSplit.getBufferedImage()));

            // Assuming server sends a response back
            Object response = in.readObject();

            // Validate response if needed
            assertTrue(response instanceof Response);
            Response serverResponse = (Response) response;
            assertEquals("ERROR", serverResponse.getStatus());
            assertEquals("Unrecognized message type", serverResponse.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}