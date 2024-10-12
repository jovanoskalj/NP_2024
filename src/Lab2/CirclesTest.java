package Lab2;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
interface Movable {


    void moveUp() throws ObjectCanNotBeMovedException ;
    TYPE getType();

    void moveLeft() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();

    int getCurrentYPosition();



}
public class CirclesTest {

    public static void main(String[] args) throws ObjectCanNotBeMovedException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                try {
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }

            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                try {
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        try {
            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        }
        catch (ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        try{
            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        }
        catch (ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }

        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        try{
            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        }
        catch (ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        try{
            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        }
        catch (ObjectCanNotBeMovedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(collection.toString());


    }


}


class MovablePoint implements Movable {

    public int x;
    public int y;

    public int xSpeed;
    public int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }


    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y < 0 || y + ySpeed > MovablesCollection.yMAX) {
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", this.x+xSpeed, this.y +ySpeed));
        }
        y += ySpeed;

    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0 || x > MovablesCollection.xMAX) {
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", this.x-xSpeed , this.y));
        }
        x -= xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x < 0 || x + xSpeed > MovablesCollection.xMAX) {
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", this.x+xSpeed, this.y ));
        }
        x += xSpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0 || y > MovablesCollection.yMAX) {
            throw new ObjectCanNotBeMovedException(String.format("Point (%d,%d) is out of bounds", this.x , this.y-ySpeed));
        }
        y -= ySpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)\n", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovablePoint)) return false;
        MovablePoint that = (MovablePoint) o;
        return x == that.x && y == that.y && xSpeed == that.xSpeed && ySpeed == that.ySpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, xSpeed, ySpeed);
    }

    public TYPE getType() {
        return TYPE.POINT;
    }
}

class MovableCircle implements Movable {
    public int radius;
    public MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }


    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        center.moveUp();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        center.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        center.moveRight();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        center.moveDown();
    }

    @Override
    public int getCurrentXPosition() {
        return center.x;
    }

    @Override
    public int getCurrentYPosition() {
        return center.y;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d\n", center.x, center.y, radius);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovableCircle)) return false;
        MovableCircle that = (MovableCircle) o;
        return radius == that.radius && Objects.equals(center, that.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, center);
    }

    public TYPE getType() {
        return TYPE.CIRCLE;
    }

    public int getRadius() {
        return radius;
    }
}


class MovablesCollection {
    public static int xMAX;
    public static int yMAX;
    public Movable[] movables;

    public static int getxMAX() {
        return xMAX;
    }

    public static void setxMax(int xMAX) {
        MovablesCollection.xMAX = xMAX;
    }

    public static int getyMAX() {
        return yMAX;
    }

    public static void setyMax(int yMAX) {
        MovablesCollection.yMAX = yMAX;
    }

    public MovablesCollection(int xMAX, int yMAX) {
        MovablesCollection.xMAX = xMAX;
        MovablesCollection.yMAX = yMAX;
        movables = new Movable[0];
    }

    public boolean isFittablePoint(int x, int y) {
        if (x < 0 || x > xMAX) {
            return false;
        }
        if (y < 0 || y > yMAX) {
            return false;
        }
        return true;
    }

    public boolean isFittableCircle(int x, int y, int radius) {
        if (x - radius < 0 || x + radius > xMAX) {
            return false;
        }
        if (y - radius < 0 || y + radius > yMAX) {
            return false;
        }
        return true;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if (m.getType() == TYPE.POINT) {
            if (!isFittablePoint(m.getCurrentXPosition(), m.getCurrentYPosition())) {
                throw new MovableObjectNotFittableException(String.format("Movable point with center (%d,%d) can not be fitted into the collection", m.getCurrentXPosition(), m.getCurrentYPosition()));

            }
        } else {
            if (!isFittableCircle(m.getCurrentXPosition(), m.getCurrentYPosition(), ((MovableCircle) m).radius)) {
                throw new MovableObjectNotFittableException(String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection", m.getCurrentXPosition(), m.getCurrentYPosition(), ((MovableCircle) m).getRadius()));
            }

        }
        movables = Arrays.copyOf(movables, movables.length + 1);
        movables[movables.length - 1] = m;

    }

    void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) throws ObjectCanNotBeMovedException {
        for (Movable m : movables) {
            if (type == m.getType()) {
                if (direction == DIRECTION.UP) {
                    m.moveUp();
                } else if (direction == DIRECTION.DOWN) {
                    m.moveDown();
                } else if (direction == DIRECTION.RIGHT) {
                    m.moveRight();
                } else {
                    m.moveLeft();
                }
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Collection of movable objects with size %d:\n", movables.length));
        for (Movable m : movables) {
            sb.append(m);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovablesCollection)) return false;
        MovablesCollection that = (MovablesCollection) o;
        return Arrays.equals(movables, that.movables);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(movables);
    }

}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

