package pt.amado.wisetax.interfaces;

import pt.amado.wisetax.model.enitities.BillingAccount;

public interface ServiceAProcessor {

    void processRequest(BillingAccount account, Object temp);

}
