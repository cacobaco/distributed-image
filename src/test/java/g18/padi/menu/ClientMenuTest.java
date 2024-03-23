package g18.padi.menu;

import g18.padi.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ClientMenuTest {

    private ClientMenu clientMenu;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("TestClient");
        clientMenu = new ClientMenu(client);
    }

    @Test
    @DisplayName("Test create method")
    void testCreateMethod() {
        assertNotNull(clientMenu.getFrame());
        assertEquals("Client TestClient", clientMenu.getFrame().getTitle());

        JPanel mainPanel = (JPanel) clientMenu.getFrame().getContentPane().getComponent(0);
        assertNotNull(mainPanel);
        assertEquals(BorderLayout.class, mainPanel.getLayout().getClass());

        JPanel imagePanel = (JPanel) mainPanel.getComponent(0);
        assertNotNull(imagePanel);
        assertEquals(BoxLayout.Y_AXIS, ((BoxLayout) imagePanel.getLayout()).getAxis());

        JPanel buttonsPanel = (JPanel) mainPanel.getComponent(1);
        assertNotNull(buttonsPanel);
        assertEquals(BoxLayout.Y_AXIS, ((BoxLayout) buttonsPanel.getLayout()).getAxis());

        JButton changeImageButton = (JButton) buttonsPanel.getComponent(1);
        assertNotNull(changeImageButton);
        assertEquals("Change image", changeImageButton.getText());

        JButton removeRedButton = (JButton) buttonsPanel.getComponent(3);
        assertNotNull(removeRedButton);
        assertEquals("Remove red", removeRedButton.getText());

        JButton resetImageButton = (JButton) buttonsPanel.getComponent(5);
        assertNotNull(resetImageButton);
        assertEquals("Reset image", resetImageButton.getText());
    }

    @Test
    @DisplayName("Test show method")
    void testShowMethod() {
        clientMenu.getFrame().setVisible(false);
        clientMenu.show();
        assertTrue(clientMenu.getFrame().isVisible());
    }

    @Test
    @DisplayName("Test close method")
    void testCloseMethod() {
        clientMenu.close();
        assertFalse(clientMenu.getFrame().isVisible());
    }

    @Test
    @DisplayName("Test getClient method")
    void testGetClientMethod() {
        assertEquals(client, clientMenu.getClient());
    }
}
