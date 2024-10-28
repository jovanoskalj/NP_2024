package Kolokviumski_zad;

import java.awt.color.CMMException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
//21,15,25,27,24
class ShapesApplication{
List<Canvas> canvases;
    public int readCanvases(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
         canvases =  bufferedReader.lines().map(r->Canvas.createCanvas(r)).collect(Collectors.toList());

         return canvases.stream().mapToInt(canvas->canvas.squareList.size()).sum();

    }

    public void printLargestCanvasTo(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        Canvas max = canvases.stream().max(Comparator.naturalOrder()).get();
        pw.println(max);
        pw.flush();
    }

    public ShapesApplication() {
    }

}
class Canvas implements Comparable<Canvas>{
    public String canvasId;
    public  List<Square> squareList;

    public Canvas(String canvasId, List<Square> squareList) {
        this.canvasId = canvasId;
        this.squareList = new ArrayList<>(squareList);
    }

    public  static  Canvas createCanvas(String line){
        String []parts = line.split("\\s+");
        String id=parts[0];
        List<Square> squares= Arrays.stream(parts).skip(1).map(Integer::parseInt).map(Square::new).collect(Collectors.toList());

        return new Canvas(id,squares);

    }

    public String getCanvasId() {
        return canvasId;
    }

    public void setCanvasId(String canvasId) {
        this.canvasId = canvasId;
    }

    public List<Square> getSquareList() {
        return squareList;
    }

    public void setSquareList(List<Square> squareList) {
        this.squareList = squareList;
    }
    public int total_squares_perimetar(){
        return  squareList.stream().mapToInt(r->r.getSide()).sum();
    }
    @Override
    public String toString() {
        return String.format("%s %d %d",canvasId, squareList.size(), total_squares_perimetar());
    }

    @Override
    public int compareTo(Canvas o) {
        return Integer.compare(this.total_squares_perimetar(),o.total_squares_perimetar());
    }
}
class Square {
    int side;

    public Square(int side) {
        this.side = side;
    }

    public int getSide() {
        return side*4;
    }

    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "Square{" +
                "side=" + side +
                '}';
    }




}
