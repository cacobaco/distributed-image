package g18.padi.client;

import g18.padi.Main;
import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.ImageTransformer;
import g18.padi.utils.Request;
import g18.padi.utils.Response;

import java.awt.image.BufferedImage;

/**
 * The ClientThread class represents a thread responsible for processing a specific image split.
 * It sends a request to the server with the image split, receives the processed image, and updates the split accordingly.
 */
public class ClientThread extends Thread {
    private final BufferedImageSplit imageSplit;
    private final Client client;
    private final String color;

    /**
     * Constructs a ClientThread with the specified image split and client.
     *
     * @param imageSplit the BufferedImageSplit to be processed
     * @param client     the Client instance for communication with the server
     * @param color      the color to be removed from the image
     */
    public ClientThread(BufferedImageSplit imageSplit, Client client, String color) {
        this.imageSplit = imageSplit;
        this.client = client;
        this.color = color;
    }

    /**
     * Executes the thread's task, which involves sending a request to the server with the image split,
     * receiving the processed image, and updating the split accordingly.
     */
    @Override
    public void run() {
        int serverPort = Main.getLoadBalancer().getBestServer();
        Request request = new Request("remove color", color, imageSplit.getBufferedImage());
        Response response = client.sendRequestAndReceiveResponse("localhost", serverPort, request);
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
