/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_stopuhr.gui;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import javax.swing.SwingWorker;
import server_client_stopuhr.Request;
import server_client_stopuhr.Response;

/**
 *
 * @author maxos
 */
public class ConnectionWorker extends SwingWorker<String, Integer> {

    private final Socket socket;

    public ConnectionWorker(int port, String hostName) throws IOException {
        socket = new Socket(hostName, port);
    }

    @Override
    protected String doInBackground() throws Exception {
        final Gson gson = new Gson();
        final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());

        while (true) {
            try {
                final Request req = new Request();
                final String reqString = gson.toJson(req);
                writer.write(reqString);
                writer.flush();

                final String string = br.readLine();
                final Response resp = gson.fromJson(string, Response.class);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

  

    
}
