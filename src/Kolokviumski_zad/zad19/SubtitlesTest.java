package Kolokviumski_zad.zad19;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}
class Subtitles{
    List<Element> elements;
    public Subtitles() {
        elements=new ArrayList<>();
    }

    int loadSubtitles(InputStream is){
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            int number = Integer.parseInt(sc.nextLine());
            String[] parts = sc.nextLine().split(" --> ");
            int timeStart = convertPartToInt(parts[0]);
            int timeEnd = convertPartToInt(parts[1]);
            StringBuilder text=new StringBuilder();
            while(true){
                if(!sc.hasNextLine())break;
                String line = sc.nextLine();
                if(line.trim().isEmpty())break;
                text.append(line);
                text.append("\n");
            }
            elements.add(new Element(number, timeStart, timeEnd, text.toString()));
        }
        return elements.size();
    }

    private int convertPartToInt(String line) {
        String[] parts1 = line.split(",");
        int milisec = Integer.parseInt(parts1[1]);
        String[] parts2 = parts1[0].split(":");
        int hours = Integer.parseInt(parts2[0]);
        int minutes = Integer.parseInt(parts2[1]);
        int sec = Integer.parseInt(parts2[2]);
        int res = milisec;
        res += sec * 1000;
        res += minutes * 60 * 1000;
        res += hours * 60 * 60 * 1000;
        return res;
    }

    void print(){
        for (Element element : elements) {
            System.out.println(element);
        }
    }
    void shift(int ms){

    }

}
class Element{
    int number;
    int timeStart;
    int timeEnd;
    String description;

    public Element() {
    }

    public Element(int number, int timeStart, int timeEnd, String description) {
        this.number = number;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.description = description;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(number).append("\n");
        sb.append(String.format("%s --> %s", timeToString(timeStart), timeToString(timeEnd))).append("\n");
        sb.append(description);
        return sb.toString();
    }

    static String timeToString(int time) {
        int h = time / (60 * 60 * 1000);
        time = time % (60 * 60 * 1000);
        int m = time / (60 * 1000);
        time = time % (60 * 1000);
        int s = time / 1000;
        int ms = time % 1000;
        return String.format("%02d:%02d:%02d,%03d", h, m, s, ms);
    }
    public void shift(int ms) {
        timeStart += ms;
        timeEnd += ms;
    }
}

