package g18.padi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * A simple config reader
 */
public class ConfigReader {

    private static ConfigReader instance = null;
    private static JsonNode config = null;

    /**
     * Private constructor of ConfigReader class.
     * Initializes the configuration settings by reading from the 'project.config' file.
     */
    private ConfigReader() {
        try {
            config = readConfig("project.config");
        } catch (IOException e) {
            System.out.println("Error reading configuration file: " + e.getMessage());
        }
    }

    /**
     * Returns the singleton instance of ConfigReader.
     *
     * @return the singleton instance
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Reads configuration settings from a JSON file.
     *
     * @param filePath the path to the JSON configuration file
     * @return a JsonNode containing the configuration settings
     * @throws IOException if an I/O error occurs while reading the file
     */
    public JsonNode readConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(filePath));
    }

    /**
     * Retrieves the number of columns for the client from the configuration settings.
     *
     * @return the number of client columns
     */
    public int getClientCols() {
        return config.get("client").get("cols_number").asInt();
    }

    /**
     * Retrieves the number of rows for the client from the configuration settings.
     *
     * @return the number of client rows
     */
    public int getClientRows() {
        return config.get("client").get("rows_number").asInt();
    }

    /**
     * Retrieves the maximum number of connections allowed for the server from the configuration settings.
     *
     * @return the maximum number of server connections
     */
    public int getServerMaxConnections() {
        return config.get("server").get("max_conn").asInt();
    }

    /**
     * Retrieves the maximum processing capacity allowed for the server from the configuration settings.
     *
     * @return the maximum processing capacity of the server
     */
    public int getServerMaxProcessingCapacity() {
        return config.get("server").get("max_processing_capacity").asInt();
    }

    /**
     * Retrieves the filename for the load info file from the configuration settings.
     *
     * @return the filename for the load info file
     */
    public String getLoadInfoFile() {
        return config.get("load_info_file").asText();
    }

    /**
     * Retrieves the total number of servers from the configuration settings.
     *
     * @return the total number of servers
     */
    public int[] getServers() {
        JsonNode portsNode = config.get("server").get("ports");
        int[] ports = new int[portsNode.size()];
        for (int i = 0; i < portsNode.size(); i++) {
            ports[i] = portsNode.get(i).asInt();
        }
        return ports;
    }
}
