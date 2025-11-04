import java.util.*;

class Employee {
    private String name;
    private int age;
    private double salary;
    
    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }
    
    @Override
    public String toString() {
        return "Employee[Name=" + name + ", Age=" + age + ", Salary=$" + salary + "]";
    }
}

public class EmployeeSort {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 75000),
            new Employee("Bob", 25, 60000),
            new Employee("Charlie", 35, 90000),
            new Employee("David", 28, 55000),
            new Employee("Eve", 32, 80000)
        );
        
        System.out.println("Original List:");
        employees.forEach(System.out::println);
        
        // Sort by name
        System.out.println("\nSorted by Name:");
        List<Employee> sortedByName = new ArrayList<>(employees);
        sortedByName.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        sortedByName.forEach(System.out::println);
        
        // Sort by age
        System.out.println("\nSorted by Age:");
        List<Employee> sortedByAge = new ArrayList<>(employees);
        sortedByAge.sort((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
        sortedByAge.forEach(System.out::println);
        
        // Sort by salary
        System.out.println("\nSorted by Salary:");
        List<Employee> sortedBySalary = new ArrayList<>(employees);
        sortedBySalary.sort((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        sortedBySalary.forEach(System.out::println);
        
        // Sort by salary (descending)
        System.out.println("\nSorted by Salary (Descending):");
        List<Employee> sortedBySalaryDesc = new ArrayList<>(employees);
        sortedBySalaryDesc.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        sortedBySalaryDesc.forEach(System.out::println);
    }
}
