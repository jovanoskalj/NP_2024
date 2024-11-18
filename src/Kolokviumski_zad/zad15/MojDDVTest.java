package Kolokviumski_zad.zad15;
import java.io.*;
import java.util.*;



class MojDDV {
    List<User> users;

    public MojDDV() {
        users = new ArrayList<>();
    }

    public void readRecords(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        bufferedReader.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            String id = parts[0];
            List<Product> products = new ArrayList<>();

            int totalAmount = 0;
            try {
                for (int i = 1; i < parts.length; i += 2) {
                    int price = Integer.parseInt(parts[i]);
                    String type = parts[i + 1];

                    Product product = new Product(price, type);
                    products.add(product);
                    totalAmount += price;
                }

                if (totalAmount > 30000) {
                    throw new AmountNotAllowedException(totalAmount);
                }

                users.add(new User(id, products));  // Only add if total amount is valid
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void printTaxReturns(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        users.forEach(printWriter::println);
        printWriter.flush();
    }

}

class User {
    String id;
    List<Product> products;

    public User(String id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public int totalSum() {
        return products.stream().mapToInt(p -> p.itemPrice).sum();
    }

    public double totalTax() {
        return products.stream().mapToDouble(Product::tax).sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f", id, totalSum(), totalTax());
    }
}

class Product {
    int itemPrice;
    String taxType;

    public Product(int itemPrice, String taxType) {
        this.itemPrice = itemPrice;
        this.taxType = taxType;
    }

    public double tax() {
        double taxRate;
        switch (taxType) {
            case "A":
                taxRate = 0.18;
                break;
            case "B":
                taxRate = 0.05;
                break;
            case "V":
                taxRate = 0.0;
                break;
            default:
                throw new IllegalArgumentException("Unknown tax type: " + taxType);
        }
        return itemPrice * taxRate * 0.15;
    }
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(int totalAmount) {
        super("Receipt with amount " + totalAmount + " is not allowed to be scanned");
    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);


    }
}
