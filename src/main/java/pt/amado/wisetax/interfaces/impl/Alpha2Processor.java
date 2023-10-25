package pt.amado.wisetax.interfaces.impl;

import pt.amado.wisetax.interfaces.ServiceAProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;

import static pt.amado.wisetax.utils.DateUtils.isNighttime;

public class Alpha2Processor implements ServiceAProcessor {

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        if (account.getBucket2() > 10) {
            double costPerMinute = 0.5;
            if (isNighttime(request.getCreatedAt())) {
                costPerMinute = 0.25;
            }
            if (account.getBucket2() >= 15) {
                costPerMinute -= 0.05;
            }
            double callCost = costPerMinute * request.getRsu();
            account.setBucket3(account.getBucket2() - (long) (callCost * 100));
        }
    }

}
