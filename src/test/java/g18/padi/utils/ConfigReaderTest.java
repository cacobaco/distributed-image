package g18.padi.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigReaderTest {

    private ConfigReader configReader;

    @BeforeEach
    void setUp() {
        configReader = ConfigReader.getInstance();
    }

    @Test
    @DisplayName("Test singleton instance")
    void testSingletonInstance() {
        ConfigReader instance1 = ConfigReader.getInstance();
        ConfigReader instance2 = ConfigReader.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertEquals(instance1, instance2);
    }

    @Test
    @DisplayName("Test getClientNames()")
    void testGetClientNames() {
        assertNotNull(configReader.getClientNames());
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
