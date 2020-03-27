/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k22.k22_2.k22_1_2;

/**
 *
 * @author maxos
 */
public class MyThread2202 extends Thread{
    int cnt = 0;
    
    public void run(){
        while(true){
            if(isInterrupted()){
                break;
            }
            printLine(cnt++);
        }
    }

    private void printLine(int cnt) {
        //ausgeben der Zeile
        System.out.println(cnt + ": ");
        for(int i = 0; i < 30; i++){
            System.out.println(i == cnt % 30 ? "* " : ". ");
        }
        System.out.println("");
        
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            interrupt();
        }
    }
    
    public static void main(String[] args) {
        MyThread2202 mt = new MyThread2202();
        {
            mt.start();
            
            try{
               Thread.sleep(2000);
            }catch(InterruptedException e ){
                
            }
            mt.interrupt();
        }
    }
    
}
