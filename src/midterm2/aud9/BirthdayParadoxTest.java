package midterm2.aud9;

import java.util.HashSet;
import java.util.Random;

public class BirthdayParadoxTest {
    public static void main(String[] args) {
        BirthdayParadox bparadox = new BirthdayParadox(50);
        bparadox.conductExperiment();
    }

}
class BirthdayParadox{
    int maxPersons;
    static int TRIALS = 5000;

    public BirthdayParadox(int maxPersons) {
        this.maxPersons = maxPersons;

    }
    public void conductExperiment(){
        for (int i = 2; i <= maxPersons; i++) {
            System.out.println(String.format("%d --> %.2f",i,runSimulation(i)));

        }
    }

    private float runSimulation(int people) {
        int counter=0;
        Random random = new Random();
        for(int i=0;i<TRIALS;i++){
            if(runTrial(people,random)){
                ++counter;
            }
        }
        return counter * 1.0f/TRIALS;
    }

    private boolean runTrial(int people,Random random) {
        HashSet<Integer> birthdays = new HashSet<>();
        for(int i=0;i<people;i++){
            int birthday = random.nextInt(365)+1;
            if(birthdays.contains(birthday))
                return true;
            birthdays.add(birthday);
        }
        return false;

    }
}
