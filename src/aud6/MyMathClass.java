package aud6;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;

public class MyMathClass {

}

class GenericMathOperationsTest{

    public static String statistics(List<? extends Number> numbers){
//        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
//        numbers.forEach(i->doubleSummaryStatistics.accept(i.doubleValue()));

        DoubleSummaryStatistics doubleSummaryStatistics1 = numbers.stream().mapToDouble(Number::doubleValue).summaryStatistics();
        double standardDeviation=0;
       for(Number n : numbers){
           standardDeviation+= (n.doubleValue() - doubleSummaryStatistics1.getAverage())
                   * (n.doubleValue()-doubleSummaryStatistics1.getAverage());
       }
       double finalDeviation = Math.sqrt(standardDeviation/numbers.size());

        return String.format("Min: %.2f\nMax: %.2f\nAverage: %.2f\nStandard deviation: %.2f\n" +
                        "Count: %d\nSum: %.2f",
                doubleSummaryStatistics1.getMin(),
                doubleSummaryStatistics1.getMax(),
                doubleSummaryStatistics1.getAverage(),
                standardDeviation,
                doubleSummaryStatistics1.getCount(),
                doubleSummaryStatistics1.getSum());
    }
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        Random rdm = new Random();
        for (int i=0;i<100000;i++) {
            integers.add(rdm.nextInt(100)+1);
        }

        List<Double> doubles = new ArrayList<>();
        for (int i=0;i<100000;i++) {
            doubles.add(rdm.nextDouble()*100.0);
        }

        System.out.println(statistics(integers));
        System.out.println(statistics(doubles));
    }
}