/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_stopuhr;

import java.io.IOException;
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
    private final List<ConnectionHandler> handler = new ArrayList<>();
    private long timeOffset;
    private long startMillis;

    public void start(int port) throws IOException {
        timeOffset = 0;
        serverSocket = new ServerSocket(port);
        while (true) {
            timeOffset++;
            
            startMillis = System.currentTimeMillis();
            
            final Socket clientSocket = serverSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(clientSocket);
            new Thread(handler).start();

        }
    }

    public boolean isTimerRunning() {

    }

    public long getTimerMillis() {

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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
