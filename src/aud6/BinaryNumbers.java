package aud6;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

public class BinaryNumbers {
    public static final String FILE_NAME = "C:\\Users\\jovan\\Desktop\\NP\\src\\aud6\\numbers.txt";

    private static void generateFile(int n) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int nextRandom = random.nextInt(1000);
            outputStream.writeInt(nextRandom);
        }
//        IntStream.range(0,n).forEach(i->
//        {
//            int nextRandom = random.nextInt(1000);
//            try {
//                outputStream.writeInt(nextRandom);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } );
        outputStream.flush();
    }

    public static double average() throws IOException {
        int count = 0;
        double sum = 0;
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));

        try {
            while (true) {
                int number = objectInputStream.readInt();
                sum += number;
                count++;
            }
        } catch (IOException e) {
            System.out.println("End of a file");
        }
        return sum / count;

    }

    public static void main(String[] args) throws IOException {
        generateFile(1000);
        System.out.println(average());
    }
}
