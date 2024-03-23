package g18.padi.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import g18.padi.server.Server;
import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.Request;
import g18.padi.utils.Response;

public class ServerTest {

    private static final String IMAGE_PATH = "sample.png";
    private final int PORT = 8001;

    private Thread serverThread;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @BeforeEach
    public void setUp() {
        serverThread = new Thread(() -> {
            Server server = new Server(PORT);
            serverThread = new Thread(server);
            serverThread.start();
        });
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        serverThread.interrupt();
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServerConnection() throws IOException, ClassNotFoundException {
        BufferedImage validImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
        BufferedImageSplit imageSplit = new BufferedImageSplit(validImage, 3, 3);

        clientSocket = new Socket("localhost", PORT);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        assertTrue(serverThread.isAlive());

        // Sending a test request
        Request request = new Request("", "", imageSplit.getBufferedImage());
        out.writeObject(request);

        // Assuming server sends a response back
        Object response = in.readObject();

        // Validate response if needed
        assertTrue(response instanceof Response);
        Response serverResponse = (Response) response;
        assertEquals("ERROR", serverResponse.getStatus());
        assertEquals("Unrecognized message type", serverResponse.getMessage());
    }
}