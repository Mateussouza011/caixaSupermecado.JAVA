import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory {
    private Map<String, Product> products;
    private List<Product> productList;

    public Inventory() {
        products = new HashMap<>();
        productList = new ArrayList<>();
        initializeProducts();
    }

    public void addProduct(Product product) {
        products.put(product.getCode(), product);
        productList.add(product);
    }

    public void removeProduct(String code) {
        Product product = products.remove(code);
        if (product != null) {
            productList.remove(product);
        }
    }

    public Product getProductByCode(String code) {
        return products.get(code);
    }

    public List<Product> searchProductByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> getAllProductsSorted() {
        return productList.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByValidity(int days) {
        return productList.stream()
                .filter(p -> p.getValidity() <= days)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByStock(int quantity) {
        return productList.stream()
                .filter(p -> p.getStockQuantity() <= quantity)
                .collect(Collectors.toList());
    }

    private void initializeProducts() {
        // Create 10 Food Products
        addProduct(new FoodProduct("Arroz", "F001", 5.50, 100, 180));
        addProduct(new FoodProduct("Feijão", "F002", 4.80, 80, 180));
        addProduct(new FoodProduct("Macarrão", "F003", 3.20, 120, 365));
        addProduct(new FoodProduct("Azeite de Oliva", "F004", 15.00, 50, 720));
        addProduct(new FoodProduct("Açúcar", "F005", 2.50, 150, 540));
        addProduct(new FoodProduct("Café", "F006", 8.00, 70, 365));
        addProduct(new FoodProduct("Farinha de Trigo", "F007", 4.00, 90, 270));
        addProduct(new FoodProduct("Leite", "F008", 3.50, 60, 10));
        addProduct(new FoodProduct("Manteiga", "F009", 5.00, 40, 60));
        addProduct(new FoodProduct("Ovos", "F010", 7.00, 30, 15));

        // Create 10 Electronic Products
        addProduct(new ElectronicProduct("Televisão", "E001", 1500.00, 10, 730));
        addProduct(new ElectronicProduct("Geladeira", "E002", 2000.00, 5, 1095));
        addProduct(new ElectronicProduct("Micro-ondas", "E003", 400.00, 15, 730));
        addProduct(new ElectronicProduct("Notebook", "E004", 2500.00, 8, 365));
        addProduct(new ElectronicProduct("Smartphone", "E005", 1200.00, 20, 365));
        addProduct(new ElectronicProduct("Máquina de Lavar", "E006", 1800.00, 4, 1095));
        addProduct(new ElectronicProduct("Aspirador de Pó", "E007", 250.00, 12, 365));
        addProduct(new ElectronicProduct("Ventilador", "E008", 150.00, 25, 365));
        addProduct(new ElectronicProduct("Impressora", "E009", 600.00, 6, 365));
        addProduct(new ElectronicProduct("Fogão", "E010", 800.00, 7, 730));
    }
}
