/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k22.k22_3.k22_3_2;

/**
 *
 * @author maxos
 */
public class PrimeNumberTools {
    public void printPrimeFactors(int num){
        int whichprime = 1;
        int prime;
        String prefix;
        
        prefix = "primeFactors("+num+")= ";
        while(num > 1){
            prime = getPrime(whichprime);
            if(num % prime == 0){
                System.out.println(prefix + prime);
                prefix = " ";
                num /= prime;
            }else{
                whichprime++;
            }
        }
        System.out.println();
    }

    int getPrime(int cnt) {
        int i = 1;
        int ret = 2;
        
        while(i < cnt){
            ret++;
            if(isPrime(ret)){
                i++;
            }
        }
        return ret;
    }

    private boolean isPrime(int num) {
        for(int i = 2; i < num; i++){
            if(num %  i == 0){
                return false;
            }
        }
        return true;
    }
}
