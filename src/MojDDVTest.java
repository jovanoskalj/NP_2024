//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class MojDDVTest {
//
//    public static void main(String[] args) {
//
//        MojDDV mojDDV = new MojDDV();
//
//        System.out.println("===READING RECORDS FROM INPUT STREAM===");
//        mojDDV.readRecords(System.in);
//
//        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
//        mojDDV.printTaxReturns(System.out);
//
//    }
//}
//class MojDDV{
//    private List<Bill> bills;
//
//    public MojDDV(List<Bill> bills) {
//        bills=new ArrayList<>();
//    }
//
//    public MojDDV() {
//    }
//
//    public void readRecords(InputStream in) {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//        bufferedReader.lines().forEach(line->{
//            try{
//            Bill bill = Bill.createBill(line);
//                bills.add(bill);
//            }
//            catch (AmountNotAllowedException e){
//                System.out.println(e.getMessage());
//            }
//        });
//
//
//    }
//
//    public void printTaxReturns(PrintStream out) {
//        PrintWriter pw = new PrintWriter(out);
//        bills.stream().forEach(i->pw.print(bills.));
//    }
//}
//class Bill{
//    public int ID;
//    static List<Item> items;
//
//
//    public static Bill createBill(String line){
//        String []part = line.split("\\s+");
//        int ID= Integer.parseInt(part[0]);
//        String type;
//        int price;
//        Arrays.stream(part).skip(1).forEach(i->{
//            if(Character.isLetter(i.charAt(0))){
//                items.get(items.size()-1).setType;
//            }
//        });
//
//    }
//}
//enum TYPE{
//    A,
//    B,
//    V
//}
//class Item{
//    public int price;
//    public TYPE type;
//
//    public Item(int price, TYPE type) {
//        this.price = price;
//        this.type = type;
//    }
//
//    public TYPE getType() {
//        return type;
//    }
//
//    public void setType(TYPE type) {
//        this.type = type;
//    }
//}
//class AmountNotAllowedException extends Exception{
//    public AmountNotAllowedException() {
//        super(String.format("Receipt with amount %d is not allowed to be scanned"));
//    }
//}