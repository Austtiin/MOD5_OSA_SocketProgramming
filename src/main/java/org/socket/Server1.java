package org.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server1 {
    private static ServerSocket server = null;
    private static Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Server1(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started....");
            System.out.println("Waiting for a client ....");

            socket = server.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Close")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void main(String[] args) {
        Server1 server = new Server1(5000);
    }

}
