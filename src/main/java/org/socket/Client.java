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

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to server on localhost, port 1234
            //1234 is the port number but, it can be any number
            Socket socket = new Socket("127.0.0.1", 1234); // Connect to server on localhost, port 1234
            System.out.println("Connected to server.");

            //We want to create a way to send and receive messages from the server
            //We use the BufferedReader method to read the input from the user

            //We need to create a BufferedReader object to read the input from the user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);


            String userInputLine;

            //We need to create a while loop to keep the connection open
            while ((userInputLine = userInput.readLine()) != null) {
                // Send user input to server
                output.println(userInputLine);

                // Receive response from server

                String response = input.readLine();
                System.out.println("Server response: " + response);
            }

            // Close the connection to the server
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
