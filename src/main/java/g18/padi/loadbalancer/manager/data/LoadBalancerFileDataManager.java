package g18.padi.loadbalancer.manager.data;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

/**
 * This class manages the load of the servers using a file.
 * The file is read and written using locks to ensure that only one thread can write to the file at a time and multiple threads can read from it.
 */
public class LoadBalancerFileDataManager implements ILoadBalancerDataManager {

    private final String filePath;
    private final ReentrantReadWriteLock lock;

    /**
     * Constructs a new LoadBalancerFileDataManager instance.
     * The file content is cleared when a new instance is created.
     *
     * @param filePath the path of the file to be used for storing server loads.
     */
    public LoadBalancerFileDataManager(String filePath) {
        this.filePath = filePath;
        this.lock = new ReentrantReadWriteLock(true);

        clearFile();
    }

    /**
     * Sets the load of a given server.
     * Only one thread can write to the file at a time.
     *
     * @param serverPort the port of the server whose load is to be set.
     * @param load       the new load of the server.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean setServerLoad(int serverPort, int load) {
        lock.writeLock().lock();

        Map<Integer, Integer> loads = getAllLoads();

        if (loads == null) {
            lock.writeLock().unlock();
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
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes the load of a given server.
     * Only one thread can write to the file at a time.
     *
     * @param serverPort the port of the server whose load is to be removed.
     * @return true if file contained the server and operation was successful, false otherwise.
     */
    @Override
    public boolean removeServerLoad(int serverPort) {
        lock.writeLock().lock();

        Map<Integer, Integer> loads = getAllLoads();

        if (loads == null) {
            lock.writeLock().unlock();
            return false;
        }

        if (!loads.containsKey(serverPort)) {
            lock.writeLock().unlock();
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
            lock.writeLock().unlock();
        }
    }

    /**
     * Returns the load of a given server.
     * Multiple threads can read from the file simultaneously.
     *
     * @param serverPort the port of the server whose load is to be returned.
     * @return the load of the server.
     */
    @Override
    public int getServerLoad(int serverPort) {
        lock.readLock().lock();

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
            lock.readLock().unlock();
        }
    }

    /**
     * Returns a map of all servers and their loads.
     * Multiple threads can read from the file simultaneously.
     *
     * @return a map where the keys are the server ports and the values are their loads or null if the file is not found.
     */
    @Override
    public Map<Integer, Integer> getAllLoads() {
        lock.readLock().lock();

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
            lock.readLock().unlock();
        }
    }

    /**
     * Clears the file content.
     * Only one thread can write to the file at a time.
     */
    private void clearFile() {
        lock.writeLock().lock();

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        } finally {
            lock.writeLock().unlock();
        }
    }

}