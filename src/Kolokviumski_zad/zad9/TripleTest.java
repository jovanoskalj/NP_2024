package Kolokviumski_zad.zad9;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
class Triple <T extends Number>{
    List<T> numbers;

    public Triple(T a, T b, T c) {
        numbers = new ArrayList<>();
        numbers.add(a);
        numbers.add(b);
        numbers.add(c);
    }
   public double max(){
        DoubleSummaryStatistics ds =
                new DoubleSummaryStatistics();
        return numbers.stream().mapToDouble(i->i.doubleValue()).max().getAsDouble();

    }

    public void sort(){
      numbers= numbers.stream().sorted().collect(Collectors.toList());
    }
    public double avarage(){
        DoubleSummaryStatistics ds =
                new DoubleSummaryStatistics();
        return numbers.stream().mapToDouble(i->i.doubleValue()).average().getAsDouble();
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",numbers.get(0).doubleValue(),numbers.get(1).doubleValue(),numbers.get(2).doubleValue()) ;
    }
}


