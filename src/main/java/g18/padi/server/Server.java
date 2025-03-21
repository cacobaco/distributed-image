package g18.padi.server;

import g18.padi.Main;
import g18.padi.utils.ConfigReader;
import g18.padi.utils.ImageTransformer;
import g18.padi.utils.Request;
import g18.padi.utils.Response;

import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * A TCP/IP server that listens for connections on a specified port and handles each client connection in a separate
 * thread.
 */
public class Server extends Thread {

    private final int port;

    /**
     * Constructs a new Server instance.
     *
     * @param port The port number on which the server will listen for incoming connections.
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * The entry point of the server thread. Starts the server to accept and handle client connections.
     */
    @Override
    public void run() {
        try {
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the server socket and continuously listens for client connections. Each client connection is handled
     * in a new thread.
     *
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    private void startServer() throws IOException {
        int maxSegments = ConfigReader.getInstance().getServerMaxProcessingCapacity();

        ExecutorService executorService = Executors.newFixedThreadPool(maxSegments);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            Main.getLoadBalancer().setServerLoad(port, 0);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                executorService.submit(new ClientHandler(clientSocket));

                Main.getLoadBalancer().incrementServerLoad(port, 1);
            }
        } finally {
            Main.getLoadBalancer().removeServerLoad(port);

            executorService.shutdown();
        }
    }

    /**
     * Gets the port number.
     *
     * @return the port number.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Handles client connections. Reads objects from the client, processes them, and sends a response back.
     */
    private class ClientHandler implements Runnable {

        private final Socket clientSocket;

        /**
         * Constructs a new ClientHandler instance.
         *
         * @param socket The client socket.
         */
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        /**
         * The entry point of the client handler thread. Manages input and output streams for communication with the
         * client.
         */
        @Override
        public void run() {
            try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                Request request;
                // Continuously read objects sent by the client and respond to each.
                while ((request = (Request) in.readObject()) != null) {
                    out.writeObject(handleRequest(request));
                }

                System.out.println("[Server " + port + "] " + request.toString());
            } catch (EOFException e) {
                System.out.println("[Server " + port + "] Client disconnected.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[Server " + port + "] Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("[Server " + port + "] Error closing client socket: " + e.getMessage());
                }
            }

            Main.getLoadBalancer().decrementServerLoad(Server.this.getPort(), 1);
        }

        /**
         * Processes the client's request and generates a response.
         *
         * @param request The object received from the client.
         * @return The response object to be sent back to the client.
         */
        private Response handleRequest(Request request) {
            switch (request.getMessageType()) {
                case "remove colors":
                    return handleRemoveColorsRequest(request);
                default:
                    return new Response("ERROR", "Unrecognized message type", ImageTransformer.createImageFromBytes(request.getImageSection()));
            }
        }

        /**
         * Processes a request to remove specific colors from an image.
         *
         * @param request The request object.
         * @return The response object containing the modified image.
         */
        private Response handleRemoveColorsRequest(Request request) {
            BufferedImage image = ImageTransformer.createImageFromBytes(request.getImageSection());

            String[] colors = request.getMessageContent().split(";");

            for (String color : colors) {
                switch (color) {
                    case "red" -> image = ImageTransformer.removeReds(image);
                    case "green" -> image = ImageTransformer.removeGreens(image);
                    case "blue" -> image = ImageTransformer.removeBlues(image);
                }
            }

            System.out.println(colors[0]);

            return new Response("OK", "Colors removed", image);
        }
    }

}