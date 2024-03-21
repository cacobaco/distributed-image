package g18.padi;

import g18.padi.client.Client;
import g18.padi.loadbalancer.LoadBalancer;
import g18.padi.loadbalancer.manager.LoadBalancerLeastLoadManager;
import g18.padi.loadbalancer.manager.LoadBalancerManager;
import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;
import g18.padi.loadbalancer.manager.data.LoadBalancerFileDataManager;
import g18.padi.menu.MainMenu;
import g18.padi.server.Server;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int FIRST_SERVER_PORT = 8000;
    private static final int SERVER_COUNT = 2;
    private static final int CLIENT_COUNT = 5;

    private static final String LOAD_BALANCER_FILE_PATH = "load.info";
    private static LoadBalancer loadBalancer;

    public static void main(String[] args) {
        List<Server> servers = createServers();
        List<Client> clients = createClients();

        MainMenu menu = new MainMenu(servers, clients);
        menu.show();
    }

    /**
     * Creates the servers.
     *
     * @return a list of servers
     */
    private static List<Server> createServers() {
        List<Server> servers = new ArrayList<>();

        for (int i = 0; i < SERVER_COUNT; i++) {
            Server server = new Server(FIRST_SERVER_PORT + i);
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

        for (int i = 0; i < CLIENT_COUNT; i++) {
            int clientId = i + 1;
            Client client = new Client("Client " + (clientId));
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
            ILoadBalancerDataManager dataManager = new LoadBalancerFileDataManager(LOAD_BALANCER_FILE_PATH);
            LoadBalancerManager manager = new LoadBalancerLeastLoadManager(dataManager);
            loadBalancer = new LoadBalancer(manager);
        }

        return loadBalancer;
    }

}