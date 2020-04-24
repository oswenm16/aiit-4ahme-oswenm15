/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server_client_stopuhr;

/**
 *
 * @author maxos
 */
public class Response {
    private boolean master;
    private long count;
    private boolean running;
    private long time;

    public Response(boolean master, long count, boolean running, long time) {
        this.master = master;
        this.count = count;
        this.running = running;
        this.time = time;
    }

    public boolean isMaster() {
        return master;
    }

    public long getCount() {
        return count;
    }

    public boolean isRunning() {
        return running;
    }

    public long getTime() {
        return time;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    
}
