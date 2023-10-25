package pt.amado.wisetax.interfaces.impl;

import pt.amado.wisetax.interfaces.ServiceAProcessor;
import pt.amado.wisetax.model.enitities.BillingAccount;
import pt.amado.wisetax.model.enitities.ChargingRequest;

import static pt.amado.wisetax.utils.DateUtils.isWeekday;

public class Alpha1Processor implements ServiceAProcessor {

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        if (isWeekday(request.getCreatedAt()) && account.getCounterA() < 100) {
            double costPerMinute = 1.0;
            if (request.isRoaming()) {
                costPerMinute = 2.0;
                if (account.getCounterA() > 10 && account.getBucket3() > 50) {
                    costPerMinute -= 0.25;
                }
            }
            double callCost = costPerMinute * request.getRsu();
            if (request.isRoaming() && account.getBucket2() >= 5) {
                account.setBucket2(account.getBucket2() - 5);
            }
            if (request.isRoaming()) {
                account.setBucket3(account.getBucket3() - (long) (callCost * 100));
            } else {
                account.setBucket1(account.getBucket1() - (long) (callCost * 100));
            }
        }
    }

}
