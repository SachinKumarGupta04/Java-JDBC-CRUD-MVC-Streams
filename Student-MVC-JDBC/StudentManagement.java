import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Model
class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    
    public Student(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    @Override
    public String toString() {
        return "Student[ID=" + id + ", Name=" + name + ", Email=" + email + ", Age=" + age + "]";
    }
}

// Data Access Layer
class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/school";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.executeUpdate();
        }
    }
    
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("age")
                ));
            }
        }
        return students;
    }
    
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ?, age = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setInt(4, student.getId());
            pstmt.executeUpdate();
        }
    }
    
    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

// View (Console-based)
class StudentView {
    public void displayStudents(List<Student> students) {
        System.out.println("\n=== Students List ===");
        for (Student student : students) {
            System.out.println(student);
        }
    }
    
    public void displayMessage(String message) {
        System.out.println(message);
    }
}

// Controller
class StudentController {
    private StudentDAO dao;
    private StudentView view;
    
    public StudentController(StudentDAO dao, StudentView view) {
        this.dao = dao;
        this.view = view;
    }
    
    public void addStudent(String name, String email, int age) {
        try {
            Student student = new Student(0, name, email, age);
            dao.addStudent(student);
            view.displayMessage("Student added successfully!");
        } catch (SQLException e) {
            view.displayMessage("Error adding student: " + e.getMessage());
        }
    }
    
    public void listAllStudents() {
        try {
            List<Student> students = dao.getAllStudents();
            view.displayStudents(students);
        } catch (SQLException e) {
            view.displayMessage("Error fetching students: " + e.getMessage());
        }
    }
    
    public void updateStudent(int id, String name, String email, int age) {
        try {
            Student student = new Student(id, name, email, age);
            dao.updateStudent(student);
            view.displayMessage("Student updated successfully!");
        } catch (SQLException e) {
            view.displayMessage("Error updating student: " + e.getMessage());
        }
    }
    
    public void deleteStudent(int id) {
        try {
            dao.deleteStudent(id);
            view.displayMessage("Student deleted successfully!");
        } catch (SQLException e) {
            view.displayMessage("Error deleting student: " + e.getMessage());
        }
    }
}

// Main Application
public class StudentManagement {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        StudentView view = new StudentView();
        StudentController controller = new StudentController(dao, view);
        
        // Example operations
        controller.addStudent("John Doe", "john@example.com", 20);
        controller.addStudent("Jane Smith", "jane@example.com", 22);
        controller.listAllStudents();
        controller.updateStudent(1, "John Updated", "john.updated@example.com", 21);
        controller.listAllStudents();
    }
}
