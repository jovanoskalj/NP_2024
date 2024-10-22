package aud5.Grades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GradesTest {

    public static void main(String[] args) throws FileNotFoundException {

        Course course = new Course();
        File inputFile = new File("C:\\Users\\jovan\\Desktop\\NP\\src\\aud5\\Grades\\input.txt");
        File outputFile = new File("C:\\Users\\jovan\\Desktop\\NP\\src\\aud5\\Grades\\output.txt");
        course.readData(new FileInputStream(inputFile));

        System.out.println("===Printing sorted students to screen===");
        course.printSortedData(System.out);

        System.out.println("===Printing detailed report to file===");
        course.printDetailedData(new FileOutputStream(outputFile));

        System.out.println("===Printing grade distribution to screen===");
        course.printDistribution(System.out);

    }
}