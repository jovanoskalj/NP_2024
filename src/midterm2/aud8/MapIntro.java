package midterm2.aud8;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapIntro {
    public static void main(String[] args) {
        //Kolekcija na parovi KLUC-VREDNOST

        //mora klucot da e comparable
        //izbegnuva duplikat klucevi
        //mapata e sortirana spored klucot
        //o(logn) za dodavanje, o(logn) contains, o(nlogn) iteriranje
        Map<String,String> treeMap = new TreeMap<>();
        treeMap.put("FINKI", "FINKI");
        treeMap.put("NP", "Napredno");

        //HASH MAPA
        //o(1) dodavanje, o(1) contains, o(n) iteriranje
        // klucot mora da ima override hash code funkcija
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("FINKI", "FINKI");
        hashMap.put("NP", "Napredno");

        //LINKED HASH MAP - SE ZADRZUVA REDOSLEDOT
        //o(1) dodavanje, o(1) contains, o(n) iteriranje
        // klucot mora da ima override hash code funkcija
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("FINKI","FINKI");
        linkedHashMap.put("NP", "Napredno");

        //Mapi se koristat za broenje, pojavuvanje na nekoj elementi.
        //Za grupiranje isto taka
    }
}
