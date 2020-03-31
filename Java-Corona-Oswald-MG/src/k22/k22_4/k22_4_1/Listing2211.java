/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k22.k22_4.k22_4_1;

/**
 *
 * @author maxos
 */
class Counter2211 {

    int cnt;

    public Counter2211(int cnt) {
        this.cnt = cnt;
    }

    public synchronized int nextNumber() {
        int rec = cnt;
        double x = 1.0, y, z;
        for (int i = 0; i < 1000; i++) {
            x = Math.sin((x * i % 35) * 1.13);
            y = Math.log(x + 10.0);
            z = Math.sqrt(x + y);
        }
        cnt++;
        return rec;
    }
}

public class Listing2211 extends Thread {

    private String name;
    private Counter2211 counter;

    public Listing2211(String name, Counter2211 counter) {
        this.name = name;
        this.counter = counter;
    }

    public static void main(String[] args) {
        Thread[] t = new Thread[5];
        Counter2211 cnt = new Counter2211(10);
        for (int i = 0; i < 5; ++i) {
            t[i] = new Listing2211("Thread-" + i, cnt);
            t[i].start();
        }

    }

    @Override
    public void run() {
        while (true) {
            System.out.println(counter.nextNumber() + "for" + name);
        }
    }

}
