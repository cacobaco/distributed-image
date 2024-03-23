package g18.padi.client;

import g18.padi.utils.ImageReader;
import g18.padi.utils.Request;
import g18.padi.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private static final int PORT = 8000;
    private static final String TEST_IMAGE_PATH = "sample.png";
    private ServerSocket serverSocket;
    private BufferedImage validImage;

    @BeforeEach
    void setUp() {
        validImage = ImageReader.readImage(TEST_IMAGE_PATH);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Send request and receive response successfully")
    void sendRequestAndReceiveResponseSuccess() throws IOException {
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8001)) {
                System.out.println("Server started. Listening on port 8001...");
                while (!Thread.currentThread().isInterrupted()) {
                    try (Socket socket = serverSocket.accept()) {
                        System.out.println("Client connected: " + socket.getInetAddress());
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        Request request = (Request) in.readObject();
                        System.out.println("Received request: " + request);
                        Response response = new Response("Success", "Request processed", validImage);
                        out.writeObject(response);
                        System.out.println("Sent response: " + response);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        Client client = new Client("TestClient");
        Request request = new Request("MessageType", "MessageContent", validImage);
        Socket socket = client.sendRequest("localhost", 8001, request);
        assertNotNull(socket);
        Response response = client.receiveResponse(socket);
        socket.close();

        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals("Request processed", response.getMessage());

        serverThread.interrupt();
    }

    @Test
    @DisplayName("Send request and receive response with error handling")
    void sendRequestAndReceiveResponseWithErrorHandling() throws IOException {
        // Simulate client sending request to non-existent server
        Client client = new Client("TestClient");
        Request request = new Request("MessageType", "MessageContent", validImage);
        Socket socket = client.sendRequest("nonexistent.server", 8001, request);

        assertNull(socket);
    }

    @Test
    @DisplayName("Get client name successfully")
    void getClientNameSuccess() {
        Client client = new Client("TestClient");
        assertEquals("TestClient", client.getName());
    }
}
