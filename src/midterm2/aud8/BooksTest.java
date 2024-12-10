package midterm2.aud8;

import java.util.*;
import java.util.stream.Collectors;

class Book {
    String title;
    String category;
    float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    //    @Override not useful for the second method
//    public int compareTo(Book o) {
//        int res = this.title.compareTo(o.title);
//        if(res==0){
//            return Float.compare(this.price, o.price);
//        }
//        return res;
//    }
}

class BookCollection {
    //    final Comparator<Book> titleAndPriceComparator = new Comparator<Book>() {
//        @Override
//        public int compare(Book o1, Book o2) {
//            int res = o1.title.compareTo(o2.title);
//            if(res==0)
//                return Float.compare(o1.price, o2.price);
//            return res;
//        }
//    };
//    final Comparator<Book> priceComparator  = new Comparator<Book>() {
//        @Override
//        public int compare(Book o1, Book o2) {
//            int res = Float.compare(o1.price,o2.price);
//            if(res==0){
//                return o1.title.compareTo(o2.title);
//            }
//            return res;
//        }
//    };
    List<Book> books;
    final Comparator<Book> titleAndPriceComparator = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
    final Comparator<Book> priceComparator = Comparator.comparing(Book::getPrice)
            .thenComparing(Book::getTitle);
    //na kraj mozis da dodadis .reversed() za vo obraten redosled t.e opagjacki

    public BookCollection() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void printByCategory(String category) {
        books.stream()
                .filter(book -> book.category.equals(category))
                .sorted(titleAndPriceComparator)
                .forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        return books.stream()
                .sorted(priceComparator)
                .limit(n)
                .collect(Collectors.toList());
    }
}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}