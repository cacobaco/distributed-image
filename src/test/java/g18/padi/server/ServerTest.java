package g18.padi.server;

import g18.padi.utils.Request;
import g18.padi.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

    private static final int SERVER_PORT = 8101;
    private BufferedImage image;
    private Server server;

    @BeforeEach
    void setUp() throws InterruptedException {
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        server = new Server(SERVER_PORT);
        server.start();
        Thread.sleep(1000); // wait to start
    }

    @Test
    @DisplayName("Server handles client connection successfully")
    void serverHandlesClientConnectionSuccessfully() throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", SERVER_PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Test red
            Request request = new Request("remove colors", "red", image);
            out.writeObject(request);

            Response response = (Response) in.readObject();

            assertEquals("OK", response.getStatus());
            assertEquals("Colors removed", response.getMessage());

            // Test green
            request = new Request("remove colors", "green", image);
            out.writeObject(request);

            response = (Response) in.readObject();

            assertEquals("OK", response.getStatus());
            assertEquals("Colors removed", response.getMessage());

            // Test blue
            request = new Request("remove colors", "blue", image);
            out.writeObject(request);

            response = (Response) in.readObject();

            assertEquals("OK", response.getStatus());
            assertEquals("Colors removed", response.getMessage());
        }

        server.interrupt();
    }

    @Test
    @DisplayName("Server handles unrecognized message type")
    void serverHandlesUnrecognizedMessageType() throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", SERVER_PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Request request = new Request("unrecognized type", "red", image);
            out.writeObject(request);

            Response response = (Response) in.readObject();

            assertEquals("ERROR", response.getStatus());
            assertEquals("Unrecognized message type", response.getMessage());
        }

        server.interrupt();
    }

    @Test
    @DisplayName("Server handles unrecognized color")
    void serverHandlesUnrecognizedColor() throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", SERVER_PORT)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Request request = new Request("remove colors", "unrecognized color", image);
            out.writeObject(request);

            Response response = (Response) in.readObject();

            assertEquals("OK", response.getStatus());
            assertEquals("Colors removed", response.getMessage());
        }

        server.interrupt();
    }
}