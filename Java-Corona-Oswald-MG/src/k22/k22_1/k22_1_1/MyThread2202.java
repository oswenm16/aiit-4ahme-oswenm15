/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k22.k22_1.k22_1_1;

/**
 *
 * @author maxos
 */
 class MyThread extends Thread{
    
    public void run (){
        int i = 0;
        while (true){
            System.out.println(i++);
        }
    }
}

public class MyThread2202{
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            
        }
        t.stop();
    }
}
