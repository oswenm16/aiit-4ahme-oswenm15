/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k22.k22_3.k22_3_2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author maxos
 */
public class Listing2208 {
    public static void main(String[] args) throws IOException {
        ThreadedPrimeNumberTools pt;
        BufferedReader bf = new BufferedReader(new InputStreamReader(new DataInputStream(System.in)));
        int num;
        
        while (true) {
            System.out.println("Bitte eine Zahl eingeben: ");
            System.out.flush();
            num = (new Integer(bf.readLine()));
            if (num == -1) {
                break;
            }
            pt = new ThreadedPrimeNumberTools();
            pt.printPrimeFactors(num);
        }
        
    }
}
