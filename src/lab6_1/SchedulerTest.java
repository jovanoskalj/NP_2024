package lab6_1;

import java.util.*;
import java.util.stream.Collectors;

public class SchedulerTest {


    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            Scheduler<String> scheduler = new Scheduler<String>();
            Date now = new Date();
            scheduler.add(new Date(now.getTime()-7200000), jin.next());
            scheduler.add(new Date(now.getTime()-3600000), jin.next());
            scheduler.add(new Date(now.getTime()-14400000), jin.next());
            scheduler.add(new Date(now.getTime()+7200000), jin.next());
            scheduler.add(new Date(now.getTime()+14400000), jin.next());
            scheduler.add(new Date(now.getTime()+3600000), jin.next());
            scheduler.add(new Date(now.getTime()+18000000), jin.next());
            System.out.println(scheduler.getFirst());
            System.out.println(scheduler.getLast());
        }
        if ( k == 3 ) { //test Scheduler with String
            Scheduler<String> scheduler = new Scheduler<String>();
            Date now = new Date();
            scheduler.add(new Date(now.getTime()-7200000), jin.next());
            scheduler.add(new Date(now.getTime()-3600000), jin.next());
            scheduler.add(new Date(now.getTime()-14400000), jin.next());
            scheduler.add(new Date(now.getTime()+7200000), jin.next());
            scheduler.add(new Date(now.getTime()+14400000), jin.next());
            scheduler.add(new Date(now.getTime()+3600000), jin.next());
            scheduler.add(new Date(now.getTime()+18000000), jin.next());
            System.out.println(scheduler.next());
            System.out.println(scheduler.last());
            ArrayList<String> res = scheduler.getAll(new Date(now.getTime()-10000000), new Date(now.getTime()+17000000));
            Collections.sort(res);
            for ( String t : res ) {
                System.out.print(t+" , ");
            }
        }
        if ( k == 4 ) {//test Scheduler with ints complex
            Scheduler<Integer> scheduler = new Scheduler<Integer>();
            int counter = 0;
            ArrayList<Date> to_remove = new ArrayList<Date>();

            while ( jin.hasNextLong() ) {
                Date d = new Date(jin.nextLong());
                int i = jin.nextInt();
                if ( (counter&7) == 0 ) {
                    to_remove.add(d);
                }
                scheduler.add(d,i);
                ++counter;
            }
            jin.next();

            while ( jin.hasNextLong() ) {
                Date l = new Date(jin.nextLong());
                Date h = new Date(jin.nextLong());
                ArrayList<Integer> res = scheduler.getAll(l,h);
                Collections.sort(res);

                // easy and fast solution for UTC problem
                String low = l.toString(), high = h.toString();
                low = low.replace("UTC", "GMT");
                high = high.replace("UTC", "GMT");

                System.out.println(low+" <: "+print(res)+" >: "+high);
            }
            System.out.println("test");
            ArrayList<Integer> res = scheduler.getAll(new Date(0),new Date(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
            for ( Date d : to_remove ) {
                scheduler.remove(d);
            }
            res = scheduler.getAll(new Date(0),new Date(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
        }
    }

    private static <T> String print(ArrayList<T> res) {
        if ( res == null || res.size() == 0 ) return "NONE";
        StringBuffer sb = new StringBuffer();
        for ( T t : res ) {
            sb.append(t+" , ");
        }
        return sb.substring(0, sb.length()-3);
    }


}

class Scheduler<T> {

    Map<Date, T> schedules;

    public Scheduler() {
        schedules = new TreeMap<>();
    }

    public void add(Date d, T t) {
        schedules.put(d, t);
    }

    public boolean remove(Date d) {
        if (!schedules.containsKey(d)) {
            return false;
        }
        schedules.remove(d);
        return true;
    }

    public T next() {
        Date temp = new Date();
        return schedules.get(schedules.keySet().stream().filter(
                d -> d.after(temp)
        ).min(Date::compareTo).orElse(null));
    }
    public T last(){
        Date temp = new Date();
        return schedules.get(schedules.keySet().stream().filter(
                d -> d.before(temp)
        ).max(Date::compareTo).orElse(null));
    }
    public ArrayList<T> getAll(Date begin, Date end) {
        ArrayList<T> list = new ArrayList<>();
        schedules.keySet().
                stream().
                filter(t->t.before(end) && t.after(begin)).
                forEach(l->list.add(schedules.get(l)));

        return list;
    }
    public T getFirst(){
       return schedules.get(schedules.keySet().stream().min(Date::compareTo).orElse(null));
    }
    public T getLast(){
        return schedules.get(schedules.keySet().stream().max(Date::compareTo).orElse(null));
    }
}




