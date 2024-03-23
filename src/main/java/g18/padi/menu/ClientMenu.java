package g18.padi.menu;

import g18.padi.client.Client;
import g18.padi.client.ClientExecutor;
import g18.padi.utils.ImageReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class represents the menu of a client.
 * It allows the client to interact with the server.
 */
public class ClientMenu implements IMenu {

    private final Client client;
    private BufferedImage image;
    private JFrame frame;

    /**
     * Constructs a new ClientMenu instance.
     *
     * @param client the client that will interact with the server.
     */
    public ClientMenu(Client client) {
        this.client = client;
        this.image = ImageReader.readImage("sample.png");

        create();
    }

    /**
     * Creates the menu.
     */
    private void create() {
        //Java Swing stuff
        frame = new JFrame("Client " + client.getName());
        frame.setSize(400, 400);
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        imagePanel.add(Box.createVerticalGlue());
        imagePanel.add(label);
        imagePanel.add(Box.createVerticalGlue());

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        // Change Image Button
        JButton changeImageButton = new JButton();
        changeImageButton.setText("Change image");
        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(changeImageButton);
        buttonsPanel.add(Box.createVerticalGlue());

        changeImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String newImagePath = fileChooser.getSelectedFile().getPath();
                image = ImageReader.readImage(newImagePath);
                icon.setImage(image);
                mainPanel.repaint();
            }
        });

        // Remove Red Button
        JButton removeRedButton = new JButton();
        removeRedButton.setText("Remove red");
        buttonsPanel.add(removeRedButton);
        buttonsPanel.add(Box.createVerticalGlue());

        removeRedButton.addActionListener(e -> {
            ClientExecutor ce = new ClientExecutor(image, client, "red");
            ce.execute();
            icon.setImage(ce.getImage());
            mainPanel.repaint();
        });

        // Remove Green Button
        JButton removeGreenButton = new JButton();
        removeGreenButton.setText("Remove green");
        buttonsPanel.add(removeGreenButton);
        buttonsPanel.add(Box.createVerticalGlue());

        removeGreenButton.addActionListener(e -> {
            ClientExecutor ce = new ClientExecutor(image, client, "green");
            ce.execute();
            icon.setImage(ce.getImage());
            mainPanel.repaint();
        });

        // Remove Blue Button
        JButton removeBlueButton = new JButton();
        removeBlueButton.setText("Remove blue");
        buttonsPanel.add(removeBlueButton);
        buttonsPanel.add(Box.createVerticalGlue());

        removeBlueButton.addActionListener(e -> {
            ClientExecutor ce = new ClientExecutor(image, client, "blue");
            ce.execute();
            icon.setImage(ce.getImage());
            mainPanel.repaint();
        });

        // Reset Image Button
        JButton resetImageButton = new JButton();
        resetImageButton.setText("Reset image");
        buttonsPanel.add(resetImageButton);
        buttonsPanel.add(Box.createVerticalGlue());

        resetImageButton.addActionListener(e -> {
            icon.setImage(image);
            mainPanel.repaint();
        });

        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(buttonsPanel, BorderLayout.EAST);

        frame.add(mainPanel);
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