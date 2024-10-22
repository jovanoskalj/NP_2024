package aud5_A.WordCount;

import java.io.*;
import java.util.Scanner;

public class WordCount {
    public static void readData (InputStream inputStream){
        int lines =0;
        int words=0;
        int chars =0;

        Scanner sc = new Scanner(inputStream);
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            ++lines;
            words+=line.split("\\s+").length;
            chars+=line.length();
        }
        System.out.printf("Line: %d, word %d, char %d", lines, words, chars);

    }
    public static void readDataBF(InputStream inputStream) throws IOException {
        //pobrzo od Scanner
        //gi zema site linii naednas i gi parsira
        int lines =0;
        int words=0;
        int chars =0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = bufferedReader.readLine()) !=null) {
            ++lines;
            words += line.split("\\s+").length;
            chars += line.length();
        }
    }
    //ova najcesto koristeno
    public static void ReadDataBFMapAndReduce(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        LineCounter result =bufferedReader.lines().map(l -> new LineCounter(l)).reduce(new LineCounter(0,0,0),(left, right)->left.sum(right));
        System.out.println(result);
    }

    public  static void ReadDataBFWithConsumer(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        LineConsumer lineConsumer = new LineConsumer();
        bufferedReader.lines().forEach(lineConsumer);
    }
    public static void main(String[] args) throws FileNotFoundException {
    File file = new File("src/aud5_A/test.txt");
    readData(new FileInputStream(file));
    }
}
