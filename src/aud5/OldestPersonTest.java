package aud5;

import java.io.*;
import java.util.Comparator;

class Person implements Comparable<Person>{
    String name;
    Integer age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.age,o.age);
    }
}
public class OldestPersonTest {
    public  static Person find (InputStream is){
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        return bf.lines().map(line->new Person(line.split("\\s+")[0],Integer.parseInt(line.split("\\s+")[1]))).max(Comparator.naturalOrder()).get();
    }
    public static void main(String[] args) throws FileNotFoundException {
        InputStream isFromFile = new FileInputStream("C:\\Users\\jovan\\Desktop\\NP\\src\\aud5\\people.txt");
        System.out.println(OldestPersonTest.find(isFromFile));
    }
}
