/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_stopuhr.gui;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import javax.swing.SwingWorker;
import server_client_stopuhr.Request;
import server_client_stopuhr.Response;

/**
 *
 * @author maxos
 */
public class ConnectionWorker extends SwingWorker<Object, Response> {
    private Socket socket;
    private Client gui;

    public ConnectionWorker() throws IOException {
        this.gui = gui;
        int port = 0;
        socket = new Socket("127.0.0.1", port);
    }
        
    @Override
    protected Response doInBackground() throws Exception {
        while(true){
            Response resp = null;
            publish(resp);
        }
    }

    @Override
    protected void process(List<Response> list) {
        for(Response r : list){
            gui.handleResponse(r);
        }
    }

    @Override
    protected void done() {
        super.done(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
