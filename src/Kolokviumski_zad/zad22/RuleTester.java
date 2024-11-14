package Kolokviumski_zad.zad22;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Student {
    String id;
    List<Integer> grades;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public static Student create(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Integer> grades = Arrays.stream(parts).skip(1).map(Integer::parseInt).collect(Collectors.toList());
        return new Student(id, grades);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", grades=" + grades +
                '}';
    }


}

class Rule<IN, OUT> {
    private Predicate<IN> predicate;
    private Function<IN, OUT> function;

    public Optional<OUT> apply(IN input) {
        if (predicate.test(input)) {
            return Optional.of(function.apply(input));
        } else
            return Optional.empty();

    }

    public Rule(Predicate<IN> predicate, Function<IN, OUT> function) {
        this.predicate = predicate;
        this.function = function;
    }
}

class RuleProcessor {
    public static <IN, OUT> void process(List<IN> inputs, List<Rule<IN, OUT>> rules) {
        for (IN input : inputs) {
            System.out.printf("Input: %s%n",input);
            for (Rule<IN, OUT> rule : rules) {

                if(rule.apply(input).isPresent()){
                    System.out.println("Result: "+rule.apply(input).get());
                }
                else {
                    System.out.println("Condition not met");
                }
            }
        }
    }
}

public class RuleTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { //Test for String,Integer
            List<Rule<String, Integer>> rules = new ArrayList<>();

            /*
            TODO: Add a rule where if the string contains the string
             "NP", the result would be index of the first occurrence
             of the string "NP"
            * */
            rules.add(new Rule<>(
                    i->i.contains("NP"),
                    i->i.indexOf("NP")
            ));
            /*
            TODO: Add a rule where if the string starts with the string
              "NP", the result would be length of the string
            * */
                rules.add(new Rule<>(
                        i->i.startsWith("NP"),
                        String::length));

            List<String> inputs = new ArrayList<>();
            while (sc.hasNext()) {
                inputs.add(sc.nextLine());
            }

            RuleProcessor.process(inputs, rules);


        } else { //Test for Student, Double
            List<Rule<Student, Double>> rules = new ArrayList<>();

            //TODO Add a rule where if the student has at least 3 grades,
            // the result would be the max grade of the student
            rules.add(new Rule<Student, Double>(
                    i->i.grades.size()>=3,
                    i->i.grades.stream().mapToDouble(each->each).max().getAsDouble()
            ));


            //TODO Add a rule where if the student has an ID that starts with 20, the result would be the average grade of the student
            // If the student doesn't have any grades, the average is 5.0

            rules.add(new Rule<Student,Double>(
                    i->i.id.startsWith("20"),
                    i->i.grades.stream().mapToDouble(each->each).average().orElse(5.0)
            ));

            List<Student> students = new ArrayList<>();
            while (sc.hasNext()) {
                students.add(Student.create(sc.nextLine()));
            }

            RuleProcessor.process(students, rules);
        }
    }
}
