package com.amg.simplecalculatorserver.server;

import com.amg.simplecalculatorserver.ServerController;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public
class ClientHandler extends Thread {


    private Socket socket;
    private ServerHandler serverHandler;
    // the game this connection is associated with
    private
    InputStream inputStream;
    private
    OutputStream outputStream;
    private
    DataOutputStream dataOutputStream;
    private
    DataInputStream dataInputStream;

    public RequestHandler requestHandler;
    private
    BufferedReader bufferedReader;
    private PrintWriter printWriter;

    private static Lock lock = new ReentrantLock();

  public ClientHandler() {

  }
  public ClientHandler(ServerHandler serverHandler, Socket socket) {
        this.serverHandler = serverHandler;
        this.socket = socket;


    }



    @Override
    public void run() {
        ServerController.writeOnConsole("Starting thread of "+this.getId());
        try {
            inputStream = socket.getInputStream();
        } catch (IOException ioException) {
            ServerController.writeOnConsole("Error in input stream of"+this.getId());
        }
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException ioException) {
          ServerController.writeOnConsole("Error in out stream of"+this.getId());
        }
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        printWriter = new PrintWriter(outputStream, true);

        requestHandler = new RequestHandler(this, serverHandler);

        while (true) {


        String request = getRequest(bufferedReader);

            if (request == null) {
               ServerController.writeOnConsole(this.getName() + " disconnected");
                return;

            }
            ServerController.writeOnConsole("Getting Request from "+this.getId()+" :["+request+"] at "+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            requestHandler.executeRequest(request);

        }
    }


    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public ClientHandler setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        return this;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public ClientHandler setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
        return this;
    }



    public String getRequest(BufferedReader bufferedReader) {

      String request = null;
        try {
            request = bufferedReader.readLine();
        } catch (IOException e) {
         ServerController.writeOnConsole("Error in getting request.");
        }
        return request;
    }

    public Socket getSocket() {
        return socket;
    }
}

