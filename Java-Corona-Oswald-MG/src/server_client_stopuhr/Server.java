/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_stopuhr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maxos
 */
public class Server {

    private ServerSocket serverSocket;
    private final List<ConnectionHandler> handlers = new ArrayList<>();
    private long timeOffset;
    private long startMillis;

    public void start(int port) throws IOException {
        timeOffset = 0;
        startMillis = System.currentTimeMillis();
        serverSocket = new ServerSocket(port);
        while (true) {
            timeOffset++;
            
            final Socket clientSocket = serverSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(clientSocket);
            new Thread(handler).start();
            handlers.add(handler);
        }
    }

    public boolean isTimerRunning() {
    }

    public long getTimerMillis() {
        return  System.currentTimeMillis() - startMillis;
    }

    public static void main(String[] args) {
        new Server();
    }

    class ConnectionHandler implements Runnable{

        private Socket socket;
        private boolean master;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        public boolean isSocket() {

        }

        public boolean isMaster() {

        }

        @Override
        public void run() {
            

    }
}
