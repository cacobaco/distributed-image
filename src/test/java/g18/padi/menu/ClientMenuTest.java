package g18.padi.menu;

import g18.padi.client.Client;
import g18.padi.utils.*;
import g18.padi.menu.ClientMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.mockito.Mockito.*;

class ClientMenuTest {

    @Mock
    private Client clientMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test show method")
    void testShowMethod() {
        ClientMenu clientMenu = new ClientMenu(clientMock);
        JFrame frameMock = mock(JFrame.class);
        when(frameMock.isVisible()).thenReturn(false);
        clientMenu.frame = frameMock;
        clientMenu.show();
        verify(frameMock, times(1)).setVisible(true);
    }

    @Test
    @DisplayName("Test close method")
    void testCloseMethod() {
        ClientMenu clientMenu = new ClientMenu(clientMock);
        JFrame frameMock = mock(JFrame.class);
        when(frameMock.isVisible()).thenReturn(true);
        clientMenu.frame = frameMock;
        clientMenu.close();
        verify(frameMock, times(1)).dispose();
    }
}
