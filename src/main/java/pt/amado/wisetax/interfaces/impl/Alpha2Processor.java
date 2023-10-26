package pt.amado.wisetax.interfaces.impl;

import org.springframework.stereotype.Component;
import pt.amado.wisetax.interfaces.ServiceProcessor;
import pt.amado.wisetax.model.entities.BillingAccount;
import pt.amado.wisetax.model.entities.ChargingRequest;
import pt.amado.wisetax.model.enums.Tariff;

import static pt.amado.wisetax.utils.DateUtils.isNightTime;

@Component
public class Alpha2Processor implements ServiceProcessor {

    private static final double COST_DAY = 0.5;
    private static final double COST_NIGHT = 0.25;
    private static final double DISCOUNT_MIN_BALANCE_BUCKET_2 = 15;
    private static final double DISCOUNT_BUCKET_2 = 0.05;
    private static final double DISCOUNT_BASED_ON_COUNTER = 0.2;

    @Override
    public void processRequest(BillingAccount account, ChargingRequest request) {
        double costPerMinute = isNightTime(request.getCreatedAt()) ? COST_NIGHT : COST_DAY;

        if(account.getCounterB() > 10)
            costPerMinute -= DISCOUNT_BASED_ON_COUNTER;

        if ((double) account.getBucket2() / 100 > DISCOUNT_MIN_BALANCE_BUCKET_2)
            costPerMinute -= DISCOUNT_BUCKET_2;


        double callCost = costPerMinute * request.getRsu();
        account.setBucket3(account.getBucket3() + (long) (callCost * 100));
    }

    @Override
    public Tariff getSupportedTariff() {
        return Tariff.ALPHA_2;
    }

}
