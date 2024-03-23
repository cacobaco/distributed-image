package g18.padi.server;
import g18.padi.utils.ImageTransformer;
import g18.padi.utils.BufferedImageSplit;
import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The ConsumerThread represents a consumer thread, which is responsible for removing the red component.
 */
public class ConsumerThread extends Thread {

    private final BlockingQueue < BufferedImageSplit > buffer;
    private final BlockingQueue < BufferedImageSplit > resultingBuffer;

    private final int nTotalImages;

    /**
     * Constructs a consumer thread responsible for retrieving image splits from the buffer, processing them, and
     * putting the result in the resulting buffer.
     *
     * @param buffer          the shared buffer to which consumer threads will retrieve images splits
     * @param resultingBuffer the buffer to which the consumer threads will put the processes image splits
     * @param nTotalImages    the total number of images to process (given by the number of rows times the number of
     *                        columns)
     */
    public ConsumerThread (BlockingQueue<BufferedImageSplit> buffer , BlockingQueue<BufferedImageSplit> resultingBuffer , int nTotalImages ) {
        this.buffer = buffer;
        this.resultingBuffer = resultingBuffer;
        this.nTotalImages = nTotalImages;
    }

    @Override
    public void run ( ) {
        boolean stop = false;
        while ( ! stop ) {
            try {
                BufferedImageSplit originalImage = buffer.poll ( 100 , TimeUnit.MILLISECONDS );
                if ( originalImage == null ) {
                    continue;
                }
                BufferedImage bufferedImage = ImageTransformer.removeReds ( originalImage.getBufferedImage ( ) );
                BufferedImageSplit bufferedImageSplit = new BufferedImageSplit ( bufferedImage , originalImage.getI ( ) , originalImage.getJ ( ) );
                resultingBuffer.add ( bufferedImageSplit );
            } catch ( InterruptedException e ) {
                e.printStackTrace ( );
            } finally {
                if ( this.nTotalImages == resultingBuffer.size ( ) ) {
                    stop = true;
                }
            }
        }
    }


}
