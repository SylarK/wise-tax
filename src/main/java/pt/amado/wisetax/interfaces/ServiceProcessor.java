package pt.amado.wisetax.interfaces;

import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

public interface ServiceProcessor {

    void processRequest(BillingAccount account, ChargingRequest request) throws RequestNotEligibleException;

    Tariff getSupportedTariff();

}
