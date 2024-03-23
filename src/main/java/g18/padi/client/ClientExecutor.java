package g18.padi.client;

import g18.padi.utils.BufferedImageSplit;
import g18.padi.utils.ImageTransformer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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
    public ClientExecutor (BufferedImage image, Client client) {
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

        image = ImageTransformer.joinImages(finalImages, image.getWidth(), image.getHeight(), image.getType());
        saveImageResult (image, "sample.png");
    }

    /**
     * Saves the resulting image to the "results" directory.
     *
     * @param image         the resulting image to be saved
     * @param originalName  the original name of the image
     */
    private void saveImageResult(BufferedImage image, String originalName) {
        String resultFileName;
        String extension;

        int extensionIndex = originalName.lastIndexOf('.');
        if (extensionIndex != -1) {
            extension = originalName.substring(extensionIndex + 1);
            resultFileName = originalName.substring(0, extensionIndex) + "_edited";
        } else {
            extension = "png"; // Caso a extensão não seja encontrada, assume-se .png como padrão
            resultFileName = originalName + "_edited";
        }

        File directory = new File("results");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File resultFile = new File(directory, resultFileName + "." + extension);

        try {
            ImageIO.write(image, extension, resultFile);
            System.out.println("Imagem resultante salva em: " + resultFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Erro ao salvar a imagem resultante: " + e.getMessage());
        }
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

