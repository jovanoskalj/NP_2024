// package Kolokviumski_zad.zad27;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}

class Risk {
    public String processAttacksData(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        AtomicInteger attackerAlive = new AtomicInteger();
        AtomicInteger attackedAlive = new AtomicInteger();

        bufferedReader.lines().forEach(line -> {
            String lineAttacker = line.split(";")[0];
            String lineAttacked = line.split(";")[1];
            String[] attackerInfo = lineAttacker.split("\\s+");
            String[] attackedInfo = lineAttacked.split("\\s+");
            Arrays.sort(attackedInfo);
            Arrays.sort(attackerInfo);

            for (int i = 0; i < attackerInfo.length; i++) {
                if (Integer.parseInt(attackerInfo[i]) > Integer.parseInt(attackedInfo[i])) {
                    attackerAlive.getAndIncrement();
                } else {
                    attackedAlive.getAndIncrement();
                }
            }

            // Print after processing each line
            System.out.printf("%d %d%n",
                    attackerAlive.get(), attackedAlive.get());
            attackerAlive.set(0);
            attackedAlive.set(0);
        });



        return "A";// Optional return
    }
}
