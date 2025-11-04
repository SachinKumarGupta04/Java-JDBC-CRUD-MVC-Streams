import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Product Management System ===");
            System.out.println("1. Create Product");
            System.out.println("2. Read Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1: createProduct(scanner); break;
                case 2: readProducts(); break;
                case 3: updateProduct(scanner); break;
                case 4: deleteProduct(scanner); break;
                case 5: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void createProduct(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.print("Enter product name: ");
            pstmt.setString(1, scanner.next());
            System.out.print("Enter price: ");
            pstmt.setDouble(2, scanner.nextDouble());
            System.out.print("Enter quantity: ");
            pstmt.setInt(3, scanner.nextInt());
            
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void readProducts() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            
            System.out.println("\n--- Products List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                 ", Name: " + rs.getString("name") +
                                 ", Price: $" + rs.getDouble("price") +
                                 ", Quantity: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void updateProduct(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            String sql = "UPDATE products SET price = ?, quantity = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.print("Enter product ID: ");
            int id = scanner.nextInt();
            System.out.print("Enter new price: ");
            pstmt.setDouble(1, scanner.nextDouble());
            System.out.print("Enter new quantity: ");
            pstmt.setInt(2, scanner.nextInt());
            pstmt.setInt(3, id);
            
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void deleteProduct(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            String sql = "DELETE FROM products WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            System.out.print("Enter product ID to delete: ");
            pstmt.setInt(1, scanner.nextInt());
            
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
