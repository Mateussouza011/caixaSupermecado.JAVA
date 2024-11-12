public abstract class Product {
    protected String name;
    protected String code;
    protected double price;
    protected int stockQuantity;

    public Product(String name, String code, double price, int stockQuantity) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        stockQuantity -= quantity;
    }

    public abstract int getValidity();
}
