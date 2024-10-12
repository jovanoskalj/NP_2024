package aud5;

import java.util.ArrayList;

public class ArrayListIntro {
    public static void main(String[] args) {
        ArrayList<Integer> integers ; //integers=null
        integers = new ArrayList<>();
        for(int i=0;i<10;i++){
            integers.add(i);
        }
        System.out.println(integers); //litsata si imat sama toString
        //ako kreirame lista od nasha klasa, toas klasata trb da sodrzit toString method
        integers.removeIf(in->in.equals(3));
        //izbrisi go ak pocvit so tri


    }
}
