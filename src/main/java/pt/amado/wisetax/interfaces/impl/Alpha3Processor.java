package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.interfaces.ServiceAProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import static pt.amado.wisetax.utils.DateUtils.isWeekend;

@Component
public class Alpha3Processor implements ServiceAProcessor {

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        if (request.isRoaming() && account.getBucket2() > 10) {
            double costPerMinute = 1.0;
            if (isWeekend(request.getCreatedAt())) {
                costPerMinute = 0.25;
            }
            if (account.getCounterC() > 10) {
                costPerMinute -= 0.2;
            }
            if (account.getBucket3() >= 15) {
                costPerMinute -= 0.05;
            }
            double callCost = costPerMinute * request.getRsu();
            account.setBucket3(account.getBucket3() - (long) (callCost * 100));
        }
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.ALPHA_3;
    }

}
