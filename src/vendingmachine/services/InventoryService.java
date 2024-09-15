package vendingmachine.services;

import vendingmachine.models.Inventory;
import vendingmachine.models.Product;

import java.util.Optional;

public class InventoryService {
    private final Inventory inventory;
    public InventoryService(int capacity) {
        inventory = new Inventory(capacity);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean areWeCapableOfServeRequestedRequest(int productId, int requestedQuant, Optional<Product> _product) {
        Product product;
        product = _product.orElseGet(() -> inventory.getProductById(productId));
        if (product == null) {
            throw new Error("Requested product does not exist");
        }
        return product.getQuantity() >= requestedQuant;
    }

    public void decreaseQuantity(int productId, int quantity) {
        Product product = inventory.getProductById(productId);
        if (product == null) {
            throw new Error("Operation can't be fulfilled");
        }
        if (this.areWeCapableOfServeRequestedRequest(productId, quantity, Optional.of(product))) {
            product.setQuantity(product.getQuantity() - quantity);
        } else {
            throw new Error("Not enough stock");
        }
    }

    public void increaseQuantity(int productId, int quantity) {
        Product product = inventory.getProductById(productId);
        if (product == null) {
            throw new Error("Operation can't be fulfilled");
        }
        product.setQuantity(product.getQuantity() + quantity);
    }

    public double getCartValue(int productId, int quantity) {
        Product product = inventory.getProductById(productId);
        if (product == null) {
            throw new Error("Operation can't be fulfilled");
        }
        return product.getPrice() * quantity;
    }

    public synchronized void removeProduct(int productId) {
        this.inventory.removeProduct(productId);
    }

    public synchronized int addProduct(Product product) {
        return this.inventory.addProduct(product);
    }

    public void displayInventory() {
        this.inventory.displayInventory();
    }
}
