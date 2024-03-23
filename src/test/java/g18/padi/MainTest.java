package g18.padi;

import g18.padi.client.Client;
import g18.padi.menu.MainMenu;
import g18.padi.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MainTest {

    private Main main;

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    @DisplayName("Test createServers()")
    void testCreateServers() {
        List<Server> servers = main.createServers();

        assertNotNull(servers);
        for (Server server : servers) {
            assertNotNull(server);
        }
    }

    @Test
    @DisplayName("Test createClients()")
    void testCreateClients() {
        List<Client> clients = main.createClients();

        assertNotNull(clients);
        for (Client client : clients) {
            assertNotNull(client);
        }
    }

    @Test
    @DisplayName("Test main method indirectly")
    void testMainMethod() {
        // This test ensures that the main method executes without throwing any exceptions
        Main.main(new String[]{});
    }
}
