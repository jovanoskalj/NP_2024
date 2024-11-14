package Kolokvium23_24.zad7;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}
class UnsupportedFormatException extends Exception{
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}
class TimeTable{
    List<Time> timeList = new ArrayList<>();

    public TimeTable() {
    }

    public void readTimes(InputStream in) throws UnsupportedFormatException,InvalidTimeException{
        BufferedReader  br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(line->{

            Time t;
            try {
                t = new Time(line);
                timeList.add(t);
            } catch (UnsupportedFormatException | InvalidTimeException e) {
                System.out.println(e.getMessage());
            }


        });
    }

    public void writeTimes(PrintStream out, TimeFormat timeFormat) {
        PrintWriter pw = new PrintWriter(out);
        Collections.sort(timeList);
        for(Time time: timeList){
            if (timeFormat == TimeFormat.FORMAT_24) {
                pw.println(time);
            } else {
                pw.println(time.toStringAMPM());
            }
        }
        pw.flush();


    }
}
class Time implements Comparable<Time> {

    int hour;
    int minutes;

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }


    public Time  (String line) throws UnsupportedFormatException, InvalidTimeException {

        String []parts = line.split("\\.");
        if(parts.length==1){
            parts=line.split(":");
        }
        if(parts.length==1){
            throw new UnsupportedFormatException(line);
        }
        this.hour= Integer.parseInt(parts[0]);
        this.minutes = Integer.parseInt(parts[1]);
        if (hour < 0 || hour > 23 || minutes < 0 || minutes > 59)
            throw new InvalidTimeException(line);
    }

    public String toStringAMPM() {
        String part = "AM";
        int h = hour;
        if (h == 0) {
            h += 12;
        } else if (h == 12) {
            part = "PM";
        } else if (h > 12) {
            h -= 12;
            part = "PM";
        }
        return String.format("%2d:%02d %s", h, minutes, part);
    }

    @Override
    public String toString() {
        return String.format("%2d:%02d", hour, minutes);
    }

    @Override
    public int compareTo(Time o) {
        if (hour == o.hour)
            return minutes - o.minutes;
        else
            return hour - o.hour;
    }
}