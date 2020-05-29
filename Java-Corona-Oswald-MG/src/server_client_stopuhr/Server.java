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
import java.io.OutputStreamWriter;
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

    private ServerSocket serversocket;
    private final List<ConnectionHandler> handlers = new ArrayList<>();
    private long timeOffset;
    private long startMillis;

    public void start(int port) throws IOException {
        serversocket = new ServerSocket(port);
        System.out.println("Server auf Port " + port + " gestartet");
        while (true) {
            final Socket socket = serversocket.accept();
            synchronized (handlers) {
                for (int i = 0; i < handlers.size(); i++) {
                    //ConnectionHandler removeHandler = null;

                    ConnectionHandler h = handlers.get(i);
                    if (h.isClosed()) {
                        handlers.remove(i--);
                    }
                }
            }
            if (handlers.size() < 3) {
                ConnectionHandler handler = new ConnectionHandler(socket);
                handlers.add(handler);
                new Thread(handler).start();

            } else {
                System.out.println("Connection refused (" + socket.toString() + ")");
                socket.close();

            }

        }
    }

    public boolean isTimerRunning() {
        synchronized (handlers) {
            return startMillis > 0;
        }
    }

    public long getTimerMillis() {
        synchronized (handlers) {
            if (startMillis > 0) {
                return System.currentTimeMillis() - startMillis + timeOffset;
            } else {
                return timeOffset;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().start(8080);

    }

    private class ConnectionHandler implements Runnable {

        private final Socket socket;
        private boolean master;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        public boolean isClosed() {
            return socket.isClosed();
        }

        public boolean isMaster() {
            return master;
        }

        @Override
        public void run() {
            int count = 0;
            while (true) {
                try {
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                    final String req = reader.readLine();
                    if (req == null) {
                        socket.close();
                        return;
                    }
                    count++;

                    final Gson gson = new Gson();
                    final Request r = gson.fromJson(req, Request.class);

                    if (r.isMaster()) {
                        boolean setMasterTrue = true;
                        synchronized (handlers) {
                            for (ConnectionHandler h : handlers) {
                                if (!h.equals(this) && h.isMaster() == true) {
                                    setMasterTrue = false;
                                    break;
                                }
                            }
                        }
                        master = setMasterTrue;
                    }

                    if (r.isMaster()) {
                        if (r.isStart()) {
                            startMillis = System.currentTimeMillis();
                        }

                        if (r.isStop()) {
                            startMillis = 0;
                        } else {
                            if (isTimerRunning()) {
                                timeOffset = System.currentTimeMillis() - startMillis + timeOffset;
                            }
                        }

                        if (r.isClear()) {
                            timeOffset = 0;
                            if (isTimerRunning()) {
                                startMillis = System.currentTimeMillis();
                            } else {
                                startMillis = 0;
                            }
                        }

                        if (r.isEnd()) {
                            serversocket.close();
                            socket.close();
                            synchronized (handlers) {
                                handlers.remove(this);
                            }
                            return;
                        }
                    }

                    //Response
                    final Response resp = new Response(master, count, isTimerRunning(), getTimerMillis());
                    final String respString = gson.toJson(resp);
                    writer.write(respString);
                    writer.flush();

                    System.out.print(req);
                    System.out.println(respString);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
