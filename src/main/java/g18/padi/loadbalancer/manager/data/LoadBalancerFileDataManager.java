package g18.padi.loadbalancer.manager.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class manages the load of the servers using a file.
 * Only one thread can write/read to/from the file at a time.
 */
public class LoadBalancerFileDataManager implements ILoadBalancerDataManager {

    private final String filePath;
    private final ReentrantLock lock;

    /**
     * Constructs a new LoadBalancerFileDataManager instance.
     * The file content is cleared when a new instance is created.
     *
     * @param filePath the path of the file to be used for storing server loads.
     */
    public LoadBalancerFileDataManager(String filePath) {
        this.filePath = filePath;
        this.lock = new ReentrantLock(true);

        clearFile();
    }

    /**
     * Sets the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be set.
     * @param load       the new load of the server.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean setServerLoad(int serverPort, int load) {
        lock.lock();

        Map<Integer, Integer> loads = getAllLoads();

        if (loads == null) {
            lock.unlock();
            return false;
        }

        loads.put(serverPort, load);

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Map.Entry<Integer, Integer> entry : loads.entrySet()) {
                String line = String.format("%d %d\n", entry.getKey(), entry.getValue());
                writer.write(line);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error setting server load: " + e.getMessage());
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Increments the load of a given server.
     * The load is incremented by the given amount.
     * If the load is not set, it is set to 0 plus the increment value.
     *
     * @param serverPort the port of the server whose load is to be incremented.
     * @param increment  the amount by which the load is to be incremented.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean incrementServerLoad(int serverPort, int increment) {
        lock.lock();

        int currentLoad = getServerLoad(serverPort);
        int newLoad = currentLoad + increment;

        boolean success = setServerLoad(serverPort, newLoad);

        lock.unlock();
        return success;
    }

    /**
     * Decrements the load of a given server.
     * The load is decremented by the given amount.
     * If the load is not set, it is set to 0 minus the decrement value.
     *
     * @param serverPort the port of the server whose load is to be decremented.
     * @param decrement  the amount by which the load is to be decremented.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean decrementServerLoad(int serverPort, int decrement) {
        lock.lock();

        int currentLoad = getServerLoad(serverPort);
        int newLoad = currentLoad - decrement;

        boolean success = setServerLoad(serverPort, newLoad);

        lock.unlock();
        return success;
    }

    /**
     * Removes the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be removed.
     * @return true if file contained the server and operation was successful, false otherwise.
     */
    @Override
    public boolean removeServerLoad(int serverPort) {
        lock.lock();

        Map<Integer, Integer> loads = getAllLoads();

        if (loads == null) {
            lock.unlock();
            return false;
        }

        if (!loads.containsKey(serverPort)) {
            lock.unlock();
            return true;
        }

        loads.remove(serverPort);

        try (FileWriter writer = new FileWriter(filePath)) {
            for (Map.Entry<Integer, Integer> entry : loads.entrySet()) {
                String line = String.format("%d %d\n", entry.getKey(), entry.getValue());
                writer.write(line);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error removing server load: " + e.getMessage());
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the load of a given server.
     *
     * @param serverPort the port of the server whose load is to be returned.
     * @return the load of the server.
     */
    @Override
    public int getServerLoad(int serverPort) {
        lock.lock();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextInt()) {
                int port = scanner.nextInt();
                int load = scanner.nextInt();

                if (port == serverPort) {
                    scanner.close();
                    return load;
                }
            }

            scanner.close();
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("Error getting server load: " + e.getMessage());
            return 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a map of all servers and their loads.
     *
     * @return a map where the keys are the server ports and the values are their loads or null if the file is not found.
     */
    @Override
    public Map<Integer, Integer> getAllLoads() {
        lock.lock();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            Map<Integer, Integer> loads = new HashMap<>();

            while (scanner.hasNextInt()) {
                int serverPort = scanner.nextInt();
                int load = scanner.nextInt();
                loads.put(serverPort, load);
            }

            scanner.close();
            return loads;
        } catch (FileNotFoundException e) {
            System.out.println("Error getting all loads: " + e.getMessage());
            return null;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Clears the file content.
     */
    private void clearFile() {
        lock.lock();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

}