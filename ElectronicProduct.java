public class ElectronicProduct extends Product {
    private int warranty; // in days

    public ElectronicProduct(String name, String code, double price, int stockQuantity, int warranty) {
        super(name, code, price, stockQuantity);
        this.warranty = warranty;
    }

    @Override
    public int getValidity() {
        return warranty;
    }
}
