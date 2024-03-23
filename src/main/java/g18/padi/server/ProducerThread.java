package g18.padi.server;

import java.util.concurrent.BlockingQueue;

/**
 * The ProducerThread represents a producer thread. In this case, a producer thread will add a
 * {@link BufferedImageSplit} to the buffer to be then processed/consumed by a consumer thread.
 */
public class ProducerThread extends Thread {

    private final BlockingQueue < BufferedImageSplit > buffer;
    private final BufferedImageSplit image;

    /**
     * Constructs a producer thread, by specifying the buffer and the image split to be added to the buffer.
     *
     * @param buffer the shared buffer to which producer threads will put images splits
     * @param image  the BufferedImageSplit to be added to the buffer
     */
    public ProducerThread ( BlockingQueue < BufferedImageSplit > buffer , BufferedImageSplit image ) {
        this.buffer = buffer;
        this.image = image;
    }

    /**
     * Executes the main function of the producer thread. In this case, add a {@link BufferedImageSplit} to the buffer
     * to be processed/consumed by a consumer thread.
     */
    private void produce ( ) {
        buffer.add ( image );
    }

    @Override
    public void run ( ) {
        produce ( );
    }
}
