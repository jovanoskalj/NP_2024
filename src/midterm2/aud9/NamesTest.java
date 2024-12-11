package midterm2.aud9;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class NamesTest {
    public static Map<String, Integer> createFromFile(String path) throws FileNotFoundException {
        InputStream is = new FileInputStream(path);
        Map<String, Integer> result = new HashMap<>();
        Scanner sc = new Scanner(is);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String parts[] = line.split("\\s+");
            String name = parts[0];
            Integer freq = Integer.parseInt(parts[1]);
            result.put(name, freq);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //KOD
        Map<String, Integer> boyNameMap = createFromFile("/./");
        Map<String, Integer> girlNameMap = createFromFile("/./");

        Set<String> allNames = new HashSet<>();
        allNames.addAll(boyNameMap.keySet());
        allNames.addAll(girlNameMap.keySet());
        Map<String, Integer> unisexNames = new HashMap<>();
        allNames.stream()
                .filter(name -> boyNameMap.containsKey(name) && girlNameMap.containsKey(name))
                .forEach(name -> {
                    int maleCount = boyNameMap.get(name);
                    int femaleCount = girlNameMap.get(name);
                    int total = maleCount + femaleCount;

                    // Печатење
                    System.out.printf("%s : Male: %d Female: %d Total: %d\n", name, maleCount, femaleCount, total);

                    // Додавање во мапата
                    unisexNames.put(name, total);
                });
        Set<Map.Entry<String, Integer>> entrySet = unisexNames.entrySet();
        unisexNames.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s : %d", entry.getKey(), entry.getValue())));


    }
}
