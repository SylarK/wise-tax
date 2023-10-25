package pt.amado.wisetax.model;

import pt.amado.wisetax.model.entities.BillingAccount;

public class Counters {
    private long counterA;

    private long counterB;

    private long counterC;

    public Counters(BillingAccount account) {
        this.counterA = account.getCounterA();
        this.counterB = account.getCounterB();
        this.counterC = account.getCounterC();
    }
}
