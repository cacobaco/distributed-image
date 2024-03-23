package g18.padi.loadbalancer.manager;

import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;

import java.util.Map;

/**
 * This abstract class defines the contract for a Load Balancer Manager.
 * The Load Balancer Manager is responsible for managing the load of servers and determining the best server to handle a request.
 */
public abstract class LoadBalancerManager {

    private final ILoadBalancerDataManager dataManager;

    /**
     * Constructs a new LoadBalancerManager instance.
     *
     * @param dataManager the data manager to be used for retrieving server loads.
     */
    public LoadBalancerManager(ILoadBalancerDataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Returns the best server to handle a request.
     * The criteria for determining the best server is implementation-specific.
     *
     * @return the port number of the best server.
     * @throws IllegalStateException if there are no servers available.
     */
    public abstract int getBestServer() throws IllegalStateException;

    /**
     * Sets the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be set.
     * @param load       the new load of the server.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean setServerLoad(int serverPort, int load) {
        return dataManager.setServerLoad(serverPort, load);
    }

    /**
     * Increments the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be incremented.
     * @param increment  the amount by which the load is to be incremented.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean incrementServerLoad(int serverPort, int increment) {
        return dataManager.incrementServerLoad(serverPort, increment);
    }

    /**
     * Decrements the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be decremented.
     * @param decrement  the amount by which the load is to be decremented.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean decrementServerLoad(int serverPort, int decrement) {
        return dataManager.decrementServerLoad(serverPort, decrement);
    }

    /**
     * Removes the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be removed.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean removeServerLoad(int serverPort) {
        return dataManager.removeServerLoad(serverPort);
    }

    /**
     * Returns the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be returned.
     * @return the load of the server.
     */
    public int getServerLoad(int serverPort) {
        return dataManager.getServerLoad(serverPort);
    }

    /**
     * Returns a map of all servers and their loads.
     *
     * @return a map where the keys are the server ports and the values are their loads.
     */
    public Map<Integer, Integer> getAllLoads() {
        return dataManager.getAllLoads();
    }

    /**
     * Returns the data manager.
     *
     * @return the data manager.
     */
    protected ILoadBalancerDataManager getDataManager() {
        return dataManager;
    }

}
