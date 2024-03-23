package g18.padi.menu;

import g18.padi.client.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientMenuTest {

    @Test
    @DisplayName("Test show method")
    void testShowMethod() {
        ClientMenu clientMenu = new ClientMenu(new Client("animal"));
        JFrame frameStub = new JFrame() {
            boolean visible = false;

            @Override
            public void setVisible(boolean visible) {
                this.visible = visible;
            }

            @Override
            public boolean isVisible() {
                return visible;
            }
        };

        clientMenu.frame = frameStub;
        clientMenu.show();

        assertTrue(frameStub.isVisible());
    }

    @Test
    @DisplayName("Test close method")
    void testCloseMethod() {
        ClientMenu clientMenu = new ClientMenu(new Client("animal"));
        JFrame frameStub = new JFrame() {
            boolean visible = true;

            @Override
            public void dispose() {
                visible = false;
            }

            @Override
            public boolean isVisible() {
                return visible;
            }
        };

        clientMenu.frame = frameStub;
        clientMenu.close();

        assertFalse(frameStub.isVisible());
    }
}
