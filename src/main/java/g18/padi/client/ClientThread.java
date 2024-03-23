package g18.padi.client;

import g18.padi.Main;
import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.ImageTransformer;
import g18.padi.utils.Request;
import g18.padi.utils.Response;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The ClientThread class represents a thread responsible for processing a specific image split.
 * It sends a request to the server with the image split, receives the processed image, and updates the split accordingly.
 */
public class ClientThread extends Thread {

    private static final ReentrantLock lock = new ReentrantLock();

    private final BufferedImageSplit imageSplit;
    private final Client client;
    private final String[] colors;

    /**
     * Constructs a ClientThread with the specified image split and client.
     *
     * @param imageSplit the BufferedImageSplit to be processed
     * @param client     the Client instance for communication with the server
     * @param colors     the colors to be removed from the image
     */
    public ClientThread(BufferedImageSplit imageSplit, Client client, String[] colors) {
        this.imageSplit = imageSplit;
        this.client = client;
        this.colors = colors;
    }

    /**
     * Executes the thread's task, which involves sending a request to the server with the image split,
     * receiving the processed image, and updating the split accordingly.
     */
    @Override
    public void run() {
        lock.lock(); // Lock to ensure that only one thread sends a request to the server at a time

        Socket socket = null;

        try {
            int serverPort = Main.getLoadBalancer().getBestServer();
            Request request = new Request("remove colors", String.join(";", colors), imageSplit.getBufferedImage());
            socket = client.sendRequest("localhost", serverPort, request);
        } catch (IllegalStateException e) {
            System.out.println("No servers available");
        } finally {
            lock.unlock(); // Unlock after sending the request
        }

        Response response = client.receiveResponse(socket);

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage responseImage = ImageTransformer.createImageFromBytes(response.getImageSection());
        imageSplit.setBufferedImage(responseImage);
    }

    /**
     * Gets the image split processed by this thread.
     *
     * @return the BufferedImageSplit processed by this thread
     */
    public BufferedImageSplit getImageSplit() {
        return imageSplit;
    }
}
