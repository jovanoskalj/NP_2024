package lab5_2024;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    private String id;
    private List<Integer> grades;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public String getId() {
        return id;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public double getAverageGrade() {
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public int getCoursesPassed() {
        return grades.size();
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    @Override
    public String toString() {
        return String.format("Student{id='%s', grades=%s}", id, grades);
    }
}

class Faculty {
    private Map<String, Student> students;

    public Faculty() {
        this.students = new HashMap<>();
    }

    public void addStudent(String id, List<Integer> grades) {
        if (students.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Student with ID %s already exists", id));
        }
        students.put(id, new Student(id, grades));
    }

    public void addGrade(String id, int grade) {
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException("Student with ID " + id + " does not exist");
        }
        students.get(id).addGrade(grade);
    }

    public Set<Student> getStudentsSortedByAverageGrade() {
        return students.values().stream()
                .sorted(Comparator.comparingDouble(Student::getAverageGrade).reversed()
                        .thenComparing(Student::getCoursesPassed, Comparator.reverseOrder())
                        .thenComparing(Student::getId, Comparator.reverseOrder()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<Student> getStudentsSortedByCoursesPassed() {
        return students.values().stream()
                .sorted((s1, s2) -> {
                    int cmp = Integer.compare(s2.getCoursesPassed(), s1.getCoursesPassed());
                    if (cmp != 0) return cmp;
                    cmp = Double.compare(s2.getAverageGrade(), s1.getAverageGrade());
                    if (cmp != 0) return cmp;
                    return s2.getId().compareTo(s1.getId());
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


}

public class SetsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Faculty faculty = new Faculty();

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] tokens = input.split("\\s+");
            String command = tokens[0];

            switch (command) {
                case "addStudent":
                    String id = tokens[1];
                    List<Integer> grades = new ArrayList<>();
                    for (int i = 2; i < tokens.length; i++) {
                        grades.add(Integer.parseInt(tokens[i]));
                    }
                    try {
                        faculty.addStudent(id, grades);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "addGrade":
                    String studentId = tokens[1];
                    int grade = Integer.parseInt(tokens[2]);
                    faculty.addGrade(studentId, grade);
                    break;

                case "getStudentsSortedByAverageGrade":
                    System.out.println("Sorting students by average grade");
                    Set<Student> sortedByAverage = faculty.getStudentsSortedByAverageGrade();
                    for (Student student : sortedByAverage) {
                        System.out.println(student);
                    }
                    break;

                case "getStudentsSortedByCoursesPassed":
                    System.out.println("Sorting students by courses passed");
                    Set<Student> sortedByCourses = faculty.getStudentsSortedByCoursesPassed();
                    for (Student student : sortedByCourses) {
                        System.out.println(student);
                    }
                    break;

                default:
                    break;
            }
        }

        scanner.close();
    }
}