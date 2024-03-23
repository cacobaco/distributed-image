package g18.padi.loadbalancer.manager;

import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;
import g18.padi.loadbalancer.manager.data.LoadBalancerFileDataManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LoadBalancerManagerTest {

    private static final int SERVER_PORT = 8000;
    private static final int SERVER_PORT_2 = 8001;
    private static final int SERVER_PORT_3 = 8002;
    private LoadBalancerManager manager;

    @Nested
    @DisplayName("Test least load manager")
    class TestWithLeastLoadManager {

        private static final String FILE_PATH = "load.info.test";

        @BeforeEach
        public void setup() {
            ILoadBalancerDataManager dataManager = new LoadBalancerFileDataManager(FILE_PATH);
            manager = new LoadBalancerLeastLoadManager(dataManager);
        }

        @AfterEach
        public void cleanup() {
            deleteFile();
        }

        @Test
        @DisplayName("Test get best server when no server load is set.")
        public void testGetBestServerWhenNotSet() {
            assertThrows(IllegalStateException.class, () -> {
                manager.getBestServer();
            });
        }

        @Test
        @DisplayName("Test get best server when one server load is set.")
        public void testGetBestServerWhenOneSet() {
            boolean result = manager.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            int bestServer = manager.getBestServer();
            assertEquals(bestServer, SERVER_PORT);
        }

        @Test
        @DisplayName("Test get best server when multiple server loads are set.")
        public void testGetBestServerWhenMultipleSet() {
            boolean result = manager.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = manager.setServerLoad(SERVER_PORT_2, 20);
            assertTrue(result);

            result = manager.setServerLoad(SERVER_PORT_3, 30);
            assertTrue(result);

            int bestServer = manager.getBestServer();
            assertEquals(bestServer, SERVER_PORT);
        }

        @Test
        @DisplayName("Test get best server after updating one.")
        public void testGetBestServerAfterUpdating() {
            boolean result = manager.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = manager.setServerLoad(SERVER_PORT_2, 20);
            assertTrue(result);

            result = manager.setServerLoad(SERVER_PORT_3, 30);
            assertTrue(result);

            int bestServer = manager.getBestServer();
            assertEquals(bestServer, SERVER_PORT);

            result = manager.setServerLoad(SERVER_PORT, 40);
            assertTrue(result);

            bestServer = manager.getBestServer();
            assertEquals(bestServer, SERVER_PORT_2);
        }

        @Test
        @DisplayName("Test get best server after removing one.")
        public void testGetBestServerAfterRemoving() {
            boolean result = manager.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = manager.setServerLoad(SERVER_PORT_2, 20);
            assertTrue(result);

            result = manager.setServerLoad(SERVER_PORT_3, 30);
            assertTrue(result);

            int bestServer = manager.getBestServer();
            assertEquals(bestServer, SERVER_PORT);

            result = manager.removeServerLoad(SERVER_PORT);
            assertTrue(result);

            bestServer = manager.getBestServer();
            assertEquals(bestServer, SERVER_PORT_2);
        }

        @Test
        @DisplayName("Test increment server load success.")
        public void testIncrementServerLoadSuccess() {
            boolean result = manager.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test increment server load correct.")
        public void testIncrementServerLoadCorrect() {
            boolean result = manager.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = manager.incrementServerLoad(SERVER_PORT, 2);
            assertTrue(result);

            int load = manager.getServerLoad(SERVER_PORT);
            assertEquals(load, 12);
        }

        @Test
        @DisplayName("Test increment server load success when server load is not set.")
        public void testIncrementServerLoadWhenNotSetSuccess() {
            boolean result = manager.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test increment server load correct when server load is not set.")
        public void testIncrementServerLoadWhenNotSetCorrect() {
            boolean result = manager.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);

            int load = manager.getServerLoad(SERVER_PORT);
            assertEquals(load, 5);
        }

        @Test
        @DisplayName("Test decrement server load success.")
        public void testDecrementServerLoadSuccess() {
            boolean result = manager.incrementServerLoad(SERVER_PORT, 5);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test decrement server load correct.")
        public void testDecrementServerLoadCorrect() {
            boolean result = manager.setServerLoad(SERVER_PORT, 10);
            assertTrue(result);

            result = manager.decrementServerLoad(SERVER_PORT, 2);
            assertTrue(result);

            int load = manager.getServerLoad(SERVER_PORT);
            assertEquals(load, 8);
        }

        @Test
        @DisplayName("Test decrement server load success when server load is not set.")
        public void testDecrementServerLoadWhenNotSetSuccess() {
            boolean result = manager.decrementServerLoad(SERVER_PORT, 2);
            assertTrue(result);
        }

        @Test
        @DisplayName("Test decrement server load correct when server load is not set.")
        public void testDecrementServerLoadWhenNotSetCorrect() {
            boolean result = manager.decrementServerLoad(SERVER_PORT, 2);
            assertTrue(result);

            int load = manager.getServerLoad(SERVER_PORT);
            assertEquals(load, -2);
        }

        @Test
        @DisplayName("Test set server load success.")
        public void testSetServerLoadSuccess() {
            LoadBalancerManagerTest.this.testSetServerLoadSuccess();
        }

        @Test
        @DisplayName("Test set server load correct.")
        public void testSetServerLoadCorrect() {
            LoadBalancerManagerTest.this.testSetServerLoadCorrect();
        }

        @Test
        @DisplayName("Test update server load success.")
        public void testUpdateServerLoadSuccess() {
            LoadBalancerManagerTest.this.testUpdateServerLoadSuccess();
        }

        @Test
        @DisplayName("Test update server load correct.")
        public void testUpdateServerLoadCorrect() {
            LoadBalancerManagerTest.this.testUpdateServerLoadCorrect();
        }

        @Test
        @DisplayName("Test remove server load when server load is not set.")
        public void testRemoveServerLoadWhenNotSet() {
            LoadBalancerManagerTest.this.testRemoveServerLoadWhenNotSet();
        }

        @Test
        @DisplayName("Test remove server load success.")
        public void testRemoveServerLoadSuccess() {
            LoadBalancerManagerTest.this.testRemoveServerLoadSuccess();
        }

        @Test
        @DisplayName("Test remove server load correct.")
        public void testRemoveServerLoadCorrect() {
            LoadBalancerManagerTest.this.testRemoveServerLoadCorrect();
        }

        @Test
        @DisplayName("Test get server load when server load is not set.")
        public void testGetServerLoadWhenNotSet() {
            LoadBalancerManagerTest.this.testGetServerLoadWhenNotSet();
        }

        @Test
        @DisplayName("Test get server load success.")
        public void testGetServerLoadSuccess() {
            LoadBalancerManagerTest.this.testGetServerLoadSuccess();
        }

        @Test
        @DisplayName("Test get server load after setting it.")
        public void testGetServerLoadAfterSetting() {
            LoadBalancerManagerTest.this.testGetServerLoadAfterSetting();
        }

        @Test
        @DisplayName("Test get server load after updating it.")
        public void testGetServerLoadAfterUpdating() {
            LoadBalancerManagerTest.this.testGetServerLoadAfterUpdating();
        }

        @Test
        @DisplayName("Test get server load after removing it.")
        public void testGetServerLoadAfterRemoving() {
            LoadBalancerManagerTest.this.testGetServerLoadAfterRemoving();
        }

        @Test
        @DisplayName("Test get all server loads when no server load is set.")
        public void testGetAllLoadsWhenNotSet() {
            LoadBalancerManagerTest.this.testGetAllLoadsWhenNotSet();
        }

        @Test
        @DisplayName("Test get all server loads when one server load is set.")
        public void testGetAllLoadsWhenOneSet() {
            LoadBalancerManagerTest.this.testGetAllLoadsWhenOneSet();
        }

        @Test
        @DisplayName("Test get all server loads when multiple server loads are set.")
        public void testGetAllLoadsWhenMultipleSet() {
            LoadBalancerManagerTest.this.testGetAllLoadsWhenMultipleSet();
        }

        @Test
        @DisplayName("Test get all server loads after updating one.")
        public void testGetAllLoadsAfterUpdating() {
            LoadBalancerManagerTest.this.testGetAllLoadsAfterUpdating();
        }

        @Test
        @DisplayName("Test get all server loads after removing one.")
        public void testGetAllLoadsAfterRemoving() {
            LoadBalancerManagerTest.this.testGetAllLoadsAfterRemoving();
        }

        private void deleteFile() {
            File file = new File(FILE_PATH);
            file.delete();
        }

    }

    private void testSetServerLoadSuccess() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);
    }

    private void testSetServerLoadCorrect() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);
    }

    private void testUpdateServerLoadSuccess() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);
    }

    private void testUpdateServerLoadCorrect() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = manager.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);

        load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 20);
    }

    private void testRemoveServerLoadWhenNotSet() {
        boolean result = manager.removeServerLoad(SERVER_PORT);
        assertTrue(result);
    }

    private void testRemoveServerLoadSuccess() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = manager.removeServerLoad(SERVER_PORT);
        assertTrue(result);
    }

    private void testRemoveServerLoadCorrect() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = manager.removeServerLoad(SERVER_PORT);
        assertTrue(result);

        load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 0);
    }

    private void testGetServerLoadWhenNotSet() {
        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 0);
    }

    private void testGetServerLoadSuccess() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);
    }

    private void testGetServerLoadAfterSetting() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);
    }

    private void testGetServerLoadAfterUpdating() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = manager.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);

        load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 20);
    }

    private void testGetServerLoadAfterRemoving() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = manager.removeServerLoad(SERVER_PORT);
        assertTrue(result);

        load = manager.getServerLoad(SERVER_PORT);
        assertEquals(load, 0);
    }

    private void testGetAllLoadsWhenNotSet() {
        Map<Integer, Integer> loads = manager.getAllLoads();
        assertEquals(loads.size(), 0);
    }

    private void testGetAllLoadsWhenOneSet() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        Map<Integer, Integer> loads = manager.getAllLoads();
        assertEquals(loads.size(), 1);
        assertEquals(loads.get(SERVER_PORT), 10);
    }

    private void testGetAllLoadsWhenMultipleSet() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT_2, 20);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT_3, 30);
        assertTrue(result);

        Map<Integer, Integer> loads = manager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 10);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);
    }

    private void testGetAllLoadsAfterUpdating() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT_2, 20);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT_3, 30);
        assertTrue(result);

        Map<Integer, Integer> loads = manager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 10);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);

        result = manager.setServerLoad(SERVER_PORT, 40);
        assertTrue(result);

        loads = manager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 40);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);
    }

    private void testGetAllLoadsAfterRemoving() {
        boolean result = manager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT_2, 20);
        assertTrue(result);

        result = manager.setServerLoad(SERVER_PORT_3, 30);
        assertTrue(result);

        Map<Integer, Integer> loads = manager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 10);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);

        result = manager.removeServerLoad(SERVER_PORT);
        assertTrue(result);

        loads = manager.getAllLoads();
        assertEquals(loads.size(), 2);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);
    }

}