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
public class Request {
    private boolean master;
    private boolean start;
    private boolean stop;
    private boolean clear;
    private boolean end;

    public void setMaster(boolean master) {
        this.master = master;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isMaster() {
        return master;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isClear() {
        return clear;
    }

    public boolean isEnd() {
        return end;
    }
    
    

}
