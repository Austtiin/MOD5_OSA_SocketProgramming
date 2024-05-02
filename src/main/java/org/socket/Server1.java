package org.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server1 {
    private ServerSocket serverSocket;
    private int clientNumber = 0;

    public Server1(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Client " + clientNumber + " connected.");

                // Start a separate thread to handle each client
                new ClientHandler(clientSocket, clientNumber).start();
            }
        } catch (IOException e) {
            System.out.println("Error in server: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Server1 server = new Server1(5000);
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream in;
        private DataOutputStream out;
        private int clientNumber;

        public ClientHandler(Socket socket, int clientNumber) {
            this.clientSocket = socket;
            this.clientNumber = clientNumber;
        }

        public void run() {
            try {
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line;
                while (true) {
                    line = in.readUTF();

                    if (line.equals("Close")) {
                        break;
                    }
                    System.out.println("Client " + clientNumber + ": " + line);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateAndTime = dateFormat.format(new Date());
                    out.writeUTF("Message received at " + currentDateAndTime);

                    // Server message to client
                    System.out.print("Server: ");
                    String serverMessage = reader.readLine();
                    out.writeUTF(serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
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
