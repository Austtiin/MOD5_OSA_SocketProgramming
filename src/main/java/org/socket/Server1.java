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
            //Create our server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started:  " + port);

            //Listen for incoming connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                //We want to label the connected clients
                clientNumber++;
                System.out.println("Client " + clientNumber + " connected.");

               //Show the client number and start the client handler
                new ClientHandler(clientSocket, clientNumber).start();
            }
        } catch (IOException e) {
            System.out.println("Error in server: " + e.getMessage());
            //Close the server socket
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

    //Class to handle client connections
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream in = null;
        private DataOutputStream out = null;
        private int clientNumber;

        public ClientHandler(Socket socket, int clientNumber) {
            //Assign the client socket and client number
            this.clientSocket = socket;
            this.clientNumber = clientNumber;
        }

        public void run() {
            try {
                //Initialize input and output streams
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                String line = "";
                //COntinuously read input from the client and send responses
                while (!line.equals("Close")) {
                    line = in.readUTF();
                    System.out.println("Client " + clientNumber + ": " + line);

                    //Grab the current date and time
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateAndTime = dateFormat.format(new Date());

                    //Send the current date and time to the client
                    out.writeUTF("Message received at " + currentDateAndTime);
                }
            } catch (IOException e) {
                System.out.println("Error handling client " + clientNumber + ": " + e.getMessage());
            } finally {
                //Close the input and output streams
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        System.out.println("Error closing input stream: " + e.getMessage());
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        System.out.println("Error closing output stream: " + e.getMessage());
                    }
                }

            }
        }

    }
}
