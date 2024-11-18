package Kolokviumski_zad.zad2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);
    }
}

class ShapesApplication {
    double maxArea;
    List<Canvas> canvases;

    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
        this.canvases = new ArrayList<>();
    }

    void readCanvases(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        this.canvases = bufferedReader.lines()
                .map(line -> {
                    try {
                        return new Canvas(line, maxArea);
                    } catch (IrregularCanvasException e) {
                        System.out.println(e.getMessage());
                        return null; // Skip invalid canvases
                    }
                })
                .filter(Objects::nonNull) // Remove null canvases
                .collect(Collectors.toList());
    }

    void printCanvases(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        this.canvases.stream()
                .sorted(Comparator.reverseOrder()) // Descending order
                .forEach(pw::println);
        pw.flush();
    }
}

enum TYPE {
    S, C
}

class Canvas implements Comparable<Canvas> {
    String id;
    List<Shape> shapes;

    public Canvas(String line, double maxArea) throws IrregularCanvasException {
        shapes = new ArrayList<>();
        String[] parts = line.split("\\s+");
        this.id = parts[0];
        for (int i = 1; i < parts.length; i += 2) {
            TYPE type = TYPE.valueOf(parts[i]);
            int size = Integer.parseInt(parts[i + 1]);
            Shape shape = (type == TYPE.C) ? new Circle(type, size) : new Square(type, size);

            if (shape.getSize() > maxArea) {
                throw new IrregularCanvasException(id, maxArea);
            }
            shapes.add(shape);
        }
    }

    int getCirclesCount() {
        return (int) shapes.stream().filter(s -> s.getType() == TYPE.C).count();
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics stats = shapes.stream().mapToDouble(Shape::getSize).summaryStatistics();
        return String.format("%s %d %d %d %.2f %.2f %.2f",
                id,
                shapes.size(),
                getCirclesCount(),
                shapes.size() - getCirclesCount(),
                stats.getMin(),
                stats.getMax(),
                stats.getAverage());
    }

    @Override
    public int compareTo(Canvas o) {
        double thisTotalArea = this.shapes.stream().mapToDouble(Shape::getSize).sum();
        double otherTotalArea = o.shapes.stream().mapToDouble(Shape::getSize).sum();
        return Double.compare( thisTotalArea,otherTotalArea); // Descending order
    }
}

abstract class Shape {
    TYPE type;
    int size;

    public Shape(TYPE type, int size) {
        this.type = type;
        this.size = size;
    }

    public TYPE getType() {
        return type;
    }

    public abstract double getSize(); // Abstract method to compute area
}

class Square extends Shape {
    public Square(TYPE type, int size) {
        super(type, size);
    }

    @Override
    public double getSize() {
        return size * size; // Area of the square
    }
}

class Circle extends Shape {
    public Circle(TYPE type, int size) {
        super(type, size);
    }

    @Override
    public double getSize() {
        return size * size * Math.PI; // Area of the circle
    }
}

class IrregularCanvasException extends Exception {
    public IrregularCanvasException(String id, double maxArea) {
        super(String.format("Canvas %s has a shape with area larger than %.2f", id, maxArea));
    }
}
