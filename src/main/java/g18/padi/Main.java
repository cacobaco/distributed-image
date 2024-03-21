package g18.padi;

import g18.padi.client.Client;
import g18.padi.loadbalancer.LoadBalancer;
import g18.padi.loadbalancer.manager.LoadBalancerLeastLoadManager;
import g18.padi.loadbalancer.manager.LoadBalancerManager;
import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;
import g18.padi.loadbalancer.manager.data.LoadBalancerFileDataManager;
import g18.padi.server.Server;
import g18.padi.utils.ImageReader;
import g18.padi.utils.ImageTransformer;
import g18.padi.utils.Request;
import g18.padi.utils.Response;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main {

    private static final String LOAD_BALANCER_FILE_PATH = "load.info";
    private static LoadBalancer loadBalancer;

    public static void main(String[] args) {
        Server server = new Server(8888);
        server.start();

        BufferedImage sampleImage = ImageReader.readImage("sample.png");

        //Java Swing stuff
        JFrame frame = new JFrame("pa-distributed-img");
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        ImageIcon icon = new ImageIcon(sampleImage);
        JLabel label = new JLabel(icon);
        panel.add(label);

        JButton button = new JButton();
        button.setText("Remove red");
        panel.add(button);

        button.addActionListener(e -> {
            Client client = new Client("Client A");
            Request request = new Request("greeting", "Hello, Server!", sampleImage);
            Response response = client.sendRequestAndReceiveResponse("localhost", 8888, request);
            icon.setImage(ImageTransformer.createImageFromBytes(response.getImageSection()));
            panel.repaint();
        });

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Returns the load balancer instance.
     * If the load balancer instance does not exist, it is created.
     *
     * @return the load balancer instance.
     */
    public static LoadBalancer getLoadBalancer() {
        if (loadBalancer == null) {
            ILoadBalancerDataManager dataManager = new LoadBalancerFileDataManager(LOAD_BALANCER_FILE_PATH);
            LoadBalancerManager manager = new LoadBalancerLeastLoadManager(dataManager);
            loadBalancer = new LoadBalancer(manager);
        }

        return loadBalancer;
    }

}