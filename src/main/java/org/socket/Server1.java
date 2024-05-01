package org.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server1 {
    private ServerSocket serverSocket = null;
    private int clientNumber = 0;

    public Server1(int port) {
        try {
            // Create server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            // Continuously listen for client connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Increment client number for each new client connection
                clientNumber++;
                System.out.println("Client " + clientNumber + " connected.");

                // Start a separate thread to handle each client
                new ClientHandler(clientSocket, clientNumber).start();
            }
        } catch (IOException e) {
            System.out.println("Error in server: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing server socket: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Server1 server = new Server1(5000);
    }

    // Inner class to handle client connections
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream in = null;
        private DataOutputStream out = null;
        private int clientNumber;

        public ClientHandler(Socket socket, int clientNumber) {
            this.clientSocket = socket;
            this.clientNumber = clientNumber;
        }

        public void run() {
            try {
                // Initialize input and output streams
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                String line = "";
                // Continuously listen for messages from the client
                while (!line.equals("Close")) {
                    line = in.readUTF();
                    System.out.println("Client " + clientNumber + ": " + line);

                    // Get current date and time
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateAndTime = dateFormat.format(new Date());

                    // Send acknowledgment and current date/time to client
                    out.writeUTF("Message received at " + currentDateAndTime);
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                // Close connections
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    clientSocket.close();
                    System.out.println("Client " + clientNumber + " disconnected.");
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
