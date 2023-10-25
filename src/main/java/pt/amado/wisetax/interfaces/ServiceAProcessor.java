package pt.amado.wisetax.interfaces;

import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;

public interface ServiceAProcessor {

    void processRequest(BillingAccount account, ChargingRequest request);

}
