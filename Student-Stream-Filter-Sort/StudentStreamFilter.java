import java.util.*;
import java.util.stream.*;

class Student {
    private String name;
    private double marks;
    private int rollNumber;
    
    public Student(String name, double marks, int rollNumber) {
        this.name = name;
        this.marks = marks;
        this.rollNumber = rollNumber;
    }
    
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public int getRollNumber() { return rollNumber; }
    
    @Override
    public String toString() {
        return "Student[Name=" + name + ", Marks=" + marks + "%, Roll=" + rollNumber + "]";
    }
}

public class StudentStreamFilter {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Alice", 85.5, 101),
            new Student("Bob", 72.0, 102),
            new Student("Charlie", 91.5, 103),
            new Student("David", 68.0, 104),
            new Student("Eve", 78.5, 105),
            new Student("Frank", 95.0, 106),
            new Student("Grace", 82.0, 107),
            new Student("Henry", 74.5, 108)
        );
        
        System.out.println("All Students:");
        students.forEach(System.out::println);
        
        // Filter students scoring above 75%
        System.out.println("\n=== Students scoring above 75% ===");
        List<Student> filteredStudents = students.stream()
            .filter(s -> s.getMarks() > 75)
            .sorted((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks())) // Sort by marks (descending)
            .collect(Collectors.toList());
        
        System.out.println("\nFiltered and Sorted Students:");
        filteredStudents.forEach(System.out::println);
        
        // Display only names
        System.out.println("\nStudent Names (scoring above 75%):");
        students.stream()
            .filter(s -> s.getMarks() > 75)
            .sorted((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()))
            .map(Student::getName)
            .forEach(System.out::println);
        
        // Statistics
        System.out.println("\n=== Statistics ===");
        long count = students.stream()
            .filter(s -> s.getMarks() > 75)
            .count();
        System.out.println("Number of students scoring above 75%: " + count);
        
        double avgMarks = students.stream()
            .filter(s -> s.getMarks() > 75)
            .mapToDouble(Student::getMarks)
            .average()
            .orElse(0.0);
        System.out.println("Average marks of filtered students: " + String.format("%.2f", avgMarks) + "%");
    }
}
