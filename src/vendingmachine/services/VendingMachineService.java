package vendingmachine.services;

import vendingmachine.models.*;

import java.util.Optional;

public class VendingMachineService {
    private static VendingMachineService vendingMachineService;
    private MachineState currentState;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private VendingMachineService(int machineId, int inventoryCapacity) {
        this.inventoryService = new InventoryService(inventoryCapacity);
        this.currentState = MachineState.IDLE;
        this.paymentService = new PaymentService(machineId);
    }

    public static synchronized VendingMachineService getInstance(int machineId, int inventoryCapacity) {
        if (vendingMachineService == null) {
            vendingMachineService = new VendingMachineService(machineId, inventoryCapacity);
        }
        return vendingMachineService;
    }

    public synchronized int addProduct(Product product) {
        return this.inventoryService.addProduct(product);
    }

    /**
     * - Check machine availability first, if it's in idle state only then this purchase can happen
     * - Check requested product availability
     * - If available
     * - Decrease inventory quantity
     * - Make entry into passbook
     * - Dispense remaining amount
     * - First get available coins
     * - Sum up all coins credit transactions
     * - Sum up all coins debit transactions
     * - Find the diff, and return diff as available coins
     * - if available coins is greater than 0 then dispense coins 1st and reduce remaining amount which needs to be dispensed.
     * - still dispense amount is greater than 0 then dispense notes if available
     * - dispense product
     */

    public synchronized void buyProduct(int productId, int quantity, int amount, PaymentType paymentType) throws IllegalAccessException {
        if (!this.isMachineAvailableToTakeOrders()) {
            throw new IllegalStateException("Machine is busy right now");
        }
        if (!this.inventoryService.areWeCapableOfServeRequestedRequest(productId, quantity, Optional.empty())) {
            throw new InternalError("Not enough capable of serve request");
        }
        this.currentState = MachineState.BUSY;
        Optional<Ledger> creditLedger = Optional.empty();
        try {
            this.inventoryService.decreaseQuantity(productId, quantity);
            double cartValue = this.inventoryService.getCartValue(productId, quantity);
            if (cartValue > amount) {
                throw new IllegalAccessException("Not enough amount to buy this cart");
            }
            double amountToDispense = amount - cartValue;
            if (amountToDispense > 0) {
                paymentService.dispenseRemainingAmount(amountToDispense, productId);
            }
            creditLedger = Optional.of(new Ledger(amount, productId, paymentType, PaymentOperation.CREDIT));
            this.paymentService.makePayment(creditLedger.get());
            this.currentState = MachineState.DISPENSE;
            dispenseProduct(productId);
        } catch (Exception e) {
            this.inventoryService.increaseQuantity(productId, quantity);
            this.paymentService.reversePayment(creditLedger);
            throw e;
        } finally {
            this.currentState = MachineState.IDLE;
            this.displayInventory();
            this.displayAvailableCash();
        }
    }

    private void dispenseProduct(int productId) {
        System.out.println("Dispensing product " + inventoryService.getInventory().getProductById((productId)).getName());
    }

    private synchronized boolean isMachineAvailableToTakeOrders() {
        return currentState.equals(MachineState.IDLE);
    }

    public void displayAvailableCash() {
        double availableCashCoins = paymentService.getAvailableCash(PaymentType.COIN);
        double availableCashInNotes = paymentService.getAvailableCash(PaymentType.NOTE);
        System.out.println("Total available cash in the system is - Coins: " + availableCashCoins + ", Notes: " + availableCashInNotes + ", Total: " + (availableCashCoins + availableCashInNotes));
    }

    public void displayInventory() {
        this.inventoryService.displayInventory();
    }
}
