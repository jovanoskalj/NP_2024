package midterm2.aud7;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArrangeLetter {
    public static String arrangeSentence(String sentence){
        return Arrays.stream(sentence.split("\\s+"))
                .map(word->arrangeWord(word))
                .sorted()
                .collect(Collectors.joining("\n"));

    }
    public static String arrangeWord(String  word){
      return word.chars()
              .sorted()
              .mapToObj(ch->String.valueOf((char)ch))
              .collect(Collectors.joining());
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();
        System.out.println(arrangeSentence(sentence));
    }
}

