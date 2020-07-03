/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import javax.swing.SwingWorker;
import server_client_stopuhr.Request;
import server_client_stopuhr.Response;

/**
 *
 * @author Christoph-PC
 */
public class ConnectionWorker extends SwingWorker<String, Response> {
  
    
    private boolean tryToStart;
    private boolean tryToStop;
    private boolean tryToClear;
    private boolean tryToEnd;
    
    private boolean cancel;
    
    private Socket socket;
    

    public ConnectionWorker(String host, int port) throws IOException {
            socket = new Socket(host, port);
    }
    
    public void setTryToStart(boolean tryToStart) {
        this.tryToStart = tryToStart;
    }

    public void setTryToStop(boolean tryToStop) {
        this.tryToStop = tryToStop;
    }

    public void setTryToClear(boolean tryToClear) {
        this.tryToClear = tryToClear;
    }

    public void setTryToEnd(boolean tryToEnd) {
        this.tryToEnd = tryToEnd;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    protected void process(List<Response> list) {
    }
    
    @Override
    protected String doInBackground() throws Exception {
        final Gson g = new Gson();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        while(!cancel) {
            try {                
                final Request resq = new Request(true, tryToStart, tryToStop, tryToClear, tryToEnd);
                final String resqString = g.toJson(resq);
                writer.write(resqString);
                writer.flush();

                tryToStart = false;
                tryToStop = false;
                tryToClear = false;
                tryToEnd = false;


                final String respString = reader.readLine();
                final Response resp = g.fromJson(respString, Response.class);
                publish(resp);
                
                
            } catch(Exception ex ){
                ex.printStackTrace();
            }
        }
        return "finished";
    }
}
