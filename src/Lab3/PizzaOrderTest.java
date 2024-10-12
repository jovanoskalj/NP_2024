package Lab3;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}

interface Item {
    int getPrice();
    String getType();
}

class ExtraItem implements Item {
    String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (!(type.equalsIgnoreCase("Ketchup") || type.equalsIgnoreCase("Coke"))) {
            throw new InvalidExtraTypeException("InvalidExtraTypeException");
        }
        this.type = type;
    }

    @Override
    public int getPrice() {
        if (type.equalsIgnoreCase("Ketchup")) {
            return 3;
        }
        return 5;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtraItem)) return false;
        ExtraItem extraItem = (ExtraItem) o;
        return Objects.equals(type, extraItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

class PizzaItem implements Item {
    String type;
    int price;
    ArrayList<String> pizza_types = new ArrayList<>();

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        pizza_types.add("Pepperoni");
        pizza_types.add("Standard");
        pizza_types.add("Vegetarian");

        if (!pizza_types.contains(type)) {
            throw new InvalidPizzaTypeException();
        }
        this.type = type;
        this.price = setPrice(type);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return type;
    }

    public int setPrice(String type) {
        switch (type) {
            case ("Standard"):
                return 10;
            case ("Pepperoni"):
                return 12;
            case "Vegetarian":
                return 8;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PizzaItem)) return false;
        PizzaItem pizzaItem = (PizzaItem) o;
        return price == pizzaItem.price && Objects.equals(type, pizzaItem.type) && Objects.equals(pizza_types, pizzaItem.pizza_types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, price, pizza_types);
    }
}


class InvalidExtraTypeException extends Exception {
    public InvalidExtraTypeException(String message) {
        super(message);
    }
}

class InvalidPizzaTypeException extends Exception {
    public InvalidPizzaTypeException() {
    }
}
class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item) {

    }
}
class OrderLockedException extends Exception{
    public OrderLockedException() {
    }
}
class EmptyOrder extends Exception{
    public EmptyOrder() {
    }
}
class Order {
    public boolean locked;
    ArrayList<Product> products;
    public Order() {
        this.products= new ArrayList<Product>();
        this.locked=false;
    }




    public void  addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if(count>10){
            throw  new ItemOutOfStockException(item);
        }
        if(locked){
            throw  new OrderLockedException();
        }
        for (Product p : products){
            if(p.getItem().equals(item)){
                p.setItem(item);
                p.setCount(count);
                return;
            }
        }
        products.add(new Product(item,count));
    }
    int getPrice(){
       return products.stream().mapToInt(i->i.getItem()
               .getPrice()*i
               .getCount())
               .sum();
    }
    public void displayOrder(){

        for(int i=0;i<products.size();i++){
            System.out.println(String.format("%3d.%-15sx%2d%5d$", i+1, products.get(i).item.getType() , products.get(i).count, products.get(i).item.getPrice() * products.get(i).count));
        }
        System.out.println(String.format("%-22s%5d$","Total:",getPrice()));
    }
    public void removeItem(int ind) throws OrderLockedException {
        if(locked){
            throw  new OrderLockedException();
        }
       products.remove(ind);
    }
    public void lock() throws EmptyOrder {
        if (products.isEmpty()){
            throw  new EmptyOrder();
        }
        locked=true;
    }
}
class Product{
    Item item;
    int count;

    public Product(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}