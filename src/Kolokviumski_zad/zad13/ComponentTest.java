package Kolokviumski_zad.zad13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.switchComponents(pos1, pos2);
        System.out.println(window);
    }
}

class Component implements Comparable<Component>{
    String color;
    int weight;
    List<Component> components;

    public Component() {
        components=new ArrayList<>();
    }

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        components=new ArrayList<>();
    }

    public void  addComponent(Component component){
        components.add(component);
        components.sort(Component::compareTo);
    }

    @Override
    public int compareTo(Component o) {
        if(this.weight!=o.weight){
            return Integer.compare(this.weight,o.weight);
        }
       return this.color.compareTo(o.color);
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String print(String prefix){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%d:%s\n",prefix,weight,color));
        components.stream().forEach(c->sb.append(c.print(String.format("---%s",prefix))));
        return sb.toString();
    }
}
class Window{
    String name;
    List<Component> components;

    public Window(String name) {
        this.name = name;
        components=new ArrayList<>();
    }
    void addComponent(int position, Component component) throws InvalidPositionException {
        if (position < components.size()) {  // Change this condition to check position against size
            throw new InvalidPositionException(String.format("Invalid position %d, already taken!", position + 1));
        } else if (position == components.size()) {  // If position matches size, add component at end
            components.add(component);
        } else {
            throw new InvalidPositionException("Cannot skip positions while adding components.");
        }
    }

    void changeColor(int weight, String color){
        components.stream().filter(i->i.weight<weight)
                .forEach(i->i.setColor(color));
    }
    void switchComponents(int pos1, int pos2) {
        // Adjust positions to zero-based index
        pos1--;
        pos2--;

        // Check if pos1 and pos2 are within bounds
        if (pos1 < 0 || pos2 < 0 || pos1 >= components.size() || pos2 >= components.size()) {
            System.out.println("Error: One or both positions are out of bounds.");
            return;
        }

        // Perform the swap
        Component first = components.get(pos1);
        Component second = components.get(pos2);
        components.set(pos1, second);
        components.set(pos2, first);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW "+ name+"\n");
        for (int i = 0; i < components.size(); i++) {
            sb.append(i+1+":"+components.get(i).print(""));
        }
        return sb.toString();
    }
}
class InvalidPositionException extends Exception{
    public InvalidPositionException(String message) {
        super(message);
    }
}