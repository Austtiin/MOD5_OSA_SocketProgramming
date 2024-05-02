package org.socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.Socket;
import java.net.*;


public class Client {
    private Socket socket = null;
    private Scanner userInput = null;
    private BufferedReader in = null;
    private DataOutputStream out = null;
    private int clientNumber = 0;

    public Client(String address, int port) {
        try {
            try {
                socket = new Socket(address, port);
                System.out.println("Connected");

                // takes input from terminal
                userInput = new Scanner(System.in);

                // sends output to the socket
                out = new DataOutputStream(
                        socket.getOutputStream());
            }
            catch (UnknownHostException u) {
                System.out.println(u);
                return;
            }
            catch (IOException i) {
                System.out.println(i);
                return;
            }
            // Read the assigned client number from the server
            clientNumber = Integer.parseInt(in.readLine());
            System.out.println("You are client number: " + clientNumber);

            String line = "";
            // Continuously read input from console and send it to the server
            while (!line.equals("Close")) {
                // Get user input
                System.out.print("Client: ");
                line = userInput.nextLine();

                // Send message to server
                out.writeUTF(line);

                // Receive and print server responses
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
