package g18.padi.loadbalancer.manager.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadBalancerFileDataManagerTest {

    private static final String FILE_PATH = "load.info.test";
    private static final int SERVER_PORT = 8000;
    private static final int SERVER_PORT_2 = 8001;
    private static final int SERVER_PORT_3 = 8002;
    private LoadBalancerFileDataManager dataManager;

    @BeforeEach
    public void setup() {
        dataManager = new LoadBalancerFileDataManager(FILE_PATH);
    }

    @AfterEach
    public void cleanup() {
        deleteFile();
    }

    @Test
    @DisplayName("Test set server load success.")
    public void testSetServerLoadSuccess() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test set server load correct.")
    public void testSetServerLoadCorrect() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);
    }

    @Test
    @DisplayName("Test increment server load success.")
    public void testIncrementServerLoadSuccess() {
        boolean result = dataManager.incrementServerLoad(SERVER_PORT, 5);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test increment server load correct.")
    public void testIncrementServerLoadCorrect() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.incrementServerLoad(SERVER_PORT, 2);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 12);
    }

    @Test
    @DisplayName("Test increment server load success when server load is not set.")
    public void testIncrementServerLoadWhenNotSetSuccess() {
        boolean result = dataManager.incrementServerLoad(SERVER_PORT, 5);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test increment server load correct when server load is not set.")
    public void testIncrementServerLoadWhenNotSetCorrect() {
        boolean result = dataManager.incrementServerLoad(SERVER_PORT, 5);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 5);
    }

    @Test
    @DisplayName("Test decrement server load success.")
    public void testDecrementServerLoadSuccess() {
        boolean result = dataManager.incrementServerLoad(SERVER_PORT, 5);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test decrement server load correct.")
    public void testDecrementServerLoadCorrect() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.decrementServerLoad(SERVER_PORT, 2);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 8);
    }

    @Test
    @DisplayName("Test decrement server load success when server load is not set.")
    public void testDecrementServerLoadWhenNotSetSuccess() {
        boolean result = dataManager.decrementServerLoad(SERVER_PORT, 2);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test decrement server load correct when server load is not set.")
    public void testDecrementServerLoadWhenNotSetCorrect() {
        boolean result = dataManager.decrementServerLoad(SERVER_PORT, 2);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, -2);
    }

    @Test
    @DisplayName("Test update server load success.")
    public void testUpdateServerLoadSuccess() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test update server load correct.")
    public void testUpdateServerLoadCorrect() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = dataManager.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);

        load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 20);
    }

    @Test
    @DisplayName("Test remove server load when server load is not set.")
    public void testRemoveServerLoadWhenNotSet() {
        boolean result = dataManager.removeServerLoad(SERVER_PORT);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test remove server load success.")
    public void testRemoveServerLoadSuccess() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.removeServerLoad(SERVER_PORT);
        assertTrue(result);
    }

    @Test
    @DisplayName("Test remove server load correct.")
    public void testRemoveServerLoadCorrect() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = dataManager.removeServerLoad(SERVER_PORT);
        assertTrue(result);

        load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 0);
    }

    @Test
    @DisplayName("Test get server load when server load is not set.")
    public void testGetServerLoadWhenNotSet() {
        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 0);
    }

    @Test
    @DisplayName("Test get server load success.")
    public void testGetServerLoadSuccess() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);
    }

    @Test
    @DisplayName("Test get server load after setting it.")
    public void testGetServerLoadAfterSetting() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);
    }

    @Test
    @DisplayName("Test get server load after updating it.")
    public void testGetServerLoadAfterUpdating() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = dataManager.setServerLoad(SERVER_PORT, 20);
        assertTrue(result);

        load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 20);
    }

    @Test
    @DisplayName("Test get server load after removing it.")
    public void testGetServerLoadAfterRemoving() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        int load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 10);

        result = dataManager.removeServerLoad(SERVER_PORT);
        assertTrue(result);

        load = dataManager.getServerLoad(SERVER_PORT);
        assertEquals(load, 0);
    }

    @Test
    @DisplayName("Test get all server loads when no server load is set.")
    public void testGetAllLoadsWhenNotSet() {
        Map<Integer, Integer> loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 0);
    }

    @Test
    @DisplayName("Test get all server loads when one server load is set.")
    public void testGetAllLoadsWhenOneSet() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        Map<Integer, Integer> loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 1);
        assertEquals(loads.get(SERVER_PORT), 10);
    }

    @Test
    @DisplayName("Test get all server loads when multiple server loads are set.")
    public void testGetAllLoadsWhenMultipleSet() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT_2, 20);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT_3, 30);
        assertTrue(result);

        Map<Integer, Integer> loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 10);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);
    }

    @Test
    @DisplayName("Test get all server loads after updating one.")
    public void testGetAllLoadsAfterUpdating() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT_2, 20);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT_3, 30);
        assertTrue(result);

        Map<Integer, Integer> loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 10);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);

        result = dataManager.setServerLoad(SERVER_PORT, 40);
        assertTrue(result);

        loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 40);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);
    }

    @Test
    @DisplayName("Test get all server loads after removing one.")
    public void testGetAllLoadsAfterRemoving() {
        boolean result = dataManager.setServerLoad(SERVER_PORT, 10);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT_2, 20);
        assertTrue(result);

        result = dataManager.setServerLoad(SERVER_PORT_3, 30);
        assertTrue(result);

        Map<Integer, Integer> loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 3);
        assertEquals(loads.get(SERVER_PORT), 10);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);

        result = dataManager.removeServerLoad(SERVER_PORT);
        assertTrue(result);

        loads = dataManager.getAllLoads();
        assertEquals(loads.size(), 2);
        assertEquals(loads.get(SERVER_PORT_2), 20);
        assertEquals(loads.get(SERVER_PORT_3), 30);
    }

    private void deleteFile() {
        File file = new File(FILE_PATH);
        file.delete();
    }

}