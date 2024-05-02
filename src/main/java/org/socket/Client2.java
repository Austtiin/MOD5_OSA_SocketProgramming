package org.socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
    private Socket socket = null;
    private Scanner userInput = null;
    private BufferedReader in = null;
    private DataOutputStream out = null;
    private int clientNumber = 0;

    public Client2(String address, int port) {
        try {
            // Connect to the server
            socket = new Socket(address, port);
            System.out.println("Connected to server.");

            // Initialize input streams
            userInput = new Scanner(System.in);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Initialize output stream to server
            out = new DataOutputStream(socket.getOutputStream());

            // Read the assigned client number from the server
            clientNumber = Integer.parseInt(in.readLine());
            System.out.println("You are client number: " + clientNumber);

            String line = "";
            // Continuously read input from console and send it to the server
            while (!line.equals("Close")) {
                line = userInput.nextLine();
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
        Client2 client = new Client2( "127.0.0.1", 5000);
    }
}
