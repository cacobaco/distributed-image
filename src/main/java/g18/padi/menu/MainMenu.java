package g18.padi.menu;

import g18.padi.client.Client;
import g18.padi.server.Server;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class represents the main menu of the application.
 * It allows the user to see the servers and clients available.
 * It also allows the user to open the menu of a client.
 */
public class MainMenu implements IMenu {

    private final List<Server> servers;
    private final List<ClientMenu> clientMenus;
    private JFrame frame;

    /**
     * Constructs a new MainMenu instance.
     *
     * @param servers the list of servers available.
     * @param clients the list of clients available.
     */
    public MainMenu(List<Server> servers, List<Client> clients) {
        this.servers = servers;
        this.clientMenus = clients.stream().map(ClientMenu::new).toList();

        create();
    }

    /**
     * Creates the menu.
     */
    private void create() {
        frame = new JFrame("Main menu");
        frame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(0, 2));

        int rows = Math.max(servers.size(), clientMenus.size()) + 1;

        JPanel serversPanel = new JPanel(new GridLayout(rows, 1));
        JLabel serversLabel = new JLabel("Servers", SwingConstants.CENTER);
        serversPanel.add(serversLabel);

        for (Server server : servers) {
            JLabel label = new JLabel(server.getName() + ": " + server.getPort());
            serversPanel.add(label);
        }

        JPanel clientsPanel = new JPanel(new GridLayout(rows, 1));
        JLabel clientsLabel = new JLabel("Clients", SwingConstants.CENTER);
        clientsPanel.add(clientsLabel);

        for (ClientMenu clientMenu : clientMenus) {
            JButton button = new JButton("Open " + clientMenu.getClient().getName());
            clientsPanel.add(button);

            button.addActionListener(e -> clientMenu.show());
        }

        panel.add(serversPanel);
        panel.add(clientsPanel);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }

    /**
     * Shows the menu.
     */
    @Override
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Closes the menu.
     */
    @Override
    public void close() {
        frame.dispose();
    }

}
