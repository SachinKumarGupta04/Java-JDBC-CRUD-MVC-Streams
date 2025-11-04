import java.util.*;
import java.util.stream.*;

class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    
    public Product(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    @Override
    public String toString() {
        return "Product[ID=" + id + ", Name=" + name + ", Category=" + category + 
               ", Price=$" + price + ", Qty=" + quantity + "]";
    }
}

public class ProductStreamOps {
    public static void main(String[] args) {
        // Create a large dataset of products
        List<Product> products = Arrays.asList(
            new Product(1, "Laptop", "Electronics", 1200.00, 15),
            new Product(2, "Mouse", "Electronics", 25.50, 50),
            new Product(3, "Keyboard", "Electronics", 75.00, 30),
            new Product(4, "Monitor", "Electronics", 300.00, 20),
            new Product(5, "Desk Chair", "Furniture", 250.00, 10),
            new Product(6, "Desk", "Furniture", 450.00, 8),
            new Product(7, "Bookshelf", "Furniture", 180.00, 12),
            new Product(8, "Headphones", "Electronics", 85.00, 40),
            new Product(9, "Webcam", "Electronics", 120.00, 25),
            new Product(10, "Office Lamp", "Furniture", 45.00, 35)
        );
        
        System.out.println("=== All Products ===");
        products.forEach(System.out::println);
        
        // Group products by category
        System.out.println("\n=== Products Grouped by Category ===");
        Map<String, List<Product>> groupedByCategory = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory));
        
        groupedByCategory.forEach((category, productList) -> {
            System.out.println("\nCategory: " + category);
            productList.forEach(p -> System.out.println("  " + p));
        });
        
        // Find maximum price product
        System.out.println("\n=== Product with Maximum Price ===");
        Optional<Product> maxPriceProduct = products.stream()
            .max(Comparator.comparingDouble(Product::getPrice));
        maxPriceProduct.ifPresent(p -> 
            System.out.println("Most expensive: " + p));
        
        // Calculate average price per category
        System.out.println("\n=== Average Price by Category ===");
        Map<String, Double> avgPriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.averagingDouble(Product::getPrice)
            ));
        
        avgPriceByCategory.forEach((category, avgPrice) -> 
            System.out.println(category + ": $" + String.format("%.2f", avgPrice)));
        
        // Calculate total inventory value
        System.out.println("\n=== Total Inventory Value ===");
        double totalValue = products.stream()
            .mapToDouble(p -> p.getPrice() * p.getQuantity())
            .sum();
        System.out.println("Total Inventory Value: $" + String.format("%.2f", totalValue));
        
        // Find products with price above average
        System.out.println("\n=== Products Above Average Price ===");
        double avgPrice = products.stream()
            .mapToDouble(Product::getPrice)
            .average()
            .orElse(0.0);
        System.out.println("Average Price: $" + String.format("%.2f", avgPrice));
        
        products.stream()
            .filter(p -> p.getPrice() > avgPrice)
            .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
            .forEach(System.out::println);
        
        // Count products by category
        System.out.println("\n=== Product Count by Category ===");
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.counting()
            ));
        
        countByCategory.forEach((category, count) -> 
            System.out.println(category + ": " + count + " products"));
    }
}
