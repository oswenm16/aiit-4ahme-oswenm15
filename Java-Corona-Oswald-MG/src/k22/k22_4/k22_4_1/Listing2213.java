/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k22.k22_4.k22_4_1;

import java.util.Vector;

/**
 *
 * @author maxos
 */
class Producer2213
        extends Thread {

    private final Vector v;

    public Producer2213(Vector v) {
        this.v = v;
    }

    @Override
    public void run() {
        String s;

        while (true) {
            synchronized (v) {
                s = "Wert " + Math.random();
                v.addElement(s);
                System.out.println("Produzent erzeugte " + s);
                v.notify();
            }
            try {
                Thread.sleep((int) (100 * Math.random()));
            } catch (InterruptedException e) {

            }
        }
    }
}

class Consumer2213
        extends Thread {

    private final Vector v;

    public Consumer2213(Vector v) {
        this.v = v;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (v) {
                if (v.size() < 1) {
                    try {
                        v.wait();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.print(
                        " Konsument fand " + (String) v.elementAt(0)
                );
                v.removeElementAt(0);
                System.out.println(" (verbleiben: " + v.size() + ")");
            }
            try {
                Thread.sleep((int) (100 * Math.random()));
            } catch (InterruptedException e) {
            }
        }
    }
}

public class Listing2213 {

    public static void main(String[] args) {
        Vector v = new Vector();

        Producer2213 p = new Producer2213(v);
        Consumer2213 c = new Consumer2213(v);
        p.start();
        c.start();
    }
}
