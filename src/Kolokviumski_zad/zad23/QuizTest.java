package Kolokviumski_zad.zad23;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            try{
                quiz.addQuestion(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }

        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
      }
        else if (testCase==2) {
            try{
                quiz.answerQuiz(answers, System.out);
            }
            catch (InvalidOperationException e){
                System.out.println(e.getMessage());
            }
        }

         else{
                System.out.println("Invalid test case");
            }
        }

}
enum TYPE{
    TF,
    MC
}
abstract class Question implements Comparable<Question>{
    TYPE type;
    String text;
    int points;
    String answer;

    public Question(TYPE type, String text, int points, String answer) {
        this.type = type;
        this.text = text;
        this.points = points;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return String.format("%s Points %d Answer: %s",text,points,answer);
    }
    public abstract double calculatePoints(Boolean isTrue);
}
class TFQuestion extends Question{

    public TFQuestion(TYPE type, String text, int points, String answer) {
        super(type, text, points, answer);
        this.type = TYPE.TF;
    }

    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points,o.points);
    }

    @Override
    public String toString() {

        return String.format("True/False Question: %s Points: %d Answer: %s",text,points,answer);
    }

    @Override
    public double calculatePoints(Boolean isTrue) {
        if(!isTrue){
            return 0;
        }
        return this.points;
    }
}
class MCQuestion extends Question{

    public MCQuestion(TYPE type, String text, int points, String answer) {
        super(type, text, points, answer);
        this.type=TYPE.MC;

    }
    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points,o.points);
    }

    @Override
    public String toString() {
        return "Multiple Choice Question: " + super.toString();
    }

    @Override
    public double calculatePoints(Boolean isTrue) {
        if(!isTrue){
            return this.points*0.2 * (-1);
        }
        return this.points;
    }
}

class Quiz{
    List<Question> questionList;
    List<String> possibleAnswers=new ArrayList<>();

    public Quiz() {
        this.questionList = new ArrayList<>();
        possibleAnswers.add("A");
        possibleAnswers.add("B");
        possibleAnswers.add("C");
        possibleAnswers.add("D");
        possibleAnswers.add("E");

    }
    public void addQuestion(String questionData) throws InvalidOperationException {
        String []parts = questionData.split(";");
        TYPE type = TYPE.valueOf(parts[0]);
        String text = parts[1];
        int points= Integer.parseInt(parts[2]);
        String answer= parts[3];
        if(type==TYPE.MC && !possibleAnswers.contains(answer)){
            throw new InvalidOperationException(String.format("%s is not allowed option for this question",answer));
        }
        else if(type == TYPE.MC){
           questionList.add(new MCQuestion(type,text,points,answer));
        }
        else {
            questionList.add(new TFQuestion(type,text,points,answer));
        }

    }
    public void printQuiz(OutputStream os){
        PrintWriter printWriter = new PrintWriter(os);
        questionList.stream().sorted(Comparator.reverseOrder())
                .forEach(q -> printWriter.println(q.toString()));

        printWriter.flush();

    }
    void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException {
        if(questionList.size()!=answers.size()){
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        PrintWriter printWriter = new PrintWriter(os);
        double sum =0;
        int[] counter = new int[1];
        counter[0]=1;
        for(int i=0;i<questionList.size();i++){
           if(questionList.get(i).answer.equals(answers.get(i))){
               sum+=questionList.get(i).calculatePoints(true);
               printWriter.println(String.format("%d. %.2f",counter[0]++,questionList.get(i).calculatePoints(true)));
           }
           else{
               sum+=questionList.get(i).calculatePoints(false);
               printWriter.println(String.format("%d. %.2f",counter[0]++,questionList.get(i).calculatePoints(false)));
           }
        }
        printWriter.println(String.format("Total points: %.2f",sum));
        printWriter.flush();
    }
}
class InvalidOperationException extends Exception{
    public InvalidOperationException(String message) {
        super(message);
    }
}