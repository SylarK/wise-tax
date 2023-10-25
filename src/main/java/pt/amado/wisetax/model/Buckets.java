package pt.amado.wisetax.model;

import lombok.NoArgsConstructor;
import pt.amado.wisetax.model.entities.BillingAccount;

@NoArgsConstructor
public class Buckets {

    private long bucket1;

    private long bucket2;

    private long bucket3;

    public Buckets(BillingAccount account) {
        this.bucket1 = account.getBucket1();
        this.bucket2 = account.getBucket2();
        this.bucket3 = account.getBucket3();
    }

}
