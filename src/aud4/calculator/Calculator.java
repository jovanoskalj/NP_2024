package aud4.calculator;

import java.util.Scanner;

public class Calculator {
    private double result;
    private Strategy strategy;

    public Calculator() {
        this.result = 0.0;
    }

    public double getResult() {
        return result;
    }

    public String execute(char operation, double value) throws UnknownOperatorException {
        if (operation == '+'){
            strategy=new Addition();
        }
        else if  (operation == '-'){
            strategy=new Substraction();
        }
        else if  (operation == '*'){
            strategy=new Multiplication();
        }
        else if  (operation == '/'){
            strategy=new Division();
        }
        else{
            throw new UnknownOperatorException(operation);
        }
        result=strategy.calculate(result,value);
        return String.format("result %c %.2f = $.2f",operation,value,result);
    }

    public String init() {
        return String.format("result = %.2f", result);
    }

    @Override
    public String toString() {
        return String.format("Updated result: %.2f", result);
    }
}
class Addition implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1+num2;
    }
}
class Multiplication implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1*num2;
    }
}
class Division implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1/num2;
    }
}
class Substraction implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1-num2;
    }
}
class UnknownOperatorException extends Exception{
    public UnknownOperatorException(char operator) {
        super(String.format("This operator %c is not valid",operator));
    }
}
//citanje od SI
class CalculatorTest{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());
            while(true)
            {
                String line = scanner.nextLine();
                if(line.equalsIgnoreCase("r")){
                    System.out.println(calculator.getResult());
                }
                String []parts = line.split("\\s+");
                char operator = parts[0].charAt(0);
                double value = Double.parseDouble(parts[1]);
                try {
                    String result = calculator.execute(operator,value);
                    System.out.println(result);
                    System.out.println(calculator);
                    System.out.println("(Y/N)");

                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }

            String line = scanner.nextLine();
            if(line.equalsIgnoreCase("n")){
                break;
            }
        }
    }
}