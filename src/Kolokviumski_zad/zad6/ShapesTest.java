package Kolokviumski_zad.zad6;

import java.util.*;

import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}
interface Scalable{
    void scale(float scaleFactor);
}
interface Stackable{
    float weight();
}
class Canvas{

List<Shape> shapeList;

    public Canvas() {
        shapeList = new ArrayList<>();
    }


    void add(String id, Color color, float radius){
        shapeList.add(new Circle(id,color,radius));
        shapeList.sort(Comparator.comparingDouble(Shape::weight));
    }
    void add(String id, Color color, float width, float height){
        shapeList.add(new Square(id,color,width,height));
        shapeList.sort(Comparator.comparingDouble(Shape::weight));
    }

    public void scale(String id, float scaleFactor) {
        for (Shape shape : shapeList) {
            if(shape.id.equals(id)){
                Shape s=shape;
                shapeList.remove(s);
                shape.scale(scaleFactor);
                shapeList.add(s);
                break;
            }
        }
//        list.sort(Comparator.comparingDouble(Shape::weight));
    }
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for (Shape shape : shapeList) {
            sb.append(shape.toString()).append("\n");
        }
        return sb.toString();
    }
}
abstract class Shape implements Scalable,Stackable,Comparator<Shape> {

    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public void scale(float scaleFactor) {

    }

    @Override
    public float weight() {
        return 0;
    }

    @Override
    public abstract int compare(Shape o1, Shape o2);

}
class Circle extends Shape implements Scalable,Stackable {

    float radius;

    public Circle(String id, Color color,float radius) {
        super(id, color);
        this.radius=radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius = radius*scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (Math.PI * radius*radius);
    }

    @Override
    public int compare(Shape o1, Shape o2) {
        return Float.compare(o2.weight(),o1.weight());
    }
    @Override
    public String toString() {
        return String.format("C: %-5s%-10s%10.2f",id,color,weight());
    }
}
class Square extends Shape implements Scalable, Stackable{
float width;
float height;
    public Square(String id, Color color,float width,float height) {
        super(id, color);
        this.height=height;
        this.width=width;
    }


    @Override
    public float weight() {
        return width*height;
    }

    @Override
    public int compare(Shape o1, Shape o2) {
        return Float.compare(o2.weight(),o1.weight());

    }
    @Override
    public void scale(float scaleFactor) {
        width=width*scaleFactor;
        height=height*scaleFactor;
    }
    public String toString() {
        return String.format("R: %-5s%-10s%10.2f",id,color,weight());
    }
}