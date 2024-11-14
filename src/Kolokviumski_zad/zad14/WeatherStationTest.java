package Kolokviumski_zad.zad14;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

class WeatherStation {
    public int days;
    List<Measurments> measurments;

    public WeatherStation(int days) {
        this.days = days;
        measurments = new ArrayList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {

        if (measurments.isEmpty()) {
            measurments.add(new Measurments(temperature, wind, humidity, visibility, date));
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);

        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime(measurments.get(measurments.size()-1).getDate());
        if(Math.abs(now.getTimeInMillis()-lastTime.getTimeInMillis())<2.5*60*1000){
            return;
        }
        measurments.add(new Measurments(temperature, wind, humidity, visibility, date));

        //REMOVE
        ArrayList<Measurments> toRemove = new ArrayList<>();
        for(Measurments measurment: measurments){
            Calendar c = Calendar.getInstance();
            c.setTime(measurment.getDate());
            if(Math.abs(now.getTimeInMillis()-c.getTimeInMillis())>days*86400000){
                toRemove.add(measurment);
            }

        }
        measurments.removeAll(toRemove);

    }
    public int total(){
        return measurments.size();
    }
    public void status (Date from, Date to){
        ArrayList<Measurments> newArr=new ArrayList<>();
        double avg=0.0;
        int ct=0;
        for (Measurments weather : measurments) {
            Date d=weather.getDate();
            if((d.after(from) || d.equals(from))&&(d.before(to)||d.equals(to))){
                newArr.add(weather);
                avg+=weather.temperature;
                ct++;
            }
        }
        if(newArr.isEmpty()){
            throw new RuntimeException();
        }
        StringBuilder sb=new StringBuilder();
        for (Measurments weather : newArr) {
            sb.append(weather).append("\n");
        }
        System.out.print(sb.toString());
        System.out.printf("Average temperature: %.2f\n",avg/ct);

    }
}

class Measurments {
    public float temperature;
    public float wind;
    public float humidity;
    public float visibility;
    Date date;

    public Measurments(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String dateString=date.toString();
        dateString=dateString.replace("UTC","GMT");
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s",temperature,wind,humidity,visibility,dateString);
    }
}