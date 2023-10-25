package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.entities.Tariff;

@Component
public class Alpha1Processor implements ServiceProcessor {

    private static final double COST_LOCAL = 1.0;
    private static final double COST_ROAMING = 2.0;
    private static final int MAX_CALLS = 10;
    private static final double BONUS = 0.25;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_3 = 50;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_2 = 5;
    private static final long DISCOUNT_BUCKET_2 = 5;

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        double costPerMinute = request.isRoaming() ? COST_ROAMING : COST_LOCAL;

        if (request.isRoaming() && account.getCounterA() > MAX_CALLS && account.getBucket3() > DISCOUNT_MIN_BALANCE_BUCKET_3) {
            costPerMinute -= BONUS;
        }

        double callCost = costPerMinute * request.getRsu();

        if (request.isRoaming() && account.getBucket2() >= DISCOUNT_MIN_BALANCE_BUCKET_2) {
            account.setBucket2(account.getBucket2() - DISCOUNT_BUCKET_2);
        }

        if (request.isRoaming()) {
            account.setBucket3(account.getBucket3() + (long) (callCost * 100));
        } else {
            account.setBucket1(account.getBucket1() + (long) (callCost * 100));
        }
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.ALPHA_1;
    }

}
