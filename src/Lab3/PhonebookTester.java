package Lab3;
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;


class InvalidNameException extends Exception {
    public String name;

    public InvalidNameException(String name) {
        super(name);
        this.name = name;
    }
}


class InvalidNumberException extends Exception {
    public InvalidNumberException() {
    }
}

class MaximumSizeExceddedException extends Exception {
    public MaximumSizeExceddedException() {
    }
}

class InvalidFormatException extends Exception {
    public InvalidFormatException() {
    }
}

class Contact implements Comparable<Contact> {

    public String name;
    public String[] phonenumber;

    public String[] prefixes = {"070", "071", "072", "075", "076", "077", "078"};

    public Contact(String name, String... phonenumber) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException {

        if (name.length() < 4 || name.length() > 10 || !name.matches("[A-Za-z0-9]+")) {
            throw new InvalidNameException(name);
        }
        this.name = name;

        for (String phone : phonenumber) {
            if (!isValidPhoneNumber(phone)) {
                throw new InvalidNumberException();
            }
        }


        if (phonenumber.length > 5) {
            throw new MaximumSizeExceddedException();
        }

        this.phonenumber = new String[phonenumber.length];

        System.arraycopy(phonenumber, 0, this.phonenumber, 0, phonenumber.length);
    }

    public boolean isValidPhoneNumber(String number) {
        return number.length() == 9 && number.matches("\\d{9}") && Arrays.stream(prefixes).anyMatch(number::startsWith);
    }

    public String getName() {
        return name;
    }
    public boolean hasNumber(String s) {
        return Arrays.stream(phonenumber).anyMatch(i -> i.startsWith(s));
    }
    public String[] getNumbers() {
        return Arrays.stream(phonenumber).sorted().toArray(String[]::new);
    }

    public void addNumber(String phone) {
        if (isValidPhoneNumber(phone)) {
            phonenumber = Arrays.copyOf(phonenumber, phonenumber.length + 1);
            phonenumber[phonenumber.length - 1] = phone;
        }
    }

    public static Contact valueOf(String s) throws InvalidFormatException {
        try {
            return new Contact(s);
        } catch (Exception e) {
            throw new InvalidFormatException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.
                append(name).
                append("\n").
                append(phonenumber.length).
                append("\n");

        Arrays.stream(phonenumber).
                sorted().
                forEach(i -> sb.append(i).append("\n"));

        return sb.toString();
    }

    public Contact(String name) throws InvalidNameException {
        if (name.length() < 4 || name.length() > 10 || !name.matches("[A-Za-z0-9]+")) {
            throw new InvalidNameException(name);
        }
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o; // Manually cast to Contact
        return Objects.equals(name, contact.name) &&
                Arrays.equals(phonenumber, contact.phonenumber) &&
                Arrays.equals(prefixes, contact.prefixes);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(phonenumber);
        result = 31 * result + Arrays.hashCode(prefixes);
        return result;
    }


    @Override
    public int compareTo(Contact other) {
        return this.name.compareTo(other.name);
    }


}

class PhoneBook {
    public static Contact[] contacts;

    public PhoneBook() {
        this.contacts = new Contact[0];
    }

    public void addContact(Contact c) throws MaximumSizeExceddedException, InvalidNameException {

        if (contacts.length == 250) {
            throw new MaximumSizeExceddedException();
        }

        if (Arrays.stream(contacts).anyMatch(i -> i.getName().equals(c.getName()))) {
            throw new InvalidNameException(c.name);
        }

        contacts = Arrays.copyOf(contacts, contacts.length + 1);
        contacts[contacts.length - 1] = c;

    }

    public Contact getContactForName(String name) {
        return Arrays.stream(contacts).
                filter(i -> i.getName().equals(name)).
                findFirst().
                orElse(null);
    }

    public int numberOfContacts(){
        return contacts.length;
    }
    public Contact[] getContacts() {
        return Arrays.stream(contacts).
                sorted(Comparator.comparing(Contact::getName)).
                toArray(Contact[]::new);
    }
    public boolean removeContact(String name) {
        Contact[] filteredContacts = Arrays.stream(contacts)
                .filter(contact -> !contact.getName().equals(name))
                .toArray(Contact[]::new);

        if (filteredContacts.length == contacts.length) {
            // No contact was removed, return false
            return false;
        } else {
            contacts = filteredContacts;
            return true;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        Arrays.stream(contacts)
                .sorted()
                .forEach(i->sb.append(i).append("\n"));

        return sb.toString();
    }

    public static boolean saveAsTextFile(PhoneBook phonebook, String path) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(path));

            for (Contact c : phonebook.getContacts()) {
                bf.write(c.getName() + "\n");
                bf.write(String.join(" ", c.getNumbers()) + "\n");
            }

            return true;
        }
        catch (IOException io){
            return false;
        }

    }

    public static PhoneBook loadFromTextFile(String path) throws InvalidFormatException {
        PhoneBook phonebook = new PhoneBook();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String name = line.trim();
                String[] numbers = br.readLine().split(" ");
                phonebook.addContact(new Contact(name, numbers));
            }
        } catch (IOException | InvalidNameException | InvalidNumberException | MaximumSizeExceddedException e) {
            throw new InvalidFormatException();
        }
        return phonebook;
    }


    public Contact[] getContactsForNumber(String number){
        return Arrays.stream(contacts).filter(i -> i.hasNumber(number)).toArray(Contact[]::new);
    }
}


public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine())
            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
        /*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */
    }

    private static void testContact(Scanner jin) throws Exception {
        // Test invalid names
        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
            } catch (InvalidNameException e) {
                // Expected behavior, so do nothing
            }
        }

        // Test invalid phone numbers
        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
            } catch (InvalidNumberException e) {
                // Expected behavior, so do nothing
            }
        }

        // Test too many numbers
        String nums[] = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        } catch (MaximumSizeExceddedException e) {
            // Expected behavior, so do nothing
        }

        // Randomly generated contacts and numbers
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}