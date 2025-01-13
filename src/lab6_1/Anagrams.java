package lab6_1;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }


    public static void findAll(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        Map<String, TreeSet<String>> allWords = new TreeMap<>();

        while(scanner.hasNextLine()) {
            String word = scanner.nextLine();

            char [] temp = word.toCharArray();
            Arrays.sort(temp);
            String sortedWord = new String(temp);

            allWords.computeIfAbsent(sortedWord, x -> new TreeSet<>());
            allWords.get(sortedWord).add(word);
        }
        allWords.values().stream()
                .filter(x -> x.size() >= 5)
                .sorted(Comparator.comparing(TreeSet::first))
                .forEach(x -> System.out.println(String.join(" ", x)));
    }
    public static boolean isAnagram(String str1,String str2){
        char[] char1=str1.toCharArray();
        char[] char2=str2.toCharArray();
        Arrays.sort(char1);
        Arrays.sort(char2);
        return Arrays.equals(char1,char2);
    }
    public static void print(Map<String,List<String>> map){
        map.values().stream()
                .filter(i->i.size()>=5)
                .forEach(i-> System.out.println(String.join(" ",i)));
    }
}