public class FoodProduct extends Product {
    private int validity; // in days

    public FoodProduct(String name, String code, double price, int stockQuantity, int validity) {
        super(name, code, price, stockQuantity);
        this.validity = validity;
    }

    @Override
    public int getValidity() {
        return validity;
    }
}
