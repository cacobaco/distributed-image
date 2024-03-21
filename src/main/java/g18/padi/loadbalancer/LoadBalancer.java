package g18.padi.loadbalancer;

import g18.padi.loadbalancer.manager.LoadBalancerManager;

/**
 * A class that represents a load balancer.
 */
public class LoadBalancer {

    private final LoadBalancerManager manager;

    /**
     * Constructs a new LoadBalancer instance.
     *
     * @param manager the manager to be used by the load balancer.
     */
    public LoadBalancer(LoadBalancerManager manager) {
        this.manager = manager;
    }

    /**
     * Returns the best server to handle a new request.
     * The criteria for determining the best server is defined by the manager.
     *
     * @return the port number of the best server.
     * @throws IllegalStateException if there are no servers available.
     */
    public int getBestServer() throws IllegalStateException {
        return manager.getBestServer();
    }

    /**
     * Sets the load of a given server.
     * Synchronization is handled by the manager.
     *
     * @param serverPort the port of the server whose load is to be set.
     * @param load       the new load of the server.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean setServerLoad(int serverPort, int load) {
        return manager.setServerLoad(serverPort, load);
    }

    /**
     * Removes the load of a given server.
     * Synchronization is handled by the manager.
     *
     * @param serverPort the port of the server whose load is to be removed.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean removeServerLoad(int serverPort) {
        return manager.removeServerLoad(serverPort);
    }

}