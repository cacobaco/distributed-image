package g18.padi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class ConfigReader {

    /**
     * Reads configuration settings from a JSON file.
     *
     * @param filePath   the path to the JSON configuration file
     * @return           a JsonNode containing the configuration settings
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static JsonNode readConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(filePath));
    }

    /**
     * Retrieves the number of columns for the client from the configuration settings.
     *
     * @param config     the JsonNode containing the configuration settings
     * @return           the number of client columns
     */
    public static int getClientCols(JsonNode config) {
        return config.get("client").get("cols_number").asInt();
    }

    /**
     * Retrieves the number of rows for the client from the configuration settings.
     *
     * @param config     the JsonNode containing the configuration settings
     * @return           the number of client rows
     */
    public static int getClientRows(JsonNode config) {
        return config.get("client").get("rows_number").asInt();
    }

    /**
     * Retrieves the maximum number of connections allowed for the server from the configuration settings.
     *
     * @param config     the JsonNode containing the configuration settings
     * @return           the maximum number of server connections
     */
    public static int getServerMaxConnections(JsonNode config) {
        return config.get("server").get("max_conn").asInt();
    }

    /**
     * Retrieves the maximum processing capacity allowed for the server from the configuration settings.
     *
     * @param config     the JsonNode containing the configuration settings
     * @return           the maximum processing capacity of the server
     */
    public static int getServerMaxProcessingCapacity(JsonNode config) {
        return config.get("server").get("max_processing_capacity").asInt();
    }

    /**
     * Retrieves the filename for the load info file from the configuration settings.
     *
     * @param config     the JsonNode containing the configuration settings
     * @return           the filename for the load info file
     */
    public static String getLoadInfoFile(JsonNode config) {
        return config.get("load_info_file").asText();
    }

    /**
     * Retrieves the total number of servers from the configuration settings.
     *
     * @param config     the JsonNode containing the configuration settings
     * @return           the total number of servers
     */
    public static int getTotalServers(JsonNode config) {
        return config.get("num_of_servers").asInt();
    }
}
