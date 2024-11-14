package Lab6;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
class IntegerList{
    List<Integer> list;

    public IntegerList() {
        list = new ArrayList<>();
    }
    public IntegerList(Integer... numbers){
       list= new ArrayList<>(Arrays.asList(numbers));
    }
    public void add (int el, int indx){
        if (indx>list.size()){
            IntStream.range(list.size(),indx)
                    .forEach(i->list.add(i,0));
        }
        list.add(indx,el);
    }

    public int remove(int idx){
        return list.remove(idx);
    }
    public void set(int el,int indx){
        list.set(indx,el);
    }
    public int get(int inx){
        return list.get(inx);
    }
    public int size(){
        return list.size();
    }
    public int count(int el){
        return (int) list.stream().filter(i->i.equals(el)).count();
    }
    public void removeDuplicates(){
        Collections.reverse(list);
        list=list.stream()
                .distinct().collect(Collectors.toList());
        Collections.reverse(list);
    }
    public int sumFirst(int k){
//        int sum=0;
//       for(int i=0;i<k;i++){
//           sum+=list.get(i);
//       }
//       return sum;
        return list.stream()
                .limit(k)
                .mapToInt(i->i)
                .sum();
    }
    public  int sumLast(int k){
        return list.stream()
                .skip((long)list.size()-k)
                .mapToInt(i->i)
                .sum();
    }
    void checkIndex(int idx){
        if(idx<0 || idx>=list.size())
            throw new ArrayIndexOutOfBoundsException();
    }
    void shiftRight(int idx, int k){
        checkIndex(idx);
        int target=(idx+k)%list.size();
        if(target<0)target=list.size()+target;
        Integer el=list.remove(idx);
        list.add(target,el);
    }
    void shiftLeft(int idx, int k){
        checkIndex(idx);
        int target=(idx-k)%list.size();
        if(target<0)target=list.size()+target;
        Integer el=list.remove(idx);
        list.add(target,el);
    }

    public IntegerList addValue(int value) {
        IntegerList il=new IntegerList();
        il.list= list.stream()
                .map(i->i+value)
                .collect(Collectors.toList());
        return il;
    }
}