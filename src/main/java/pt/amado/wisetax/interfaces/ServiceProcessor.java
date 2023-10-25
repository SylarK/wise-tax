package pt.amado.wisetax.interfaces;

import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.entities.Tariff;

public interface ServiceProcessor {

    void processRequest(BillingAccount account, ChargingRequest request) throws RequestNotEligibleException;

    Tariff getSupportedTariff();

}
