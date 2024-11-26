public class ItemSale {
    private Product product;
    private int quantity;

    public ItemSale(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalValue() {
        return product.getPrice() * quantity;
    }

    public void removeQuantity(int qty) {
        quantity -= qty;
    }

    public void addQuantity(int qty) {
        quantity += qty;
    }
}
