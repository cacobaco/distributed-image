package g18.padi.client;

import g18.padi.utils.Request;
import g18.padi.utils.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple TCP/IP client that connects to a server, sends an object, and receives a response.
 */
public class Client {

    // The name of the client
    private final String name;

    /**
     * Constructs a new client with the specified name.
     *
     * @param name The name of the client.
     */
    public Client(String name) {
        this.name = name;
    }

    public Socket sendRequest(String host, int port, Request request) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Connecting to server at " + host + ":" + port);
            System.out.println("Sending: " + request);
            out.writeObject(request);
            return socket;
        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
        return null;
    }

    public Response receiveResponse(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Response response = (Response) in.readObject();
            System.out.println("Received: " + response);
            return response;
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns the name of the client.
     *
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }

}