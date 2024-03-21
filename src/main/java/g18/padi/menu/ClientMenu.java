package g18.padi.menu;

import g18.padi.client.Client;
import g18.padi.utils.ImageReader;
import g18.padi.utils.ImageTransformer;
import g18.padi.utils.Request;
import g18.padi.utils.Response;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * This class represents the menu of a client.
 * It allows the client to interact with the server.
 */
public class ClientMenu implements IMenu {

    private final Client client;
    private JFrame frame;

    /**
     * Constructs a new ClientMenu instance.
     *
     * @param client the client that will interact with the server.
     */
    public ClientMenu(Client client) {
        this.client = client;

        create();
    }

    /**
     * Creates the menu.
     */
    private void create() {
        BufferedImage sampleImage = ImageReader.readImage("sample.png");

        //Java Swing stuff
        frame = new JFrame("Client " + client.getName());
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        ImageIcon icon = new ImageIcon(sampleImage);
        JLabel label = new JLabel(icon);
        panel.add(label);

        JButton button = new JButton();
        button.setText("Remove red");
        panel.add(button);

        button.addActionListener(e -> {
            Request request = new Request("greeting", "Hello server! I'm client " + client.getName() + "!", sampleImage);
            int port = 8000; // TODO: Use Load Balancer to get the best server's port
            Response response = client.sendRequestAndReceiveResponse("localhost", port, request);
            icon.setImage(ImageTransformer.createImageFromBytes(response.getImageSection()));
            panel.repaint();
        });

        JButton resetButton = new JButton();
        resetButton.setText("Reset image");
        panel.add(resetButton);

        resetButton.addActionListener(e -> {
            icon.setImage(sampleImage);
            panel.repaint();
        });

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    /**
     * Gets the client.
     *
     * @return the client.
     */
    public Client getClient() {
        return client;
    }

}
