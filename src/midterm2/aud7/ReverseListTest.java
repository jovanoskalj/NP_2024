package midterm2.aud7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ReverseListTest {
    public static <T> void reversePrint(Collection<T> collection){
        List<T> lista = new ArrayList<>(collection);
        for (int i=lista.size()-1;i>=0;i--){
            System.out.println(lista.get(i));
        }
    }
    public static <T> void reversePrint2(Collection<T> collection){
        List<T> lista = new ArrayList<>(collection);
        Collections.reverse(lista);
        lista.forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<Integer> ints = List.of(1,2,3,4,5,6,7,8);
        reversePrint2(ints);
    }
}
