package Kolokviumski_zad.Race;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    private List<Driver> drivers = new ArrayList<>();
    public F1Race() {
    }


    public void readResults(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        bf.lines().forEach(line->{
            Driver driver = Driver.createDriver(line);
            drivers.add(driver);
        });

    }

    public void printSorted(PrintStream out) {
        final int[] counter = {1};
        drivers.stream()
                .sorted(Comparator.comparing(Driver::findBestTime))
                .forEach(driver -> out.printf("%d. %-10s%10s%n", counter[0]++,driver.getName(), driver.findBestTime()));
    }


}
class Driver{
    private String name;
    List<Lap> lapList;

    public Driver() {
        lapList = new ArrayList<>();
    }

    public Driver(String name, List<Lap> lapList) {
        this.name = name;
        this.lapList = lapList;
    }

    public static Driver createDriver(String line){
        String []parts = line.split("\\s+");
        String name = parts[0];
        List<Lap> laps = new ArrayList<>();
        Arrays.stream(parts).skip(1)
                .forEach(part-> laps.add(new Lap(part)));
        return new Driver(name,laps);
    }
    public  String findBestTime(){
        return lapList.stream()
                .min(Comparator.comparingInt(Lap::StringtoTime))
                .map(Lap::getTime).orElse("");
    }

    public String getName() {
        return name;
    }
}
class Lap{
    String time;

    public Lap(String time) {
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public int StringtoTime() {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 * 1000 +
                Integer.parseInt(parts[1]) * 1000 +
                Integer.parseInt(parts[2]);
    }


}

