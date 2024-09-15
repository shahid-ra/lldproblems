package vendingmachine.models;

public class Ledger {
    private int id;
    private final double amount;
    private final int productId;
    private final PaymentType paymentType;
    private final PaymentOperation operation;
    public Ledger(double amount, int productId, PaymentType paymentType, PaymentOperation operation) {
        this.amount = amount;
        this.productId = productId;
        this.paymentType = paymentType;
        this.operation = operation;
    }

    public PaymentOperation getOperation() {
        return operation;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public int getProductId() {
        return productId;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
