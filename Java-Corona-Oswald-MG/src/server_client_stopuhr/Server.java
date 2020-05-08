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
        return startMillis > 0;
    }

    public long getTimerMillis() {
        if (startMillis > 0) {
            return System.currentTimeMillis() - startMillis + timeOffset;
        } else {
            return timeOffset;
        }
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
            return socket.isClosed();
        }

        public boolean isMaster() {
            return master;
        }

        @Override
        public void run() {
            try {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final String line;
                line = reader.readLine();

                final Gson gson = new Gson();
                gson.toJson(line);
                final Request rq = gson.fromJson(line, Request.class);

                if (master == true) {
                    if (rq.isMaster()) {
                        for (ConnectionHandler ch : handlers) {
                            master = true;
                            if (ch != this && ch.isMaster() == true) {
                                master = false;
                            }
                        }

                        if (rq.isStart()) {
                            startMillis = System.currentTimeMillis();
                        }
                        if (rq.isStop()) {
                            startMillis = 0;
                        }
                        if (rq.isClear()) {
                            timeOffset = 0;
                        }
                        if (rq.isEnd()) {
                            handlers.remove(this);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
