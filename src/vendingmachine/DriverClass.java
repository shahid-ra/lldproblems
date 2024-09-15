package vendingmachine;

import vendingmachine.models.PaymentType;
import vendingmachine.models.Product;
import vendingmachine.services.VendingMachineService;

public class DriverClass {
    public static void main(String[] args) throws IllegalAccessException {
        VendingMachineService _instance = VendingMachineService.getInstance(1, 10);
        Product[] products = {
                new Product("Pepsi", 10.0, 10),
                new Product("Cake", 20.0, 5),
                new Product("Chocolate", 50.0, 2),
        };
        for (Product p : products) {
            p.setId(_instance.addProduct(p));
        }
        _instance.buyProduct(
                products[0].getId(),
                5,
                50,
                PaymentType.NOTE
                );
        _instance.buyProduct(
                products[1].getId(),
                2,
                50,
                PaymentType.NOTE
        );
    }
}
