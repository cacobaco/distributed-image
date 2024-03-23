package g18.padi.menu;

import g18.padi.client.Client;
import g18.padi.client.ClientExecutor;
import g18.padi.utils.ImageReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the menu of a client.
 * It allows the client to interact with the server.
 */
public class ClientMenu implements IMenu {

    private final Client client;
    private String imagePath;
    private BufferedImage image;
    private JFrame frame;

    /**
     * Constructs a new ClientMenu instance.
     *
     * @param client the client that will interact with the server.
     */
    public ClientMenu(Client client) {
        this.client = client;
        this.imagePath = "sample.png";
        this.image = ImageReader.readImage(imagePath);

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
                imagePath = fileChooser.getSelectedFile().getPath();
                image = ImageReader.readImage(imagePath);

                if (image == null) {
                    JOptionPane.showMessageDialog(null, "Invalid image file", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                icon.setImage(image);
                mainPanel.repaint();
            }
        });

        // Remove Colors Button
        JButton removeColorsButton = new JButton();
        removeColorsButton.setText("Remove colors");
        buttonsPanel.add(removeColorsButton);
        buttonsPanel.add(Box.createVerticalGlue());

        removeColorsButton.addActionListener(e -> {
            String[] colors = {"red", "green", "blue"};

            JPanel al = new JPanel();
            for (String color : colors) {
                JCheckBox box = new JCheckBox(color);
                al.add(box);
            }
            int option = JOptionPane.showConfirmDialog(null, al);

            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            List<String> selectedColors = new ArrayList<>();
            for (Component component : al.getComponents()) {
                JCheckBox box = (JCheckBox) component;
                if (box.isSelected()) {
                    selectedColors.add(box.getText());
                }
            }

            if (selectedColors.isEmpty()) {
                return;
            }

            // Send the request to the server
            ClientExecutor executor = new ClientExecutor(image, imagePath, client, selectedColors.toArray(new String[0]));
            executor.execute();

            // Update the image
            BufferedImage resultImage = executor.getImage();
            icon.setImage(resultImage);
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