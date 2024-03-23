package g18.padi.loadbalancer.manager.data;

import java.util.Map;

/**
 * This interface defines the contract for a Load Balancer Data Manager.
 * A Load Balancer Load Manager is responsible for managing the load data of servers.
 */
public interface ILoadBalancerDataManager {

    /**
     * Sets the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be set.
     * @param load       the new load of the server.
     * @return true if the operation was successful, false otherwise.
     */
    boolean setServerLoad(int serverPort, int load);

    /**
     * Increments the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be incremented.
     * @param increment  the amount by which the load is to be incremented.
     * @return true if the operation was successful, false otherwise.
     */
    boolean incrementServerLoad(int serverPort, int increment);

    /**
     * Decrements the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be decremented.
     * @param decrement  the amount by which the load is to be decremented.
     * @return true if the operation was successful, false otherwise.
     */
    boolean decrementServerLoad(int serverPort, int decrement);

    /**
     * Removes the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be removed.
     * @return true if the operation was successful, false otherwise.
     */
    boolean removeServerLoad(int serverPort);

    /**
     * Returns the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be returned.
     * @return the load of the server.
     */
    int getServerLoad(int serverPort);

    /**
     * Returns a map of all servers and their loads.
     *
     * @return a map where the keys are the server ports and the values are their loads.
     */
    Map<Integer, Integer> getAllLoads();

}