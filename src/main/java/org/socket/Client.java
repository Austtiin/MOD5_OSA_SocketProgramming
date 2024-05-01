package org.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket = null;
    private BufferedReader userInput = null;
    private BufferedReader in = null;
    private DataOutputStream out = null;

    public Client(String address, int port) {
        try {
            // Connect to the server
            socket = new Socket(address, port);
            System.out.println("Connected to server.");

            // Initialize input streams
            userInput = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Initialize output stream to server
            out = new DataOutputStream(socket.getOutputStream());

            String line = "";
            // Continuously read input from console and send it to the server
            while (!line.equals("Close")) {
                line = userInput.readLine();
                out.writeUTF(line);

                // Print server responses
                String serverResponse = in.readLine();
                System.out.println("Server: " + serverResponse);
            }

            // Close connections
            userInput.close();
            in.close();
            out.close();
            socket.close();
        } catch (UnknownHostException u) {
            System.out.println("Unknown host: " + u.getMessage());
        } catch (IOException i) {
            System.out.println("IO Exception: " + i.getMessage());
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 5000);
    }
}
