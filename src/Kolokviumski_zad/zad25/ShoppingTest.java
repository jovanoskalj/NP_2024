package Kolokviumski_zad.zad25;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

enum TYPE {
    WS,
    PS
}

abstract class Product implements Comparable<Product> {
    String productID;
    String productName;
    int productPrice;
    TYPE type;

    public Product(TYPE type, String productID, String productName, int productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.type = type;
    }

    public abstract double priceTotal();
    public abstract String calculateDiscount();
    @Override
    public String toString() {
        return String.format("%s - %.2f", productID, priceTotal());
    }

}

class WSProduct extends Product {
    int quantity;

    public WSProduct(TYPE type, String productID, String productName, int productPrice, int quantity) {
        super(type, productID, productName, productPrice);
        this.quantity = quantity;
        this.type = TYPE.WS;
    }

    @Override
    public double priceTotal() {
        return quantity * productPrice;
    }

    @Override
    public String calculateDiscount() {
        return String.format("%s - %.2f",productID,this.priceTotal()-(this.priceTotal() - this.priceTotal()*0.1));
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.priceTotal(), o.priceTotal());
    }
}

class PSProduct extends Product {
    double quantity;

    public PSProduct(TYPE type, String productID, String productName, int productPrice, double quantity) {
        super(type, productID, productName, productPrice);
        this.quantity = quantity;
        this.type = TYPE.PS;
    }

    @Override
    public double priceTotal() {
        return quantity/1000 * productPrice;
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(this.priceTotal(), o.priceTotal());
    }
    @Override
    public String calculateDiscount() {
        return String.format("%s - %.2f",productID,this.priceTotal()-(this.priceTotal() - this.priceTotal()*0.1));
    }
}

class ShoppingCart {
    List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    void addItem(String itemData) throws InvalidOperationException {
        String[] parts = itemData.split(";");
        String productID = parts[1];
        String productName = parts[2];
        int productPrice = Integer.parseInt(parts[3]);
        TYPE type = TYPE.valueOf(parts[0]);
        double quantity = Double.parseDouble(parts[4]);
        if (quantity == 0) {
            throw new InvalidOperationException(String.format("The quantity of the product with id %s can not be 0.",productID));
        }
        if (type == TYPE.PS) {
            products.add(new PSProduct(type, productID, productName, productPrice, quantity));
        } else {
            products.add(new WSProduct(type, productID, productName, productPrice, (int) quantity));
        }
    }
    void printShoppingCart(OutputStream os){
        PrintWriter printWriter = new PrintWriter(os);
        products.stream().sorted(Comparator.reverseOrder()).forEach(i->
            printWriter.println(i.toString())
        );
        printWriter.flush();
    }

    void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
        PrintWriter printWriter = new PrintWriter(os);
        if(discountItems.isEmpty()){
            throw new InvalidOperationException("There are no products with discount.");
        }
        products.stream().filter(p->discountItems.contains(Integer.parseInt(p.productID)))
                .forEach(p->{
                    printWriter.println(p.calculateDiscount());
                });
        printWriter.flush();

    }


}

class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }
}