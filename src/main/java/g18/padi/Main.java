package g18.padi;

import g18.padi.client.Client;
import g18.padi.loadbalancer.LoadBalancer;
import g18.padi.loadbalancer.manager.LoadBalancerLeastLoadManager;
import g18.padi.loadbalancer.manager.LoadBalancerManager;
import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;
import g18.padi.loadbalancer.manager.data.LoadBalancerFileDataManager;
import g18.padi.menu.MainMenu;
import g18.padi.server.Server;
import g18.padi.utils.ConfigReader;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static LoadBalancer loadBalancer;

    public static void main(String[] args) {
        initializeLoadBalancer();

        List<Server> servers = createServers();
        List<Client> clients = createClients();

        MainMenu menu = new MainMenu(servers, clients);
        menu.show();
    }

    /**
     * Initializes the load balancer.
     */
    private static void initializeLoadBalancer() {
        ILoadBalancerDataManager dataManager = new LoadBalancerFileDataManager(ConfigReader.getInstance().getLoadInfoFile());
        LoadBalancerManager manager = new LoadBalancerLeastLoadManager(dataManager);
        loadBalancer = new LoadBalancer(manager);
    }

    /**
     * Creates the servers.
     *
     * @return a list of servers
     */
    private static List<Server> createServers() {
        List<Server> servers = new ArrayList<>();

        int[] serverPorts = ConfigReader.getInstance().getServers();
        for (int serverPort : serverPorts) {
            Server server = new Server(serverPort);
            servers.add(server);
            server.start();
        }

        return servers;
    }

    /**
     * Creates the clients.
     *
     * @return a list of clients
     */
    private static List<Client> createClients() {
        List<Client> clients = new ArrayList<>();

        String[] clientNames = ConfigReader.getInstance().getClientNames();
        for (String clientName : clientNames) {
            Client client = new Client(clientName);
            clients.add(client);
        }

        return clients;
    }

    /**
     * Returns the load balancer instance.
     * If the load balancer instance does not exist, it is created.
     *
     * @return the load balancer instance.
     */
    public static LoadBalancer getLoadBalancer() {
        if (loadBalancer == null) {
            initializeLoadBalancer();
        }

        return loadBalancer;
    }

}