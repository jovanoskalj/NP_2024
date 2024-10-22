package aud6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

//V-value
//E - element
//T-type
public class Box<E extends Drawable> {
    private List<E> elements;
    public static Random random = new Random();

    public Box() {
        elements = new ArrayList<>();
    }
    public void add(E element){
        elements.add(element);
    }
    public boolean isEmpty(){
        return elements.isEmpty();
    }
    public E draw(){
        if(isEmpty()) return  null;

        int index= random.nextInt(elements.size());
        E elem = elements.get(index);
        elements.remove(elem);

//        //dr reshenie
//        return elements.remove(random.nextInt(elements.size()));
        return elem;
    }
}
class BoxTest{
    public static void main(String[] args) {
        Box<Circle> box = new Box<Circle>();
        IntStream.range(0,100).forEach(i-> new Circle());
        IntStream.range(0,103).forEach(el-> System.out.println(box.draw()));

    }
}