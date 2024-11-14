package Kolokviumski_zad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException | InvalidOpeningException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

abstract class Archive {
    private int id;
    private LocalDate dateArchived;


    public Archive(int id, LocalDate dateArchived) {
        this.id = id;
        this.dateArchived = dateArchived;
    }

    public Archive(int id) {
        this.id = id;
    }

    public abstract LocalDate open(int id, LocalDate date) throws InvalidOpeningException;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(LocalDate dateArchived) {
        this.dateArchived = dateArchived;
    }
}

class LockedArchive extends Archive {
    private LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public LocalDate open(int id, LocalDate date) throws InvalidOpeningException {
        if (date.isBefore(dateToOpen)) {
            throw new InvalidOpeningException(String.format("Item %d cannot be opened before %s", id, dateToOpen));
        }
        return date;

    }

    public LocalDate getDateToOpen() {
        return dateToOpen;
    }

    public void setDateToOpen(LocalDate dateToOpen) {
        this.dateToOpen = dateToOpen;
    }
}

class SpecialArchive extends Archive {
    private int maxOpen;
    private int countOpen;


    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.countOpen = 0;
    }

    @Override
    public LocalDate open(int id, LocalDate date) throws InvalidOpeningException {
        if (countOpen >= maxOpen) {
            throw new InvalidOpeningException(String.format("Item %d cannot be opened more than %d times", id, maxOpen));

        }
        countOpen++;
        return date;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public void setMaxOpen(int maxOpen) {
        this.maxOpen = maxOpen;
    }

    public int getCountOpen() {
        return countOpen;
    }

    public void setCountOpen(int countOpen) {
        this.countOpen = countOpen;
    }
}

class ArchiveStore {
    private final List<Archive> archiveList;
    StringBuilder sb;

    public ArchiveStore() {
        this.archiveList = new ArrayList<Archive>();
        sb = new StringBuilder();
    }

    public void archiveItem(Archive item, LocalDate date) {
        item.setDateArchived(date);
        archiveList.add(item);
        sb.append(String.format("Item %d archived at %s\n", item.getId(), date));
    }

    void openItem(int id, LocalDate date) throws NonExistingItemException, InvalidOpeningException {
        Archive item = archiveList.stream().filter(i -> i.getId() == id)
                .findFirst().orElseThrow(() -> new NonExistingItemException(id));

        try {
            item.open(id, date);
            sb.append(String.format("Item %d opened at %s\n", id, date));

        } catch (InvalidOpeningException e) {
            sb.append(e.getMessage()).append("\n");
        }


    }

    public String getLog() {
        return sb.toString();
    }
}

class InvalidOpeningException extends Exception {
    public InvalidOpeningException(String message) {
        super(message);
    }

}

class NonExistingItemException extends Exception {
    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist", id));
    }
}