package vendingmachine.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Passbook {
    private final int machineId;
    private final List<Ledger> ledgers;
    private int lastInsertedLedgerId;
    private final Map<Integer, Integer> ledgerPositions;

    public Passbook(int machineId) {
        this.machineId = machineId;
        this.ledgers = new ArrayList<>();
        this.lastInsertedLedgerId = -1;
        this.ledgerPositions = new ConcurrentHashMap<>();
    }

    public void addLedger(Ledger ledger) {
        ledger.setId(++lastInsertedLedgerId);
        this.ledgers.add(ledger);
        this.ledgerPositions.put(lastInsertedLedgerId, ledgers.size() - 1);
    }

    public void removeLedger(Optional<Ledger> ledger) {
        if (ledger.isEmpty()) {
            return;
        }
        int ledgerPosition = this.ledgerPositions.getOrDefault(ledger.get().getId(), -1);
        if (ledgerPosition >= 0) {
            this.ledgers.remove(ledgerPosition);
        }
    }

    public List<Ledger> getLedgers() {
        return ledgers;
    }

    public int getMachineId() {
        return machineId;
    }

    public double getAvailableCash(PaymentType paymentType) {
        List<Ledger> credits = this.ledgers.stream().filter((ledger -> ledger.getPaymentType().equals(paymentType) && ledger.getOperation().equals(PaymentOperation.CREDIT))).toList();
        List<Ledger> debits = this.ledgers.stream().filter((ledger -> ledger.getPaymentType().equals(paymentType) && ledger.getOperation().equals(PaymentOperation.DEBIT))).toList();
        double totalAmount = 0;
        for (Ledger crediLedger : credits) {
            totalAmount += crediLedger.getAmount();
        }
        for (Ledger debiLedger : debits) {
            totalAmount -= debiLedger.getAmount();
        }
        return totalAmount;
    }
}