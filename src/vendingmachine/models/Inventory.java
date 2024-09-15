package vendingmachine.models;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private final Map<Integer, Product> productMap;
    private int lastInsertedId;
    private final int inventoryCanAccommodateItemCount;
    private int inventorySize;
    public Inventory(int inventoryCapacity) {
        this.productMap = new ConcurrentHashMap<>();
        this.lastInsertedId = -1;
        this.inventoryCanAccommodateItemCount = inventoryCapacity;
        this.inventorySize = 0;
    }

    public synchronized int addProduct(Product product) {
        if (inventorySize >= inventoryCanAccommodateItemCount) {
            throw new IllegalStateException("Inventory is full");
        }
        product.setId(++lastInsertedId);
        this.productMap.put(lastInsertedId, product);
        this.inventorySize++;
        return lastInsertedId;
    }

    public synchronized void removeProduct(int productId) {
        if (inventorySize <= 0) {
            throw new IllegalStateException("Inventory is full");
        }
        this.productMap.remove(productId);
        this.inventorySize--;
    }

    public synchronized Product getProductById(int id) {
        return this.productMap.get(id);
    }

    public void displayInventory() {
        if (this.inventorySize == 0) {
            System.out.println("Inventory is empty");
        }
        for (Map.Entry<Integer, Product> entry : this.productMap.entrySet()) {
            System.out.println(entry.getValue().getName() + " available quantity: " + entry.getValue().getQuantity() + ", and price is: "+ entry.getValue().getPrice());
        }
    }
}
