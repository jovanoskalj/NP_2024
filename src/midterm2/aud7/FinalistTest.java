package midterm2.aud7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class InvalidPickerArguments extends Exception{

    public InvalidPickerArguments(String s) {
        super(s);
    }
}

class FinalistPicker{
    int finalist;
    static Random RANDOM = new Random();

    public FinalistPicker(int finalist) {
        this.finalist = finalist;
    }

    public List<Integer> pick(int n) throws InvalidPickerArguments {
        if (n > finalist) {
            throw  new InvalidPickerArguments("The number cannot exceed the number of finalists!");
        }
        if(n<=0){
            throw new InvalidPickerArguments("n must be a positive number");
        }
        //List<Integer> variable = RANDOM.ints(0,1,finalist+1).boxed().collect(Collectors.toList());
        List<Integer> pickedFinalists = new ArrayList<>();
        while (pickedFinalists.size()!=n){
            int pick = RANDOM.nextInt(finalist)+1; //[0-bound)+1 = [1,bound]
            if (!pickedFinalists.contains(pick)){
                pickedFinalists.add(pick);
            }
        }
        return pickedFinalists;
    }
}
public class FinalistTest {
    public static void main(String[] args) {
        FinalistPicker picker = new FinalistPicker(5);
        try {
            System.out.println(picker.pick(2));
        } catch (InvalidPickerArguments e) {
            System.out.println(e.getMessage());
        }
    }
}
