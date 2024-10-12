package aud5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionalInterfacesIntro
{
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("TEST");
        strings.add("blabla");
        strings.add("Makedonija");
        strings.add("Napredno programiranje teST");

        //Function
        Function<String,Integer> lenghtFunction = s -> s.indexOf("a");
        System.out.println(strings.stream().map(lenghtFunction).collect(Collectors.toList()));

        //Predicate - condition, uslov
        //mostly used FILTER stream operator
        Predicate<Integer> positiveNumber = x -> x>0;
        System.out.println(strings.stream().map(lenghtFunction).filter(positiveNumber).collect(Collectors.toList()));

        //Suplier - ne prima vrednosti
        Supplier<Integer> supplier = () -> new Random().nextInt(9);
        for (int i=0;i<100;i++){
            System.out.println(supplier);
        }

        //Consumer - za pecatenje,void akcija, so foreach s ekoristi
        Consumer<Integer> consumer = integer -> System.out.println(integer);
        for(int i=0;i<100;i++){
            consumer.accept(i); //pecati ova
        }

    }

}
