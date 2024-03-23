package g18.padi.loadbalancer;

import g18.padi.loadbalancer.manager.LoadBalancerLeastLoadManager;
import g18.padi.loadbalancer.manager.LoadBalancerManager;
import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;
import g18.padi.loadbalancer.manager.data.LoadBalancerFileDataManager;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class LoadBalancerTest {

    private static final int SERVER_PORT = 8000;
    private static final int SERVER_PORT_2 = 8001;
    private static final int SERVER_PORT_3 = 8002;
    private LoadBalancer loadBalancer;

    @Nested
    @DisplayName("Test with least load manager")
    class TestWithLeastLoadManager {

        private static final String FILE_PATH = "load.info.test";

        @BeforeEach
        public void setup() {
            ILoadBalancerDataManager dataManager = new LoadBalancerFileDataManager(FILE_PATH);
            LoadBalancerManager manager = new LoadBalancerLeastLoadManager(dataManager);
            loadBalancer = new LoadBalancer(manager);
        }

        @AfterEach
        public void cleanup() {
            deleteFile();
        }

        @Test
        @DisplayName("Test get best server when no server load is set.")
        public void testGetBestServerWhenNotSet() {
            assertThrows(IllegalStateException.class, () -> {
                loadBalancer.getBestServer();
            });
        }

        @Test
        @DisplayName("Test get best server when one server load is set.")
        public void testGetBestServerWhenOneSet() {
            boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            int bestServer = loadBalancer.getBestServer();
            assertEquals(bestServer, SERVER_PORT);
        }

        @Test
        @DisplayName("Test get best server when multiple server loads are set.")
        public void testGetBestServerWhenMultipleSet() {
            boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = loadBalancer.setServerLoad(SERVER_PORT_2, 20);
            assertTrue(result);

            result = loadBalancer.setServerLoad(SERVER_PORT_3, 30);
            assertTrue(result);

            int bestServer = loadBalancer.getBestServer();
            assertEquals(bestServer, SERVER_PORT);
        }

        @Test
        @DisplayName("Test get best server after updating one.")
        public void testGetBestServerAfterUpdating() {
            boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = loadBalancer.setServerLoad(SERVER_PORT_2, 20);
            assertTrue(result);

            result = loadBalancer.setServerLoad(SERVER_PORT_3, 30);
            assertTrue(result);

            int bestServer = loadBalancer.getBestServer();
            assertEquals(bestServer, SERVER_PORT);

            result = loadBalancer.setServerLoad(SERVER_PORT, 40);
            assertTrue(result);

            bestServer = loadBalancer.getBestServer();
            assertEquals(bestServer, SERVER_PORT_2);
        }

        @Test
        @DisplayName("Test get best server after removing one.")
        public void testGetBestServerAfterRemoving() {
            boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = loadBalancer.setServerLoad(SERVER_PORT_2, 20);
            assertTrue(result);

            result = loadBalancer.setServerLoad(SERVER_PORT_3, 30);
            assertTrue(result);

            int bestServer = loadBalancer.getBestServer();
            assertEquals(bestServer, SERVER_PORT);

            result = loadBalancer.removeServerLoad(SERVER_PORT);
            assertTrue(result);

            bestServer = loadBalancer.getBestServer();
            assertEquals(bestServer, SERVER_PORT_2);
        }

        @Test
        @DisplayName("Test increment server load success.")
        public void testIncrementServerLoadSuccess() {
            boolean result = loadBalancer.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test increment server load success when server load is not set.")
        public void testIncrementServerLoadWhenNotSetSuccess() {
            boolean result = loadBalancer.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test decrement server load success.")
        public void testDecrementServerLoadSuccess() {
            boolean result = loadBalancer.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test decrement server load correct.")
        public void testDecrementServerLoadCorrect() {
            boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = loadBalancer.decrementServerLoad(SERVER_PORT, 2);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test decrement server load success when server load is not set.")
        public void testDecrementServerLoadWhenNotSetSuccess() {
            boolean result = loadBalancer.decrementServerLoad(SERVER_PORT, 2);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test set server load success.")
        public void testSetServerLoadSuccess() {
            LoadBalancerTest.this.testSetServerLoadSuccess();
        }

        @Test
        @DisplayName("Test update server load success.")
        public void testUpdateServerLoadSuccess() {
            LoadBalancerTest.this.testUpdateServerLoadSuccess();
        }

        @Test
        @DisplayName("Test remove server load when server load is not set.")
        public void testRemoveServerLoadWhenNotSet() {
            LoadBalancerTest.this.testRemoveServerLoadWhenNotSet();
        }

        @Test
        @DisplayName("Test remove server load success.")
        public void testRemoveServerLoadSuccess() {
            LoadBalancerTest.this.testRemoveServerLoadSuccess();
        }

        private void deleteFile() {
            File file = new File(FILE_PATH);
            file.delete();
        }

    }

    private void testSetServerLoadSuccess() {
        boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);
    }

    private void testUpdateServerLoadSuccess() {
        boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = loadBalancer.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);
    }

    private void testRemoveServerLoadWhenNotSet() {
        boolean result = loadBalancer.removeServerLoad(SERVER_PORT);
        assertTrue(result);
    }

    private void testRemoveServerLoadSuccess() {
        boolean result = loadBalancer.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = loadBalancer.removeServerLoad(SERVER_PORT);
        assertTrue(result);
    }

}