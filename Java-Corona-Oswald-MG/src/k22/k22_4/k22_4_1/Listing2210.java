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
public class Listing2210 extends Thread{
    static int cnt = 0;
    
    public static void main(String[] args) {
        Thread t1 = new Thread();
        Thread t2 = new Thread();
        t1.start();
        t2.start();
    }
    
    @Override
    public void run(){
        while(true){
            synchronized(getClass()){
                System.out.println(cnt++);
            }
            
        }
    }
}
