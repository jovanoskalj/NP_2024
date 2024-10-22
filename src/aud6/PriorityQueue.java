package aud6;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue <T>{
    private List<PriorityQueueEl<T>> elements;

    public PriorityQueue() {
        elements = new ArrayList<>();
    }
    public void add(T item, int priority){
        PriorityQueueEl<T> newElement = new PriorityQueueEl<>(item,priority);
        int i;
        for(i=0;i<elements.size();i++){
            if(newElement.compareTo(elements.get(i))<=0) break;

        }

        elements.add(i,newElement);
    }
    public T remove(){
        if(elements.isEmpty()) return null;
        return elements.remove(elements.size()-1).getElement();
    }
}
class PriorityQueueTest{
    public static void main(String[] args) {
        PriorityQueue<String> priorityQueue = new PriorityQueue<String>();
        priorityQueue.add("middle1", 49);
        priorityQueue.add("middle2", 50);
        priorityQueue.add("middle3", 57);
        priorityQueue.add("middle4", 48);
        priorityQueue.add("middle5", 60);

        String element;
        while((element=priorityQueue.remove())!=null){
            System.out.println(element);
        }


    }
}
