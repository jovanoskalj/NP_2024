package aud6;

public class PriorityQueueEl <T> implements Comparable<PriorityQueueEl>{

    private int priority;
    private T element;

    public PriorityQueueEl(T element,int priority ) {
        this.priority = priority;
        this.element = element;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "PriorityQueueEl{" +
                "priority=" + priority +
                ", element=" + element +
                '}';
    }


    @Override
    public int compareTo(PriorityQueueEl o) {
        return Integer.compare(this.priority,o.priority);
    }
}
