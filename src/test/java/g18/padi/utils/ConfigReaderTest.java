package g18.padi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfigReaderTest {

    private ConfigReader configReader;

    @BeforeEach
    void setUp() {
        try {
            configReader = ConfigReader.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test singleton instance")
    void testSingletonInstance() throws IOException {
        ConfigReader instance1 = ConfigReader.getInstance();
        ConfigReader instance2 = ConfigReader.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertEquals(instance1, instance2);
    }

    @Test
    @DisplayName("Test getClientCols()")
    void testGetClientCols() {
        assertNotNull(configReader.getClientCols());
    }

    @Test
    @DisplayName("Test getClientRows()")
    void testGetClientRows() {
        assertNotNull(configReader.getClientRows());
    }

    @Test
    @DisplayName("Test getServerMaxConnections()")
    void testGetServerMaxConnections() {
        assertNotNull(configReader.getServerMaxConnections());
    }

    @Test
    @DisplayName("Test getServerMaxProcessingCapacity()")
    void testGetServerMaxProcessingCapacity() {
        assertNotNull(configReader.getServerMaxProcessingCapacity());
    }

    @Test
    @DisplayName("Test getLoadInfoFile()")
    void testGetLoadInfoFile() {
        assertNotNull(configReader.getLoadInfoFile());
    }

    @Test
    @DisplayName("Test getServers()")
    void testGetServers() {
        assertNotNull(configReader.getServers());
    }
}
