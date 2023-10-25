package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.exception.RequestNotEligibleException;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import static pt.amado.wisetax.utils.DateUtils.isNightTime;

@Component
public class Alpha2Processor implements ServiceProcessor {

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        double costPerMinute = 0.5;
        if (isNightTime(request.getCreatedAt())) {
            costPerMinute = 0.25;
        }
        if (account.getBucket2() >= 15) {
            costPerMinute -= 0.05;
        }
        double callCost = costPerMinute * request.getRsu();
        account.setBucket3(account.getBucket2() + (long) (callCost * 100));
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.ALPHA_2;
    }

}
