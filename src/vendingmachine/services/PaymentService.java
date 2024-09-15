package vendingmachine.services;

import vendingmachine.models.Ledger;
import vendingmachine.models.Passbook;
import vendingmachine.models.PaymentOperation;
import vendingmachine.models.PaymentType;

import java.util.Optional;

public class PaymentService {
    private final Passbook passbook;
    public PaymentService(int machineId) {
        this.passbook = new Passbook(machineId);
    }

    public void dispenseRemainingAmount(double amountToDispense, int productId) {
        if (!canDispenseExactChange(amountToDispense)) {
            throw new Error("Machine cannot provide exact change.");
        }

        Optional<Ledger> coinLedger = Optional.empty();
        Optional<Ledger> noteLedger = Optional.empty();
        try {
            coinLedger = dispenseWithCashType(amountToDispense, productId, PaymentType.COIN);
            if (coinLedger.isPresent()) {
                amountToDispense = coinLedger.get().getAmount();
            }

            if (amountToDispense > 0) {
                noteLedger = dispenseWithCashType(amountToDispense, productId, PaymentType.NOTE);
                if (noteLedger.isPresent()) {
                    amountToDispense = noteLedger.get().getAmount();
                }
            }

            System.out.println("Remaining amount dispensed successfully.");
        } catch (Exception e) {
            passbook.removeLedger(coinLedger);
            passbook.removeLedger(noteLedger);
        }
    }

    private boolean canDispenseExactChange(double amountToDispense) {
        double availableCashCoins = passbook.getAvailableCash(PaymentType.COIN);
        double availableCashInNotes = passbook.getAvailableCash(PaymentType.NOTE);

        double totalAvailableCash = availableCashCoins + availableCashInNotes;

        if (totalAvailableCash < amountToDispense) {
            return false;
        }

        return !(amountToDispense > availableCashCoins) || !((amountToDispense - availableCashCoins) > availableCashInNotes);// The machine can provide the exact change
    }

    private Optional<Ledger> dispenseWithCashType(double amountToDispense, int productId, PaymentType paymentType) {
        double availableCash = passbook.getAvailableCash(paymentType);
        if (availableCash <= 0) {
            return Optional.empty();
        }

        double amountToUse = Math.min(amountToDispense, availableCash);

        Ledger ledger = new Ledger(amountToUse, productId, paymentType, PaymentOperation.DEBIT);
        passbook.addLedger(ledger);

        System.out.println(amountToUse + " " + paymentType.name() + " used to dispense.");

        return Optional.of(ledger);
    }


    public void makePayment(Ledger ledger) {
        this.passbook.addLedger(ledger);
    }

    public void reversePayment(Optional<Ledger> ledger) {
        this.passbook.removeLedger(ledger);
    }

    public double getAvailableCash(PaymentType paymentType) {
        return this.passbook.getAvailableCash(paymentType);
    }
}
