package aud3.date;

import java.util.Objects;

public class Date implements  Comparable<Date> {
    private  static final int FIRST_YEAR = 1800;
    private static  final  int LAST_YEAR=2500;
    private  static  final  int DAYS_IN_YEAR=365;
    private  static  final int[] daysOfMonth={31,28,31,30,31,30,31,31,30,31,30,31};
    private static final int[] daysTillFirstOfMonth;
    private  static  final int[] daysTillFirstOfYear;
    private int days;

    static{
        daysTillFirstOfMonth= new int[12];
        for(int i=1;i<=12;i++){
            daysTillFirstOfMonth[i]=daysTillFirstOfMonth[i-1]+daysOfMonth[i-1];
        }
        int totalYears=LAST_YEAR-FIRST_YEAR+1;
        daysTillFirstOfYear=new  int[totalYears];
        int currentYear = FIRST_YEAR;
        for(int i=1;i<totalYears;i++){
            if(isLeapYear(currentYear)){
                daysTillFirstOfYear[i]=daysTillFirstOfYear[i-1]+DAYS_IN_YEAR+1;
            }
            else{
                daysTillFirstOfYear[i]=daysTillFirstOfYear[i-1]+DAYS_IN_YEAR;
            }
        }
    }

    private  static boolean isLeapYear(int years){
        return (years%400==0||(years%4==0 && years%100!=0));
    }

    public Date(int days) {
        this.days = days;
    }
    public Date(int day,int month,int years){
        if(isDateInvalid(years)){
            throw new RuntimeException();
        }
        int days=0;
        days+=daysTillFirstOfYear[years-FIRST_YEAR];
        days+=daysTillFirstOfMonth[month-1];
        if(isLeapYear(years) && month>=2){
            days++;
        }
        days+=day;
        this.days=days;

    }

    private boolean isDateInvalid(int year){
        return year<FIRST_YEAR || year>LAST_YEAR;
    }
    public  Date increment(int days){
        return  new Date(this.days+days);
    }
    public  int substract(Date date){
        return  Math.abs(this.days-date.days);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;
        Date date = (Date) o;
        return days == date.days;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days);
    }


    @Override
    public int compareTo(Date o) {
        return Integer.compare(this.days,o.days);
    }

    @Override
    public String toString() {
        int allDays=days;
        int i;
        for(i=0;i<daysTillFirstOfYear.length;i++){
            if(daysTillFirstOfYear[i]>=allDays) break;
        }
        allDays-=daysTillFirstOfMonth[i-1];
        int year=i-1+FIRST_YEAR;
        if(isLeapYear(year)){
            allDays++;
        }
        for(i=0;i<daysTillFirstOfMonth.length;i++){
            if(daysTillFirstOfMonth[i]>=allDays) break;
        }
        allDays-=daysTillFirstOfMonth[i-1];
        int month=i;
        allDays-=daysTillFirstOfMonth[i-1];
        return String.format("%02d.%02m.%4d",allDays,month,year);
    }
}
