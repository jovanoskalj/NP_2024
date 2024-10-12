package aud5;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.Consumer;

class LineConsumer implements Consumer<String>{
int lines=0, word=0, chars=0;
    @Override
    public void accept(String s) {
        ++lines;
        word+=s.split("\\s+").length;
        chars+=s.length();
    }

    @Override
    public String toString() {
        return String.format("Lines: %d, Words: %d, Characters: %d", lines,word,chars);
    }
}
class WordCounter {
    public static void count(InputStream is){
        Scanner sc = new Scanner(is);
        int lines=0;
        int words=0;
        int characters=0;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            ++lines;
            String [] w = line.split("\\s+");
            words+=w.length;
            characters+=line.length();

        }
        System.out.println(String.format("Lines: %d, Words: %d, Characters: %d", lines,words,characters));


    }
    public static void count2(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        LineConsumer consumer = new LineConsumer();
        br.lines().forEach(consumer);
        System.out.println(consumer);
    }
}
public class WordCounterTest{
    public static void main(String[] args) throws FileNotFoundException {
    InputStream isFromFile = new FileInputStream("C:\\Users\\jovan\\Desktop\\NP\\src\\aud5\\source.txt");
    WordCounter.count(isFromFile);
    }
}
