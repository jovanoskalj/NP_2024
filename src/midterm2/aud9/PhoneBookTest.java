package midterm2.aud9;
import java.awt.*;
import java.util.*;
import java.util.List;

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String phone) {
        super(String.format("Duplicate number: %s",phone));
    }
}

class PhoneBook{
    Set<String> allPhoneNumbers;
    Map<String, Set<Contact>> contactsBySubstring;
    Map<String,Set<Contact>> contactsByName;

    public PhoneBook() {
        allPhoneNumbers = new HashSet<>();
        contactsBySubstring = new HashMap<>();
        contactsByName = new HashMap<>();
    }
    private List<String> getSubstring (String phone){
        List<String> result = new ArrayList<>();

        for (int length = 3;length<= phone.length();length++){
            for(int i=0;i<=phone.length()-length;i++){
                result.add(phone.substring(i,i+length));
            }
        }
        return result;
    }

    public void addContact(String name, String phone) throws DuplicateNumberException {
        if(allPhoneNumbers.contains(phone)){
            throw new DuplicateNumberException(phone);
        }
         allPhoneNumbers.add(phone);
        Contact c = new Contact(name,phone);
        List<String> subnumbers = getSubstring(phone);
        for (String subnumber:subnumbers){
            contactsBySubstring.putIfAbsent(subnumber,new TreeSet<>());
            contactsBySubstring.get(subnumber).add(c);
        }
        contactsByName.putIfAbsent(name,new TreeSet<>());
        contactsByName.get(name).add(c);
    }

    public void contactsByNumber(String number) {
        Set<Contact> cp = contactsBySubstring.get(number);
        if (cp == null || cp.isEmpty()) { // Проверка за null или празна множина
            System.out.println("NOT FOUND");
            return;
        }
        cp.forEach(System.out::println);
    }


    public void contactsByName(String name) {
        Set<Contact> contacts = contactsByName.get(name);
        if (contacts == null) {
            System.out.println("NOT FOUND");
            return;
        }
        contacts.forEach(System.out::println);
    }

}
class Contact implements Comparable<Contact>{
    String name;
    String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int compareTo(Contact o) {
        int res = this.name.compareTo(o.name);
        if(res==0){
            return this.phone.compareTo(o.phone);
        }
        return res;
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,phone);
    }
}
public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}


