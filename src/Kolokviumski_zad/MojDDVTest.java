package Kolokviumski_zad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(String message) {
        super(message);
    }
}

enum TYPE {
    A, B, V
}

class Item {
    int price;
    TYPE type;

    static final double TAX_A = 0.18;
    static final double TAX_B = 0.05;
    static final double TAX_V = 0.00;
    static final double DDV_RETURN = 0.15;

    public Item(int price, TYPE type) {
        this.price = price;
        this.type = type;
    }

    public double getTaxReturn() {
        switch (type) {
            case A:
                return price * TAX_A * DDV_RETURN;
            case B:
                return price * TAX_B * DDV_RETURN;
            case V:
                return price * TAX_V * DDV_RETURN;
            default:
                return 0.00;
        }
    }

    public double totalPrice() {
        return this.price + getTaxReturn();
    }
}

class Amount {
    String id;
    List<Item> items;

    public Amount(String id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public Amount(String id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public static Amount createAmount(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Item> items = new ArrayList<>();
        double total = 0;
        Amount amount = new Amount(id);

        for (int i = 1; i < parts.length; i += 2) {
            int price = Integer.parseInt(parts[i]);
            TYPE type = TYPE.valueOf(parts[i + 1]);
            Item item = new Item(price, type);
            items.add(item);
            total += price;
        }

        if (total > 30000) {
            throw new AmountNotAllowedException(
                    String.format("Receipt with amount %s is not allowed to be scanned", formatDouble(total))
            );
        }

        return new Amount(id, items);
    }

    public double sumReceipt() {
        return items.stream().mapToDouble(Item::totalPrice).sum();
    }

    public double getTotalTaxReturn() {
        return items.stream().mapToDouble(Item::getTaxReturn).sum();
    }

    @Override
    public String toString() {
        double sumAmounts = items.stream().mapToDouble(item -> item.price).sum();
        double taxReturn = getTotalTaxReturn();
        if(taxReturn==0){
            return String.format("%s %s %s",
                    id,
                    formatDouble(sumAmounts),
                    "0.00");
        }

        // Format the sum and tax return to display up to 2 decimal places but remove unnecessary ".00"
        return String.format("%s %s %s",
                id,
                formatDouble(sumAmounts),
                formatDouble(taxReturn)
        );
    }

    // Helper method to format the double values
    private static String formatDouble(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);  // No decimal places if it's a whole number
        } else {
            return String.format("%.2f", value);  // Two decimal places otherwise
        }
    }
}

class MojDDV {
    List<Amount> amounts;

    public MojDDV() {
        amounts = new ArrayList<>();
    }

    public void readRecords(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        br.lines().forEach(line -> {
            try {
                Amount amount = Amount.createAmount(line);
                amounts.add(amount);
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void printTaxReturns(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        amounts.forEach(amount -> pw.println(amount.toString()));
        pw.flush();
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