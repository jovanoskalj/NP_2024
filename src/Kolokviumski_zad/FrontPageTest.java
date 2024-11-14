package Kolokviumski_zad;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

abstract class NewsItem {
    public String title;
    public Date datePublished;
    Category category;

    public NewsItem(String title, Date datePublished, Category category) {
        this.title = title;
        this.datePublished = datePublished;
        this.category = category;
    }
    public abstract String getTeaser();

    @Override
    public String toString() {
        return getTeaser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsItem)) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(title, newsItem.title) && Objects.equals(datePublished, newsItem.datePublished) && Objects.equals(category, newsItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, datePublished, category);
    }
}


class Category implements Comparable<Category> {
    public String name;

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class TextNewsItem extends NewsItem {

    public String text;


    public TextNewsItem(String title, Date datePublished, Category category, String text) {
        super(title, datePublished, category);
        this.text = text;
    }

    @Override
    public String getTeaser() {
        long minutesAgo = Duration.between(datePublished.toInstant(), Instant.now()).toMinutes();
        String shortText = text.length() > 80 ? text.substring(0, 80) : text;
        return String.format("%s\n%d\n%s\n", title, minutesAgo, shortText);
    }


}

class MediaNewsItem extends NewsItem {
    public String url;
    public int views;

    public MediaNewsItem(String title, Date datePublished, Category category, String url, int views) {
        super(title, datePublished, category);
        this.url = url;
        this.views = views;
    }


    @Override
    public String getTeaser() {
        long minutesAgo = Duration.between(datePublished.toInstant(), Instant.now()).toMinutes();
        return String.format("%s\n%d\n%s\n%d\n", title, minutesAgo, url, views);
    }
}
class FrontPage{
    List <NewsItem> newsItemsList;
    Category [] categories;

    public FrontPage(Category[] categories) {
        this.categories = new Category[categories.length];
        System.arraycopy(categories, 0, this.categories, 0, categories.length);
        newsItemsList = new ArrayList<>();
    }

    public FrontPage() {
        newsItemsList = new ArrayList<>();
    }
    void addNewsItem(NewsItem newsItem){
        newsItemsList.add(newsItem);
    }
    public List<NewsItem> listByCategory(Category category){
        return newsItemsList.stream().filter(i->i.category.equals(category)).collect(Collectors.toList());
    }
    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        boolean categoryExists = Arrays.stream(categories).anyMatch(c -> c.name.equals(category));
        if (!categoryExists) {
            throw new CategoryNotFoundException(category);
        }

        return newsItemsList.stream()
                .filter(i -> i.category.name.equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        newsItemsList.forEach(sb::append);

        return sb.toString();
    }
}
class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException(String category) {
        super(String.format("Category %s was not found",category));
    }
}

