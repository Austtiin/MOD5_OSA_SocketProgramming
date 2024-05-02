package org.socket;/*
* Austin Stephens
* Rasmussen University
*Professor Amitava Karmaker
*
* Second Attempt. Rebuild
*
*
* Overview:
Socket programming refers to writing programs that execute across multiple computers in which the devices are connected to each other through a network, specifically using the TCP or UDP protocols over TCP/IP. In this exercise, demonstrates how to write a client/server application in Java.



Instructions:
Write a Java program:

That emulates a client/server application running on your local host (127.0.0.1)
Where there is two-way communication between client and server: client sends message to server and server responds with acknowledgement and current date and time
Where server can handle multiple clients simultaneously, e.g., multiple threads running
Outputs client/server


Requirements:
Submit a compressed folder of your work. It should include:

Your annotated code
Screen captures of code execution
In 2-3 paragraphs, give a brief description of your results

* */



import java.io.*;
import java.net.*;
import java.util.Date;


public class Server1 {
    public static void main(String[] args) {
        // Create a server socket to listen for incoming client connections
        try {
            // Create server socket on port 1234 to listen for incoming client connections
            ServerSocket serverSocket = new ServerSocket(1234); // Create server socket on port 1234
            System.out.println("Server started. Listening for connections...");

            // Continuously listen for incoming client connections to the server
            while (true) {
                // Accept incoming client connections to the server
                Socket clientSocket = serverSocket.accept(); // Accept incoming client connections
                System.out.println("Client connected: " + clientSocket);

                // Create a new thread for each client by passing the client socket to the client handler
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                // Start the client handler thread
                clientHandler.start();
            }
            // Handle any exceptions that occur during server execution
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// ClientHandler class to handle client connections because the server can handle multiple clients simultaneously
class ClientHandler extends Thread {
    //Because the server can handle multiple clients simultaneously,
    // the ClientHandler class extends the Thread class to create a new thread for each client connection.
    private Socket clientSocket;
    // Create a new client handler with the client socket
    //We want to do this because we want to create a new thread for each client connection.
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    // Run method to handle client communication
    public void run() {
        // Create input and output streams for client communication to send and receive messages
        try {

            // Create input and output streams for client communication by reading from and writing to the client socket
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            //String holds the message from the client
            String message;

            //Check if the message is not null
            while ((message = input.readLine()) != null) {
                System.out.println("Message from client: " + message);

                //Send the acknowledgement and current date/time to the client
                output.println("Message received. Current date/time: " + new Date());
            }

            // Close the connection
            clientSocket.close();
            // Handle any exceptions that occur during client communication
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
