package g18.padi.client;

import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.ImageTransformer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * The ClientExecutor class is responsible for executing client tasks in parallel using multiple threads.
 * It splits the input image into sub-images, processes them concurrently, and then reassembles the final image.
 */
public class ClientExecutor {

    private BufferedImage image;
    private final Client client;
    private final List<ClientThread> threads;

    /**
     * Constructs a ClientExecutor with the specified image and client.
     *
     * @param image  the BufferedImage to be processed
     * @param client the Client instance for communication with the server
     */
    public ClientExecutor(BufferedImage image, Client client) {
        this.image = image;
        this.client = client;
        this.threads = new ArrayList<>();
    }

    /**
     * Executes the client tasks in parallel.
     * It splits the input image into sub-images, processes them concurrently, and then reassembles the final image.
     */
    public void execute() {
        int numRows = 4; // TODO buscar da config
        int numCols = 4; // TODO buscar da config

        BufferedImage[][] images = ImageTransformer.splitImage(image, numRows, numCols);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                BufferedImageSplit imageSplit = new BufferedImageSplit(images[i][j], i, j);

                ClientThread thread = new ClientThread(imageSplit, client);
                threads.add(thread);

                thread.start();
            }
        }

        BufferedImage[][] finalImages = new BufferedImage[numRows][numCols];
        for (ClientThread thread : threads) {
            try {
                thread.join();

                BufferedImageSplit imageSplit = thread.getImageSplit();

                finalImages[imageSplit.getI()][imageSplit.getJ()] = imageSplit.getBufferedImage();
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        image = ImageTransformer.joinImages(finalImages, image.getType());
    }

    /**
     * Gets the processed image.
     *
     * @return the processed BufferedImage
     */
    public BufferedImage getImage() {
        return image;
    }

}

