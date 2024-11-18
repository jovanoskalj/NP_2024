package Kolokviumski_zad.zad24;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}
class Risk{

    public AtomicInteger processAttacksData(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        AtomicInteger wins= new AtomicInteger();
        bufferedReader.lines().forEach(line->{
            String lineAttacker = line.split(";")[0];
            String lineAttacked= line.split(";")[1];
            String [] attackerInfo = lineAttacker.split("\\s+");
            String [] attackedInfo = lineAttacked.split("\\s+");
           Arrays.sort(attackedInfo);
           Arrays.sort(attackerInfo);
           int count=0;
            for(int i=0;i<attackerInfo.length;i++){
                if(Integer.parseInt(attackerInfo[i])>Integer.parseInt(attackedInfo[i])){
                    count++;
                }

            }

            if(count==3){
                wins.getAndIncrement();
            }




        });
        return wins;
    }
}