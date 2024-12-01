package midterm2.aud7;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Counter {
    int[] countingArray;

    public Counter() {
        countingArray = new int[10];
    }

    public void addToCounter(int digit) {
        countingArray[digit]++;
    }

    @Override
    public String toString() {
        //StringBuilder sb = new StringBuilder();

//        for (int i = 0; i < countingArray.length; i++) {
//            sum+=countingArray[i];
//        }

//        for (int i = 1; i < countingArray.length; i++) {
//            sb.append(String.format("%d: %.2fd", i , (float)countingArray[i]/sum));
//        }

        // return sb.toString();


        int sum = Arrays.stream(countingArray).sum();
        return IntStream.range(1, 10).
                mapToObj(i -> String.format("%d: %.2f%%", i, countingArray[i] * 100.0 / sum))
                .collect(Collectors.joining("\n"));
    }
}


class BenfordLawExperiment {
    List<Integer> numbers;
    Counter counter;

    public BenfordLawExperiment() {
        numbers = new ArrayList<>();
        counter = new Counter();
    }

    public void readData(InputStream inputStream) {
//        Scanner sc = new Scanner(inputStream);
//        while (sc.hasNext()){
//            int number = sc.nextInt();
//            numbers.add(number);
//        }

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        numbers = br.lines().
                filter(line -> !line.equals("")).
                map(Integer::parseInt).collect(Collectors.toList());
    }

    public void conductExperiment() {
        numbers.stream().map(this::getFirstDigit)
                .forEach(firstDigit -> counter.addToCounter(firstDigit));
    }

    public int getFirstDigit(Integer i) {
        while (i >= 10) {
            i /= 10;
        }
        return i;
    }

    @Override
    public String toString() {
        return counter.toString();
    }
}

public class BenfordLawTest {
    public static void main(String[] args) {
        // Counter counter = new Counter();
        //System.out.println(counter);
        BenfordLawExperiment experiment = new BenfordLawExperiment();
        System.out.println(experiment.getFirstDigit(923456));
        try {
            experiment.readData(new FileInputStream("data.txt"));
            experiment.conductExperiment();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}