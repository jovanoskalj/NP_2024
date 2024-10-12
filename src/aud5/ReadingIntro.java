package aud5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadingIntro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<String>();
//        while(sc.hasNextLine()){
//            String line = sc.nextLine();
//            inputs.add(line);
//        }
//        System.out.println(inputs);

//        int n;
//        n= sc.nextInt();
//        ArrayList<Integer> numbers = new ArrayList<>();
//        for(int i=0;i<n;i++){
//            numbers.add(sc.nextInt());
//        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        inputs= (ArrayList<String>) bufferedReader.lines().collect(Collectors.toList());
        System.out.println(inputs);
    }
}
