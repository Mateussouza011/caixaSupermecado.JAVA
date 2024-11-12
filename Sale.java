import java.util.ArrayList;
import java.util.List;

public class Sale {
    private List<ItemSale> items;
    private double totalValue;

    public Sale() {
        items = new ArrayList<>();
        totalValue = 0.0;
    }

    public void addItem(Product product, int quantity) {
        ItemSale item = new ItemSale(product, quantity);
        items.add(item);
        totalValue += item.getTotalValue();
        product.removeStock(quantity);
    }

    public void removeItem(int index, int quantity) {
        ItemSale item = items.get(index);
        if (quantity <= item.getQuantity()) {
            item.removeQuantity(quantity);
            item.getProduct().addStock(quantity);
            totalValue -= item.getProduct().getPrice() * quantity;
            if (item.getQuantity() == 0) {
                items.remove(index);
            }
        } else {
            System.out.println("Quantidade inválida.");
        }
    }

    public List<ItemSale> getItems() {
        return items;
    }

    public double getTotalValue() {
        return totalValue;
    }
}
