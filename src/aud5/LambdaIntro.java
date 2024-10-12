package aud5;

public class LambdaIntro {
    public static void main(String[] args) {

        //anonimni  so alt enter gi prajs vo lambda
        Operation addition = new Operation() {
            @Override
            public int execute(int a, int b) {
                return a + b;
            }
        };
        Operation substraction = new Operation() {
            @Override
            public int execute(int a, int b) {
                return a - b;
            }
        };
        //lambda
        Operation division = (a, b) -> a / b;

    }
}
