package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import static pt.amado.wisetax.utils.DateUtils.isWeekend;

@Component
public class Alpha3Processor implements ServiceProcessor {

    private static final double COST_WEEKDAY = 1.0;
    private static final double COST_WEEKEND = 0.25;
    private static final double DISCOUNT_MIN_COUNTER_C = 10;
    private static final double DISCOUNT_COUNTER_C = 0.2;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_3 = 15;
    private static final double DISCOUNT_BUCKET_3 = 0.05;

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        double costPerMinute = isWeekend(request.getCreatedAt()) ? COST_WEEKEND : COST_WEEKDAY;

        if (account.getCounterC() > DISCOUNT_MIN_COUNTER_C) {
            costPerMinute -= DISCOUNT_COUNTER_C;
        }

        if (account.getBucket3() >= DISCOUNT_MIN_BALANCE_BUCKET_3) {
            costPerMinute -= DISCOUNT_BUCKET_3;
        }

        double callCost = costPerMinute * request.getRsu();
        account.setBucket3(account.getBucket3() + (long) callCost);
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.ALPHA_3;
    }

}
