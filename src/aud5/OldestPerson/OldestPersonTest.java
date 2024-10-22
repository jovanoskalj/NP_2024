package aud5.OldestPerson;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPersonTest {
    public static List<Person> readData(InputStream inputStream){
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        return  bf.lines().map(p->new Person(p)).collect(Collectors.toList());
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/aud5/OldestPerson/people.txt");
        List<Person> people = readData(new FileInputStream(file));
        Collections.sort(people);
        System.out.println(people.get(people.size()-1));
        //podreduva po rastecki redosled
        System.out.println(people.stream().max(Comparator.naturalOrder()).get()); ;
    }
}

class Person implements Comparable<Person>{
    private  String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public  Person(String line){
        this.name = line.split("\\s+")[0];
        this.age =Integer.parseInt(line.split("\\s+")[1]);
    }

    @Override
    public int compareTo(Person o ) {
        return Integer.compare(this.age,o.age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
