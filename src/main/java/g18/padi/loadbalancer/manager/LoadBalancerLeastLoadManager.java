package g18.padi.loadbalancer.manager;


import g18.padi.loadbalancer.manager.data.ILoadBalancerDataManager;

import java.util.Map;

/**
 * This class manages the load of the servers using the least load strategy.
 */
public class LoadBalancerLeastLoadManager extends LoadBalancerManager {

    /**
     * Constructs a new LoadBalancerLeastLoadManager instance.
     *
     * @param dataManager the data manager to be used for retrieving server loads.
     */
    public LoadBalancerLeastLoadManager(ILoadBalancerDataManager dataManager) {
        super(dataManager);
    }

    /**
     * Returns the server with the least load to handle a request.
     *
     * @return the port number of the best server.
     * @throws IllegalStateException if there are no servers available.
     */
    @Override
    public int getBestServer() throws IllegalStateException {
        Map<Integer, Integer> loads = getAllLoads();

        if (loads == null || loads.isEmpty()) {
            throw new IllegalStateException("No servers available");
        }

        int bestServerPort = -1;
        int minLoad = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> entry : loads.entrySet()) {
            if (entry.getValue() < minLoad) {
                minLoad = entry.getValue();
                bestServerPort = entry.getKey();
            }
        }

        return bestServerPort;
    }

}