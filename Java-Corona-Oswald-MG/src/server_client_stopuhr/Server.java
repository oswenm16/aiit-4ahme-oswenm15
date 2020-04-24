/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_stopuhr;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        return
    }

    public long getTimerMillis() {
        return System.currentTimeMillis() - startMillis;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(8080);
    }

    class ConnectionHandler implements Runnable {

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
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                line = reader.readLine();
                
                Gson gson = new Gson();
                gson.toJson(line);
                Request rq = gson.fromJson(line, Request.class);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
