package com.amg.simplecalculatorserver.server;

import com.amg.simplecalculatorserver.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public
class ServerHandler implements Runnable {
    public static int SERVER_PORT = 8087;
    public static String SERVER_IP = "127.0.0.1";
    public volatile boolean started = false;
    private ServerSocket serverSocket;
    LinkedList<ClientHandler> clientHandlerLinkedList = new LinkedList<>();

    public static void setServerPort(int serverPort) {
        SERVER_PORT = serverPort;
    }

    public static void setServerIp(String serverIp) {
        SERVER_IP = serverIp;
    }

    @Override
    public void run() {
        try {
            System.out.println("Dinning Room ");

            serverSocket = new ServerSocket(SERVER_PORT);

            // SERVER_PORT = serverSocket.getLocalPort();

            System.out.println("Server running on: " + SERVER_IP);
            System.out.println("Port: " + SERVER_PORT);
            ServerController.writeOnConsole("Server running on: " + SERVER_IP);
            ServerController.writeOnConsole("Port: " + SERVER_PORT);

            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();


                //create a new connection
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clientHandler.start();

                ServerController.writeOnConsole("client connected as socket " + socket + " in ClientHandler " + clientHandler.getName());
                clientHandlerLinkedList.add(clientHandler);
            }

        } catch (Exception e) {

            ServerController.writeOnConsole("Something went wrong while creating the serversocket");

        } finally {
            try {
                if (serverSocket != null & !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {

                ServerController.writeOnConsole("Something went wrong while closing the serversocket");

            }
        }

    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
