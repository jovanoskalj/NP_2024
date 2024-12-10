package midterm2.aud8;

import java.util.*;

//TreeSet - zadaci kaj so se barat da nema duplikati i
// vrednostite da se sortirani

public class SetIntro {
    public static void main(String[] args) {
        //Set ne dozvoluva duplikati
        //pristap O(logn) iteriranje 0(nlogn) --> TREE SET
        // ,dodavanje O(logn), brisenje O(nlogn)
        Set<Integer> treeIntSet = new TreeSet<>(Comparator.reverseOrder());//mora comparable
        for(int i=1;i<=10;i++){
            treeIntSet.add(i);
        }
        //ne zadrzuva redosled (se izmestuva),
        // najednostavna vremenska kompleksnost,
        // ako se bara so O(n), nema duplikati
        Set<Integer> hasIntSet = new HashSet<>();
        for(int i=1;i<=10;i++){
            hasIntSet.add(i);
        }
        //ako sakame da se zapazit redosledot  na
        // vnesuvanje koristime LinkedHashSet
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        for(int i=1;i<=10;i++){
            linkedHashSet.add(i);
        }


    }
}
