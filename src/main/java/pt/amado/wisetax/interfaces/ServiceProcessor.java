package pt.amado.wisetax.interfaces;

import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

public interface ServiceProcessor {

    /**
     * Processes a charging request and performs any necessary calculations or actions.
     *
     * @param account The BillingAccount associated with the request.
     * @param request The ChargingRequest to be processed.
     */
    void processRequest(BillingAccount account, ChargingRequest request);

    /**
     * Retrieves the tariff supported by this service processor.
     * Used to create a kind of map in the main flow, to link each tariff to your processor
     *
     * @return The supported Tariff.
     */
    Tariff getSupportedTariff();

}
